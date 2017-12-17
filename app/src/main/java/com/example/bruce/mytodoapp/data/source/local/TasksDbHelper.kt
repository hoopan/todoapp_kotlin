package com.example.bruce.mytodoapp.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**
 * Created by bruce on 17-12-15.
 */
class TasksDbHelper : SQLiteOpenHelper {

    companion object {

        public val DATABASE_VERSION: Int = 1

        private val DATABASE_NAME: String = "Tasks.db"

        private val TEXT_TYPE = " TEXT"

        private val BOOLEAN_TYPE = " INTEGER"

        private val COMMA_SEP = ","

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TasksPersistenceContract.TaskEntry.TABLE_NAME + " (" +
    BaseColumns._ID + TEXT_TYPE + " PRIMARY KEY," +
    TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
    TasksPersistenceContract.TaskEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
    TasksPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
    TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED + BOOLEAN_TYPE +
    " )"
    }

    public constructor(context: Context) : super(context, DATABASE_NAME, null, DATABASE_VERSION)



    override fun onCreate(p0: SQLiteDatabase?) {
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Not required as at version 1
//    }

}

