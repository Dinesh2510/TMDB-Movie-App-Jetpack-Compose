package com.app.movieapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "watch_list_table")
data class WatchListModel(
    @PrimaryKey val mediaId: Int,
    val imagePath: String?,
    val title: String,
    val releaseDate: String,
    val rating: Double,
    val addedOn: String
)
