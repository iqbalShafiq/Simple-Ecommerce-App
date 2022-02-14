package space.iqbalsyafiq.ecommerceapp.model.item

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemMessage(
    @SerializedName("item_code")
    val itemCode: String?,
    @SerializedName("item_name")
    val itemName: String?,
    @SerializedName("item_price")
    val itemPrice: Double?
) : Parcelable