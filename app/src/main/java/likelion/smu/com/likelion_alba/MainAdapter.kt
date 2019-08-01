package likelion.smu.com.likelion_alba

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup

class MainAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragmentTitleList = mutableListOf("A","B")

    override fun getItem(position: Int): Fragment? {

        return when(position) {

            0       ->  AFragment()

            1       ->  BFragment()

            else    ->  null
        }

    }

    // 생성 할 Fragment 의 개수
    override fun getCount() = 2

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }

}