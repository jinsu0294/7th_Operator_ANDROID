package likelion.smu.com.likelion_alba

import android.support.v7.widget.RecyclerView

import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_schedule.view.*



class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), LayoutContainer{
    override val containerView: View?
        get() = itemView
    val tv_date = itemView.tv_date
    val time1=itemView.time1
    val time2=itemView.time2
    val time3=itemView.time3
}