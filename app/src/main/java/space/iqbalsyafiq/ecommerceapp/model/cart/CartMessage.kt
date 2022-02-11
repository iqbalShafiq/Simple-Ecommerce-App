package space.iqbalsyafiq.ecommerceapp.model.cart

import com.google.gson.annotations.SerializedName

data class CartMessage(
    @SerializedName("item_base_price")
    val itemBasePrice: Double?,
    @SerializedName("item_code")
    val itemCode: String?,
    @SerializedName("item_name")
    val itemName: String?,
    @SerializedName("item_subtotal")
    val itemSubtotal: Double?,
    @SerializedName("sum(a.qty)")
    val sum: Double?
)