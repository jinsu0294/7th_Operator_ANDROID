package likelion.smu.com.likelion_alba

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.annotation.Dimension
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_participation.*
import org.json.JSONObject


class Making : AppCompatActivity() {

    // 레이아웃 초기화
    fun setup(button: Button){
        button.setText("중복확인")
        button.setTextColor(Color.WHITE)
        button.setTextSize(Dimension.SP,10.0f)
        button.setBackgroundResource(R.drawable.round_maincolor)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participation)

        setup(btnSearch)
        btnParticipation.setText("생성")

        btnParticipation.setOnClickListener {
            val intent = Intent(this, UserRoom::class.java)
            intent.putExtra("userStore",etUserStoreName.text.toString())
            intent.putExtra("userNickName",etUserNIckName.text.toString())
            startActivity(intent)
        }


    }
    inner class Asynctask : AsyncTask<String, Void, String>() {
        var state : Int = -1 //POST_req = 0, PUT_res = 1, DELET_daeta = 2
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])

            var client = okhttp3.OkHttpClient()
            var url = params[1]

            //POST_req, 3 = 그룹이름
            if(state == 0){
                var groupname = params[2]
                url = url + "{${groupname}}"
                response = Okhttp().GET(client, url)
            }
            //GET_check, 3 = 그룹 index, 4 = 닉네임, 5 = 멤버아이디, 6 = 그룹 비밀번호
            else if (state == 1) {
                var grouppid = params[2]
                var nickname= params[3]
                var memberid= params[4]
                var grouppw= params[5]
                url = url + "${grouppid}/${nickname}/${memberid}/${grouppw}"
                response = Okhttp().GET(client, url)
            }
            //POST_create, 3 = 그룹 이름, 4 = 그룹 비밀번호, 5 = 멤버 아이디, 6 = 닉네임
            else if (state == 2) {
                var groupname = params[2]
                var grouppw= params[3]
                var memberid= params[4]
                var nickname= params[5]
                response = Okhttp().POST(client, url, CreateJson().json_create_room(groupname,nickname,grouppw,memberid))
            }
            return response
        }

        override fun onPostExecute(result: String) {
            if(!result[0].equals("{")) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                return
            }

            var json = JSONObject(result)
            //GET_search
            if(state == 0){
                json.getInt("GroupPid") //방 검색 시 방 인덱스 받아옴, 전역 변수에 넣어주어야 한다
                json.getString("GroupName") //방 검색 시 방 이름 받아옴, 전역 변수에 넣어주어야 한다
            }
            //GET_check
            else if (state == 1) Toast.makeText(applicationContext,json.getString("message"), Toast.LENGTH_SHORT).show()
            //POST_participation
            else if (state == 2){
                Toast.makeText(applicationContext,json.getString("message"),Toast.LENGTH_SHORT).show()
            }
        }
    }

}
