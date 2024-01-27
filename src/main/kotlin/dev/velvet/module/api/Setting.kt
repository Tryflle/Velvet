package dev.velvet.module.api

abstract class Setting {
    abstract val name: String
    abstract val description: String
    abstract val subSettings: Array<Setting>
}
data class SliderSetting(
    override val name: String,
    override val description: String,
    override val subSettings: Array<Setting>,
    var min: Double,
    var max: Double,
    var intervals: Double,
    var value: Double
): Setting()
data class TickSetting(
    override val name: String,
    override val description: String,
    override val subSettings: Array<Setting>,
    var value: Boolean
): Setting()
data class EnumSetting(
    override val name: String,
    override val description: String,
    override val subSettings: Array<Setting>,
    var value: String,
    var values: List<String>
): Setting()