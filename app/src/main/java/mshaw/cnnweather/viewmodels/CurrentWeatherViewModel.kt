package mshaw.cnnweather.viewmodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import mshaw.cnnweather.ForecastModel
import mshaw.cnnweather.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class CurrentWeatherViewModel(private val forecastModel: ForecastModel): BaseObservable() {
    @Bindable
    fun getIcon(): Int {
        return when (forecastModel.icon) {
            "01n" -> R.drawable.art_clear
            "02n" -> R.drawable.art_light_clouds
            "03n", "04n" -> R.drawable.art_clouds
            "09n" -> R.drawable.art_light_rain
            "10n" -> R.drawable.art_rain
            "11n" -> R.drawable.art_light_rain
            "13n" -> R.drawable.art_snow
            "50n" -> R.drawable.art_fog
            else -> 0
        }
    }

    @Bindable
    fun getHigh() = forecastModel.high.roundToInt().toString()

    @Bindable
    fun getLow() = forecastModel.low.roundToInt().toString()

    @Bindable
    fun getDate(): String {
        return "Today, " + SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(forecastModel.date * 1000)).toString()
    }

    @Bindable
    fun getDescription() = forecastModel.description
}