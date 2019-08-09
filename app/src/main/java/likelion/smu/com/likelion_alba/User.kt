package likelion.smu.com.likelion_alba

import android.app.Application

class User : Application() {
    var memberid : String? = null

    fun setmemberid(memberid : String){ this.memberid = memberid}
    fun getmemberid(): String?{return memberid}

}