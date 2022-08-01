package co.shepherd.sports.ui.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import co.shepherd.sports.core.BaseAdapter
import co.shepherd.sports.databinding.EventItemViewBinding
import co.shepherd.sports.domain.model.Event

class EventsAdapter (
    //private val callBack: (Event, View, View, View, View, View) -> Unit
    private val callBack: (Event, View) -> Unit
) : BaseAdapter<Event>(diffCallback) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val mBinding = EventItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewModel = EventViewModel()
        mBinding.viewModel = viewModel

        mBinding.cardView.setOnClickListener {
            mBinding.viewModel?.item?.get()?.let {
                callBack(
                    it,
                    mBinding.cardView
//                    mBinding.imageViewForecastIcon,
//                    mBinding.textViewDayOfWeek,
//                    mBinding.textViewTemp,
//                    mBinding.linearLayoutTempMaxMin
                )
            }
        }
        return mBinding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        (binding as EventItemViewBinding).viewModel?.item?.set(getItem(position))
        binding.executePendingBindings()
    }
}

val diffCallback = object : DiffUtil.ItemCallback<Event>() {
    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
        oldItem.id == newItem.id
}
