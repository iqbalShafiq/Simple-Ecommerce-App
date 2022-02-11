package space.iqbalsyafiq.ecommerceapp.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import space.iqbalsyafiq.ecommerceapp.model.MessageResponse
import space.iqbalsyafiq.ecommerceapp.model.authentication.LoginRequest
import space.iqbalsyafiq.ecommerceapp.model.authentication.RegisterRequest
import space.iqbalsyafiq.ecommerceapp.model.authentication.TokenizeRequest
import space.iqbalsyafiq.ecommerceapp.model.cart.CartRequest
import space.iqbalsyafiq.ecommerceapp.model.cart.CartResponse
import space.iqbalsyafiq.ecommerceapp.model.item.ItemRequest
import space.iqbalsyafiq.ecommerceapp.model.item.ItemResponse

interface EcommerceApi {
    // register user
    @POST("/api/method/test_ecommerce.apis.v1.user.add")
    fun registerUser(
        @Header("Authorization") token: String,
        @Body registerRequest: RegisterRequest
    ): Call<MessageResponse>

    // login user
    @POST("/api/method/test_ecommerce.apis.v1.user.auth")
    fun loginUser(
        @Header("Authorization") token: String,
        @Body loginRequest: LoginRequest
    ): Call<MessageResponse>

    // tokenize
    @POST("/api/method/test_ecommerce.apis.v1.user.tokenize")
    fun tokenize(
        @Header("Authorization") token: String,
        @Body tokenizeRequest: TokenizeRequest
    ): Call<MessageResponse>

    // check session
    @POST("/api/method/test_ecommerce.apis.v1.user.check_session")
    fun checkSession(
        @Header("Authorization") token: String
    ): Call<MessageResponse>

    // get list item
    @POST("/api/method/test_ecommerce.apis.v1.item.get")
    fun getListItem(
        @Header("Authorization") token: String,
        @Body itemRequest: ItemRequest
    ): Call<ItemResponse>

    // add cart
    @POST("/api/method/test_ecommerce.apis.v1.cart.add")
    fun addChart(
        @Header("Authorization") token: String,
        @Body cartRequest: CartRequest
    ): Call<MessageResponse>

    // get cart
    @POST("/api/method/test_ecommerce.apis.v1.cart.get")
    fun getCart(
        @Header("Authorization") token: String
    ): Call<CartResponse>
}