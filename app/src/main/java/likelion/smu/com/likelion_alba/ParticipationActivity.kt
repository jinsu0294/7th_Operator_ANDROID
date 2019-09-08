package likelion.smu.com.likelion_alba

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_participation.*
import kotlinx.android.synthetic.main.item_schedule_add.view.*
import org.json.JSONArray
import org.json.JSONObject


class ParticipationActivity : AppCompatActivity() {
    var groupindex: Int = -1
    var user = User()
    var json = JSONObject()

    var response: String? = null

    var searchRoom = arrayListOf<SearchRoom>()

    var search = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participation)

        btnSearch.text = "검색"
        spSpinner.visibility = View.VISIBLE

        // 검색 버튼 리스너
        btnSearch.setOnClickListener {
            if(etUserStoreName.text.isEmpty()){
                Toast.makeText(this,"입력값이 없습니다.",Toast.LENGTH_SHORT).show()
            }else{
                if(search == -1){
                    search = 1
                }else{
                    searchRoom.removeAll(searchRoom)
                    search = -1
                }
                Asynctask().execute("0", getString(R.string.search_room), etUserStoreName.text.toString())
                Log.e("vresponse", "123"+response)
            }
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

        spSpinner.onItemSelectedListener = object: AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                etUserStoreName.setText(searchRoom[position].groupName)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1000 -> setResult(Activity.RESULT_OK)
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
                json.optInt("GroupPid",999) //방 검색 시 방 인덱스 받아옴, 전역 변수에 넣어주어야 한다
                json.optString("GroupName","text on no value") //방 검색 시 방 이름 받아옴, 전역 변수에 넣어주어야 한다
                response = Okhttp().GET(client, url)
                Log.e("SEARCHROOM RESPONSE",response.toString())
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
                if(response == "[]"){
                    var choice = arrayListOf("결과 없음")
                    val sAdapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,choice)
                    spSpinner.adapter = sAdapter
                }
                else{
                    for (i in 0..jsonary.length() - 1) {
                        var json = jsonary.getJSONObject(i)
                        var groupPid = json.getInt("GroupPid")
                        var groupName = json.getString("GroupName")
                        Log.e("Search_Room","${groupPid}, ${groupName}")
                        searchRoom.add(SearchRoom(groupPid,groupName))
                        Log.e("SearchRoomSize",searchRoom.size.toString())
                    }

                    var choice = arrayListOf<String>()
                    for(i in 0..searchRoom.size-1){
                        choice.add(i,searchRoom[i].groupName)
                    }
                    val sAdapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,choice)
                    spSpinner.adapter = sAdapter
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