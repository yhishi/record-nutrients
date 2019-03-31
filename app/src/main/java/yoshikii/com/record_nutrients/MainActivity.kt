package yoshikii.com.record_nutrients

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import yoshikii.com.record_nutrients.NutrientFragment.Companion.TAG_NUTRIENT_LIST_FRAGMENT

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            //プロジェクト一覧のFragment
            val fragment = NutrientFragment()

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, TAG_NUTRIENT_LIST_FRAGMENT)
                    .commit()
        }
    }
}
