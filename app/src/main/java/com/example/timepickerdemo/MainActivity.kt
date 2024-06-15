package com.example.timepickerdemo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.timepickerdemo.databinding.TimePickerDialogBinding
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn).setOnClickListener {
            showTimePickerDialog()
        }
    }
    private fun showTimePickerDialog() {

        TimePickerDialog().timePicker(this@MainActivity){
            Log.e( "showTimePickerDialog: ", it.toString())
        }
    }
}
class TimePickerDialog{
    private lateinit var binding:TimePickerDialogBinding
    private val now: Calendar = Calendar.getInstance()

    
    fun timePicker(context: Context, setTime: (String?) -> Unit){
        val dialog = Dialog(context)
        binding = TimePickerDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.timePicker.setIs24HourView(true)

        now.timeZone = TimeZone.getDefault()

        binding.tvTime.text = millisToFormat(now.timeInMillis, "hh:mm")

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            now.set(Calendar.HOUR_OF_DAY, hourOfDay)
            now.set(Calendar.MINUTE, minute)
            binding.tvTime.text = millisToFormat(now.timeInMillis, "hh:mm")
            setTime(millisToFormat(now.timeInMillis, "hh:mm aa"))
        }

        binding.tvOk.setOnClickListener {
            dialog.dismiss()
            setTime(millisToFormat(now.timeInMillis, "hh:mm aa"))
        }

        binding.tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun millisToFormat(millis: Long, format: String?): String? {
        var millis1 = millis
        if (millis < 1000000000000L) {
            millis1 *= 1000
        }
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeZone = TimeZone.getDefault()
        cal.timeInMillis = millis1
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(cal.time)
    }
}