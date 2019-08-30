package likelion.smu.com.likelion_alba.adapter

import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_daeta.view.*
import likelion.smu.com.likelion_alba.CreateJson
import likelion.smu.com.likelion_alba.DaetaSchedule
import likelion.smu.com.likelion_alba.Okhttp
import likelion.smu.com.likelion_alba.R

class DaetaAdapter : RecyclerView.Adapter<DaetaAdapter.Holder>(){
    val daetaSchedule : MutableList<DaetaSchedule> = mutableListOf(
        DaetaSchedule(
            "123", "123", "123",
            "123", "123", "123", "123"
        )
    )
    override fun onCreateViewHolder(parant: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(parant.context).inflate(R.layout.item_daeta,parant,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return daetaSchedule.size
    }

    override fun onBindViewHolder(holder: Holder, p1: Int) {
        daetaSchedule[p1].let {item->

            holder.date.text = item.date
            holder.start_time_hour.text = item.starttimehour
            holder.start_time_min.text = item.starttimemin
            holder.end_time_hour.text = item.endtimehour
            holder.end_time_in.text = item.endtimemin
            holder.seek_nick.text = item.seeknick
            holder.daeta_nick.text = item.daetanick

        }
        holder.icon.setBackgroundResource(R.drawable.light_selected)

    }

    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        val date = itemView.tvDate
        val start_time_hour = itemView.tvStartTimeHour
        val start_time_min = itemView.tvStartTimeMin
        val end_time_hour = itemView.tvEndTimeHour
        val end_time_in = itemView.tvEndTimeMin
        val seek_nick = itemView.tvSeekNick
        val daeta_nick = itemView.tvDaetaNick
        val icon = itemView.ibIcon

    }
    class Asynctask: AsyncTask<String, Void, String>() {
        var state : Int = -1 // POST _req=0, PUT_res = 1,DELETE_req = 2
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])

            var client = okhttp3.OkHttpClient()
            var url = params[1]


            //POST_req =0
            if(state == 0){
                var schedulpid = Integer.getInteger(params[2])
                var requestor=params[3]
                var grouppid = Integer.getInteger(params[4])

                response = Okhttp().POST(client,url,
                    CreateJson().json_req_re(schedulpid,requestor,grouppid))
            }
            //PUT_res = 1
            else if(state == 1) {
                var substitutepid = Integer.getInteger(params[2])
                var schedulepid = Integer.getInteger(params[3])
                var grouppid = Integer.getInteger(params[4])
                var requestor=params[5]
                var responsor=params[6]
                var member_id=params[7]

                url = url + "${schedulepid}"

                response = Okhttp().PUT(client,url,
                    CreateJson().json_res_re(substitutepid,schedulepid,grouppid,requestor,responsor,member_id))
            }
            //DELETE_re = 2
            else if(state == 2){
                var substitutepid = Integer.getInteger(params[2])
                url.plus("${substitutepid}")
                response = Okhttp().DELETE(client,url)
            }

            return response
        }

    }
}