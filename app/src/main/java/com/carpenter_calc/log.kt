package com.carpenter_calc

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

fun log10(b: BigDecimal): BigDecimal {
    val SCALE = 18
    var b: BigDecimal = b
    val NUM_OF_DIGITS: Int = SCALE + 2
    // need to add one to get the right number of dp
    //  and then add one again to get the next number
    //  so I can round it correctly.
    val mc = MathContext(NUM_OF_DIGITS, RoundingMode.HALF_EVEN)
    //special conditions:
    // log(-x) -> exception
    // log(1) == 0 exactly;
    // log of a number lessthan one = -log(1/x)
    if (b.signum() <= 0) {
        throw ArithmeticException("log of a negative number! (or zero)")
    } else if (b.compareTo(BigDecimal.ONE) === 0) {
        return BigDecimal.ZERO
    } else if (b.compareTo(BigDecimal.ONE) < 0) {
        return log10(BigDecimal.ONE.divide(b, mc)).negate()
    }
    val sb = StringBuilder()
    //number of digits on the left of the decimal point
    var leftDigits: Int = b.precision() - b.scale()

    //so, the first digits of the log10 are:
    sb.append(leftDigits - 1).append(".")

    //this is the algorithm outlined in the webpage
    var n = 0
    while (n < NUM_OF_DIGITS) {
        b = b.movePointLeft(leftDigits - 1).pow(10, mc)
        leftDigits = b.precision() - b.scale()
        sb.append(leftDigits - 1)
        n++
    }
    var ans = BigDecimal(sb.toString())

    //Round the number to the correct number of decimal places.
    ans = ans.round(MathContext(ans.precision() - ans.scale() + SCALE, RoundingMode.HALF_EVEN))
    return ans
}

fun pow(savedValue: BigDecimal, value: BigDecimal?): BigDecimal? {
    var result: BigDecimal? = null
    result = exp(ln(savedValue, 32).multiply(value), 32)
    return result
}

/**
 * Compute e^x to a given scale.
 * Break x into its whole and fraction parts and
 * compute (e^(1 + fraction/whole))^whole using Taylor's formula.
 * @param x the value of x
 * @param scale the desired scale of the result
 * @return the result value
 */
fun exp(x: BigDecimal, scale: Int): BigDecimal {
    // e^0 = 1
    if (x.signum() === 0) {
        return BigDecimal.valueOf(1)
    } else if (x.signum() === -1) {
        return BigDecimal.valueOf(1).divide(exp(x.negate(), scale), scale, BigDecimal.ROUND_HALF_EVEN)
    }

    // Compute the whole part of x.
    var xWhole: BigDecimal = x.setScale(0, BigDecimal.ROUND_DOWN)

    // If there isn't a whole part, compute and return e^x.
    if (xWhole.signum() === 0) {
        return expTaylor(x, scale)
    }

    // Compute the fraction part of x.
    val xFraction: BigDecimal = x.subtract(xWhole)

    // z = 1 + fraction/whole
    val z: BigDecimal = BigDecimal.valueOf(1).add(xFraction.divide(xWhole, scale, BigDecimal.ROUND_HALF_EVEN))

    // t = e^z
    val t: BigDecimal = expTaylor(z, scale)
    val maxLong: BigDecimal = BigDecimal.valueOf(Long.MAX_VALUE)
    var result: BigDecimal = BigDecimal.valueOf(1)

    // Compute and return t^whole using intPower().
    // If whole > Long.MAX_VALUE, then first compute products
    // of e^Long.MAX_VALUE.
    while (xWhole.compareTo(maxLong) >= 0) {
        result = result.multiply(intPower(t, Long.MAX_VALUE, scale)).setScale(scale,
                BigDecimal.ROUND_HALF_EVEN)
        xWhole = xWhole.subtract(maxLong)
        Thread.yield()
    }
    return result.multiply(intPower(t, xWhole.longValueExact(), scale)).setScale(scale, BigDecimal.ROUND_HALF_EVEN)
}

/**
 * Compute the natural logarithm of x to a given scale, x > 0.
 */
fun ln(x: BigDecimal, scale: Int): BigDecimal {
    // Check that x > 0.
    require(x.signum() > 0) { "x <= 0" }

    // The number of digits to the left of the decimal point.
    val magnitude: Int = x.toString().length - x.scale() - 1
    return if (magnitude < 3) {
        lnNewton(x, scale)
    } else {

        // x^(1/magnitude)
        val root: BigDecimal = intRoot(x, magnitude.toLong(), scale)

        // ln(x^(1/magnitude))
        val lnRoot: BigDecimal = lnNewton(root, scale)

        // magnitude*ln(x^(1/magnitude))
        BigDecimal.valueOf(magnitude.toLong()).multiply(lnRoot).setScale(scale, BigDecimal.ROUND_HALF_EVEN)
    }
}

/**
 * Compute e^x to a given scale by the Taylor series.
 * @param x the value of x
 * @param scale the desired scale of the result
 * @return the result value
 */
private fun expTaylor(x: BigDecimal, scale: Int): BigDecimal {
    var factorial: BigDecimal = BigDecimal.valueOf(1)
    var xPower: BigDecimal = x
    var sumPrev: BigDecimal?

    // 1 + x
    var sum: BigDecimal = x.add(BigDecimal.valueOf(1))

    // Loop until the sums converge
    // (two successive sums are equal after rounding).
    var i = 2
    do {
        // x^i
        xPower = xPower.multiply(x).setScale(scale, BigDecimal.ROUND_HALF_EVEN)

        // i!
        factorial = factorial.multiply(BigDecimal.valueOf(i.toLong()))

        // x^i/i!
        val term: BigDecimal = xPower.divide(factorial, scale, BigDecimal.ROUND_HALF_EVEN)

        // sum = sum + x^i/i!
        sumPrev = sum
        sum = sum.add(term)
        ++i
        Thread.yield()
    } while (sum.compareTo(sumPrev) !== 0)
    return sum
}

/**
 * Compute x^exponent to a given scale.  Uses the same
 * algorithm as class numbercruncher.mathutils.IntPower.
 * @param x the value x
 * @param exponent the exponent value
 * @param scale the desired scale of the result
 * @return the result value
 */
fun intPower(x: BigDecimal, exponent: Long, scale: Int): BigDecimal {
    // If the exponent is negative, compute 1/(x^-exponent).
    var x: BigDecimal = x
    var exponent = exponent
    if (exponent < 0) {
        return BigDecimal.valueOf(1).divide(intPower(x, -exponent, scale), scale, BigDecimal.ROUND_HALF_EVEN)
    }
    var power: BigDecimal = BigDecimal.valueOf(1)

    // Loop to compute value^exponent.
    while (exponent > 0) {

        // Is the rightmost bit a 1?
        if (exponent and 1 == 1L) {
            power = power.multiply(x).setScale(scale, BigDecimal.ROUND_HALF_EVEN)
        }

        // Square x and shift exponent 1 bit to the right.
        x = x.multiply(x).setScale(scale, BigDecimal.ROUND_HALF_EVEN)
        exponent = exponent shr 1
        Thread.yield()
    }
    return power
}

/**
 * Compute the natural logarithm of x to a given scale, x > 0.
 * Use Newton's algorithm.
 */
private fun lnNewton(x: BigDecimal, scale: Int): BigDecimal {
    var x: BigDecimal = x
    val sp1 = scale + 1
    val n: BigDecimal = x
    var term: BigDecimal

    // Convergence tolerance = 5*(10^-(scale+1))
    val tolerance: BigDecimal = BigDecimal.valueOf(5).movePointLeft(sp1)

    // Loop until the approximations converge
    // (two successive approximations are within the tolerance).
    do {

        // e^x
        val eToX: BigDecimal = exp(x, sp1)

        // (e^x - n)/e^x
        term = eToX.subtract(n).divide(eToX, sp1, BigDecimal.ROUND_DOWN)

        // x - (e^x - n)/e^x
        x = x.subtract(term)
        Thread.yield()
    } while (term.compareTo(tolerance) > 0)
    return x.setScale(scale, BigDecimal.ROUND_HALF_EVEN)
}

/**
 * Compute the integral root of x to a given scale, x >= 0.
 * Use Newton's algorithm.
 * @param x the value of x
 * @param index the integral root value
 * @param scale the desired scale of the result
 * @return the result value
 */
fun intRoot(x: BigDecimal, index: Long, scale: Int): BigDecimal {
    // Check that x >= 0.
    var x: BigDecimal = x
    require(x.signum() >= 0) { "x < 0" }
    val sp1 = scale + 1
    val n: BigDecimal = x
    val i: BigDecimal = BigDecimal.valueOf(index)
    val im1: BigDecimal = BigDecimal.valueOf(index - 1)
    val tolerance: BigDecimal = BigDecimal.valueOf(5).movePointLeft(sp1)
    var xPrev: BigDecimal

    // The initial approximation is x/index.
    x = x.divide(i, scale, BigDecimal.ROUND_HALF_EVEN)

    // Loop until the approximations converge
    // (two successive approximations are equal after rounding).
    do {
        // x^(index-1)
        val xToIm1: BigDecimal = intPower(x, index - 1, sp1)

        // x^index
        val xToI: BigDecimal = x.multiply(xToIm1).setScale(sp1, BigDecimal.ROUND_HALF_EVEN)

        // n + (index-1)*(x^index)
        val numerator: BigDecimal = n.add(im1.multiply(xToI)).setScale(sp1, BigDecimal.ROUND_HALF_EVEN)

        // (index*(x^(index-1))
        val denominator: BigDecimal = i.multiply(xToIm1).setScale(sp1, BigDecimal.ROUND_HALF_EVEN)

        // x = (n + (index-1)*(x^index)) / (index*(x^(index-1)))
        xPrev = x
        x = numerator.divide(denominator, sp1, BigDecimal.ROUND_DOWN)
        Thread.yield()
    } while (x.subtract(xPrev).abs().compareTo(tolerance) > 0)
    return x
}