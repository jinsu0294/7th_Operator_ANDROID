package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import likelion.smu.com.likelion_alba.adapter.RecyclerViewAdapter
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var scheduleRecyclerViewAdapter: RecyclerViewAdapter
    var dailySchedule : MutableList<DailySchedule> = mutableListOf()
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

    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state : Int = -1 //GET_selete_month = 0
        var response : String? = null


        override fun doInBackground(vararg params: String): String? {

            state = Integer.parseInt(params[0])

            var client = okhttp3.OkHttpClient()
            var url = params[1]
            var grouppid = params[2]
            var date = params[3]

            //GET_selete_month
            if (state == 0){
                url.plus("${grouppid}&Date=${date}")
                response = Okhttp().GET(client,url)
            }
            return response
        }

        override fun onPostExecute(result: String) {
            if(!result[0].equals("{")) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                return
            }
            var jsonary = JSONArray(result)

            if (state == 0){
                for(i in 0..jsonary.length()-1){
                    var json = jsonary.getJSONObject(i)
                    dailySchedule[i].SchedulPid = json.getString("SchedulePid")
                    dailySchedule[i].Date = json.getString("Date")
                    dailySchedule[i].StartHour = json.getString("StartHour")
                    dailySchedule[i].StartMinute = json.getString("StartMinute")
                    dailySchedule[i].EndHour = json.getString("EndHour")
                    dailySchedule[i].EndMinute = json.getString("EndMinute")
                    dailySchedule[i].Nickname = json.getString("Nickname")
                    dailySchedule[i].SubstituteTF = json.getString("SubstituteTF")
                    dailySchedule[i].GroupPid = json.getString("GroupPid")
                    dailySchedule[i].memder_id = json.getString("member_id")
                    Log.d("daily",dailySchedule[i].Date)
                }
            }
        }
    }
}
