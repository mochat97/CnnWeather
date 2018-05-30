package mshaw.cnnweather.api.fiveday

import com.google.gson.annotations.SerializedName


data class FiveDayResponse(
    val city: City,
    val cod: String,
    val message: Double,
    val cnt: Int,
    @SerializedName("list")
    val days: List<Day>
)

data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Day(
    val dt: Long,
    val temp: Temp,
    val pressure: Double,
    val humidity: Int,
    val weather: List<Weather>,
    val speed: Double,
    val deg: Int,
    val clouds: Int,
    val rain: Double
)

data class Temp(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)