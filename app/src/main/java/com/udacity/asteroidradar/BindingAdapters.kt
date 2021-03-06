package com.udacity.asteroidradar

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("pictureOfDay")
fun bindingPictureOfDay(imageView: ImageView, picture: PictureOfDay?) {
    imageView.setImageResource(R.drawable.placeholder_picture_of_day)
    imageView.contentDescription = imageView.context.getString(R.string.picture_not_available)
    picture?.let {
        if (picture.mediaType == "image") {
            val uri = Uri.parse(picture.url)
            Picasso.with(imageView.context).load(uri).into(imageView)
            imageView.contentDescription = picture.title
        }
    }
}

@BindingAdapter("contentDescriptionImage")
fun bindingContentDescriptionImage(imageView: ImageView, asteroid: Asteroid?){
    asteroid?.let {
        imageView.contentDescription = when (asteroid.isPotentiallyHazardous) {
            true -> imageView.context.getString(R.string.hazardous_content_desc)
            false -> imageView.context.getString(R.string.not_hazardous_content_desc)
        }
    }
}
