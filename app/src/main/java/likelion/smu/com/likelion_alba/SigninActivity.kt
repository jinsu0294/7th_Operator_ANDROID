package likelion.smu.com.likelion_alba

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {

    var IdCheck = false// 아이디중복 확인 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        // 아이디 중복 체크
        btnCheckId.setOnClickListener {
            if(IdCheck == false){ // 중복 아닐때
                Toast.makeText(this, "사용 가능한 아이디입니다 :-)",Toast.LENGTH_SHORT).show()
                btnStart.isClickable = true
            }else{ //중복일 때(IdCheck == true)
                Toast.makeText(this, "*이미 존재합니다:-(*",Toast.LENGTH_SHORT).show()
                btnStart.isClickable = false
            }
        }

        // 시작 버튼
        btnStart.setOnClickListener {
            val intent = Intent(this, SelectMain::class.java)
            startActivityForResult(intent, 101)
            finish()
        }
    }
}
