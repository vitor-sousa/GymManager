package com.vitorsousa.gymmanager.viewBinder

import android.net.Uri
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import de.hdodenhof.circleimageview.CircleImageView
import java.time.LocalDate
import java.time.LocalDateTime
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

@BindingAdapter("dateToFormat")
fun TextView.formatToLocalDate(timestamp: Timestamp?) {
    timestamp?.let {
        val date = timestamp.toDate().toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime()
        val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        this.text = date.format(dateTimeFormatter)
    }
}