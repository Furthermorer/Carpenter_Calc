package com.carpenter_calc.ui.ArchHeight

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.carpenter_calc.R
import com.carpenter_calc.databinding.FragmentGetCoordinateCoefficientOfArcArchHeightBinding
import com.carpenter_calc.sqrt
import java.math.BigDecimal


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [get_coordinate_coefficient_of_arc_arch_height.newInstance] factory method to
 * create an instance of this fragment.
 */
class get_coordinate_coefficient_of_arc_arch_height : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentGetCoordinateCoefficientOfArcArchHeightBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.arch_height_fragment, container, false)
        _binding =FragmentGetCoordinateCoefficientOfArcArchHeightBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText1 = binding.editTextNumberDecimal
        val textView = binding.textView4
        val textView2 = binding.textView5
        val button = binding.button

        textView2.movementMethod = ScrollingMovementMethod()
        button.setOnClickListener {
            textView2.text=""
            val b: BigDecimal = editText1.text.toString().toBigDecimal()
            val r = 100.toBigDecimal()
            val fenmu = 10.toBigDecimal()
            val r2 = 10000.toBigDecimal()
            val tep = r2.subtract(b.pow(2))
            //val baseb = b * b * 0.01.toBigDecimal()
            if (b < 0.01.toBigDecimal()){
                Toast.makeText(this.context, "弦径比应大于0.01%", Toast.LENGTH_SHORT).show()
                openSoftKeyboard(this.requireContext(),textView)
            }
            else{
                var re = r.subtract(sqrt(tep,6))
                textView.text = String.format("%.4f%%\n", re)
                var x:BigDecimal
                var result:BigDecimal
                for (i in 0..9) {
                    var iB=i.toBigDecimal()
                    x = b.multiply(iB).divide(fenmu)
                    var x2 = x.pow(2)
                    result = sqrt(r2.subtract(x2),6).subtract(sqrt(tep,6))
                    textView2.append(String.format("坐标：%d 拱高坐标系数：%.6f", i, result))
                    if (i != 9)
                        textView2.append("\n")
                }
            }

        }
    }
    fun openSoftKeyboard(context: Context, view: View) {
        view.requestFocus()
        // open the soft keyboard
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}