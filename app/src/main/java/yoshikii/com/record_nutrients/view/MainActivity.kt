package yoshikii.com.record_nutrients.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import yoshikii.com.record_nutrients.R


class MainActivity : AppCompatActivity(), CalendarFragment.OnDateSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            /** [CalendarFragment]表示 */
            CalendarFragment
                .newInstance()
                .show(supportFragmentManager, CalendarFragment.TAG_CALENDAR_FRAGMENT)
        }
    }

    /** [CalendarFragment]で日付選択した値を受け取る */
    override fun onSelected(year: Int, month: Int, day: Int) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, MealListFragment(), MealListFragment.TAG_MEAL_LIST_FRAGMENT)
            .commit()
    }
}
