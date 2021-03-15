package com.carpenter_calc.ui.ArcSlope

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.carpenter_calc.databinding.FragmentGetArcSlopeBinding
import java.math.BigDecimal
import java.math.MathContext
import com.carpenter_calc.big.BigDecimalMath.*
import java.math.RoundingMode

class get_arc_slope : Fragment() {
    private var _binding: FragmentGetArcSlopeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val root = inflater.inflate(R.layout.slope_coefficient_fragment, container, false)
        _binding = FragmentGetArcSlopeBinding.inflate(inflater, container, false)
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
            textView.text = ""
            val a: BigDecimal = editText1.text.toString().toBigDecimal()
            val b: BigDecimal = editText2.text.toString().toBigDecimal()
            if (a <= BigDecimal.ZERO || b <= BigDecimal.ZERO || a > b) {
                Toast.makeText(this.requireContext(), "拱高和半弦长都应大于0，半弦长应大于等于拱高。", Toast.LENGTH_SHORT).show()
            }
            else if(a == b){
                textView.append(String.format("圆弧搭肩度数为：45°00′00.00″\n"+
                        "圆弧搭肩坡度为：100.00%%\n"))
            }
            else {
                val b_a = b.divide(a, MathContext(13))
                val a_b = BigDecimal.ONE.divide(b_a,MathContext(13))
                //val tan_a = a.divide(b, MathContext(13))
                //val tan2_a = tan_a.pow(2)
                val slope = 2.toBigDecimal().divide(b_a.subtract(a_b),MathContext(13))
//                val numerator = 2.toBigDecimal().multiply(tan_a)
//                val denominator = BigDecimal.ONE.subtract(tan2_a)
//                val slope = numerator.divide(denominator,MathContext(13))
//                val radian = acot(slope,MathContext(13))
                val radian = 2.toBigDecimal().multiply(acot(b_a, MathContext(13)))
                val degree = 90.toBigDecimal().subtract(radian.multiply(180.toBigDecimal()).divide(PI,MathContext(13)))
                var ew = degree.subtract(degree.setScale(0, RoundingMode.DOWN)).multiply(60.toBigDecimal())
                val g = ew.subtract(ew.setScale(0, RoundingMode.DOWN)).multiply(60.toBigDecimal())
                ew = ew.setScale(0, RoundingMode.DOWN)
                textView.append(String.format("圆弧搭肩度数为：%.0f°%02.0f′%05.2f″\n"+
                    "圆弧搭肩坡度为：%.2f%%\n", degree.setScale(0,RoundingMode.DOWN),ew.setScale(0,RoundingMode.DOWN),g,slope.multiply(100.toBigDecimal())))

            }
        }
    }
    fun getR(a: BigDecimal, b: BigDecimal): BigDecimal {
        val r: BigDecimal
        val b_a = b.divide(a, MathContext(13))
        val a_b = a.divide(b, MathContext(13))
        r = b.multiply(b_a.add(a_b)).divide(2.toBigDecimal(), MathContext(13))
        return r
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}