package com.carpenter_calc.ui.RegularPolygon

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.carpenter_calc.databinding.RegularPolygonFragmentBinding
import java.math.BigDecimal
import java.math.MathContext
import com.carpenter_calc.big.BigDecimalMath.*
import java.math.RoundingMode


class RegularPolygon : Fragment() {
    private var _binding: RegularPolygonFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var regularPolygonViewModel: RegularPolygonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        regularPolygonViewModel =
            ViewModelProvider(this).get(RegularPolygonViewModel::class.java)
        //val root = inflater.inflate(R.layout.slope_coefficient_fragment, container, false)
        _binding = RegularPolygonFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText1 = binding.editTextNumberDecimal
        val textView = binding.textView3
        val button = binding.button
        textView.movementMethod = ScrollingMovementMethod()
        button.setOnClickListener{
            textView.text=""
            val n:BigDecimal
            val a:BigDecimal
            val b:BigDecimal
            val c:BigDecimal
            var d:BigDecimal
            var ew:BigDecimal
            var f:BigDecimal
            val g:BigDecimal
            val h:BigDecimal
            var saved:BigDecimal

            n = editText1.text.toString().toBigDecimal()
            if(n<3.toBigDecimal()){
                Toast.makeText(this.requireContext(), "边数应大于3。", Toast.LENGTH_SHORT).show()
            }
            else if(n.subtract(n.setScale(0,RoundingMode.DOWN))> BigDecimal.ZERO){
                Toast.makeText(this.requireContext(), "边数应为整数。", Toast.LENGTH_SHORT).show()
            }
            else{
                a = sin(BigDecimal.ONE.divide(n, MathContext(13)).multiply(PI), MathContext(13))
                b = BigDecimal.ONE.subtract(cos(BigDecimal.ONE.divide(n, MathContext(13)).multiply(PI),MathContext(13))).divide(2.toBigDecimal())
                c = 90.toBigDecimal().subtract(180.toBigDecimal().divide(n, MathContext(13)))
                ew = c.subtract(c.setScale(0,RoundingMode.DOWN)).multiply(60.toBigDecimal())
                g = ew.subtract(ew.setScale(0,RoundingMode.DOWN)).multiply(60.toBigDecimal())
                ew = ew.setScale(0,RoundingMode.DOWN)
                d = 360.toBigDecimal().divide(n, MathContext(13))
                if(d > 90.toBigDecimal()){
                    d = 180.toBigDecimal().subtract(d)
                }
                f = d.subtract(d.setScale(0,RoundingMode.DOWN)).multiply(60.toBigDecimal())
                h = f.subtract(f.setScale(0,RoundingMode.DOWN)).multiply(60.toBigDecimal())
                f = f.setScale(0,RoundingMode.DOWN)
                if(n == 3.toBigDecimal()){
                    saved = 120.toBigDecimal()
                }
                saved = d
                textView.append(String.format("分块系数：%.4f\n" +
                        "圆弧拱高系数：%.4f\n" +
                        "交角度数：%.0f°%02.0f′%05.2f″\n" +
                        "交角坡度：%.2f%%\n" +
                        "互角度数：%.0f°%02.0f′%05.2f″\n" +
                        "互角坡度：%.2f%%\n",a,b,c.setScale(0,RoundingMode.DOWN),ew.setScale(0,RoundingMode.DOWN),g,
                        100.toBigDecimal().divide(tan(c.multiply(PI).divide(180.toBigDecimal(),MathContext(13)),MathContext(13)),MathContext(13)).abs(),
                        saved.setScale(0,RoundingMode.DOWN),f.setScale(0,RoundingMode.DOWN),h,
                        100.toBigDecimal().divide(tan(d.multiply(PI).divide(180.toBigDecimal(),MathContext(13)),MathContext(13)),MathContext(13)).abs()))
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}