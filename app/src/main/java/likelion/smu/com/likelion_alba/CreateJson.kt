package likelion.smu.com.likelion_alba

import org.json.JSONObject

class CreateJson{

    fun json_signup(member_id:String?):String?{

        var JSONObject= JSONObject()
        JSONObject.put("member_id",member_id)

        return JSONObject.toString()
    }

    fun json_in_room(GroupPid:Int?, member_id: String?, GroupPassword:String?,Nickname:String?):String?{

        var JSONObject= JSONObject()
        JSONObject.put("GroupPid",GroupPid)
        JSONObject.put("member_id",member_id)
        JSONObject.put("GroupPassword",GroupPassword)
        JSONObject.put("Nickname",Nickname)

        return JSONObject.toString()
    }
    fun json_out_room(GroupPid:Int?, member_id: String?):String?{

        var JSONObject= JSONObject()
        JSONObject.put("GroupPid",GroupPid)
        JSONObject.put("member_id",member_id)

        return JSONObject.toString()
    }
    fun json_create_room(GroupName:String?,GroupPassword:String?,member_id: String?,Nickname:String?):String?{

        var JSONObject= JSONObject()

        JSONObject.put("GroupName",GroupName)
        JSONObject.put("member_id",member_id)
        JSONObject.put("GroupPassword",GroupPassword)
        JSONObject.put("Nickname",Nickname)

        return JSONObject.toString()
    }
    fun json_edit_sche(GroupPid:Int?, member_id: String?,Nickname:String?,Date:String?,StartHour:Int?,StartMinute:Int?,EndHour:Int?,EndMinute:Int?):String?{

        var JSONObject= JSONObject()

        JSONObject.put("GroupPid",GroupPid)
        JSONObject.put("member_id",member_id)
        JSONObject.put("Nickname",Nickname)
        JSONObject.put("Date",Date)
        JSONObject.put("StartHour",StartHour)
        JSONObject.put("StartMinute",StartMinute)
        JSONObject.put("EndHour",EndHour)
        JSONObject.put("EndMinute",EndMinute)

        return JSONObject.toString()
    }

    fun json_delete_sche(SchedulePid:Int?):String?{

        var JSONObject= JSONObject()

        JSONObject.put("SchedulePid",SchedulePid)

        return JSONObject.toString()
    }
    fun json_add_sche(GroupPid:Int?, member_id: String?,Nickname:String?,Date:String?,StartHour:Int?,StartMinute:Int?,EndHour:Int?,EndMinute:Int?):String?{

        var JSONObject= JSONObject()

        JSONObject.put("GroupPid",GroupPid)
        JSONObject.put("member_id",member_id)
        JSONObject.put("Nickname",Nickname)
        JSONObject.put("Date",Date)
        JSONObject.put("StartHour",StartHour)
        JSONObject.put("StartMinute",StartMinute)
        JSONObject.put("EndHour",EndHour)
        JSONObject.put("EndMinute",EndMinute)

        return JSONObject.toString()
    }
    fun json_req_re(SchedulePid: Int?,Requestor:String?, GroupPid: Int?):String?{

        var JSONObject= JSONObject()

        JSONObject.put("SchedulePid",SchedulePid)
        JSONObject.put("Requestor",Requestor)
        JSONObject.put("GroupPid",GroupPid)

        return JSONObject.toString()
    }

    fun json_res_re(SubstitutePid: Int?,SchedulePid: Int?,GroupPid: Int?,Requestor: String?,Responsor:String?,member_id: String?):String?{

        var JSONObject= JSONObject()

        JSONObject.put("SubstitutePid",SubstitutePid)
        JSONObject.put("SchedulePid",SchedulePid)
        JSONObject.put("GroupPid",GroupPid)
        JSONObject.put("Responsor",Responsor)
        JSONObject.put("Requestor",Requestor)
        JSONObject.put("memmeber_id",member_id)


        return JSONObject.toString()
    }
    fun json_delete_re(SubstitutePid: Int?,SchedulePid: Int?):String?{

        var JSONObject= JSONObject()

        JSONObject.put("SubstitutePid",SubstitutePid)
        JSONObject.put("SchedulePid",SchedulePid)

        return JSONObject.toString()
    }
}