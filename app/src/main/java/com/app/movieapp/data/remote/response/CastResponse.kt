package com.ericg.neatflix.data.remote.response

import com.app.movieapp.models.Cast
import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val castResult: List<Cast>
)
