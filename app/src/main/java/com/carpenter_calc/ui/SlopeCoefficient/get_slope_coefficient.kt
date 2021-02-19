package com.carpenter_calc.ui.SlopeCoefficient

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.carpenter_calc.R
import java.math.MathContext
import com.carpenter_calc.big.BigDecimalMath.*
import com.carpenter_calc.databinding.FragmentGetSlopeCoefficientBinding
import java.math.BigDecimal

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [get_slope_coefficient.newInstance] factory method to
 * create an instance of this fragment.
 */
class get_slope_coefficient : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentGetSlopeCoefficientBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.slope_coefficient_fragment, container, false)
        _binding =FragmentGetSlopeCoefficientBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText1 = binding.editTextNumberDecimal
        val textView = binding.textView4
        val button = binding.button
        var A2:BigDecimal
        var b2:BigDecimal
        var poxi:BigDecimal
        val A:BigDecimal = 100.toBigDecimal()
        button.setOnClickListener {
            textView.text = ""
            val b = editText1.text.toString().toBigDecimal()
            if(b < 0.toBigDecimal()){
                Toast.makeText(this.requireContext(), "坡度应大于0。", Toast.LENGTH_SHORT).show()
            }
            else{
                A2 = A.pow(2)
                b2 = b.pow(2)
                poxi = sqrt(A2+b2,MathContext(8)).divide(A)
                textView.append(String.format("%.5f\n", poxi))
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}