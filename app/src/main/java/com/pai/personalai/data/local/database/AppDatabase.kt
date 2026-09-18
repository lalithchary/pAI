package com.pai.personalai.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pai.personalai.data.local.Converters
import com.pai.personalai.data.local.dao.ChatDao
import com.pai.personalai.data.local.dao.MessageDao
import com.pai.personalai.data.local.entity.ChatEntity
import com.pai.personalai.data.local.entity.MessageEntity

@Database(
    entities = [MessageEntity::class, ChatEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun chatDao(): ChatDao
}
