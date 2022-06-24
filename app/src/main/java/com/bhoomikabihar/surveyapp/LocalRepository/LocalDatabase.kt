package com.example.dbtagri.LocalDataRepository.Dao

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [InputSubsidyApplicationLocal::class, InputSubsidyLandLocal::class],
    version = 1, exportSchema = false
)

public abstract class LocalDatabase() : RoomDatabase() {

    abstract fun localDao(): LocalDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            LocalDatabase::class.java,
                            "LocalDatabase"
                        )
                            .addCallback(object : Callback() {
                                override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    // add this code
                                    db.execSQL("PRAGMA encoding='UTF-8';")
                                }
                            })
                            // .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                        return INSTANCE
                    }
                }
            }
            return INSTANCE
        }
//        .fallbackToDestructiveMigration()
    }

}