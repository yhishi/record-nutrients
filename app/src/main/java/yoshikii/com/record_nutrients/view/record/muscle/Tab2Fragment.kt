package yoshikii.com.record_nutrients.view.record.muscle

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import yoshikii.com.record_nutrients.R
import yoshikii.com.record_nutrients.databinding.FragmentTab2Binding


class Tab2Fragment : Fragment() {

    private lateinit var binding: FragmentTab2Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab2, container, false)
        return binding.root
    }
}