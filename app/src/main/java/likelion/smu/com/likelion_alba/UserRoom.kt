package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.user_room.*
import kotlinx.android.synthetic.main.user_room_item.*
import org.json.JSONArray
import org.json.JSONObject

class UserRoom : AppCompatActivity() {
    var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_room)
        user = application as User

        // 리사이클러뷰 어뎁터 연결
        val mAdapter = RoomRvAdapter(this)
        roomRecyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        roomRecyclerView.layoutManager = lm
        roomRecyclerView.setHasFixedSize(true)

        //리스트 값 받아옴
        Asynctask().execute("0",getString(R.string.find_room),user.getmemberid())

        // 인텐트로 넘어온 값이 있으면(그냥 스토어 이름이 있는지 없는지로 확인했음)
        if(intent.hasExtra("userStore")){
            var userStore = intent.getStringExtra("userStore")
            var userNick = intent.getStringExtra("userNickName")
            mAdapter.add("${userStore}","${userNick}")
        }

        // 가지고 있는 방이 없는 경우
        if(mAdapter.itemCount == 0){
            tvIntro.visibility = View.VISIBLE   // 추가해달라는 텍스트박스 보이게하기
            roomRecyclerView.visibility = View.INVISIBLE    //리사이클러뷰 안 보이게
        }
        //가지고 있는 방이 있는 경우
        else{
            tvIntro.visibility = View.GONE
            roomRecyclerView.visibility = View.VISIBLE
        }

        // 추가
        btnAdd.setOnClickListener {
            val intent = Intent(this, SelectMain::class.java)
            startActivity(intent)
        }

    }
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state : Int = -1 //GET_room_selete = 0,
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = okhttp3.OkHttpClient()
            var url = params[1]
            var memberid = params[2]

            //GET_room_selete
            if(state == 0){
                url = url + memberid
                response = Okhttp().GET(client,url)
            }

            return response
        }

        override fun onPostExecute(result: String) {
            if(!result.isNullOrEmpty()){
                Log.d("User_net_test",result)

            }

            if(!result[0].equals('{')&&!result[0].equals('[')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                return
            }

            var jsonary = JSONArray(result)
            //GET_room_selete
            if(state == 0){
                //참여중인 방 조회 정보 넘어옴
                //값 형태 변환 후 리스트에 넣어줘야 함

                for(i in 0..jsonary.length()-1){
                    var json = jsonary.getJSONObject(i)
                    var json1 = json.getJSONObject("member_id")
                    var member_id = json1.getString("member_id")
                    var json2 = json.getJSONObject("GroupPid")
                    var GroupPid = json2.getInt("GroupPid")
                    var GroupName = json2.getString("GroupName")
                    var Nickname = json.getString("Nickname")
                    Log.d("User_net_test","${member_id} ${GroupPid} ${GroupName} ${Nickname}")
                    //해당 정보 업로드
                }
            }
        }
    }
}
