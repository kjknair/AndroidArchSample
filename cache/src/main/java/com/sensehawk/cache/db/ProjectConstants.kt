package com.sensehawk.cache.db

object ProjectConstants {

    const val TABLE_NAME = "project"
    const val COLUMN_PROJECT_ID = "projectId"
    const val COLUMN_IS_BOOKMARKED = "is_bookmarked"

    const val QUERY_PROJECTS = "SELECT * FROM $TABLE_NAME"

    const val QUERY_DELETE_PROJECT = "DELETE FROM $TABLE_NAME"

    const val QUERY_BOOKMARKED_PROJECT = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_IS_BOOKMARKED=1"

}