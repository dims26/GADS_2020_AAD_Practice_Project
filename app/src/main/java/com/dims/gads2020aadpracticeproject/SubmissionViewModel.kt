package com.dims.gads2020aadpracticeproject

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubmissionViewModel(private val submissionService: SubmissionService) : ViewModel() {

    private val _isSubmitableLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSubmitableLiveData: LiveData<Boolean>
        get() = _isSubmitableLiveData

    private val _submissionLiveData: MutableLiveData<LoadState> = MutableLiveData(LoadState.IDLE)
    val submissionLiveData: LiveData<LoadState>
        get() = _submissionLiveData

    var fname = ""
    var lname = ""
    var email = ""
    var projectLink = ""

    fun submit(){
        if (fname.isNotBlank() && lname.isNotBlank() &&
            Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            Patterns.WEB_URL.matcher(projectLink).matches())
        {submitForm()}
    }

    private fun submitForm() {
        _submissionLiveData.value = LoadState.LOADING

        val call = submissionService.postSubmission(
            "https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse",
            fname = fname.trim(),
            lname = lname.trim(),
            email = email.trim(),
            link = projectLink.trim()
        )

        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                _submissionLiveData.postValue(LoadState.FAILED)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) _submissionLiveData.postValue(LoadState.SUCCESSFUL)
                else _submissionLiveData.postValue(LoadState.FAILED)
            }
        })
    }

    fun checkIsSubmitable() {
        _isSubmitableLiveData.value = fname.isNotBlank() &&
                lname.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                Patterns.WEB_URL.matcher(projectLink).matches()
    }
}