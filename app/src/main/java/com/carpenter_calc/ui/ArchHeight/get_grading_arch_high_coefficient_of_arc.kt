package com.carpenter_calc.ui.ArchHeight

import android.content.Context
import android.os.Bundle

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.carpenter_calc.databinding.FragmentGetGradingArchHighCoefficientOfArcBinding
import java.math.BigDecimal
import java.math.MathContext
import com.carpenter_calc.big.BigDecimalMath.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [get_grading_arch_high_coefficient_of_arc.newInstance] factory method to
 * create an instance of this fragment.
 */
class get_grading_arch_high_coefficient_of_arc : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentGetGradingArchHighCoefficientOfArcBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //val root = inflater.inflate(R.layout.arch_height_fragment, container, false)
        _binding =FragmentGetGradingArchHighCoefficientOfArcBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText1 = binding.editTextNumberDecimal
        val editText2 = binding.editTextNumberDecimal2
        val textView = binding.textView4
        val button = binding.button

        textView.movementMethod = ScrollingMovementMethod()
        button.setOnClickListener {
            textView.text=""
            var w:BigDecimal = 0.toBigDecimal()
            var x:BigDecimal = 0.toBigDecimal()
            var gonggaoxs:BigDecimal
            var xiebianxs:BigDecimal
            var w2:BigDecimal
            val t:BigDecimal
            val k:BigDecimal
            val dek:BigDecimal
            val logx:BigDecimal
            val ix:BigDecimal
            w = editText1.text.toString().toBigDecimal()
            k = w
            w2 = w.pow(2)
            x = editText2.text.toString().toBigDecimal()
            var idek:Int
            if(x<2.toBigDecimal())
                Toast.makeText(this.requireContext(), "弦长级的分母应大于等于2", Toast.LENGTH_SHORT).show()
            else {
                logx = log2(x, MathContext(6))
                ix= logx.setScale(0, BigDecimal.ROUND_DOWN)
                if(logx.subtract(ix).toDouble()!=0.toDouble())
                    Toast.makeText(this.requireContext(), "弦长级的分母应为2的幂指数", Toast.LENGTH_SHORT).show()
                else{
                    t = 1.toBigDecimal().divide(x)
                    dek = log2(x,MathContext(6)).subtract(1.toBigDecimal())
                    idek = dek.toInt()
                    gonggaoxs = 100.toBigDecimal().subtract(sqrt(10000.toBigDecimal().subtract(w2),MathContext(8)))
                    xiebianxs =xieBianxs(w)
                    if(0.5.toBigDecimal() == t){
                        textView.append(String.format("弦径比：%.4f%%\n", w))
                        textView.append(String.format("圆弧拱高系数：%.4f%%\n", gonggaoxs))
                        textView.append(String.format("斜边系数：%.4f%%\n", xiebianxs))
                    }
                    else{
                        while(idek-- >0){
                            w = xieBianxs(w).divide(2.toBigDecimal())
                        }
                        w2 = w.pow(2)
                        gonggaoxs = 100.toBigDecimal().subtract(sqrt(10000.toBigDecimal().subtract(w2),MathContext(6)))
                        xiebianxs = xieBianxs(w)
                        textView.append(String.format("1/2弦径比：%.4f%%\n", k))
                        textView.append(String.format("弦径比：%.4f%%\n", w))
                        textView.append(String.format("圆弧拱高系数：%.4f%%\n", gonggaoxs))
                        textView.append(String.format("斜边系数：%.4f%%\n", xiebianxs))
                    }
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
    private fun xieBianxs(w:BigDecimal): BigDecimal {
        val m = MathContext(8)
        val w1 = (PI.divide(2.toBigDecimal()).subtract(asin(w.divide(100.toBigDecimal()),m).divide(2.toBigDecimal())))
        val w2 = sin(w1,m)
        return w.divide(w2,m)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}