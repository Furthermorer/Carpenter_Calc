package com.carpenter_calc.ui.SlopeCoefficient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.carpenter_calc.databinding.SlopeCoefficientFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator



class SlopeCoefficient : Fragment() {
    private var _binding: SlopeCoefficientFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var slopeCoefficientViewModel: SlopeCoefficientViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        slopeCoefficientViewModel =
                ViewModelProvider(this).get(SlopeCoefficientViewModel::class.java)
        //val root = inflater.inflate(R.layout.slope_coefficient_fragment, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        _binding = SlopeCoefficientFragmentBinding.inflate(inflater, container, false)

        val view = binding.root
        val viewPager2Adapter = binding.slopeViewPager2
        val tabLayout = binding.tabLayout
        viewPager2Adapter.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                //TODO("Not yet implemented")
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                //TODO("Not yet implemented")
                return when(position){
                    0-> get_slope_coefficient()
                    1-> get_slope_coefficient_of_arbitrary_triangular()
                    else-> get_angle_coefficient_table()
                }
            }
        }

        TabLayoutMediator(tabLayout,viewPager2Adapter){ tab,position->
            when(position){
                0->tab.text = "求坡度系数"
                1->tab.text = "求任意三角形坡度与坡度系数"
                else-> tab.text = "角坡系对查表"
            }

        }.attach()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}