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
import space.iqbalsyafiq.ecommerceapp.util.SharedPreferencesHelper

class LoginViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        const val TAG = "LoginVM"
    }

    private val service = EcommerceApiService()
    private val prefHelper = SharedPreferencesHelper(getApplication())

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
                    if (response.isSuccessful) {
                        tokenize(TokenizeRequest(loginRequest.email))
                    } else {
                        loading.value = false
                        error.value = true
                    }
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
                    response.body()?.message?.let {
                        Log.d(TAG, "onResponse: $it")
                        prefHelper.saveToken(it)
                        Log.d(TAG, "onResponse: ${prefHelper.getToken()}")
                        checkSession(it)
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(RegisterViewModel.TAG, "onFailure: $t")
                    loading.value = false
                    error.value = true
                }

            })
    }

    fun checkSession(token: String) {
        service.checkSession(token)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    Log.d(RegisterViewModel.TAG, "onResponse: ${response.isSuccessful}")
                    loading.value = false
                    message.value = token
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(RegisterViewModel.TAG, "onFailure: $t")
                    loading.value = false
                }

            })
    }
}