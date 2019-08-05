package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_participation.*
import kotlinx.android.synthetic.main.main_select.*

class Exit: AppCompatActivity() {

    fun setup(){
        tvMessage.text="나가시겠습니까?"
        btnLeft.text = "네"
        btnRight.text = "아니오"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_select)


        setup()

        btnLeft.setOnClickListener {
            val intent = Intent(this, UserRoom::class.java)
            startActivity(intent)
            finish()
        }

        btnRight.setOnClickListener {
            finish()
        }


    }
}