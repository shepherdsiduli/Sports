package co.shepherd.sports.ui.events

import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.shepherd.sports.R
import co.shepherd.sports.core.BaseFragment
import co.shepherd.sports.databinding.FragmentEventsBinding
import co.shepherd.sports.domain.model.Event
import co.shepherd.sports.domain.usecase.EventsUseCase
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
//                (activity as MainActivity).viewModel.toolbarTitle.set(
//                    it.data?.city?.getCityAndCountry()
//                )
            }
        }

        binding.viewModel?.getEventsViewState()?.observeWith(
            viewLifecycleOwner
        ) {
            with(binding) {
                it.getEvents()
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

//    private lateinit var eventsViewModel: EventsViewModel
//
//    private var _binding: FragmentEventsBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        _binding = FragmentEventsBinding.inflate(inflater, container, false)
//        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_EventsFragment_to_ScheduleFragment)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}