package com.bangkit.storyappdicoding.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [StoryEntity::class, RemoteKeys::class],
    version = 2,
    exportSchema = true,
)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): StoryDatabase {
            if(INSTANCE == null) {
                synchronized(StoryDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StoryDatabase::class.java, "story_database"
                    ).build()
                }
            }
            return INSTANCE as StoryDatabase
        }
    }
}