package likelion.smu.com.likelion_alba

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ListView
import kotlinx.android.synthetic.main.user_room.*
import kotlinx.android.synthetic.main.user_room_item.*

class UserRoom : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_room)

        // 리사이클러뷰 어뎁터 연결
        val mAdapter = RoomRvAdapter(this)
        roomRecyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        roomRecyclerView.layoutManager = lm
        roomRecyclerView.setHasFixedSize(true)

        // 인텐트로 넘어온 값이 있으면(그냥 스토어 이름이 있는지 없는지로 확인했음)
        if(intent.hasExtra("userStore")){
            var userStore = intent.getStringExtra("userStore")
            var userNick = intent.getStringExtra("userNickName")
            mAdapter.add("${userStore}","${userNick}")
        }

        // 가지고 있는 방이 없는 경우
        if(mAdapter.itemCount == 0){
            tvIntro.visibility = View.VISIBLE   // 추가해달라는 텍스트박스 보이게하기
            roomRecyclerView.visibility = View.INVISIBLE    //리사이클러뷰 안 보이게
        }
        //가지고 있는 방이 있는 경우
        else{
            tvIntro.visibility = View.GONE
            roomRecyclerView.visibility = View.VISIBLE
        }

        // 추가
        btnAdd.setOnClickListener {
            val intent = Intent(this, SelectMain::class.java)
            startActivity(intent)
        }




    }
}
