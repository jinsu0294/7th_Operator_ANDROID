package likelion.smu.com.likelion_alba

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_participation.*
import kotlinx.android.synthetic.main.activity_select_main.*
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import java.net.UnknownServiceException

class ExitActivity: AppCompatActivity() {
    var json = JSONObject()
    var response: String? = null

    fun setup(){
        tvMessage.text="나가시겠습니까?"
        btnLeft.text = "아니오"
        btnRight.text = "네"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_main)

        setup()
        val user = application as User
        Log.e("Exit memberId", user.getmemberid().toString())

        // state = 0, GET_방검색, storename (GroupPid 얻기 위함)
        val storeName = intent.getStringExtra("storeName")
        Asynctask().execute("0",getString(R.string.search_room),storeName)

       // 아니오 버튼 리스너 (액티비티 종료)
        btnLeft.setOnClickListener {
            val intent = Intent(this, UserRoomActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 네 버튼 리스너 (방 나가기)
        btnRight.setOnClickListener {
            Asynctask().execute("1",getString(R.string.out_room),user.getPid().toString(),user.getmemberid())
            val intent = Intent(this,UserRoomActivity::class.java)
            setResult(Activity.RESULT_OK)
            startActivity(intent)
            finish()
        }
    }

    inner class Asynctask:AsyncTask<String, Void, String>(){
        var state:Int =-1

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = okhttp3.OkHttpClient()
            var url = params[1]

            if(state == 0){
                var groupName = params[2]
                url += "${groupName}"
                Log.e(" state == 0, url =",url)

                response = Okhttp().GET(client, url)
            }else if(state == 1){
                var groupPid = Integer.parseInt(params[2])
                var memberId = params[3]
                url += "${groupPid}/${memberId}"
                Log.e(" state == 1, url = ",url)

                response = Okhttp().DELETE(client, url)
                Log.e("RESPONSE", response)
            }

            return response
        }

        override fun onPostExecute(result: String) {
            var groupPid:Int = -1
            val user = application as User

            if(!result[0].equals('{') && !result[0].equals('[')){
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_SHORT).show()
                return
            }else if(state == 0){
                var jsonary = JSONArray(result)
                Log.e("Get search","!!yes!!")
                for (i in 0..jsonary.length()-1){
                    var json = JSONObject(jsonary.get(i).toString())
                    groupPid = json.getInt("GroupPid")
                    user.setGroupPid(groupPid)
                    Log.e(" OnPot UER G_PID", user.getPid().toString())
                }
            }else if(state == 1){
                var json = JSONObject(result)
                Toast.makeText(applicationContext,json.getString("message"),Toast.LENGTH_SHORT).show()
                Log.e("DELETE ROOM","!!yes!!")
            }
        }
    }
}