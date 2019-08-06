package likelion.smu.com.likelion_alba

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.Dimension
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_participation.*


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
            finish()
        }


    }

}
