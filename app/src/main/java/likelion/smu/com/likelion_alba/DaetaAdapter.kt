package likelion.smu.com.likelion_alba

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.daeta_item.view.*

class DaetaAdapter : RecyclerView.Adapter<DaetaAdapter.Holder>(){
    val daetaSchedule : MutableList<DaetaSchedule> = mutableListOf(DaetaSchedule("123","123","123",
        "123","123","123","123"))
    override fun onCreateViewHolder(parant: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(parant.context).inflate(R.layout.daeta_item,parant,false)
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
}