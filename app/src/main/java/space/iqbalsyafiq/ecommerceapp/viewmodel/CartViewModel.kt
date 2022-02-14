package space.iqbalsyafiq.ecommerceapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.iqbalsyafiq.ecommerceapp.api.EcommerceApiService
import space.iqbalsyafiq.ecommerceapp.model.MessageResponse
import space.iqbalsyafiq.ecommerceapp.model.cart.CartMessage
import space.iqbalsyafiq.ecommerceapp.model.cart.CartRequest
import space.iqbalsyafiq.ecommerceapp.model.cart.CartResponse

class CartViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        const val TAG = "CartVM"
    }

    private val service = EcommerceApiService()

    // Live Data
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    val carts = MutableLiveData<List<CartMessage>>()
    val addItem = MutableLiveData<Boolean>()

    fun getCartList(token: String) {
        loading.value = true

        service.getCart(token)
            .enqueue(object : Callback<CartResponse> {
                override fun onResponse(
                    call: Call<CartResponse>,
                    response: Response<CartResponse>
                ) {
                    Log.d(RegisterViewModel.TAG, "onResponse: ${response.isSuccessful}")
                    Log.d(RegisterViewModel.TAG, "onResponse: ${response.body()?.message}")
                    loading.value = false
                    carts.value = response.body()?.message
                }

                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Log.d(RegisterViewModel.TAG, "onFailure: $t")
                    loading.value = false
                    error.value = true
                }
            })
    }

    fun addItemToCart(token: String, cartRequest: CartRequest) {
        service.addCart(token, cartRequest)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    if (response.isSuccessful) {
                        addItem.value = true
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(DashboardViewModel.TAG, "onFailure: $t")
                }

            })
    }
}