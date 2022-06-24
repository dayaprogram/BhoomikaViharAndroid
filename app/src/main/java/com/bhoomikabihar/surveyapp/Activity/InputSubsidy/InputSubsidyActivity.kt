package com.dbtagri.dbtagriverify.Activity.InputSubsidy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bhoomikabihar.surveyapp.R
import com.dbtagri.dbtagriverify.Activity.InputSubsidy.InputSubsidyFragments.InputSubsidyFarmerListFragment

class InputSubsidyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_subsidy_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, InputSubsidyFarmerListFragment.newInstance())
                .commitNow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}