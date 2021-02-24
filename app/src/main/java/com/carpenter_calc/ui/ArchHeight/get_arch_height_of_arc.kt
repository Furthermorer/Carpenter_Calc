package com.carpenter_calc.ui.ArchHeight


import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.carpenter_calc.databinding.FragmentGetArchHeightOfArcBinding
import java.math.BigDecimal
import com.carpenter_calc.big.BigDecimalMath.*
import java.math.MathContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [get_arch_height_of_arc.newInstance] factory method to
 * create an instance of this fragment.
 */
class get_arch_height_of_arc : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentGetArchHeightOfArcBinding? = null
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
        _binding =FragmentGetArchHeightOfArcBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText1 = binding.editTextNumberDecimal
        val editText2 = binding.editTextNumberDecimal2
        val editText3 = binding.editTextNumberDecimal3
        val textView = binding.textView6
        val textView2 = binding.textView7
        val button = binding.button

        textView.movementMethod = ScrollingMovementMethod()
        button.setOnClickListener {
            Log.d("btnSetup", "Selected")
            //textView2.text = "2"
            textView.text=""
            val r: BigDecimal = editText1.text.toString().toBigDecimal()
            val b: BigDecimal = editText2.text.toString().toBigDecimal()
            val x: BigDecimal = editText3.text.toString().toBigDecimal()
            if (r <= BigDecimal.ZERO || b <= BigDecimal.ZERO || x < BigDecimal.ZERO)
            {
                Toast.makeText(this.requireContext(), "r和b都应大于0, x应大于等于0。", Toast.LENGTH_SHORT).show()
            }
            else if (r < b)
            {
                Toast.makeText(this.requireContext(), "半径r应大于等于半弦长b。", Toast.LENGTH_SHORT).show()
            }
            else if (b < x)
            {
                Toast.makeText(this.requireContext(), "坐标x应小于等于半弦长b。", Toast.LENGTH_SHORT).show()
            }
            else {
                val r2 = r.pow(2)
                val x1 = r2.subtract(x.pow(2))
                val x2 = r2.subtract(b.pow(2))
                //val baseb = b * b * 0.01.toBigDecimal()
                val baseb = b.pow(2).multiply(BigDecimal("0.01"))
                val d = (sqrt(x1, MathContext(7)) - sqrt(x2, MathContext(7)))
                //d = Convert.ToDecimal(Math.Sqrt(Convert.ToDouble(x1)) - Math.Sqrt(Convert.ToDouble(x2)));
                textView2.text = String.format("%6f\n", d)
                for (i in 0..9) {
                    var resultgonggao: BigDecimal
                    val i2 = BigDecimal(i * i)
                    resultgonggao = sqrt((r2.subtract(baseb.multiply(i2))), MathContext(7)) - sqrt(x2, MathContext(7))
                    textView.append(String.format("相对坐标：%d 拱高：%.6f", i, resultgonggao))
                    if (i != 9)
                        textView.append("\n")
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}