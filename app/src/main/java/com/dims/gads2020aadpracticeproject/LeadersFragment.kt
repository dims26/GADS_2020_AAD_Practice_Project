package com.dims.gads2020aadpracticeproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call

class LeadersFragment(private val call: Call<List<Leader>>) : Fragment() {
    private lateinit var viewModel: LeadersViewModel
    private lateinit var leaderRecycler: RecyclerView
    private lateinit var adapter: LeaderItemsAdapter
    private lateinit var loadingProgressBar: ProgressBar

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.leaders_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingProgressBar = view.findViewById(R.id.loading_progress_bar)
        leaderRecycler = view.findViewById(R.id.leaders_recycler)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        leaderRecycler.layoutManager = layoutManager

        val factory = ViewModelFactory(call = call)
        viewModel = ViewModelProvider(this, factory).get(LeadersViewModel::class.java)

        viewModel.dataLoadLiveData.observe(viewLifecycleOwner, Observer { t ->
            loadingProgressBar.visibility = View.GONE
                when(t){
                    LoadState.LOADING -> {
                        loadingProgressBar.visibility = View.VISIBLE
                    }
                    LoadState.FAILED -> {
                        Snackbar.make(this@LeadersFragment.requireView(), "Load Failed", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY") { viewModel.retryCall() }.show()
                    }
                    LoadState.SUCCESSFUL -> {
                        adapter = LeaderItemsAdapter(viewModel.leaders)
                        leaderRecycler.adapter = adapter
                    }
                    else -> {/*do nothing*/}
                }
            })
    }
}