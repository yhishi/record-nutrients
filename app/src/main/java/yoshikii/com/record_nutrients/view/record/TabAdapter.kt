package yoshikii.com.record_nutrients.view.record

import android.content.res.Resources
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.view.record.meal.MealListFragment
import yoshikii.com.record_nutrients.view.record.muscle.Tab2Fragment


class TabAdapter(
    resources: Resources,
    fragmentManager: FragmentManager,
    private val selectedDate: String
) : FragmentPagerAdapter(fragmentManager) {

    private val tabTitles = resources.getStringArray(R.array.tab_array)

    override fun getPageTitle(position: Int): String? {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> MealListFragment.newInstance(selectedDate)
            1 -> Tab2Fragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return tabTitles.size
    }
}