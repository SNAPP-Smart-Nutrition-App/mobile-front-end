package com.dicoding.snapp_smartnutritionapp.ui.Navigation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.snapp_smartnutritionapp.databinding.FragmentHomeBinding
import com.dicoding.snapp_smartnutritionapp.ui.Navigation.EventAdapter
import com.dicoding.snapp_smartnutritionapp.ui.profile.ProfileActivity

class HomeFragment : Fragment() {

    private lateinit var eventsAdapter: EventAdapter
    private val eventHomeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileImage.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }
        setupRecyclerView()
        observeEvents()
    }

    private fun setupRecyclerView() {
        eventsAdapter = EventAdapter()
        binding.rvReview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventsAdapter
        }
    }

    private fun observeEvents() {
        eventHomeViewModel.getEvents(1)
        eventHomeViewModel.eventList.observe(viewLifecycleOwner) { eventList ->
            if (eventList != null) {
                eventsAdapter.submitList(eventList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}