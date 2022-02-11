package space.iqbalsyafiq.ecommerceapp.model.item

import com.google.gson.annotations.SerializedName

data class ItemMessage(
    @SerializedName("item_code")
    val itemCode: String?,
    @SerializedName("item_name")
    val itemName: String?,
    @SerializedName("item_price")
    val itemPrice: Double?
)