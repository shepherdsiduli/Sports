package co.shepherd.sports.ui.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import co.shepherd.sports.core.BaseAdapter
import co.shepherd.sports.databinding.EventItemViewBinding
import co.shepherd.sports.domain.model.Event
import co.shepherd.sports.ui.MediaPlayerActivity
import co.shepherd.sports.utils.extensions.isNetworkAvailable

class EventsAdapter : BaseAdapter<Event>(diffCallback) {

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
                if (it.videoUrl != null && isNetworkAvailable(mBinding.cardView.context)) {
                    mBinding.cardView.context.startActivity(
                        MediaPlayerActivity.getStartIntent(
                            mBinding.cardView.context,
                            it.videoUrl,
                            it.title
                        )
                    )
                }
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
