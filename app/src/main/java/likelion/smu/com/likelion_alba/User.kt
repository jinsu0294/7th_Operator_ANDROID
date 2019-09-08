package likelion.smu.com.likelion_alba

import android.app.Application
import android.preference.PreferenceManager
import android.provider.ContactsContract

class User : Application() {
    var memberid : String? = null
    var groupPid: Int? = null


    fun setmemberid(memberid : String){this.memberid = memberid}
    fun getmemberid(): String?{return memberid}

    fun setGroupPid(groupPid: Int){this.groupPid = groupPid}
    fun getPid():Int? { return groupPid}
}