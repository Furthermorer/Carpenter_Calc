package com.carpenter_calc.ui.SlopeCoefficient

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.carpenter_calc.databinding.FragmentGetAngleCoefficientTableBinding
import java.math.BigDecimal
import com.carpenter_calc.big.BigDecimalMath.*
import java.math.MathContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [get_angle_coefficient_table.newInstance] factory method to
 * create an instance of this fragment.
 */
class get_angle_coefficient_table : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentGetAngleCoefficientTableBinding? = null
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
        _binding = FragmentGetAngleCoefficientTableBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText1 = binding.editTextNumberDecimal
        val editText2 = binding.editTextNumberDecimal2
        val editText3 = binding.editTextNumberDecimal3
        val textView = binding.textView3
        val button = binding.button

        textView.movementMethod = ScrollingMovementMethod()
        button.setOnClickListener {
            Log.d("btnSetup", "Selected")
            var d = editText1.text.toString().toBigDecimal()
            val f = editText2.text.toString().toBigDecimal()
            val m = editText3.text.toString().toBigDecimal()
            //textView2.text = "2"
            textView.text=""
            if((d< BigDecimal.ZERO || f< BigDecimal.ZERO||m< BigDecimal.ZERO)||(d == BigDecimal.ZERO && f == BigDecimal.ZERO && m <= BigDecimal.ZERO)){
                Toast.makeText(this.requireContext(), "角度应大于0°0′0″。", Toast.LENGTH_SHORT).show()
            }
            else if(d >= 180.toBigDecimal()){
                Toast.makeText(this.requireContext(), "度数应小于180°", Toast.LENGTH_SHORT).show()
            }
            else{
                d = d.add(f.divide(60.toBigDecimal(),MathContext(13))).add(m.divide(3600.toBigDecimal(),MathContext(13)))
                val pd = BigDecimal.ONE.divide(tan(d.multiply(PI).divide(180.toBigDecimal(), MathContext(13)),MathContext(13)), MathContext(13)).multiply(100.toBigDecimal())
                val pdxs = sqrt(BigDecimal.ONE.add(pd.pow(2).divide(10000.toBigDecimal(), MathContext(13))), MathContext(13))
                textView.append(String.format("坡度：%.4f%%\n" +
                        "坡度系数：%.4f\n",pd.abs(),pdxs))
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}