package likelion.smu.com.likelion_alba

import android.app.Application
import android.provider.ContactsContract

class User : Application() {
    var memberid : String? = null
    var nickname : String? = null

    fun setmemberid(memberid : String){ this.memberid = memberid}
    fun getmemberid(): String?{return memberid}

    fun setncikname(nickname: String){this.nickname=nickname}
    fun getnickname():String?{return nickname}
}