package dev.velvet.module.api

abstract class Setting {
    abstract val name: String
    abstract val description: String
    abstract val subSettings: List<Setting>
}
data class SliderSetting(
    override val name: String,
    override val description: String,
    override val subSettings: List<Setting>,
    val min: Int,
    val max: Int,
    val intervals: Int,
    val value: Int
): Setting()
data class TickSetting(
    override val name: String,
    override val description: String,
    override val subSettings: List<Setting>,
    val value: Boolean
): Setting()
data class EnumSetting(
    override val name: String,
    override val description: String,
    override val subSettings: List<Setting>,
    val value: String,
    val values: List<String>
): Setting()
data class DoubleSliderSetting(
    override val name: String,
    override val description: String,
    override val subSettings: List<Setting>,
    val min: Double,
    val max: Double,
    val selectedmax: Double,
    val selectedmin: Double,
    val intervals: Int,
    val value: Double
): Setting()