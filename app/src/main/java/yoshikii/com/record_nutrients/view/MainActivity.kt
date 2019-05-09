package yoshikii.com.record_nutrients.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.databinding.ActivityMainBinding
import yoshikii.com.record_nutrients.view.record.meal.MealListFragment
import yoshikii.com.record_nutrients.view.record.TabFragment


class MainActivity : AppCompatActivity(), CalendarFragment.OnDateSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

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
                TabFragment.newInstance("$year/$month/$day"),
                TabFragment.TAG
            )
            .commit()
    }

    /** 端末の戻るボタン押した時 */
    override fun onBackPressed() {
        // TODO カレンダー表示時の戻るボタン制御
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
            if (isEmpty() || last().tag == CalendarFragment.TAG) {
                finish()
            } else {
                /** fragmentをすべて削除 */
                this.forEach {
                    supportFragmentManager
                        .beginTransaction()
                        .remove(it)
                        .commit()
                }

                /** 再度[CalendarFragment]表示 */
                CalendarFragment
                    .newInstance()
                    .show(supportFragmentManager, CalendarFragment.TAG)
            }
        }
    }
}
