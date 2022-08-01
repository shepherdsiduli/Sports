package co.shepherd.sports.domain.model

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("subtitle")
    val subtitle: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    @SerializedName("videoUrl")
    val videoUrl: String? = null
)