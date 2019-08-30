package likelion.smu.com.likelion_alba

import android.app.Application
import android.preference.PreferenceManager
import android.provider.ContactsContract

class User : Application() {
    var memberid : String? = null

    var rooms = ArrayList<Room>()

    fun setmemberid(memberid : String){this.memberid = memberid}
    fun getmemberid(): String?{return memberid}

    // 방 추가
    fun addRooms(GroupPid:Int, GroupName:String, NickName:String){
        rooms.add(Room(GroupPid, GroupName, NickName))
    }
//    fun loadRooms():Int{
//        return rooms.size
//    }
//

}