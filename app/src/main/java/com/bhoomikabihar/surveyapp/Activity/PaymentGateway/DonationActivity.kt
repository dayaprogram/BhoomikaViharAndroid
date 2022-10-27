package com.bhoomikabihar.surveyapp.Activity.PaymentGateway

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.bhoomikabihar.surveyapp.R
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class DonationActivity : AppCompatActivity(), PaymentResultWithDataListener, ExternalWalletListener,
    DialogInterface.OnClickListener {
    val TAG: String = DonationActivity::class.toString()
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)
        val toolbar: Toolbar = findViewById(R.id.title_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);


        Checkout.preload(applicationContext)
        alertDialogBuilder = AlertDialog.Builder(this@DonationActivity)
        alertDialogBuilder.setTitle("Payment Result")
        alertDialogBuilder.setCancelable(true)
        alertDialogBuilder.setPositiveButton("Ok", this)
        val button: Button = findViewById(R.id.btn_pay)
        button.setOnClickListener {
            val donationAmt = findViewById<EditText>(R.id.donationAmt)
            val mobileno = findViewById<EditText>(R.id.mobileno)
            val email = findViewById<EditText>(R.id.email)
            val donationAmtInt = donationAmt.text.toString().toInt()
            if (donationAmt.text == null) {
                return@setOnClickListener
            }
            if (mobileno.text == null) {
                return@setOnClickListener
            }
            if (email.text == null) {
                return@setOnClickListener
            }
            startPayment(donationAmtInt, mobileno.text.toString(), email.text.toString())
        }
    }

    private fun startPayment(donationAmtInt: Int, mobileno: String, email: String) {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()


        val etCustomOptions = findViewById<EditText>(R.id.et_custom_options)

        co.setKeyID("rzp_live_L2ZWHolf7icWX0")

        try {
            var options = JSONObject()
            if (!TextUtils.isEmpty(etCustomOptions.text.toString())) {
                options = JSONObject(etCustomOptions.text.toString())
            } else {
                options.put("name", "bhoomika vihar")
                options.put("description", "Support Marginalized Girls/Women (Bihar)")
                //You can omit the image option to fetch the image from dashboard
                options.put("image", "https://cdn.razorpay.com/logos/JrXzfqmCD1MbE7_large.png")
                options.put("currency", "INR")
                options.put("amount", donationAmtInt * 100)
                options.put("send_sms_hash", true);

                val prefill = JSONObject()
                prefill.put("email", email)
                prefill.put("contact", mobileno)

                options.put("prefill", prefill)
            }

            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        try {
            alertDialogBuilder.setMessage("Payment Successful : Payment ID: $p0\nPayment Data: ${p1?.data}")
            alertDialogBuilder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        try {
            alertDialogBuilder.setMessage("Payment Failed : Payment Data: ${p2?.data}")
            alertDialogBuilder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        try {
            alertDialogBuilder.setMessage("External wallet was selected : Payment Data: ${p1?.data}")
            alertDialogBuilder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}