package space.iqbalsyafiq.ecommerceapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.iqbalsyafiq.ecommerceapp.api.EcommerceApiService
import space.iqbalsyafiq.ecommerceapp.model.MessageResponse
import space.iqbalsyafiq.ecommerceapp.model.authentication.RegisterRequest

class RegisterViewModel(application: Application) : BaseViewModel(application) {

    companion object {
        const val TAG = "RegisterVM"
    }

    private val service = EcommerceApiService()

    // Live Data
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun registerUser(registerRequest: RegisterRequest) {
        loading.value = true

        service.registerUser(registerRequest)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    Log.d(TAG, "onResponse: ${response.isSuccessful}")
                    Log.d(TAG, "onResponse: ${response.body()?.message}")
                    loading.value = false
                    message.value = response.body()?.message
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: $t")
                    loading.value = false
                    error.value = true
                }

            })
    }
}