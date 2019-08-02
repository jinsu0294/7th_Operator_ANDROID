package likelion.smu.com.likelion_alba

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.test_fragment.*
import kotlinx.android.synthetic.main.test_fragment.rv_schedule
import kotlinx.android.synthetic.main.test_fragment.tv_current_month
import kotlinx.android.synthetic.main.test_fragment.tv_next_month
import kotlinx.android.synthetic.main.test_fragment.tv_prev_month
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var scheduleRecyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scheduleRecyclerViewAdapter = RecyclerViewAdapter(this)

        rv_schedule.layoutManager =
            GridLayoutManager(this, BaseCalendar.DAYS_OF_WEEK)
        rv_schedule.adapter = scheduleRecyclerViewAdapter
        rv_schedule.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        rv_schedule.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        tv_prev_month.setOnClickListener {
            scheduleRecyclerViewAdapter.changeToPrevMonth()
            Toast.makeText(this,"눌렸음", Toast.LENGTH_SHORT).show()
        }

        tv_next_month.setOnClickListener {
            scheduleRecyclerViewAdapter.changeToNextMonth()
        }

        btn_gotoDaeta.setOnClickListener {
            val intent = Intent(this,DaetaActivity::class.java)
            startActivity(intent)
        }


    }

    fun refreshCurrentMonth(calendar: Calendar) {
        val sdf = SimpleDateFormat("yyyy MM", Locale.KOREAN)
        tv_current_month.text = sdf.format(calendar.time)
    }

}
