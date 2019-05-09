package yoshikii.com.record_nutrients.view.record

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.databinding.FragmentTabBinding


class TabFragment : Fragment() {

    private lateinit var binding: FragmentTabBinding
    private val selectedDate by lazy { arguments?.getString(KEY_DATE) ?: throw IllegalArgumentException() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tab,
            container,
            false
        )
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            val adapter = TabAdapter(
                resources,
                fragmentManager ?: throw IllegalArgumentException(),
                selectedDate
            )
            viewPager.apply {
                offscreenPageLimit = 2
                setAdapter(adapter)
            }
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    companion object {
        const val TAG = " TabFragment"
        private const val KEY_DATE = "date"
        fun newInstance(name: String): TabFragment {
            return TabFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_DATE, name)
                }
            }
        }
    }
}