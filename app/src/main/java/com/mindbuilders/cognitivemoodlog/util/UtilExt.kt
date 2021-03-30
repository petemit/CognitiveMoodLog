package com.mindbuilders.cognitivemoodlog.util

import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat

//this is stupid.  Compose is not baked yet.  Slider steps aren't working
fun Float.roundTo(numDigits: Int) : Float {
    var sb: StringBuilder? = null
    if (numDigits > 0) {
        sb = StringBuilder()
        sb.append(".")
        repeat(numDigits) {
            sb.append("#")
        }
    }
    val pattern = if (sb != null) {
        "#$sb"
    } else {
        "#"
    }
    val df = DecimalFormat(pattern)
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toFloat()
}