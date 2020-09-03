package com.dims.gads2020aadpracticeproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeadersViewModel(private val call: Call<List<Leader>>) : ViewModel() {

    init {
        executeCall()
    }

    private val _dataLoadLiveData: MutableLiveData<LoadState> = MutableLiveData(LoadState.LOADING)
    val dataLoadLiveData: LiveData<LoadState>
        get() = _dataLoadLiveData
    lateinit var leaders: List<Leader>

    fun executeCall() {
        call.enqueue(object : Callback<List<Leader>> {
            override fun onFailure(call: Call<List<Leader>>, t: Throwable) {
                _dataLoadLiveData.postValue(LoadState.FAILED)
            }

            override fun onResponse(
                call: Call<List<Leader>>,
                response: Response<List<Leader>>
            ) {
                if (response.isSuccessful){
                    leaders = response.body()!!
                    _dataLoadLiveData.postValue(LoadState.SUCCESSFUL)
                }else _dataLoadLiveData.postValue(LoadState.FAILED)
            }
        })
    }

}

enum class LoadState{
    IDLE, LOADING, FAILED, SUCCESSFUL
}