package mshaw.cnnweather

interface WeatherCoordinator {
    interface WeatherView {
        fun onGetCurrentWeatherStarted()
        fun onGetFiveDayStarted()
        fun onGetCurrentWeatherSuccess(response: ForecastModel)
        fun onGetFiveDayWeatherSuccess(response: List<ForecastModel>)
        fun onGetCurrentWeatherFailure(error: String)
        fun onGetFiveDayWeatherFailure(error: String)
    }

    interface WeatherPresenter {
        fun getCurrentWeather(url: String)
        fun getFiveDayForecast(url: String)
    }
}