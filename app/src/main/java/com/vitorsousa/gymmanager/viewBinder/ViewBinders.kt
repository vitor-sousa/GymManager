package com.vitorsousa.gymmanager.viewBinder

import android.net.Uri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

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