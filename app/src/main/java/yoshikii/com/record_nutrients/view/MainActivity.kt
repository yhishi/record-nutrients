package yoshikii.com.record_nutrients.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import yoshikii.com.record_nutrients.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, MealListFragment(), MealListFragment.TAG_MEAL_LIST_FRAGMENT)
                    .commit()
        }
    }
}
