package com.lixd.pokemon.db

import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lixd.pokemon.PokemonApplication

object DBManager {
    private const val TAG = "DBManager"
    private const val dbName = "pokemon"

    val db by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        Room.databaseBuilder(
            PokemonApplication.appContext,
            PokemonDatabase::class.java, dbName
        )
            .allowMainThreadQueries() //允许在主线程操作
            .addCallback(DbCreateCallBack)//增加回调监听
            .build()
    }

    private object DbCreateCallBack : RoomDatabase.Callback() {
        //第一次创建数据库时调用
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d(TAG, "onCreate")
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            Log.d(TAG, "onOpen")
        }

        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
            super.onDestructiveMigration(db)
            Log.d(TAG, "onDestructiveMigration")
        }
    }
}