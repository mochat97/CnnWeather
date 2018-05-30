package mshaw.cnnweather.viewmodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.text.format.DateUtils
import mshaw.cnnweather.ForecastModel
import mshaw.cnnweather.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DetailActivityViewModel(private val forecastModel: ForecastModel): BaseObservable() {
    @Bindable
    fun getIcon(): Int {
        return when (forecastModel.icon) {
            "01n", "01d" -> R.drawable.art_clear
            "02n", "02d" -> R.drawable.art_light_clouds
            "03n", "04n", "03d", "04d" -> R.drawable.art_clouds
            "09n", "09d" -> R.drawable.art_light_rain
            "10n", "10d" -> R.drawable.art_rain
            "11n", "11d" -> R.drawable.art_light_rain
            "13n", "13d" -> R.drawable.art_snow
            "50n", "50d" -> R.drawable.art_fog
            else -> 0
        }
    }

    @Bindable
    fun getHigh() = forecastModel.high.roundToInt().toString()

    @Bindable
    fun getLow() = forecastModel.low.roundToInt().toString()

    @Bindable
    fun getDate(): String {
        return SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(forecastModel.date * 1000)).toString()
    }

    @Bindable
    fun getDayOfWeek(): String {
        val epochTime = forecastModel.date * 1000
        return when {
            DateUtils.isToday(epochTime) -> "Today"
            isTomorrow(epochTime) -> "Tomorrow"
            else -> SimpleDateFormat("EEEE", Locale.getDefault()).format(Date(epochTime)).toString()
        }
    }

    private fun isTomorrow(dateMillis: Long): Boolean {
        return DateUtils.isToday(dateMillis - DateUtils.DAY_IN_MILLIS)
    }

    @Bindable
    fun getDescription() = forecastModel.description

    @Bindable
    fun getHumidity(): String = "Humidity: ${forecastModel.humidity} %"

    @Bindable
    fun getPressure(): String = "Pressure: ${forecastModel.pressure} hPa"

    @Bindable
    fun getWind(): String = "Wind: ${forecastModel.windSpeed} km/h"
}