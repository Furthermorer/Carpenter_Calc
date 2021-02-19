package com.carpenter_calc

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

fun sqrt(value:BigDecimal, scale:Int):BigDecimal {
    val num2:BigDecimal = BigDecimal.valueOf(2)
    val precision = 100
    val mc:MathContext = MathContext(precision, RoundingMode.HALF_UP)
    var deviation:BigDecimal = value
    var cnt = 0
    while (cnt < precision)
    {
        deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc)
        cnt++
    }
    deviation = deviation.setScale(scale, BigDecimal.ROUND_HALF_UP)
    return deviation
}