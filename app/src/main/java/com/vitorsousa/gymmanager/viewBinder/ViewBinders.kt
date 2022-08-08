package com.vitorsousa.gymmanager.viewBinder

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.vitorsousa.gymmanager.R
import de.hdodenhof.circleimageview.CircleImageView
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@BindingAdapter("srcUrl")
fun CircleImageView.loadScrUrl(uri: Uri?){
    uri?.let {
        Glide
            .with(this)
            .load(uri)
            .fitCenter()
            .into(this)
    }
}

@BindingAdapter("srcImage")
fun ImageView.loadScrUrl(uri: String?){
    uri?.let {
        Glide
            .with(this)
            .load(uri)
            .centerCrop()
            .error(R.mipmap.ic_launcher_foreground)
            .into(this)
    }
}

@BindingAdapter("dateToFormat")
fun TextView.formatToLocalDate(timestamp: Timestamp?) {
    timestamp?.let {
        val date = timestamp.toDate().toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime()
        val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        this.text = date.format(dateTimeFormatter)
    }
}

@BindingAdapter("name")
fun TextView.displayUserName(name: String?) {
    if (name.isNullOrBlank())
        this.text = context?.getString(R.string.anonymous_user)
    else
        this.text = name
}