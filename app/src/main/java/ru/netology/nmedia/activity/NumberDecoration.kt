package ru.netology.nmedia.activity

import java.util.*
import kotlin.math.abs

class NumberDecoration(private val number: Long) {
    private fun formatKilo(number: Long, postfix : Char) : String{
        val kiloD = number.toDouble() / 1000.0
        val kiloL = number / 1000
        //исправляем погрешность деления
        val diff = ((kiloD - kiloL.toDouble()) * 10.0 + 0.0001).toLong()
        return if (diff >= 1L && (kiloL in 1..9))
                    "%.${1}f${postfix}".format(Locale.ENGLISH, kiloD)
               else
                    "%.${0}f${postfix}".format(Locale.ENGLISH, kiloD)
    }

    override fun toString() : String{
        val absNumber = abs(number)
        val sign      = if (number > 0) "" else "-"
        val absFormat =  when (absNumber){
            in 0..999           -> absNumber.toString()
            in 1_000..1_000_000 -> formatKilo(absNumber, 'K')
            else                -> formatKilo(absNumber / 1000, 'M')
        }
        return sign + absFormat
    }
}