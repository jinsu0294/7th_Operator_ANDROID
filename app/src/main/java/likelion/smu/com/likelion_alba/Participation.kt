package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_participation.*


class Participation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participation)

        btnSearch.setBackgroundResource(R.drawable.search)

        btnParticipation.setOnClickListener {
            val intent = Intent(this,UserRoom::class.java)
            intent.putExtra("userStore",etUserStoreName.text.toString())
            intent.putExtra("userNickName",etUserNIckName.text.toString())
            startActivity(intent)
        }

    }
}