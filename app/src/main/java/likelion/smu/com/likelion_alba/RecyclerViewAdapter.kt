package likelion.smu.com.likelion_alba

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.Toast
import org.json.JSONArray

import java.util.*





class RecyclerViewAdapter(val mainActivity: MainActivity) : RecyclerView.Adapter<MyViewHolder>() {


    //baseCalendar.data[position].toString() == 오늘 날짜
    //baseCalendar.getYear() == 달력에 나오는 년도
    //baseCalendar.getMonth() == 달력에 나오는 월

    var dailySchedule : MutableList<DailySchedule> = mutableListOf()
    lateinit var SchedulPid :String
    lateinit var Date :String
    lateinit var StartHour :String
    lateinit var StartMinute :String
    lateinit var EndHour :String
    lateinit var EndMinute :String
    lateinit var Nickname :String
    lateinit var SubstituteTF :String
    lateinit var GroupPid :String
    lateinit var memder_id :String

    val baseCalendar = BaseCalendar()
    //modify

    lateinit var mdate :String
    val myyear:Int =2019
    val mymonth:Int=8
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
        mdate = baseCalendar.getYear()+"-"+baseCalendar.getMonth()+"-"+baseCalendar.data[position].toString()
        Asynctask().execute("http://ec2-54-180-32-25.ap-northeast-2.compute.amazonaws.com:8000//schedule/?GroupPid=")
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



        if (mdate==Date&&dailySchedule[0].Nick!=null){
            holder.time1.text = dailySchedule[0].Nick
            holder.time1.setBackgroundResource(R.color.color2)
        }
        if (mdate==Date&&dailySchedule[1].Nick!=null) {
            holder.time2.text = dailySchedule[1].Nick
            holder.time2.setBackgroundResource(R.color.color3)
        }
        if (mdate==Date&&dailySchedule[2].Nick!=null) {
            holder.time3.text = dailySchedule[2].Nick
            holder.time3.setBackgroundResource(R.color.color4)
        }


        //val mmmdate = baseCalendar.data[position].toString()
        holder.tv_date.setOnClickListener {
            yy.toString()+baseCalendar.calendar.time.year.toString()
            Log.d("TAG", (baseCalendar.getYear())+" / "+(baseCalendar.getMonth()))

            // 스케줄 추가 액티비티로 전환
            val intetnt = Intent(mainActivity,ScheduleAdd::class.java)
            holder.tv_date.context.startActivity(intetnt)
            Log.d("TAG",mdate)
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


    inner class Asynctask: AsyncTask<String, Void, String>() {
        var state : Int = -1 //GET_selete_month = 0
        var response : String? = null


        override fun doInBackground(vararg params: String): String? {

            state = Integer.parseInt(params[0])

            var client = okhttp3.OkHttpClient()
            var url = params[1]
            var grouppid = params[2]
            var date = params[3]

            //GET_selete_month
            if (state == 0){
                url.plus("${grouppid}&Date=${date}")
                response = Okhttp().GET(client,url)
            }
            return response
        }

        override fun onPostExecute(result: String) {
            if(!result[0].equals('{')) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료

                return
            }

            var jsonary = JSONArray(result)

            if (state == 0){
                for(i in 0..jsonary.length()-1){
                    var json = jsonary.getJSONObject(i)
                    SchedulPid = json.getString("SchedulePid")
                    Date = json.getString("Date")
                    StartHour = json.getString("StartHour")
                    StartMinute = json.getString("StartMinute")
                    EndHour = json.getString("EndHour")
                    EndMinute = json.getString("EndMinute")
                    Nickname = json.getString("Nickname")
                    SubstituteTF = json.getString("SubstituteTF")
                    GroupPid = json.getString("GroupPid")
                    memder_id = json.getString("member_id")

                    dailySchedule[i].Nick=Nickname
                }
            }
        }
    }
}