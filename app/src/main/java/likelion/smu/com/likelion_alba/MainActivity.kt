package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
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

    inner class AsyncTast: AsyncTask<String, Void, String>() {
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
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                return
            }

            var jsonary = JSONArray(result)

            if (state == 0){
                for(i in 0..jsonary.length()-1){
                    var json = jsonary.getJSONObject(i)
                    var SchedulPid = json.getString("SchedulePid")
                    var Date = json.getString("Date")
                    var StartHour = json.getString("StartHour")
                    var StartMinute = json.getString("StartMinute")
                    var EndHour = json.getString("EndHour")
                    var EndMinute = json.getString("EndMinute")
                    var Nickname = json.getString("Nickname")
                    var SubstituteTF = json.getString("SubstituteTF")
                    var GroupPid = json.getString("GroupPid")
                    var memder_id = json.getString("member_id")
                }
            }
        }
    }
}
