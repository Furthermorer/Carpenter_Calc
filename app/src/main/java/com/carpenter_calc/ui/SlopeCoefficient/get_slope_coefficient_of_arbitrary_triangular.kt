package com.carpenter_calc.ui.SlopeCoefficient

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.carpenter_calc.databinding.FragmentGetSlopeCoefficientOfArbitraryTriangularBinding
import java.math.BigDecimal
import java.math.MathContext
import com.carpenter_calc.big.BigDecimalMath.*
import java.math.RoundingMode


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [get_slope_coefficient_of_arbitrary_triangular.newInstance] factory method to
 * create an instance of this fragment.
 */
class get_slope_coefficient_of_arbitrary_triangular : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentGetSlopeCoefficientOfArbitraryTriangularBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //val root = inflater.inflate(R.layout.slope_coefficient_fragment, container, false)
        _binding = FragmentGetSlopeCoefficientOfArbitraryTriangularBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText1 = binding.editTextNumberDecimal
        val editText2 = binding.editTextNumberDecimal2
        val editText3 = binding.editTextNumberDecimal3
        val textView = binding.textView5
        val button = binding.button
        var a: BigDecimal
        var b: BigDecimal
        var c: BigDecimal
        textView.movementMethod = ScrollingMovementMethod()
        button.setOnClickListener {
            textView.text = ""
            a = editText1.text.toString().toBigDecimal()
            b = editText2.text.toString().toBigDecimal()
            c = editText3.text.toString().toBigDecimal()
            val a2 = a.pow(2)
            val b2 = b.pow(2)
            val c2 = c.pow(2)
            var max:BigDecimal
            max = if (a > b) a else b
            max = if (c > max)c else max
            val p = (a.add(b).add(c)).divide(2.toBigDecimal())
            if(a< BigDecimal.ZERO||b<BigDecimal.ZERO||c<BigDecimal.ZERO){
                Toast.makeText(this.requireContext(), "三边长应都大于0。", Toast.LENGTH_SHORT).show()
            }
            else if(p.multiply(2.toBigDecimal()).subtract(max)<=max){
                Toast.makeText(this.requireContext(), "两小边长之和应大于第三边。", Toast.LENGTH_SHORT).show()
            }
            else{
                val s1 = p.multiply(p.subtract(a)).multiply(p.subtract(b)).multiply(p.subtract(c))
                val HUD = 100.toBigDecimal()
                val s = sqrt(s1,MathContext(13))
                val s_2 = s.multiply(2.toBigDecimal())
                val p2 = p.multiply(2.toBigDecimal())
                var resulta = (p2.multiply(p.subtract(a)).subtract(b.multiply(c))).divide(s_2,13,RoundingMode.HALF_UP).multiply(HUD)
                var resultb = (p2.multiply(p.subtract(b)).subtract(a.multiply(c))).divide(s_2,13,RoundingMode.HALF_UP).multiply(HUD)
                var resultc = (p2.multiply(p.subtract(c)).subtract(a.multiply(b))).divide(s_2,13,RoundingMode.HALF_UP).multiply(HUD)
                val resultaa = b.multiply(c).divide(s_2,13,RoundingMode.HALF_UP)
                var d1:BigDecimal = asin(BigDecimal.ONE.divide(resultaa,13,RoundingMode.HALF_UP),MathContext(13)).multiply(180.toBigDecimal()).divide(PI,13,RoundingMode.HALF_UP)
                val _x = b2.add(c2).subtract(a2)
                val _y = a2.add(c2).subtract(b2)
                val _z = a2.add(b2).subtract(c2)
                if(_x < BigDecimal.ZERO)
                    d1 = 180.toBigDecimal().subtract(d1)
                var d1fen = fen(d1)
                var d1miao = miao(d1fen)
                val resultbb = a.multiply(c).divide(s_2,13,RoundingMode.HALF_UP)
                var d2:BigDecimal = asin(1.toBigDecimal().divide(resultbb,13,RoundingMode.HALF_UP),MathContext(13)).multiply(180.toBigDecimal()).divide(PI,13,RoundingMode.HALF_UP)
                if(_y < BigDecimal.ZERO)
                    d2 = 180.toBigDecimal().subtract(d2)
                var d2fen = fen(d2)
                var d2miao = miao(d2fen)
                val resultcc = a.multiply(b).divide(s_2,13,RoundingMode.HALF_UP)
                var d3:BigDecimal = asin(1.toBigDecimal().divide(resultcc,13,RoundingMode.HALF_UP),MathContext(13)).multiply(180.toBigDecimal()).divide(PI,13,RoundingMode.HALF_UP)
                if(_z < BigDecimal.ZERO)
                    d3 = 180.toBigDecimal().subtract(d3)
                var d3fen = fen(d3)
                var d3miao = miao(d3fen)

                if(d1miao.setScale(0,RoundingMode.DOWN)==60.toBigDecimal()){
                    d1miao = d1miao.subtract(60.toBigDecimal())
                    d1fen = d1fen.add(BigDecimal.ONE)
                }
                if(d1fen.setScale(0,RoundingMode.DOWN)==60.toBigDecimal()){
                    d1fen = d1fen.subtract(60.toBigDecimal())
                    d1 = d1.add(BigDecimal.ONE)
                }
                if(d2miao.setScale(0,RoundingMode.DOWN)==60.toBigDecimal()){
                    d2miao = d2miao.subtract(60.toBigDecimal())
                    d2fen = d2fen.add(BigDecimal.ONE)
                }
                if(d2fen.setScale(0,RoundingMode.DOWN)==60.toBigDecimal()){
                    d2fen = d2fen.subtract(60.toBigDecimal())
                    d2 = d2.add(BigDecimal.ONE)
                }
                if(d3miao.setScale(0,RoundingMode.DOWN)==60.toBigDecimal()){
                    d3miao = d3miao.subtract(60.toBigDecimal())
                    d3fen = d3fen.add(BigDecimal.ONE)
                }
                if(d3fen.setScale(0,RoundingMode.DOWN)==60.toBigDecimal()){
                    d3fen = d3fen.subtract(60.toBigDecimal())
                    d3 = d3.add(BigDecimal.ONE)
                }

                if(resulta< BigDecimal.ZERO)resulta = resulta.abs()
                if(resultb< BigDecimal.ZERO)resultb = resultb.abs()
                if(resultc< BigDecimal.ZERO)resultc = resultc.abs()

                textView.append(String.format("∠A：\n"))
                textView.append(String.format("∠A = %.0f°%02.0f′%05.2f″\n∠A坡度：%.2f%%\n" +
                        "∠A坡度系数：%.4f\n\n",d1.setScale(0,RoundingMode.DOWN),d1fen.setScale(0,RoundingMode.DOWN),d1miao,resulta,resultaa))
                textView.append(String.format("∠B：\n"))
                textView.append(String.format("∠B = %.0f°%02.0f′%05.2f″\n∠B坡度：%.2f%%\n" +
                        "∠B坡度系数：%.4f\n\n",d2.setScale(0,RoundingMode.DOWN),d2fen.setScale(0,RoundingMode.DOWN),d2miao,resultb,resultbb))
                textView.append(String.format("∠C：\n"))
                textView.append(String.format("∠C = %.0f°%02.0f′%05.2f″\n∠C坡度：%.2f%%\n" +
                        "∠C坡度系数：%.4f", d3.setScale(0,RoundingMode.DOWN),d3fen.setScale(0,RoundingMode.DOWN),d3miao,resultc,resultcc))
            }
        }
    }
    fun fen(d:BigDecimal):BigDecimal{
        val dfen:BigDecimal=d.subtract(d.setScale(0,RoundingMode.DOWN)).multiply(60.toBigDecimal())
        return dfen
    }
    fun miao(d:BigDecimal):BigDecimal{
        val dmiao:BigDecimal=d.subtract(d.setScale(0,RoundingMode.DOWN)).multiply(60.toBigDecimal())
        return dmiao
    }

    /*data class p<T>(   // It's a generic wrap class for scalar type T
            var v:T
    )
    fun <T>miaoCarry(dmiao:p<BigDecimal>, dfen:p<BigDecimal>){  // It's a generic swap for scalar types
        if(dmiao.v.setScale(0,RoundingMode.DOWN) == 60.toBigDecimal()){
            dmiao.v = dmiao.v.subtract(60.toBigDecimal())
            dfen.v = dfen.v.add(BigDecimal.ONE)
        }
    }
    fun <T>fenCarry(d:p<BigDecimal>, dfen:p<BigDecimal>){  // It's a generic swap for scalar types
        if(dfen.v.setScale(0,RoundingMode.DOWN) == 60.toBigDecimal()){
            dfen.v = dfen.v.subtract(60.toBigDecimal())
            d.v = d.v.add(BigDecimal.ONE)
        }
    }*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}