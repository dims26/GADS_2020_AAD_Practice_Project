package com.dims.gads2020aadpracticeproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import java.lang.IllegalArgumentException

class ViewModelFactory(private val call: Call<*>? = null, private val submissionService: SubmissionService? = null) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LeadersViewModel::class.java) &&
                    call != null -> LeadersViewModel(call as Call<List<Leader>>) as T
            modelClass.isAssignableFrom(SubmissionViewModel::class.java) &&
                    submissionService != null -> SubmissionViewModel(submissionService) as T
            else -> throw IllegalArgumentException("Incompatible ViewModel type or unsupported factory parameters")
        }
    }
}