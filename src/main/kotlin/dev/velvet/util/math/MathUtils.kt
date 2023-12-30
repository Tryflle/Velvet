package dev.velvet.util.math

object MathUtils {
    fun randInt(min: Int, max: Int): Int {
        return (Math.random() * (max - min + 1) + min).toInt()
    }
    fun randDouble(min: Double, max: Double): Double {
        return (Math.random() * (max - min + 1) + min).toDouble()
    }
}