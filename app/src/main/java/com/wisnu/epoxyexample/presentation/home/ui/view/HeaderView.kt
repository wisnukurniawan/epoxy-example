package com.wisnu.epoxyexample.presentation.home.ui.view

import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.wisnu.epoxyexample.R
import com.wisnu.epoxyexample.util.KotlinEpoxyHolder


@EpoxyModelClass(layout = R.layout.layout_header)
abstract class HeaderView : EpoxyModelWithHolder<HeaderView.Holder>() {

    @EpoxyAttribute
    lateinit var title: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.title.text = title
    }

    inner class Holder : KotlinEpoxyHolder() {
        val title by bind<AppCompatTextView>(R.id.header_title)
    }
}