package co.shepherd.sports.ui.schedule

import android.transition.TransitionInflater
import androidx.recyclerview.widget.LinearLayoutManager
import co.shepherd.sports.R
import co.shepherd.sports.core.BaseFragment
import co.shepherd.sports.databinding.FragmentScheduleBinding
import co.shepherd.sports.domain.model.Event
import co.shepherd.sports.domain.usecase.ScheduleUseCase
import co.shepherd.sports.ui.MainActivity
import co.shepherd.sports.ui.events.EventsAdapter
import co.shepherd.sports.utils.extensions.isNetworkAvailable
import co.shepherd.sports.utils.extensions.observeWith
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment : BaseFragment<ScheduleViewModel, FragmentScheduleBinding>(
    R.layout.fragment_schedule,
    ScheduleViewModel::class.java,
) {

    override fun init() {
        super.init()
        initEventsAdapter()
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(
            android.R.transition.move
        )

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.viewModel?.setScheduleParams(
                ScheduleUseCase.ScheduleParams(
                    isNetworkAvailable(requireContext()),
                )
            )
        }

        binding.viewModel?.setScheduleParams(
            ScheduleUseCase.ScheduleParams(
                isNetworkAvailable(requireContext()),
            )
        )

        binding.viewModel?.getScheduleViewState()?.observeWith(
            viewLifecycleOwner
        ) {
            with(binding) {
                viewState = it
                it.data?.schedule?.let { events -> initEvents(events.schedule) }
            }
        }

        binding.viewModel?.getScheduleViewState()?.observeWith(
            viewLifecycleOwner
        ) {
            with(binding) {
                viewState = it
                it.data?.schedule?.let { schedule -> initEvents(schedule.schedule) }
                (activity as MainActivity).viewModel.toolbarTitle.set(
                    "Schedule"
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
        (binding.recyclerViewEvents.adapter as ScheduleAdapter).submitList(list)
    }
}