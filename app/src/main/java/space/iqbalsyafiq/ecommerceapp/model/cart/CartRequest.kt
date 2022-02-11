package space.iqbalsyafiq.ecommerceapp.model.cart

import com.google.gson.annotations.SerializedName

data class CartRequest(
    @SerializedName("item_code")
    val itemCode: String,
    val qty: Int = 0
)