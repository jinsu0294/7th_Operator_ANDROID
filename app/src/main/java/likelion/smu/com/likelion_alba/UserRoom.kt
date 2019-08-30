package likelion.smu.com.likelion_alba

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_room)

        val user = application as User

        // 방 조회
        Asynctask().execute("0",getString(R.string.find_room),user.getmemberid())

        // 추가
        btnAdd.setOnClickListener {
            val intent = Intent(this,SelectMain::class.java)
            startActivity(intent)
            finish()
        }


    }

    // execute()로 실행됨
    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state : Int = -1 //GET_room_selete = 0,
        var response : String? = null

        // 전달된 값 사용하여 response 리턴함
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

        // doInBackground에서 리턴한 response 사용.
        // 이를 통해 스레드 작업 끝났을 때 동작을 구현함
        override fun onPostExecute(result: String) {

            if(!result.isNullOrEmpty()){
                Log.d("User_net_test",result)
            }

            //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
            if(!result[0].equals('{')&&!result[0].equals('[')) {
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                return
            }

            var jsonary = JSONArray(result)
            var room = arrayListOf<Room>()
            val mAdapter = RoomRvAdapter(applicationContext, room)

            roomRecyclerView.adapter = mAdapter


            // 사용자방 조회하여 추가합니다.
            for(i in 0..jsonary.length()-1){
                var json = jsonary.getJSONObject(i)
                var json1 = json.getJSONObject("member_id")
                var member_id = json1.getString("member_id")
                var json2 = json.getJSONObject("GroupPid")
                var GroupPid = json2.getInt("GroupPid")
                var GroupName = json2.getString("GroupName")
                var Nickname = json.getString("Nickname")

                Log.e("User_net_test","${member_id} ${GroupPid} ${GroupName} ${Nickname}")
                room.add(Room(GroupPid, GroupName, Nickname))

//                Log.e("User의 rooms", "${i} -> ${user.rooms[i].groupPid}")
            }

            //GET_room_selete 방조회
            if(state == 0){
                // 리사이클러뷰 adapter 연결
                val mAdapter = RoomRvAdapter(applicationContext, room)
                roomRecyclerView.adapter = mAdapter

                val lm = LinearLayoutManager(applicationContext)
                roomRecyclerView.layoutManager = lm
                roomRecyclerView.setHasFixedSize(true)


                // 가지고 있는 방이 없는 경우
                if(mAdapter.itemCount == 0) {
                    tvIntro.visibility = View.VISIBLE   // 추가해달라는 텍스트박스 보이게하기
                    roomRecyclerView.visibility = View.INVISIBLE    //리사이클러뷰 안 보이게
                }else{  //가지고 있는 방이 있는 경우
                    tvIntro.visibility = View.GONE
                    roomRecyclerView.visibility = View.VISIBLE  //(아이템이 추가된 리사이클러뷰를 보여줍니다)
                }
            }
        }
    }
}
