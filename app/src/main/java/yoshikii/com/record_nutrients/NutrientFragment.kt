package yoshikii.com.record_nutrients

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmList
import yoshikii.com.record_nutrients.data.NutrientViewModel
import yoshikii.com.record_nutrients.databinding.FragmentNutrientBinding
import java.text.SimpleDateFormat
import java.util.*

class NutrientFragment : Fragment() {

    //    private var binding: FragmentNutrientBinding? = null
    private val nutrientViewModel by lazy {
        ViewModelProviders.of(this).get(NutrientViewModel::class.java)
    }
    private val nutrientAdapter by lazy { NutrientAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewModel.getRealm()

        //initしたインスタンスをとってきて、トランザクションで書き込み
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm ->
            realm.where(NutrientRealm::class.java).findAll().deleteAllFromRealm()
        }

        var bbb: RealmList<Nutrient> = RealmList()
        bbb.add(
            Nutrient(
                item = "サラダチキン",
                value = 25
            )
        )
        bbb.add(
            Nutrient(
                item = "ステーキ",
                value = 40
            )
        )

        var ddd: RealmList<Nutrient> = RealmList()
        ddd.add(
            Nutrient(
                item = "ご飯大盛り",
                value = 140
            )
        )
        ddd.add(
            Nutrient(
                item = "おにぎり",
                value = 60
            )
        )

        val ccc = NutrientRealm(
            proteinSpinner = bbb,
            sugarSpinner = ddd
        )
        realm.executeTransaction { realm ->
            realm.insertOrUpdate(ccc)
        }

        //全件取得
        val all = realm.where(NutrientRealm::class.java).findAll()

        // 取り出し方
//        val a = all[0]?.id
//        val b = all[0]!!.proteinSpinner[0]!!.item
//        val c = all[0]!!.proteinSpinner[0]!!.value
        all.forEach {
            it.proteinSpinner.forEach {
                println(it.item)
                println(it.value)
            }
        }

        //val df = SimpleDateFormat("yyyy年MM月dd日 HH:mm")
        val df = SimpleDateFormat("MM/dd")
        val message = df.format(Date())
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

        //dataBinding用のレイアウトリソースをセット
        val binding: FragmentNutrientBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_nutrient, container, false)
        binding.apply {
            nutrientViewModel.apply {
                getRealm()
                recyclerView.adapter = nutrientAdapter
                nutrientAdapter.updateData(spinnerItems)
            }
            return root
        }
    }

    companion object {
        const val TAG_NUTRIENT_LIST_FRAGMENT = "NutrientListFragment"
    }
}