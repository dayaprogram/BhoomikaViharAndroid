package com.example.dbtagri.ViewAdaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bhoomikavihar.surveyapp.R


class DashboardPagerAdapter(private var context: Context) : PagerAdapter() {
    private var inflater: LayoutInflater? = null
    private val images = arrayOf(
        R.drawable.bihar_logo,
        R.drawable.bihar_logo,
        R.drawable.bihar_logo,
        R.drawable.bihar_logo
    )

    fun ImageAdapter(context: Context) {
        this.context = context
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }


    override fun getCount(): Int {

        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
        imageView.setImageResource(images.get(position))
        container.addView(imageView, 0)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView?)
    }


}