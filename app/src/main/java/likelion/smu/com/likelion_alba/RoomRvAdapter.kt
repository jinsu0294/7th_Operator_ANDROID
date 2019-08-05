package likelion.smu.com.likelion_alba

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.user_room_item.view.*

var roomList = arrayListOf<Room>()

class RoomRvAdapter(val context: Context): RecyclerView.Adapter<RoomRvAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_room_item,parent,false)
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

            store.text =  room.storeName
            nickname.text = room.nickName

            // 아이템뷰 클릭
            itemView.setOnClickListener {
                val intent = Intent(context,MainActivity::class.java)
                itemView.context.startActivity(intent)
            }

            // 나가기
            exit.setOnClickListener {
                roomList.removeAt(index)
                val intent = Intent(context, Exit::class.java)
                itemView.context.startActivity(intent)
            }

        }
    }

    // roomList item 추가
    fun add(store:String, Nick:String){
        roomList.add(Room(store,Nick))
    }

}

