package com.wisnu.epoxyexample.util

import android.view.View
import com.airbnb.epoxy.CarouselModelBuilder
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A pattern for easier view binding with an [EpoxyHolder]
 *
 * See [com.airbnb.epoxy.kotlinsample.models.ItemEpoxyHolder] for a usage example.
 */
abstract class KotlinEpoxyHolder : EpoxyHolder() {
  private lateinit var view: View

  override fun bindView(itemView: View) {
    view = itemView
  }

  protected fun <V : View> bind(id: Int): ReadOnlyProperty<KotlinEpoxyHolder, V> =
      Lazy { holder: KotlinEpoxyHolder, prop ->
        holder.view.findViewById(id) as V?
            ?: throw IllegalStateException("View ID $id for '${prop.name}' not found.")
      }

  /**
   * Taken from Kotterknife.
   * https://github.com/JakeWharton/kotterknife
   */
  private class Lazy<V>(
      private val initializer: (KotlinEpoxyHolder, KProperty<*>) -> V
  ) : ReadOnlyProperty<KotlinEpoxyHolder, V> {
    private object EMPTY

    private var value: Any? = EMPTY

    override fun getValue(thisRef: KotlinEpoxyHolder, property: KProperty<*>): V {
      if (value == EMPTY) {
        value = initializer(thisRef, property)
      }
      @Suppress("UNCHECKED_CAST")
      return value as V
    }
  }
}

/** Add models to a CarouselModel_ by transforming a list of items into EpoxyModels.
 *
 * @param items The items to transform to models
 * @param modelBuilder A function that take an item and returns a new EpoxyModel for that item.
 */
inline fun <T> CarouselModelBuilder.withModelsFrom(
  items: List<T>,
  modelBuilder: (T) -> EpoxyModel<*>
) {
  models(items.map { modelBuilder(it) })
}