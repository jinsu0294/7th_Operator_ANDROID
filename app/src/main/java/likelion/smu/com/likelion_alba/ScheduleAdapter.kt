package likelion.smu.com.likelion_alba

import android.content.Context
import android.os.AsyncTask
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

    class Asynctask: AsyncTask<String, Void, String>() {
        var state: Int = -1 // POST _req=0, PUT_res = 1,DELETE_req = 2
        var response: String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])

            var client = okhttp3.OkHttpClient()
            var url = params[1]


            //POST_req =0
            if (state == 0) {
                var grouppid = Integer.getInteger(params[2])
                var member_id = params[3]
                var nickname = params[4]
                var date = params[5]
                var starthour = Integer.getInteger(params[6])
                var startminute = Integer.getInteger(params[7])
                var endhour = Integer.getInteger(params[8])
                var endminute = Integer.getInteger(params[9])

                response = Okhttp().POST(
                    client,
                    url,
                    CreateJson().json_add_sche(
                        grouppid,
                        member_id,
                        nickname,
                        date,
                        starthour,
                        startminute,
                        endhour,
                        endminute
                    )
                )
            }
            //DELETE_sche = 1
            else if (state == 1) {
                var substitutepid = Integer.getInteger(params[2])
                url.plus("${substitutepid}")
                response = Okhttp().DELETE(client, url)
            }
            return response
        }


    }
}