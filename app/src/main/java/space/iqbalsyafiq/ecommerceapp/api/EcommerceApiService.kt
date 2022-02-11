package space.iqbalsyafiq.ecommerceapp.api

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.iqbalsyafiq.ecommerceapp.constanst.ApiConstant.Companion.BASE_URL
import space.iqbalsyafiq.ecommerceapp.constanst.ApiConstant.Companion.GLOBAL_TOKEN
import space.iqbalsyafiq.ecommerceapp.model.MessageResponse
import space.iqbalsyafiq.ecommerceapp.model.authentication.LoginRequest
import space.iqbalsyafiq.ecommerceapp.model.authentication.RegisterRequest
import space.iqbalsyafiq.ecommerceapp.model.authentication.TokenizeRequest
import space.iqbalsyafiq.ecommerceapp.model.cart.CartRequest
import space.iqbalsyafiq.ecommerceapp.model.cart.CartResponse
import space.iqbalsyafiq.ecommerceapp.model.item.ItemRequest
import space.iqbalsyafiq.ecommerceapp.model.item.ItemResponse

class EcommerceApiService {
    private val gson = GsonBuilder().setLenient().create()
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(EcommerceApi::class.java)

    // register user
    fun registerUser(registerRequest: RegisterRequest): Call<MessageResponse> {
        return api.registerUser(GLOBAL_TOKEN, registerRequest)
    }

    // login user
    fun loginUser(loginRequest: LoginRequest): Call<MessageResponse> {
        return api.loginUser(GLOBAL_TOKEN, loginRequest)
    }

    // tokenize
    fun tokenize(tokenizeRequest: TokenizeRequest): Call<MessageResponse> {
        return api.tokenize(GLOBAL_TOKEN, tokenizeRequest)
    }

    // check session
    fun checkSession(token: String): Call<MessageResponse> {
        return api.checkSession(token)
    }

    // get item
    fun getItem(token: String, itemRequest: ItemRequest): Call<ItemResponse> {
        return api.getListItem(token, itemRequest)
    }

    // add cart
    fun addCart(token: String, cartRequest: CartRequest): Call<MessageResponse> {
        return api.addChart(token, cartRequest)
    }

    // get cart
    fun getCart(token: String): Call<CartResponse> {
        return api.getCart(token)
    }
}