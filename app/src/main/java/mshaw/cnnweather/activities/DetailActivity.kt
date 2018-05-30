package mshaw.cnnweather.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import mshaw.cnnweather.Constants
import mshaw.cnnweather.ForecastModel
import mshaw.cnnweather.R
import mshaw.cnnweather.databinding.ActivityDetailBinding
import mshaw.cnnweather.viewmodels.DetailActivityViewModel

class DetailActivity : AppCompatActivity() {
    var binding: ActivityDetailBinding? = null
    var model: ForecastModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        model = intent.getParcelableExtra(Constants.DETAIL_FORECAST)
        model?.let {
            binding?.viewmodel = DetailActivityViewModel(it)
        }
    }
}
