package yoshikii.com.record_nutrients

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import yoshikii.com.record_nutrients.databinding.FragmentNutrientBinding

class NutrientFragment : Fragment() {

    private var binding: FragmentNutrientBinding? = null
    private var adapter: NutrientAdapter? = null

    //callbackに操作イベントを設定
//    private val projectClickCallback = object : ProjectClickCallback {
//        override fun onClick(project: Project) {
//            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && activity is MainActivity) {
//                //(activity as MainActivity).show(project)
//            }
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //dataBinding用のレイアウトリソースをセット
        val binding: FragmentNutrientBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_nutrient, container, false)
        binding.apply {
            adapter = NutrientAdapter()
            itemList.adapter = adapter
            //isLoading = true
            return root
        }

        //イベントのcallbackをadapterに伝達
//        projectAdapter = ProjectAdapter(projectClickCallback)
//
//        //上記adapterをreclclerViewに適用
//        requireNotNull(binding).projectList.adapter = projectAdapter
//        //Loading開始
//        requireNotNull(binding).isLoading = true
//        //rootViewを取得
//        return requireNotNull(binding).root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        val viewModel = ViewModelProviders.of(this).get(ProjectListViewModel::class.java)
//        //監視を開始
//        observeViewModel(viewModel)
//    }
//
//    //observe開始
//    private fun observeViewModel(viewModel: ProjectListViewModel) {
//
//        //データが更新されたらアップデートするように、LifecycleOwnerを紐付け、ライフサイクル内にオブザーバを追加
//        //オブザーバーは、STARTED かRESUMED状態である場合にのみ、イベントを受信する
//        viewModel.projectListObservable.observe(this, Observer { projects ->
//            if (projects != null) {
//                requireNotNull(binding).isLoading = false
//                //projectAdapter!!.setProjectList(projects)
//            }
//        })
//    }

    companion object {
        const val TAG_NUTRIENT_LIST_FRAGMENT = "NutrientListFragment"
    }
}