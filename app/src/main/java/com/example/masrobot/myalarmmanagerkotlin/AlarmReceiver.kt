package com.example.masrobot.myalarmmanagerkotlin

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {

    val TYPE_ONE_TIME = "OneTimeAlarm"
    val TYPE_REPEATING = "RepeatingAlarm"
    val EXTRA_MESSAGE = "message"
    val EXTRA_TYPE = "type"
    val NOTIF_ID_ONETIME = 100
    val NOTIF_ID_REPEATING = 101

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val title = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) "One Time Alarm" else "Repeating Alarm"
        val notifId = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) NOTIF_ID_ONETIME else NOTIF_ID_REPEATING
        showAlarmNotification(context, title, message, notifId)
    }

    private fun showAlarmNotification(context: Context, title: String, message: String, notifId: Int) {
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_access_alarms_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
        notificationManagerCompat.notify(notifId, builder.build())
    }

    fun setOneTimeAlarm(context: Context, type: String, date: String, time: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        val dateArray = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }
}
