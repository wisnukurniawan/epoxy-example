package com.wisnu.epoxyexample.feature.home.ui.view

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.wisnu.epoxyexample.R
import com.wisnu.epoxyexample.feature.home.domain.model.ProfileUiModel
import com.wisnu.epoxyexample.util.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.layout_profile_item)
abstract class ProfileView(
    private val context: Context
) : EpoxyModelWithHolder<ProfileView.Holder>() {

  @EpoxyAttribute
  lateinit var model: ProfileUiModel

  override fun bind(holder: Holder) {
    super.bind(holder)
    holder.nameTv.text = model.name
    holder.bioTv.text = model.bio
    holder.blogTv.text = model.blog
    holder.locationTv.text = model.location
    Glide
        .with(context)
        .load(model.avatarUrl)
        .into(holder.profileIv)
  }

  inner class Holder : KotlinEpoxyHolder() {
    val profileIv by bind<ImageView>(R.id.profile_iv)
    val nameTv by bind<TextView>(R.id.profile_name_tv)
    val locationTv by bind<TextView>(R.id.profile_location_tv)
    val bioTv by bind<TextView>(R.id.profile_bio_tv)
    val blogTv by bind<TextView>(R.id.profile_blog_tv)
  }

}