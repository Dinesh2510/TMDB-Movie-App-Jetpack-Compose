package com.app.movieapp.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@AutoMigration(from = 1, to = 2)
@Database(version = 1, entities = [WatchListModel::class], exportSchema = false)
abstract class MovieDatabase():RoomDatabase() {
    abstract fun movieDao(): MovieDao
}