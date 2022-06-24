package com.bhoomikabihar.surveyapp.Activity.InputSubsidy.InputSubsidyFragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bhoomikabihar.surveyapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class FarmerPictureFragment : Fragment() {
    private var imgCapture: ImageView? = null
    private val Image_Capture_Code = 1
    private val PERMISSION_REQUEST_CODE = 200
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123
    private var imageUri: Uri? = null
    private var mCameraFileName: String? = null

    private lateinit var viewModel: InputSubsidyViewModel

    companion object {
        fun newInstance() = FarmerPictureFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_farmer_picture, container, false)
        viewModel = InputSubsidyViewModel(requireActivity().application)

        imgCapture = root.findViewById<ImageView>(R.id.imagefarmer)
        var regNo =
            requireActivity().findViewById<TextView>(R.id.textRegNoVerify).text.toString()

        var appId =
            requireActivity().findViewById<TextView>(R.id.textApplicationId).text.toString()
        viewImage(regNo)
        val submitPicture = root.findViewById<Button>(R.id.submitPicture)
        submitPicture.setOnClickListener {


            if (mCameraFileName == null) {
                Toast.makeText(
                    requireContext(),
                    "Farmer Picture Required", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                if (mCameraFileName == "") {
                    Toast.makeText(
                        requireContext(),
                        "Farmer Picture Required", Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            }


            var formatted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                current.format(formatter)
            } else {
                var date = Date()
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                formatter.format(date)
            }

            viewModel.setPictureDetails(mCameraFileName!!, formatted, regNo, appId)

            val fragment: Fragment = NeighbourFarmerDetailsFragment.newInstance()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnTakePhoto =
            root.findViewById<View>(R.id.floatingActionButtonTakePic) as FloatingActionButton

        btnTakePhoto.setOnClickListener {
            if (checkPermissionREAD_EXTERNAL_STORAGE(requireContext())) {


                val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())
                val intent = Intent()
                intent.action = MediaStore.ACTION_IMAGE_CAPTURE

                val newPicFile: String = "$regNo.jpeg"
                val path =
                    File(Environment.getExternalStorageDirectory().toString(), "Pictures/DBTAgri/")
                if (!path.exists()) {
                    path.mkdirs()
                }
                val outFile = File(path, newPicFile)
                mCameraFileName = outFile.toString()
                val outUri = Uri.fromFile(outFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri)
                startActivityForResult(intent, Image_Capture_Code)
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var regNo =
            requireActivity().findViewById<TextView>(R.id.textRegNoVerify).text.toString()
        viewImage(regNo)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Image_Capture_Code && resultCode == AppCompatActivity.RESULT_OK) {
            Log.d("CameraDemo", "Pic saved")
            if (data != null) {
                imageUri = data.data
                imgCapture!!.setImageURI(imageUri)
                imgCapture!!.visibility = View.VISIBLE
            }

            if (imageUri == null && mCameraFileName != null) {
                imageUri = Uri.fromFile(File(mCameraFileName))
                //  imgCapture!!.setImageURI(imageUri)

                val imageBitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    imageUri
                )
                val scaledImage = scaleBitmap(imageBitmap!!, 230, 460)
                imgCapture!!.setImageBitmap(scaledImage)
                //  imgCapture!!.setImageBitmap(thumbnail);

                imgCapture!!.visibility = View.VISIBLE
            }
            val file = File(mCameraFileName)
            if (!file.exists()) {
                file.mkdir()
            }
        }
    }

    private fun viewImage(regNo: String) {
        try {
            val path =
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator.toString() + "Pictures/DBTAgri/$regNo.jpeg"
            val pictureFile = File(path)

            if (pictureFile.exists()) {
                var loadedBitmap = BitmapFactory.decodeFile(path)
                if (loadedBitmap != null) {
                    val scaledImage = scaleBitmap(loadedBitmap, 230, 460)
                    imgCapture!!.setImageBitmap(scaledImage)
                    mCameraFileName = pictureFile.toString()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun scaleBitmap(bm: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap? {
        var bm: Bitmap = bm
        var width: Int = bm.width
        var height: Int = bm.height
        if (width > height) {
            // landscape
            val ratio = width / maxWidth
            width = maxWidth
            height /= ratio
        } else if (height > width) {
            // portrait
            val ratio = height / maxHeight
            height = maxHeight
            width /= ratio
        } else {
            // square
            height = maxHeight
            width = maxWidth
        }
        bm = Bitmap.createScaledBitmap(bm, width, height, true)
        return bm
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do your stuff
            } else {
                Toast.makeText(
                    requireContext(), "GET_ACCOUNTS Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> super.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        }
    }

    private fun checkPermissionREAD_EXTERNAL_STORAGE(
        context: Context?
    ): Boolean {
        val currentAPIVersion: Int = Build.VERSION.SDK_INT
        return if (currentAPIVersion >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (context as Activity?)!!,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    showDialog(
                        "External storage", context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                } else {
                    ActivityCompat
                        .requestPermissions(
                            requireActivity(),
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                }
                false
            } else {
                true
            }
        } else {
            true
        }
    }

    private fun showDialog(msg: String, context: Context?, permission: String) {
        val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle("Permission necessary")
        alertBuilder.setMessage("$msg permission is necessary")
        alertBuilder.setPositiveButton(
            android.R.string.ok
        ) { _, _ ->
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf<String>(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), PERMISSION_REQUEST_CODE
            )
        }
        val alert: AlertDialog = alertBuilder.create()
        alert.show()
    }
}