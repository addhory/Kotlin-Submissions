package com.example.myasus.footballKADE.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DbHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Favorites.db", null, 1) {

    companion object {
        private var instance: DbHelper? = null

        @Synchronized
        fun getInstance(context: Context): DbHelper {
            if (instance == null) {
                instance = DbHelper(context.applicationContext)
            }

            return instance as DbHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(MatchFavorite.TABLE_FAVORITES, true,
            MatchFavorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            MatchFavorite.ID_EVENT to TEXT,
            MatchFavorite.DATE to TEXT,

            // home team
            MatchFavorite.HOME_ID to TEXT,
            MatchFavorite.HOME_TEAM to TEXT,
            MatchFavorite.HOME_SCORE to TEXT,
            /*MatchFavorite.HOME_GOAL_DETAILS to TEXT,
            MatchFavorite.HOME_LINEUP_GOALKEEPER to TEXT,
            MatchFavorite.HOME_LINEUP_DEFENSE to TEXT,
            MatchFavorite.HOME_LINEUP_MIDFIELD to TEXT,
            MatchFavorite.HOME_LINEUP_FORWARD to TEXT,
            MatchFavorite.HOME_LINEUP_SUBSTITUTES to TEXT,
            */
            // away team
            MatchFavorite.AWAY_ID to TEXT,
            MatchFavorite.AWAY_TEAM to TEXT,
            MatchFavorite.AWAY_SCORE to TEXT
            /*MatchFavorite.AWAY_GOAL_DETAILS to TEXT,
            MatchFavorite.AWAY_LINEUP_GOALKEEPER to TEXT,
            MatchFavorite.AWAY_LINEUP_DEFENSE to TEXT,
            MatchFavorite.AWAY_LINEUP_MIDFIELD to TEXT,
            MatchFavorite.AWAY_LINEUP_FORWARD to TEXT,
            MatchFavorite.AWAY_LINEUP_SUBSTITUTES to TEXT
            */
            )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MatchFavorite.TABLE_FAVORITES, true)
    }
}

val Context.database: DbHelper
    get() = DbHelper.getInstance(applicationContext)
