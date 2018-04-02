package com.example.masrobot.myalarmmanagerkotlin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val calOneTimeDate = Calendar.getInstance()
    private val calOneTimeTime = Calendar.getInstance()
    private val calRepeatTimeTime = Calendar.getInstance()

    private var alarmPreference: AlarmPreference? = null
    private val alarmReceiver = AlarmReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_one_time_alarm_date.setOnClickListener(this)
        btn_one_time_alarm_time.setOnClickListener(this)
        btn_set_one_time_alarm.setOnClickListener(this)
        btn_repeating_time_alarm_time.setOnClickListener(this)
        btn_repeating_time_alarm.setOnClickListener(this)
        btn_cancel_repeating_alarm.setOnClickListener(this)

        alarmPreference = AlarmPreference(this)

        if (!TextUtils.isEmpty(alarmPreference?.oneTimeDate)) setOneTimeText()
        if (!TextUtils.isEmpty(alarmPreference?.repeatingTime)) setRepeatingText()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_one_time_alarm_date -> {
                val currentDate = Calendar.getInstance()
                DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    calOneTimeDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                    tv_one_time_alarm_date.text = dateFormat.format(calOneTimeDate.time)
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
                        currentDate.get(Calendar.DATE)).show()
            }
            R.id.btn_one_time_alarm_time -> {
                val currentDate = Calendar.getInstance()
                TimePickerDialog(this@MainActivity, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    calOneTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calOneTimeTime.set(Calendar.MINUTE, minute)
                    val dateFormat = SimpleDateFormat("HH:mm")
                    tv_one_time_alarm_time.text = dateFormat.format(calOneTimeTime.time)
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE),
                        true).show()
            }
            R.id.btn_set_one_time_alarm -> {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val oneTimeDate = dateFormat.format(calOneTimeDate.time)
                val timeFormat = SimpleDateFormat("HH:mm")
                val oneTimeTime = timeFormat.format(calOneTimeTime.time)
                val oneTimeMessage = edt_one_time_alarm_message.text.toString()

                alarmPreference?.oneTimeDate = oneTimeDate
                alarmPreference?.oneTimeMessage = oneTimeMessage
                alarmPreference?.oneTimeTime = oneTimeTime

                alarmReceiver.setOneTimeAlarm(this@MainActivity, AlarmReceiver().TYPE_ONE_TIME,
                        alarmPreference?.oneTimeDate.toString(),
                        alarmPreference?.oneTimeTime.toString(),
                        alarmPreference?.oneTimeMessage.toString())
            }
            R.id.btn_repeating_time_alarm_time -> {
                val currentDate = Calendar.getInstance()
                TimePickerDialog(this@MainActivity, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    calRepeatTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calRepeatTimeTime.set(Calendar.MINUTE, minute)
                    val dateFormat = SimpleDateFormat("HH:mm")
                    btn_repeating_time_alarm_time.text = dateFormat.format(calRepeatTimeTime.time)
                }, currentDate.get(Calendar.HOUR_OF_DAY),
                        currentDate.get(Calendar.MINUTE), true).show()
            }
            R.id.btn_repeating_time_alarm -> {
                val timeFormat = SimpleDateFormat("HH:mm")
                val repeatTimeTime = timeFormat.format(calRepeatTimeTime.time)
                val repeatTimeMessage = edt_repeating_alarm_message.text.toString()
                alarmPreference?.repeatingTime = repeatTimeTime
                alarmPreference?.repeatingMessage = repeatTimeMessage
                setRepeatingText()
                alarmReceiver.setRepeatingAlarm(this@MainActivity, AlarmReceiver().TYPE_REPEATING,
                        alarmPreference?.repeatingTime.toString(),
                        alarmPreference?.repeatingMessage.toString())
            }
            R.id.btn_cancel_repeating_alarm -> alarmReceiver.cancelAlarm(this@MainActivity,
                    AlarmReceiver().TYPE_REPEATING)
        }
    }

    private fun setOneTimeText() {
        tv_one_time_alarm_time.text = alarmPreference?.oneTimeTime
        tv_one_time_alarm_date.text = alarmPreference?.oneTimeDate
        edt_one_time_alarm_message.setText(alarmPreference?.oneTimeMessage)
    }

    private fun setRepeatingText() {
        tv_repeating_time_alarm_time.text = alarmPreference?.repeatingTime
        edt_repeating_alarm_message.setText(alarmPreference?.repeatingMessage)
    }
}
