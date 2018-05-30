package mshaw.cnnweather.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import mshaw.cnnweather.*
import mshaw.cnnweather.databinding.ActivityMainBinding
import mshaw.cnnweather.viewmodels.CurrentWeatherViewModel

class MainActivity : AppCompatActivity(), WeatherCoordinator.WeatherView {
    var progressDialog: ProgressDialog? = null
    var weatherPresenter: WeatherPresenter? = null
    var binding: ActivityMainBinding? = null
    var currentWeather: ForecastModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        progressDialog = ProgressDialog(this)
        weatherPresenter = WeatherPresenter(this)

        weatherPresenter?.getCurrentWeather("http://api.openweathermap.org/data/2.5/weather?q=Atlanta,us&units=imperial&appid=eaebd9c08dae0f79c169d175837b0f55")

        current_background.setOnClickListener {
            currentWeather?.let {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(Constants.DETAIL_FORECAST, it)
                startActivity(intent)
            }
        }
    }

    override fun onGetCurrentWeatherStarted() {
        progressDialog?.apply {
            setMessage("Getting current weather...")
            show()
        }
    }

    override fun onGetFiveDayStarted() {
        progressDialog?.apply {
            setMessage("Getting five day forecast...")
            show()
        }
    }

    override fun onGetCurrentWeatherSuccess(response: ForecastModel) {
        currentWeather = response
        binding?.viewmodel = CurrentWeatherViewModel(response)
        binding?.notifyChange()

        weatherPresenter?.getFiveDayForecast("http://api.openweathermap.org/data/2.5/forecast/daily?q=Atlanta,us&units=imperial&appid=eaebd9c08dae0f79c169d175837b0f55")
        progressDialog?.dismiss()
    }

    override fun onGetFiveDayWeatherSuccess(response: List<ForecastModel>) {
        five_day_forecast.layoutManager = LinearLayoutManager(this)
        five_day_forecast.adapter = FiveDayForecastAdapter(this, response)
        progressDialog?.dismiss()
    }

    override fun onGetCurrentWeatherFailure(error: String) {
        AlertDialog.Builder(this).apply {
            setMessage(error)
            setPositiveButton("Got It", null)
            show()
        }
    }

    override fun onGetFiveDayWeatherFailure(error: String) {
        AlertDialog.Builder(this).apply {
            setMessage(error)
            setPositiveButton("Got It", null)
            show()
        }
    }
}
