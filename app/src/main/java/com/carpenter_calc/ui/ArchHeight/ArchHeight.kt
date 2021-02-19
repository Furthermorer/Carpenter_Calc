package com.carpenter_calc.ui.ArchHeight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.carpenter_calc.R
import androidx.viewpager2.widget.ViewPager2
import com.carpenter_calc.databinding.ArchHeightFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//import kotlinx.android.synthetic.main.arch_height_fragment.*

class ArchHeight : Fragment() {
    private var _binding: ArchHeightFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var archHeightViewModel: ArchHeightViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        archHeightViewModel =
                ViewModelProvider(this).get(ArchHeightViewModel::class.java)
        val root = inflater.inflate(R.layout.arch_height_fragment, container, false)
        //val textView: TextView = root.findViewById(R.id.text_dashboard)
        /*archHeightViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        _binding = ArchHeightFragmentBinding.inflate(inflater, container, false)

        val view = binding.root
        val viewPager2Adapter = binding.archViewPager2
        val tabLayout = binding.tabLayout
        viewPager2Adapter.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                //TODO("Not yet implemented")
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                //TODO("Not yet implemented")
                return when(position){
                    0->get_arch_height_of_arc()
                    1->get_coordinate_coefficient_of_arc_arch_height()
                    else->get_grading_arch_high_coefficient_of_arc()
                }
            }
        }

        /*tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            //viewPager2Adapter.currentItem = tab!!.position
            override fun onTabSelected(tab: TabLayout.Tab?) {
                *//*when(tab!!.position){
                    0->get_arch_height_of_arc()
                    1->get_coordinate_coefficient_of_arc_arch_height()
                    else->get_grading_arch_high_coefficient_of_arc()
                }*//*
                var a:Int = 3
                Toast.makeText(activity, a.toString(), Toast.LENGTH_SHORT).show()
                //viewPager2Adapter.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

        })*/

        TabLayoutMediator(tabLayout,viewPager2Adapter){ tab,position->
            when(position){
                0->tab.text = "求圆弧拱高"
                1->tab.text = "求圆弧拱高坐标系数"
                else-> tab.text = "求圆弧分级拱高系数"
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