package mshaw.cnnweather.api.currentweather

import android.os.AsyncTask
import com.google.gson.Gson
import mshaw.cnnweather.ForecastModel
import mshaw.cnnweather.WeatherCoordinator
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class CurrentWeatherManager(private val view: WeatherCoordinator.WeatherView) : AsyncTask<String, Int, ForecastModel?>() {
    override fun onPreExecute() {
        view.onGetCurrentWeatherStarted()
    }

    override fun doInBackground(vararg params: String?): ForecastModel? {
        val urlString = params[0]
        val connection = URL(urlString).openConnection() as HttpURLConnection

        connection.readTimeout = 5000
        connection.connectTimeout = 8000
        return when (connection.responseCode) {
            HttpURLConnection.HTTP_OK -> {
                connection.inputStream.use { inputStream ->
                    val bufferedReader = BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))
                    val stringBuilder = StringBuilder()

                    bufferedReader.forEachLine {
                        stringBuilder.append(it)
                    }
                    val response = Gson().fromJson(stringBuilder.toString(), CurrentWeatherResponse::class.java)
                    val currentWeather = response.weather[0]
                    ForecastModel(description = currentWeather.main,
                            icon = currentWeather.icon,
                            high = response.main.temp_max,
                            low = response.main.temp_min,
                            date = response.dt,
                            windDegree = response.wind.deg,
                            windSpeed = response.wind.speed,
                            humidity = response.main.humidity,
                            pressure = response.main.pressure.toDouble()
                    )
                }
            }
            else -> null
        }
    }

    override fun onPostExecute(result: ForecastModel?) {
        result?.let { view.onGetCurrentWeatherSuccess(it) } ?: view.onGetCurrentWeatherFailure("Could not retrieve current weather.")
    }
}