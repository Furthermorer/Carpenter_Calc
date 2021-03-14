package com.carpenter_calc.ui.ArcRadius

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.carpenter_calc.databinding.ArcRadiusFragmentBinding
import com.carpenter_calc.ui.ArcSlope.get_arc_slope
import com.google.android.material.tabs.TabLayoutMediator


class ArcRadius : Fragment() {
    private var _binding: ArcRadiusFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var arcRadiusViewModel: ArcRadiusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arcRadiusViewModel =
            ViewModelProvider(this).get(ArcRadiusViewModel::class.java)

        _binding = ArcRadiusFragmentBinding.inflate(inflater, container, false)

        val view = binding.root
        val viewPager2Adapter = binding.archViewPager2
        val tabLayout = binding.tabLayout
        viewPager2Adapter.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0->get_arc_radius()
                    else-> get_arc_slope()
                }
            }
        }
        TabLayoutMediator(tabLayout,viewPager2Adapter){ tab,position->
            when(position){
                0->tab.text = "求圆弧半径"
                else-> tab.text = "求圆弧搭肩斜度"
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