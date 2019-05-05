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
                .show(supportFragmentManager, CalendarFragment.TAG)
        }
    }

    /** [CalendarFragment]で日付選択した値を受け取る */
    override fun onSelected(year: Int, month: Int, day: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                MealListFragment.newInstance("$year/$month/$day"),
                MealListFragment.TAG
            )
            .commit()
    }

    /** 戻るボタンを押した時の処理 */
    override fun onBackPressed() {
        when (supportFragmentManager.fragments.last().tag) {
            MealListFragment.TAG -> {
                /** [MealListFragment]削除*/
                supportFragmentManager
                    .beginTransaction()
                    .remove(supportFragmentManager.fragments.last())
                    .commit()

                /** 再度[CalendarFragment]表示 */
                CalendarFragment
                    .newInstance()
                    .show(supportFragmentManager, CalendarFragment.TAG)
            }
            else -> {
                finish()
            }
        }
    }
}
