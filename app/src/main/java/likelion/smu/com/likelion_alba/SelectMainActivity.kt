package likelion.smu.com.likelion_alba

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_select_main.*


class SelectMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_main)

        Log.e("SELECT MAIN INIT","!!yes!!")

        // 참여 버튼
        btnLeft.setOnClickListener {
            val intent = Intent(this, ParticipationActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 생성 버튼
        btnRight.setOnClickListener {
            val intent = Intent(this,MakingActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}