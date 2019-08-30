package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_main.*

class ExitActivity: AppCompatActivity() {

    fun setup(){
        tvMessage.text="나가시겠습니까?"
        btnLeft.text = "네"
        btnRight.text = "아니오"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_main)


        setup()

        btnLeft.setOnClickListener {
            val intent = Intent(this, UserRoomActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnRight.setOnClickListener {
            finish()
        }


    }
}