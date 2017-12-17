package com.example.bruce.mytodoapp.data.source.local

import android.provider.BaseColumns

/**
 * Created by bruce on 17-12-16.
 */
class TasksPersistenceContract {

    abstract class TaskEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "task"
            val COLUMN_NAME_ENTRY_ID = "entryid"
            val COLUMN_NAME_TITLE = "title"
            val COLUMN_NAME_DESCRIPTION = "description"
            val COLUMN_NAME_COMPLETED = "completed"
        }
    }
}