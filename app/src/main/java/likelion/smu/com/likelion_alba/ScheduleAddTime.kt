package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.schedule_add.*
import kotlinx.android.synthetic.main.schedule_add.btnAdd
import kotlinx.android.synthetic.main.schedule_add.tvDate
import kotlinx.android.synthetic.main.schedule_add_time.*

class ScheduleAddTime : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_add_time)

        tvDate.text="0000-00-00"

        btnSave.setOnClickListener {
            val intent = Intent(this,ScheduleAdd::class.java)
            intent.putExtra("startHour",etStartHour.text.toString())
            intent.putExtra("startMin",etStartMin.text.toString())
            intent.putExtra("endHour",etEndHour.text.toString())
            intent.putExtra("endMin",etEndMin.text.toString())
            startActivity(intent)
        }






    }


}