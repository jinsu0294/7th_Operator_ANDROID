package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_participation.*
import org.json.JSONArray
import org.json.JSONObject


class Participation : AppCompatActivity() {
    var groupindex: Int = -1
    var user = User()
    var json = JSONObject()

    var response: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participation)
        user = application as User

        btnSearch.setBackgroundResource(R.drawable.search)

        // 방검색 버튼 리스너
        btnSearch.setOnClickListener {
            Asynctask().execute("0", getString(R.string.search_room), etUserStoreName.text.toString())
            Toast.makeText(this, "검색 확인 되었습니다", Toast.LENGTH_SHORT).show()
            Log.e("vresponse", "123"+response)
        }

        // 닉네임 중복 버튼 리스너
        btnCheckNickName.setOnClickListener {
            Asynctask().execute(
                "1", getString(R.string.create_room_check), groupindex.toString(), etUserNickName.text.toString(),
                user.getmemberid(), etUserPassword.text.toString()

            )
        }
        // 참가버튼 리스너
        btnParticipation.setOnClickListener {
            Asynctask().execute(
                "2",
                getString(R.string.in_room),
                groupindex.toString(),
                user.getmemberid(),
                etUserPassword.text.toString(),
                etUserNickName.text.toString()
            )

            val intent = Intent(this, UserRoomActivity::class.java)
            intent.putExtra("UserStore", etUserStoreName.text.toString())
            intent.putExtra("UserNick", etUserNickName.text.toString())
            startActivity(intent)

        }

    }

    //1번째 파라미터 = 상태, 2번째 파라미터 = 주소
    inner class Asynctask : AsyncTask<String, Void, String>() {
        var state: Int = -1 //GET_search = 0, GET_check = 1, POST_participation = 2
        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = okhttp3.OkHttpClient()
            var url = params[1]

            //GET_search(방검색), 3 = 그룹이름
            if (state == 0) {
                var groupname = params[2]
                url += "${groupname}"
                Log.e("123123","123123123:::"+url)
                Log.e("123123","123123123:::"+url.toString())
                json.optInt("GroupPid",999) //방 검색 시 방 인덱스 받아옴, 전역 변수에 넣어주어야 한다
                json.optString("GroupName","text on no value") //방 검색 시 방 이름 받아옴, 전역 변수에 넣어주어야 한다
                response = Okhttp().GET(client, url)
            }

            //GET_check(닉네임 중복), 3 = 그룹 index, 4 = 닉네임, 5 = 멤버아이디, 6 = 그룹 비밀번호
            else if (state == 1) {
                var grouppid = Integer.getInteger(params[2])
                var nickname = params[3]
                var memberid = params[4]
                var grouppw = params[5]
                url += "${grouppid}/${nickname}/${memberid}/${grouppw}"
                Log.e("123123","123123123:::"+url)
                Log.e("123123","123123123:::"+url.toString())

                response = Okhttp().GET(client, url)
            }
            //POST_participation(참여), 3 = 그룹 index, 4 = 닉네임, 5 = 멤버아이디, 6 = 그룹 비밀번호
            else if (state == 2) {
                //var grouppid = Integer.getInteger(params[2])
                var nickname = params[3]
                var memberid = params[4]
                var grouppw = params[5]
                response =
                    Okhttp().POST(client, url, CreateJson().json_in_room(groupindex, nickname, grouppw, memberid))
            }
            return response
        }

        override fun onPostExecute(result: String) {
            if (!result.isEmpty()) {
                Log.d("Part_net_test", result)
            }
            if (!result[0].equals('{') && !result[0].equals('[')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                return
            }

            //GET_search 방 검색
            if (state == 0) {
                var jsonary = JSONArray(result)
                Log.e("123", ":::GET_search")
                for (i in 0..jsonary.length() - 1) {
                    var json = JSONObject(jsonary.get(i).toString())
                    Log.e("123", ":::state")
                    groupindex = json.getInt("GroupPid")

                }
            }
            //GET_check 닉네임 중복확인
            else if (state == 1) {
                var json = JSONObject(result)
                Toast.makeText(applicationContext, json.getString("message"), Toast.LENGTH_SHORT).show()
            } else {
                return
            }
        }
    }
}