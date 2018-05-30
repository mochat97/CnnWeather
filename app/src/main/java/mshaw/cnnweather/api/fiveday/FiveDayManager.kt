package mshaw.cnnweather.api.fiveday

import android.os.AsyncTask
import com.google.gson.Gson
import mshaw.cnnweather.ForecastModel
import mshaw.cnnweather.WeatherCoordinator
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class FiveDayManager(private val view: WeatherCoordinator.WeatherView) : AsyncTask<String, Int, List<ForecastModel?>>() {
    override fun onPreExecute() {
        view.onGetCurrentWeatherStarted()
    }

    override fun doInBackground(vararg params: String?): List<ForecastModel?> {
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

                    val response = Gson().fromJson(stringBuilder.toString(), FiveDayResponse::class.java)
                    response.days.takeLast(5).map { forecast ->
                        val currentWeather = forecast.weather[0]
                        ForecastModel(description = currentWeather.main,
                                icon = currentWeather.icon,
                                high = forecast.temp.max,
                                low = forecast.temp.min,
                                date = forecast.dt,
                                windSpeed = forecast.speed,
                                windDegree = forecast.deg,
                                humidity = forecast.humidity,
                                pressure = forecast.pressure)
                    }
                }
            }
            else -> emptyList()
        }
    }

    override fun onPostExecute(result: List<ForecastModel?>) {
        if (result.isNotEmpty()) {
            view.onGetFiveDayWeatherSuccess(result.filterNotNull())
        } else {
            view.onGetCurrentWeatherFailure("Could not retrieve five day forecast.")
        }
    }
}