package likelion.smu.com.likelion_alba

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signin.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.net.UnknownServiceException

class SigninActivity : AppCompatActivity() {

    var IdCheck = true// 아이디중복 확인 변수(중복일때 true, 중복이 아니면 false)

    //아이디저장하고 액티비티 전환
    fun saveId(userId:String){
        // 아이디 저장
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putString("userId",userId).apply()
        editor.commit()

        val user = application as User
        user.setmemberid(userId)

        val intent = Intent(this,UserRoom::class.java)
        startActivity(intent)
        finish()
    }

    // 아이디 있는지 없는지 확인.
    fun idCheck(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val result = pref.getString("userId","none")
        val user = application as User
        user.setmemberid(result)


        Log.e("userId","${result}")
        Log.e("USER","${user.getmemberid()}")

        if(result != "none"){  // 아이디가 있으면 UserRoom으로 이동
            // 토스트 메시지 띄우고 UserRoom으로 이동
            Toast.makeText(this,"${result}로그인됨",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,UserRoom::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        //아이디 있는지 없는지 체크
        idCheck()

        // 아이디 중복 체크
        btnCheckId.setOnClickListener {
            // 입력값이 없으면(입력값이 없습니다)
            if(etUserId.text.isEmpty()){
                Toast.makeText(this,getString(R.string.noInput),Toast.LENGTH_SHORT).show()
            }else{ // 입력값이 있으면
                Asynctask().execute("0",getString(R.string.sign_up_checkid),etUserId.text.toString())
            }
        }

        // 시작 버튼
        btnStart.setOnClickListener {
            // 입력값이 없으면(입력값이 없습니다)
            if(etUserId.text.isEmpty()){
                Toast.makeText(this,getString(R.string.noInput),Toast.LENGTH_SHORT).show()
            }else{// 입력값이 있으면
                // 입력값을 DB에 보냄
                Asynctask().execute("1",getString(R.string.sign_up),etUserId.text.toString())
                // 중복이 아니면
                if(!IdCheck){
                    // 아이디 저장하고 액티비티 전환
                    saveId(etUserId.text.toString())
                }else{  //중복이 맞으면(중복입니다)
                    Toast.makeText(this,getString(R.string.againInput), Toast.LENGTH_SHORT).show()
                }
            }
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
                url = url + "${id}"
                response = Okhttp().GET(client, url)
            }
            else if (state == 1){ //POST_register
                response = Okhttp().POST(client,url,CreateJson().json_signup(id))
            }
            return response
        }

        override fun onPostExecute(result: String) {
            Log.e("network1",result)
            if(!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                //Log.d("network1",result)
                return
            }
            Log.e("network1",result)
            var json = JSONObject(result)
            if(state == 0) { //GET_idcheck (아이디 중복확인)
                Toast.makeText(applicationContext, json.getString("message"), Toast.LENGTH_SHORT).show()
                if (json.getString("message").equals("아이디를 사용할 수 있습니다.")){
                    IdCheck = false // 중복이 아니면
                }else{
                    IdCheck = true  // 중복이 맞으면
                }
            }
        }
    }
}
