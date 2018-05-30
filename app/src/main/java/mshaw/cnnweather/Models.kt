package mshaw.cnnweather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastModel(val icon: String, val description: String, val date: Long, val high: Double, val low: Double, val humidity: Int, val pressure: Double, val windSpeed: Double, val windDegree: Int): Parcelable