package likelion.smu.com.likelion_alba.adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.item_user_room.view.*
import likelion.smu.com.likelion_alba.*


class RoomRvAdapter(val context: Context, val roomList:ArrayList<Room>): RecyclerView.Adapter<RoomRvAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user_room,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(roomList[position], context, position)

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(room: Room, context: Context, index: Int){
            val store = itemView.findViewById<TextView>(R.id.tvUserStore)
            val nickname =  itemView.findViewById<TextView>(R.id.tvUserNick)
            val exit = itemView.findViewById<Button>(R.id.btnExit)

            store.text =  room.groupName
            nickname.text = room.nickName

            // 아이템뷰 클릭 리스너
            itemView.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                itemView.context.startActivity(intent)
            }

            // 나가기 버튼 리스터
            exit.setOnClickListener {
                val storeName = itemView.tvUserStore.text.toString()
                val intent = Intent(context, ExitActivity::class.java)
                // 클릭한 아이템의 스토어네임을 인텐트로 넘겨줌
                intent.putExtra("storeName",storeName)
                itemView.context.startActivity(intent)
            }

        }
    }

}

