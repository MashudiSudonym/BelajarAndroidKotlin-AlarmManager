package com.example.masrobot.myalarmmanagerkotlin

import android.content.Context
import android.content.SharedPreferences

class AlarmPreference(contect: Context) {
    private val PREF_NAME = "AlarmPreference"
    private val KEY_ONE_TIME_DATE = "oneTimeDate"
    private val KEY_ONE_TIME_TIME = "oneTimeTime"
    private val KEY_ONE_TIME_MESSAGE = "oneTimeMessage"
    private val KEY_REPEATING_TIME = "repeatingTime"
    private val KEY_REPEATING_MESSAGE = "repeatingMessage"
    private val mSharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    var oneTimeDate: String?
        get() = mSharedPreferences.getString(KEY_ONE_TIME_DATE, null)
        set(date) {
            editor.putString(KEY_ONE_TIME_DATE, date)
            editor.commit()
        }

    var oneTimeTime: String?
        get() = mSharedPreferences.getString(KEY_ONE_TIME_TIME, null)
        set(time) {
            editor.putString(KEY_ONE_TIME_TIME, time)
            editor.commit()
        }

    var oneTimeMessage: String?
        get() = mSharedPreferences.getString(KEY_ONE_TIME_MESSAGE, null)
        set(message) {
            editor.putString(KEY_ONE_TIME_MESSAGE, message)
            editor.commit()
        }

    var repeatingTime: String?
        get() = mSharedPreferences.getString(KEY_REPEATING_TIME, null)
        set(time) {
            editor.putString(KEY_REPEATING_TIME, time)
            editor.commit()
        }

    var repeatingMessage: String?
        get() = mSharedPreferences.getString(KEY_REPEATING_MESSAGE, null)
        set(message) {
            editor.putString(KEY_REPEATING_MESSAGE, message)
            editor.commit()
        }

    init {
        mSharedPreferences = contect.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = mSharedPreferences.edit()
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }
}
