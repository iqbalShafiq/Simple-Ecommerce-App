package space.iqbalsyafiq.ecommerceapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.iqbalsyafiq.ecommerceapp.api.EcommerceApiService
import space.iqbalsyafiq.ecommerceapp.model.MessageResponse
import space.iqbalsyafiq.ecommerceapp.util.SharedPreferencesHelper

class SplashScreenViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        const val TAG = "LoginVM"
    }

    private val service = EcommerceApiService()
    private val prefHelper = SharedPreferencesHelper(getApplication())

    // Live Data
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    val token = MutableLiveData<String>()

    fun checkSession() {
        Log.d(TAG, "checkSession: ${prefHelper.getToken()!!}")
        service.checkSession(prefHelper.getToken()!!)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    Log.d(RegisterViewModel.TAG, "onResponse: ${response.isSuccessful}")
                    loading.value = false
                    token.value = prefHelper.getToken()!!
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(RegisterViewModel.TAG, "onFailure: $t")
                    loading.value = false
                }

            })
    }
}