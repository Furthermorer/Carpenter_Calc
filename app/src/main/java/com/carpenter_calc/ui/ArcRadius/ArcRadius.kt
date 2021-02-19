package com.carpenter_calc.ui.ArcRadius

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.carpenter_calc.R
import com.carpenter_calc.big.BigDecimalMath.*
import com.carpenter_calc.databinding.ArcRadiusFragmentBinding
import java.math.BigDecimal
import java.math.MathContext

class ArcRadius : Fragment() {
    private var _binding: ArcRadiusFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var arcRadiusViewModel: ArcRadiusViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        arcRadiusViewModel =
                ViewModelProvider(this).get(ArcRadiusViewModel::class.java)
        val root = inflater.inflate(R.layout.slope_coefficient_fragment, container, false)
        _binding = ArcRadiusFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText1 = binding.editTextNumberDecimal
        val editText2 = binding.editTextNumberDecimal2
        val textView = binding.textView4
        val button = binding.button
        button.setOnClickListener {
            textView.text = ""
            var a: BigDecimal = editText1.text.toString().toBigDecimal()
            var b: BigDecimal = editText2.text.toString().toBigDecimal()
            var r: BigDecimal
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
        var r:BigDecimal
        var b_a = b.divide(a, MathContext(13))
        var a_b = a.divide(b, MathContext(13))
        r = b.multiply(b_a.add(a_b)).divide(2.toBigDecimal(), MathContext(13))
        return r
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}