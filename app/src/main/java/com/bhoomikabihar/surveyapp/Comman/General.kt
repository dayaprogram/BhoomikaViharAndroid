package com.bhoomikabihar.surveyapp.Comman

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream


class General {

//    fun saveBitmapToFile(file: File): File? {
//        return try {
//
//            // BitmapFactory options to downsize the image
//            val o = BitmapFactory.Options()
//            o.inJustDecodeBounds = true
//            o.inSampleSize = 6
//            // factor of downsizing the image
//            var inputStream = FileInputStream(file)
//            //Bitmap selectedBitmap = null;
//            BitmapFactory.decodeStream(inputStream, null, o)
//            inputStream.close()
//
//            // The new size we want to scale to
//            val REQUIRED_SIZE = 75
//
//            // Find the correct scale value. It should be the power of 2.
//            var scale = 1
//            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
//                o.outHeight / scale / 2 >= REQUIRED_SIZE
//            ) {
//                scale *= 2
//            }
//            val o2 = BitmapFactory.Options()
//            o2.inSampleSize = scale
//            inputStream = FileInputStream(file)
//            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
//            inputStream.close()
//
//            // here i override the original image file
//            file.createNewFile()
////            val outputStream = FileOutputStream(file)
////            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//            file
//        } catch (e: Exception) {
//            null
//        }
//    }
//
//    fun getBase64FromPath(path: String?): String? {
//        var base64 = ""
//        try {
//            val file = File(path)
//            var beforeLength = file.length()
//            var lowImg = saveBitmapToFile(file)
//            var afterLength = lowImg?.length()
//            val buffer = ByteArray(lowImg?.length() as Int + 100)
//            val length: Int = FileInputStream(file).read(buffer)
//            base64 = Base64.encodeToString(
//                buffer, 0, length,
//                Base64.DEFAULT
//            )
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return base64
//    }


    private fun scaleBitmap(bm: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap? {
        var bm: Bitmap = bm
        var width: Int = bm.getWidth()
        var height: Int = bm.getHeight()
        if (width > height) {
            // landscape
            val ratio = width / maxWidth
            width = maxWidth
            height = height / ratio
        } else if (height > width) {
            // portrait
            val ratio = height / maxHeight
            height = maxHeight
            width = width / ratio
        } else {
            // square
            height = maxHeight
            width = maxWidth
        }
        bm = Bitmap.createScaledBitmap(bm, width, height, true)
        return bm
    }

    fun getBitmap(path: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val f = File(path)
            val options = BitmapFactory.Options()
            // options.inPreferredConfig = Bitmap.Config.ARGB_8888
            bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)
            // image.setImageBitmap(bitmap)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return scaleBitmap(bitmap!!, 500, 800)
    }

    fun encodeTobase64(image: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.NO_WRAP)
    }

}