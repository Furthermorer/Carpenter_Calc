package com.carpenter_calc.ui.ArcRadius

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.carpenter_calc.databinding.FragmentGetArcRadiusBinding
import java.math.BigDecimal
import java.math.MathContext

class get_arc_radius : Fragment() {
    private var _binding: FragmentGetArcRadiusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        //val root = inflater.inflate(R.layout.slope_coefficient_fragment, container, false)
        _binding = FragmentGetArcRadiusBinding.inflate(inflater, container, false)
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
            val r: BigDecimal
            if (a <= BigDecimal.ZERO || b <= BigDecimal.ZERO) {
                Toast.makeText(this.requireContext(), "拱高和半弦长都应大于0。", Toast.LENGTH_SHORT).show()
            }
            else {
                r = getR(a, b)
                textView.append(String.format("圆弧半径长为：%.2f\n", r))
            }
        }
    }
    fun getR(a:BigDecimal, b:BigDecimal):BigDecimal{
        val r:BigDecimal
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