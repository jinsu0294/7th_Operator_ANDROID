package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signin.*
import okhttp3.OkHttpClient
import org.json.JSONObject

class SigninActivity : AppCompatActivity() {

    var IdCheck = false// 아이디중복 확인 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        // 아이디 중복 체크
        btnCheckId.setOnClickListener {
            Asynctask().execute("0","https://boiling-sands-41507.herokuapp.com/member/idcheck/",etUserId.text.toString())
        }

        // 시작 버튼
        btnStart.setOnClickListener {
            if(IdCheck){
                Asynctask().execute("1","https://boiling-sands-41507.herokuapp.com/member/register",etUserId.text.toString())
                val intent = Intent(this, SelectMain::class.java)
                startActivity(intent)
                //finish()
            }
            else
                Toast.makeText(this,"중복확인이 필요합니다",Toast.LENGTH_SHORT).show()
        }

    }

    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state : Int = -1 //GET = 0, POST = 1
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = OkHttpClient()
            var url = params[1]
            var id = params[2]

            if(state == 0){ //GET_idcheck
                url = url + "{${id}}"
                response = Okhttp().GET(client, url)
            }
            else if (state == 1){ //POST_register
                response = Okhttp().POST(client,url,CreateJson().json_signup(id))
            }
            return response
        }

        override fun onPostExecute(result: String) {
            if(!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, result, Toast.LENGTH_LONG).show()
                return
            }

            var json = JSONObject(result)
            if(state == 0) //GET_idcheck
                Toast.makeText(applicationContext,json.getString("message"),Toast.LENGTH_SHORT).show()
            if(json.getString("message").equals("아이디를 사용할 수 있습니다."))
                IdCheck = true
            else
                IdCheck = false
        }
    }
}
