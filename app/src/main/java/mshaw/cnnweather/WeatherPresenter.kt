package mshaw.cnnweather

import mshaw.cnnweather.api.currentweather.CurrentWeatherManager
import mshaw.cnnweather.api.fiveday.FiveDayManager

class WeatherPresenter(val view: WeatherCoordinator.WeatherView): WeatherCoordinator.WeatherPresenter {
    override fun getCurrentWeather(url: String) {
        CurrentWeatherManager(view).execute(url)
    }

    override fun getFiveDayForecast(url: String) {
        FiveDayManager(view).execute(url)
    }
}