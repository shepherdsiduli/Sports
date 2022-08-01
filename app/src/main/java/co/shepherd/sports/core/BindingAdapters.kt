package co.shepherd.sports.core

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import co.shepherd.sports.R
import co.shepherd.sports.utils.extensions.getEventDate
import co.shepherd.sports.utils.extensions.hide
import co.shepherd.sports.utils.extensions.show
import com.squareup.picasso.Picasso

@BindingAdapter("app:visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    if (isVisible) {
        view.show()
    } else {
        view.hide()
    }
}

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.download_error)
        .into(view)
}

@BindingAdapter("app:loadDate")
fun loadDate(view: TextView, data: String) {
    view.text = getEventDate(data)
}

@BindingAdapter("app:setErrorView")
fun setErrorView(view: View, viewState: BaseViewState?) {
    if (viewState?.shouldShowErrorMessage() == true) {
        view.show()
    } else {
        view.hide()
    }

    view.setOnClickListener { view.hide() }
}

@BindingAdapter("app:setErrorText")
fun setErrorText(view: TextView, viewState: BaseViewState?) {
    if (viewState?.shouldShowErrorMessage() == true) {
        view.text = viewState.getErrorMessage()
    } else {
        view.text = view.context.getString(R.string.unexpected_exception)
    }
}