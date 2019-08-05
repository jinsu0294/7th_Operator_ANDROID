package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.schedule_add.*
import kotlinx.android.synthetic.main.schedule_add.tvDate

class ScheduleAdd : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_add)

        // 리사이클러뷰 어뎁터 연결
        val mAdapter = ScheduleAdapter(this)
        rvSchedule.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        rvSchedule.layoutManager = lm
        rvSchedule.setHasFixedSize(true)

        tvDate.text="0000-00-00"

        if(intent.getStringExtra("startHour")!=null){
            val SH = intent.getStringExtra("startHour")
            val SM = intent.getStringExtra("startMin")
            val EH = intent.getStringExtra("endHour")
            val EM = intent.getStringExtra("endMin")
            mAdapter.add("${SH}","${SM}","${EH}","${EM}","test1")
        }

        btnAdd.setOnClickListener {
            val intent = Intent(this,ScheduleAddTime::class.java)
            startActivity(intent)
        }




    }


}