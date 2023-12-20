package iqro.mobile.retrofitexample

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val user:User
)
