package yoshikii.com.record_nutrients.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import yoshikii.com.record_nutrients.R


class MainActivity : AppCompatActivity(), CalendarFragment.OnDateSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    /** 端末の戻るボタン押した時 */
    override fun onBackPressed() {
        onBack()
    }

    /** アクションバーの戻るボタン押した時 */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBack()
        return super.onOptionsItemSelected(item)
    }

    /** 戻るボタンを押した時の処理 */
    private fun onBack() {
        supportFragmentManager.fragments.apply {
            if (isNotEmpty() && last().tag == MealListFragment.TAG) {
                /** [MealListFragment]削除*/
                supportFragmentManager
                    .beginTransaction()
                    .remove(supportFragmentManager.fragments.last())
                    .commit()

                /** 再度[CalendarFragment]表示 */
                CalendarFragment
                    .newInstance()
                    .show(supportFragmentManager, CalendarFragment.TAG)
            } else {
                finish()
            }
        }
    }
}
