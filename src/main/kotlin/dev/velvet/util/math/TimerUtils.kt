package dev.velvet.util.math

class TimerUtils {
    private var lastMS: Long = 0

    fun hasTimePassed(ms: Long): Boolean {
        return getCurrentMS() - lastMS >= ms
    }
    private fun getCurrentMS(): Long {
        return System.nanoTime() / 1000000
    }
    fun resetTimer() {
        lastMS = getCurrentMS()
    }
}