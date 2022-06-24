package com.bhoomikabihar.surveyapp.ViewAdaptor


interface RecyclerViewEditTextInterface {
    fun onTextChanged(
        position: Int, text: String
    )

    fun onItemSelected(
        position: Int, text: String
    )
}