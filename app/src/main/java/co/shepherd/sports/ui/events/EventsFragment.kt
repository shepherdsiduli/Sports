package co.shepherd.sports.ui.events

import android.transition.TransitionInflater
import androidx.recyclerview.widget.LinearLayoutManager
import co.shepherd.sports.R
import co.shepherd.sports.core.BaseFragment
import co.shepherd.sports.databinding.FragmentEventsBinding
import co.shepherd.sports.domain.model.Event
import co.shepherd.sports.domain.usecase.EventsUseCase
import co.shepherd.sports.ui.MainActivity
import co.shepherd.sports.utils.extensions.isNetworkAvailable
import co.shepherd.sports.utils.extensions.observeWith
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : BaseFragment<EventsViewModel, FragmentEventsBinding>(
    R.layout.fragment_events,
    EventsViewModel::class.java,
) {

    override fun init() {
        super.init()
        initEventsAdapter()
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(
            android.R.transition.move
        )

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.viewModel?.setEventsParams(
                EventsUseCase.EventsParams(
                    isNetworkAvailable(requireContext()),
                )
            )
        }

        binding.viewModel?.setEventsParams(
            EventsUseCase.EventsParams(
                isNetworkAvailable(requireContext()),
            )
        )

        binding.viewModel?.getEventsViewState()?.observeWith(
            viewLifecycleOwner
        ) {
            with(binding) {
                viewState = it
                it.data?.events?.let { events -> initEvents(events.events) }
                (activity as MainActivity).viewModel.toolbarTitle.set(
                    "Events"
                )
            }
        }
    }

    private fun initEventsAdapter() {
        val adapter =
            EventsAdapter { item, cardView ->
            }

        binding.recyclerViewEvents.adapter = adapter
        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        postponeEnterTransition()
        binding.recyclerViewEvents.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
    }

    private fun initEvents(list: List<Event>) {
        (binding.recyclerViewEvents.adapter as EventsAdapter).submitList(list)
    }
}