package com.bhoomikabihar.surveyapp.Activity.MainFragments.Sync

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bhoomikabihar.surveyapp.R

class SyncFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel = SlideshowViewModel()
        // ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sync, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}