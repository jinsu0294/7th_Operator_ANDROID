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
import org.json.JSONObject

class UserRoom : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_room)

        // 리사이클러뷰 어뎁터 연결
        val mAdapter = RoomRvAdapter(this)
        roomRecyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        roomRecyclerView.layoutManager = lm
        roomRecyclerView.setHasFixedSize(true)

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



        //방 나가기 리스너에 넣어주세요 필요한 파라미터 GroupPid, Member_id



    }
    inner class AsyncTast: AsyncTask<String, Void, String>() {
        var state : Int = -1 //GET_room_selete = 0, DELETE_room_out = 1
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = okhttp3.OkHttpClient()
            var url = params[1]
            var memberid = params[2]

            //GET_room_selete
            if(state == 0){
                url.plus("{${memberid}}")
                response = Okhttp().GET(client,url)
            }
            //DELETE_room_out
            else if(state == 1){
                var groupid = params[3]
                url.plus("{${groupid}}/{${memberid}}")
                response = Okhttp().DELETE(client,url)
            }

            return response
        }

        override fun onPostExecute(result: String) {
            if(!result[0].equals("{")) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                return
            }

            var json = JSONObject(result)
            //GET_room_selete
            if(state == 0){
                //참여중인 방 조회 정보 넘어옴
            }
            //DELETE_room_out
            else if(state == 1) Toast.makeText(applicationContext,json.getString("message"),Toast.LENGTH_SHORT).show()
        }
    }
}
