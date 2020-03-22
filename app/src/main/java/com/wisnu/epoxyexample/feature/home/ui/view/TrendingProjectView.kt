package com.wisnu.epoxyexample.feature.home.ui.view

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.wisnu.epoxyexample.R
import com.wisnu.epoxyexample.feature.home.ui.model.TrendingProjectUiModel
import com.wisnu.epoxyexample.util.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.layout_trending_project_item)
abstract class TrendingProjectView(
    private val context: Context
) : EpoxyModelWithHolder<TrendingProjectView.Holder>() {

    @EpoxyAttribute
    lateinit var model: TrendingProjectUiModel

    override fun bind(holder: Holder) {
        super.bind(holder)
        Log.d("ProjectView", "bind $model")
        holder.nameTv.text = model.name
        holder.descriptionTv.text = model.description
        holder.starsTv.text = context.getString(R.string.profile_item_stars, model.stars.toString())
        holder.languageTv.text = context.getString(R.string.profile_item_language, model.language)
    }

    inner class Holder : KotlinEpoxyHolder() {
        val nameTv by bind<TextView>(R.id.project_name_tv)
        val descriptionTv by bind<TextView>(R.id.project_description_tv)
        val starsTv by bind<TextView>(R.id.project_stars_tv)
        val languageTv by bind<TextView>(R.id.project_language_tv)
    }

}