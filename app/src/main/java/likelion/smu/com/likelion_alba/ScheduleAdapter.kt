package likelion.smu.com.likelion_alba

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

var userSchedule : MutableList<ScheduleUser> = mutableListOf()

class ScheduleAdapter(val context: Context) : RecyclerView.Adapter<ScheduleAdapter.Holder>(){


    override fun onCreateViewHolder(context: ViewGroup, position: Int): Holder {
        val view = LayoutInflater.from(context.context).inflate(R.layout.schedule_add_item,context,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return userSchedule.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(userSchedule[position], context)


    }

    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(schedule: ScheduleUser, context: Context){
            val startHour = itemView.findViewById<TextView>(R.id.tvStartTimeHour)
            val startMin = itemView.findViewById<TextView>(R.id.tvStartTimeMin)
            val endHour = itemView.findViewById<TextView>(R.id.tvEndTimeHour)
            val endMin = itemView.findViewById<TextView>(R.id.tvEndTimeMin)
            val nick=itemView.findViewById<TextView>(R.id.tvNick)

            val buttonOne = itemView.findViewById<Button>(R.id.btnTop)
            val buttonTwo = itemView.findViewById<Button>(R.id.btnBottom)

            startHour.text = schedule.StartTimeHour
            startMin.text =schedule.StartTimeMin
            endHour.text = schedule.EndTimeHour
            endMin.text = schedule.EndTimeMin
            nick.text = schedule.Nickname

            buttonOne.text = "대타요청"
            buttonTwo.text = "삭제"
        }
    }

    fun add(startHour:String, startMin:String, endHour:String, endMin:String, nick:String){
        userSchedule.add(ScheduleUser(0,0,"0","0000-00-00",startHour,startMin,endHour,endMin,"0",nick))
    }
}