package space.iqbalsyafiq.ecommerceapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.iqbalsyafiq.ecommerceapp.api.EcommerceApiService
import space.iqbalsyafiq.ecommerceapp.model.MessageResponse
import space.iqbalsyafiq.ecommerceapp.model.authentication.LoginRequest
import space.iqbalsyafiq.ecommerceapp.model.authentication.TokenizeRequest

class LoginViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        const val TAG = "LoginVM"
    }

    private val service = EcommerceApiService()

    // Live Data
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun loginUser(loginRequest: LoginRequest) {
        loading.value = true

        service.loginUser(loginRequest)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    Log.d(RegisterViewModel.TAG, "onResponse: ${response.isSuccessful}")
                    tokenize(TokenizeRequest(loginRequest.email))
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(RegisterViewModel.TAG, "onFailure: $t")
                    loading.value = false
                    error.value = true
                }

            })
    }

    fun tokenize(tokenizeRequest: TokenizeRequest) {
        loading.value = true

        service.tokenize(tokenizeRequest)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    Log.d(RegisterViewModel.TAG, "onResponse: ${response.isSuccessful}")
                    response.body()?.message?.let { checkSession(it) }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(RegisterViewModel.TAG, "onFailure: $t")
                    loading.value = false
                    error.value = true
                }

            })
    }

    fun checkSession(token: String) {
        loading.value = true

        service.checkSession(token)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    Log.d(RegisterViewModel.TAG, "onResponse: ${response.isSuccessful}")
                    loading.value = false
                    message.value = response.body()?.message
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(RegisterViewModel.TAG, "onFailure: $t")
                    loading.value = false
                    error.value = true
                }

            })
    }
}