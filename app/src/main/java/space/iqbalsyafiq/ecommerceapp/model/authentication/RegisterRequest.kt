package space.iqbalsyafiq.ecommerceapp.model.authentication

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    val password: String
)