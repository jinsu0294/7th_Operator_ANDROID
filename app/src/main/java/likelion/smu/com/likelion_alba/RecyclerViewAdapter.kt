package likelion.smu.com.likelion_alba

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.Toast

import java.util.*





class RecyclerViewAdapter(val mainActivity: MainActivity) : RecyclerView.Adapter<MyViewHolder>() {

    val baseCalendar = BaseCalendar()
    //modify
    val myyear:Int =2019
    val mymonth:Int=8
    val mydate :Int= 2
    val mydate2:Int= 2
    val mydate3:Int= 2
    val yy :Int=1900

    init {
        baseCalendar.initBaseCalendar {
            refreshView(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return MyViewHolder(view)
    }
    override fun getItemCount(): Int {
        return BaseCalendar.LOW_OF_CALENDAR * BaseCalendar.DAYS_OF_WEEK
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (position % BaseCalendar.DAYS_OF_WEEK == 0) {
            holder.tv_date.setTextColor(Color.parseColor("#ff1200"))
        } else {
            holder.tv_date.setTextColor(Color.parseColor("#676d6e"))
        }

        if (position < baseCalendar.prevMonthTailOffset || position >= baseCalendar.prevMonthTailOffset + baseCalendar.currentMonthMaxDate) {
            holder.tv_date.alpha = 0.3f
        } else {
            holder.tv_date.alpha = 1f
        }
        holder.tv_date.text = baseCalendar.data[position].toString()

        holder.tv_date.setOnClickListener {
            // 일정추가

        }

        if (mydate.toString() == baseCalendar.data[position].toString()&&mymonth.toString()==baseCalendar.getMonth()
            &&myyear.toString()==baseCalendar.getYear()){

            holder.time1.text = "진수"
            holder.time1.setBackgroundResource(R.color.color2)
        }
        if (mydate2.toString() == baseCalendar.data[position].toString()&&mymonth.toString()==baseCalendar.getMonth()
            &&myyear.toString()==baseCalendar.getYear()) {
            holder.time2.text = "tina"
            holder.time2.setBackgroundResource(R.color.color3)
        }
        if (mydate3.toString() == baseCalendar.data[position].toString()&&mymonth.toString()==baseCalendar.getMonth()
            &&myyear.toString()==baseCalendar.getYear()) {
            holder.time3.text = "정민"
            holder.time3.setBackgroundResource(R.color.color4)
        }

        val mmmdate = baseCalendar.data[position].toString()
        holder.tv_date.setOnClickListener {
            yy.toString()+baseCalendar.calendar.time.year.toString()
            Log.d("TAG", (baseCalendar.getYear())+" / "+(baseCalendar.getMonth()))

            // 스케줄 추가 액티비티로 전환
            val intetnt = Intent(mainActivity,ScheduleAdd::class.java)
            holder.tv_date.context.startActivity(intetnt)
        }

    }
    fun changeToPrevMonth() {
        baseCalendar.changeToPrevMonth {
            refreshView(it)
        }
    }

    fun changeToNextMonth() {
        baseCalendar.changeToNextMonth {
            refreshView(it)
        }
    }

    private fun refreshView(calendar: Calendar) {
        notifyDataSetChanged()
        mainActivity.refreshCurrentMonth(calendar)
    }


}
