package co.shepherd.sports.ui.schedule

import android.transition.TransitionInflater
import androidx.recyclerview.widget.LinearLayoutManager
import co.shepherd.sports.R
import co.shepherd.sports.core.BaseFragment
import co.shepherd.sports.databinding.FragmentScheduleBinding
import co.shepherd.sports.domain.model.Event
import co.shepherd.sports.domain.usecase.ScheduleUseCase
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
//                (activity as MainActivity).viewModel.toolbarTitle.set(
//                    it.data?.city?.getCityAndCountry()
//                )
            }
        }

        binding.viewModel?.getScheduleViewState()?.observeWith(
            viewLifecycleOwner
        ) {
            with(binding) {
                it.getSchedule()
                //  containerEvents.viewState = it
            }
        }
    }

    private fun initEventsAdapter() {
        val adapter =
            EventsAdapter { item, cardView ->
//                val action =
//                    DashboardFragmentDirections.actionDashboardFragmentToWeatherDetailFragment(
//                        item
//                    )
//                findNavController()
//                    .navigate(
//                        action,
//                        FragmentNavigator.Extras.Builder()
//                            .addSharedElements(
//                                mapOf(
//                                    cardView to cardView.transitionName,
//                                    forecastIcon to forecastIcon.transitionName,
//                                    dayOfWeek to dayOfWeek.transitionName,
//                                    temp to temp.transitionName,
//                                    tempMaxMin to tempMaxMin.transitionName
//                                )
//                            )
//                            .build()
//                    )
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