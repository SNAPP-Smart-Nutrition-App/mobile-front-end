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
import android.graphics.Color
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var eventsAdapter: EventAdapter
    private val eventHomeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_FROM_LOGIN = "extra_from_login"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.intent?.getBooleanExtra(EXTRA_FROM_LOGIN, false)?.let { fromLogin ->
            if (fromLogin) {
                Snackbar.make(binding.root, "Selamat datang! Login berhasil", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.GREEN)
                    .setTextColor(Color.WHITE)
                    .show()
                activity?.intent?.removeExtra(EXTRA_FROM_LOGIN)
            }
        }

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