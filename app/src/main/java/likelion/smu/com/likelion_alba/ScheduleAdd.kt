package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.schedule_add.*
import kotlinx.android.synthetic.main.schedule_add.tvDate
import org.json.JSONObject

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

    inner class AsyncTast: AsyncTask<String, Void, String>() {
        var state : Int = -1 //GET_selete_date = 0
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = okhttp3.OkHttpClient()
            var url = params[1]
            var schedulepid = Integer.getInteger(params[2])

            //GET_selete_date
            if (state == 0){
                url.plus("${schedulepid}")
                response = Okhttp().GET(client,url)
            }
            return response
        }

        override fun onPostExecute(result: String) {
            if(!result[0].equals("{")) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                return
            }
            var json = JSONObject(result)
            //GET_selete_date
            if (state == 0){
                //데이터 받아옴
            }
        }
    }
}