package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.schedule_add.*
import kotlinx.android.synthetic.main.schedule_add.btnAdd
import kotlinx.android.synthetic.main.schedule_add.tvDate
import kotlinx.android.synthetic.main.schedule_add_time.*
import org.json.JSONObject

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

    inner class AsyncTast: AsyncTask<String, Void, String>() {
        var state : Int = -1 //POST_scheduleAdd = 0
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = okhttp3.OkHttpClient()
            var url = params[1]
            var grouppid = Integer.getInteger(params[2])
            var memberid = params[3]
            var nickname= params[4]
            var date= params[5]
            var starthour= Integer.getInteger(params[6])
            var startminute= Integer.getInteger(params[7])
            var endhour= Integer.getInteger(params[8])
            var endminute= Integer.getInteger(params[9])

            //POST_scheduleAdd
            if (state == 0){
                response = Okhttp().POST(client,url,CreateJson().json_add_sche(grouppid,memberid,nickname,date,starthour,startminute,endhour,endminute))
            }
            return response
        }

        override fun onPostExecute(result: String) {
            if(!result[0].equals("{")) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                return
            }
            var json = JSONObject(result)
            //POST_scheduleAdd
            if (state == 0){

            }
        }
    }
}