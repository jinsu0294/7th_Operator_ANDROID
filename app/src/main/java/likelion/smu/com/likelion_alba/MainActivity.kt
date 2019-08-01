package likelion.smu.com.likelion_alba

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { MainAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 뷰페이저 어댑터 연결
        vpMainActivity.adapter = MainActivity@adapter

        vpMainActivity.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                tabLayout.getTabAt(0)?.setIcon(R.drawable.calendar)
                tabLayout.getTabAt(1)?.setIcon(R.drawable.daeta)

                when(p0){
                    0 -> tabLayout.getTabAt(0)?.setIcon(R.drawable.calendar_selected)
                    1 -> tabLayout.getTabAt(1)?.setIcon(R.drawable.daeta_selected)


                }
            }

        })

        // 탭 레이아웃에 뷰페이저 연결
        tabLayout.setupWithViewPager(vpMainActivity)

        // 탭 레이아웃 초기화
        tabLayout.getTabAt(0)?.setIcon(R.drawable.calendar_selected)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.daeta)


    }
}
