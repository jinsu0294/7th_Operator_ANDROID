package likelion.smu.com.likelion_alba

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_daeta.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_gotoMain
import org.json.JSONObject

class DaetaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daeta)

        btn_gotoMain.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        rvDaetaItem.layoutManager = LinearLayoutManager(this)
        rvDaetaItem.adapter=DaetaAdapter()

    }

    inner class AsyncTast: AsyncTask<String, Void, String>() {
        var state : Int = -1 //GET_daeta_select
        var response : String? = null

        override fun doInBackground(vararg params: String): String? {
            state = Integer.parseInt(params[0])
            var client = okhttp3.OkHttpClient()
            var url = params[1]
            var grouppid = params[2]

            //GET_daeta_select
            if (state == 0){
                url.plus("${grouppid}")
                response = Okhttp().GET(client,url)
            }
            return response
        }

        override fun onPostExecute(result: String) {
            if(!result[0].equals("{")) { //Json구문이 넘어오지 않을 시 Toast 메세지 출력 후 종료
                Toast.makeText(applicationContext, "네트워크 연결상태가 좋지 않습니다", Toast.LENGTH_LONG).show()
                return
            }
            var json = JSONObject(result)
            //GET_daeta_select
            if (state == 0){
                var json = json

                //데이터 받아옴
            }
        }
    }
}
