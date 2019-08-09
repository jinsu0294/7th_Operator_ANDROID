package likelion.smu.com.likelion_alba

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.annotation.Dimension
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_participation.*
import okhttp3.OkHttpClient
import org.json.JSONObject


class Making : AppCompatActivity() {

    var storeCheck = false // 가게이름 중복확인 변수


    // 버튼 초기화
    fun setup(button: Button){
        button.setText("중복확인")
        button.setTextColor(Color.WHITE)
        button.setTextSize(Dimension.SP,10.0f)
        button.setBackgroundResource(R.drawable.round_maincolor)
    }


    // 사용자 아이디 있을 경우 memberid값 받아와서 return
    fun checkUserId():String?{
        var user:User = application as User
        var result:String? = user.getmemberid()

        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participation)

        //닉네임 중복확인 버튼 없앰
        btnCheckNickName.visibility = View.GONE

        // 버튼 설정
        setup(btnSearch)

        // 버튼 텍스트 "생성"으로 설정
        btnParticipation.setText("생성")

        // 가게이름 중복확인 버튼 리스너 (state 0)
        btnSearch.setOnClickListener {
            Asynctask().execute("0",getString(R.string.room_name_check),etUserStoreName.text.toString())
        }

        // 생성(방만들기) 버튼 리스너 (state 1)
        btnParticipation.setOnClickListener {
            var user : User = application as User
            Asynctask().execute("1",getString(R.string.creat_room),etUserStoreName.text.toString(),etUserPassword.text.toString(),user.getmemberid(),etUserNickName.text.toString())
            val intent = Intent(this,UserRoom::class.java)
            intent.putExtra("userStore",etUserStoreName.text.toString())
            intent.putExtra("userNick",etUserNickName.text.toString())
            startActivity(intent)
            finish()
        }

    }
    inner class Asynctask : AsyncTask<String, Void, String>() {
        var state : Int = -1 //GET_search = 0, GET_check = 1, POST_create = 2
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = OkHttpClient()
            var url = params[1]

            //GET_search(가게이름 중복확인), 3 = 그룹이름
            if(state == 0){
                var groupname = params[2]
                url = url + "${groupname}"
                response = Okhttp().GET(client, url)
            }
            //POST_create(생성), 3 = 그룹 이름, 4 = 그룹 비밀번호, 5 = 멤버 아이디, 6 = 닉네임
            else if (state == 1) {
                var groupname = params[2]
                var grouppw= params[3]
                var memberid= params[4]
                var nickname= params[5]
                response = Okhttp().POST(client, url, CreateJson().json_create_room(groupname,grouppw,memberid,nickname))
            }
            return response
        }

        override fun onPostExecute(result: String) {
            //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
            if(!result[0].equals('{')) {
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                return
            }

            var json = JSONObject(result)
            //GET_search(가게이름 중복확인)
            if(state == 0){
                Toast.makeText(applicationContext,json.getString("message"),Toast.LENGTH_SHORT).show()
                //json.getInt("GroupPid") //방 검색 시 방 인덱스 받아옴, 전역 변수에 넣어주어야 한다
                //json.getString("GroupName") //방 검색 시 방 이름 받아옴, 전역 변수에 넣어주어야 한다
            }
            //POST_participation(방만들기)
            else if (state == 1){
                Toast.makeText(applicationContext,json.getString("message"),Toast.LENGTH_SHORT).show()
            }
        }
    }

}