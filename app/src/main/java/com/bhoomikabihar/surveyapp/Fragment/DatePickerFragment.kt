package com.bhoomikabihar.surveyapp.Fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickerFragment(activity: Activity) : DialogFragment() {


    @RequiresApi(Build.VERSION_CODES.O)
    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        //following line to restrict future date selection

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            activity as OnDateSetListener?,
            year, month, day
        )
        val cf = Calendar.getInstance();
        cf.set(2011, 0, 0);
        datePickerDialog.datePicker.maxDate = cf.timeInMillis
        //System.currentTimeMillis()
//        datePickerDialog.show()
        return datePickerDialog
    }
}