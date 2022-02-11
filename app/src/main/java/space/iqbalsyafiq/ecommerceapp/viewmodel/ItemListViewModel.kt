package space.iqbalsyafiq.ecommerceapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.iqbalsyafiq.ecommerceapp.api.EcommerceApiService
import space.iqbalsyafiq.ecommerceapp.model.MessageResponse
import space.iqbalsyafiq.ecommerceapp.model.cart.CartRequest
import space.iqbalsyafiq.ecommerceapp.model.item.ItemMessage
import space.iqbalsyafiq.ecommerceapp.model.item.ItemRequest
import space.iqbalsyafiq.ecommerceapp.model.item.ItemResponse

class ItemListViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        const val TAG = "RegisterVM"
    }

    private val service = EcommerceApiService()

    // Live Data
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    val items = MutableLiveData<List<ItemMessage>>()
    val message = MutableLiveData<String>()

    fun getItemList(token: String, itemRequest: ItemRequest = ItemRequest("", 0)) {
        loading.value = true

        service.getItem(token, itemRequest)
            .enqueue(object : Callback<ItemResponse> {
                override fun onResponse(
                    call: Call<ItemResponse>,
                    response: Response<ItemResponse>
                ) {
                    Log.d(RegisterViewModel.TAG, "onResponse: ${response.isSuccessful}")
                    Log.d(RegisterViewModel.TAG, "onResponse: ${response.body()?.message}")
                    loading.value = false
                    items.value = response.body()?.message
                }

                override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
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
                    message.value = response.body()?.message
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: $t")
                }

            })
    }
}