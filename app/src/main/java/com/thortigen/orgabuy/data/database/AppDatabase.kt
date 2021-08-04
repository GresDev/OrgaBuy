package com.thortigen.orgabuy.data

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thortigen.orgabuy.data.dao.CartDao
import com.thortigen.orgabuy.data.dao.CatalogDao
import com.thortigen.orgabuy.data.dao.ShopListDao
import com.thortigen.orgabuy.data.models.CartItem
import com.thortigen.orgabuy.data.models.CatalogItem
import com.thortigen.orgabuy.data.models.ShopListItem

@Database(entities = [CatalogItem::class, ShopListItem::class, CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun catalogDao() : CatalogDao
    abstract fun shopListDao(): ShopListDao
    abstract fun cartDao(): CartDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

//        private val migration: Migration = object : Migration(1, 2)
//        {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("")
//            }
//        }

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
//                    .addMigrations(migration)
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}