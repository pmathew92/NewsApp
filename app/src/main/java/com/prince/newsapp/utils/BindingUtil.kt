package com.prince.newsapp.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.prince.newsapp.GlideApp
import com.prince.newsapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * set the views visibility to View.GONE if value is fale, else View.VISIBLE
 *
 * @param view  View
 * @param value hide true or false
 */
@BindingAdapter("gone")
fun setGone(view: View, value: Boolean?) {
    if (value != null)
        view.visibility = if (value) View.VISIBLE else View.GONE
}


/**
 * set the views visibility to View.INVISIBLE if value is false, else View.VISIBLE
 *
 * @param view  View
 * @param value hide true or false
 */
@BindingAdapter("invisible")
fun setInvisible(view: View, value: Boolean?) {
    if (value != null)
        view.visibility = if (value) View.VISIBLE else View.INVISIBLE
}


/**
 * Load an image url to the specified view
 *
 * @param imageView
 * @param imageUrl
 */
@BindingAdapter("imageUrl")
fun displayImage(imageView: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        GlideApp.with(imageView.context)
            .load(imageUrl)
            .placeholder(imageView.context.getDrawable(R.drawable.placeholder))
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }
}


/**
 * Method to display time of news
 */
@BindingAdapter("dateText")
fun getHoursDiff(view: TextView, date: String?) {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val dtf = SimpleDateFormat(pattern, Locale.getDefault())
    var dateTime: Date? = null
    try {
        dateTime = dtf.parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val currentDate = Date()
    val diff = currentDate.time - dateTime!!.time
    view.text = " ${TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)} hrs ago"
}