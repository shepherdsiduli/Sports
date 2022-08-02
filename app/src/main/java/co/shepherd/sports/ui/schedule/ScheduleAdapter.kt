package co.shepherd.sports.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import co.shepherd.sports.core.BaseAdapter
import co.shepherd.sports.databinding.ScheduleItemViewBinding
import co.shepherd.sports.domain.model.Event
import co.shepherd.sports.ui.events.EventViewModel

class ScheduleAdapter : BaseAdapter<Event>(diffCallback) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val mBinding = ScheduleItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewModel = EventViewModel()
        mBinding.viewModel = viewModel
        return mBinding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        (binding as ScheduleItemViewBinding).viewModel?.item?.set(getItem(position))
        binding.executePendingBindings()
    }
}

val diffCallback = object : DiffUtil.ItemCallback<Event>() {
    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
        oldItem.id == newItem.id
}
