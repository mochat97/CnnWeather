package mshaw.cnnweather

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import mshaw.cnnweather.activities.DetailActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class FiveDayForecastAdapter(val context: Context, private val forecasts: List<ForecastModel>): RecyclerView.Adapter<FiveDayForecastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.day_view))

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView) {
        val icon = itemView?.findViewById<ImageView>(R.id.icon)
        val dayOfWeek = itemView?.findViewById<TextView>(R.id.day_of_week)
        val forecast = itemView?.findViewById<TextView>(R.id.forecast)
        val high = itemView?.findViewById<TextView>(R.id.high)
        val low = itemView?.findViewById<TextView>(R.id.low)

        fun bind(model: ForecastModel) {
            icon?.setImageResource(getIcon(model.icon))
            forecast?.text = model.description
            high?.text = model.high.roundToInt().toString()
            low?.text = model.low.roundToInt().toString()
            dayOfWeek?.text = getRelativeDate(model.date)

            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(Constants.DETAIL_FORECAST, model)
                context.startActivity(intent)
            }
        }

        private fun getIcon(iconId: String): Int {
            return when (iconId) {
                "01d" -> R.drawable.ic_clear
                "02d" -> R.drawable.ic_light_clouds
                "03d", "04d" -> R.drawable.ic_cloudy
                "09d" -> R.drawable.ic_light_rain
                "10d" -> R.drawable.ic_rain
                "11d" -> R.drawable.ic_light_rain
                "13d" -> R.drawable.ic_snow
                "50d" -> R.drawable.ic_fog
                else -> 0
            }
        }

        private fun getRelativeDate(dateMillis: Long): String {
            val epochTime = dateMillis * 1000
            return when {
                DateUtils.isToday(epochTime) -> "Today"
                isTomorrow(epochTime) -> "Tomorrow"
                else -> SimpleDateFormat("EEEE", Locale.getDefault()).format(Date(epochTime)).toString()
            }
        }

        private fun isTomorrow(dateMillis: Long): Boolean {
            return DateUtils.isToday(dateMillis - DateUtils.DAY_IN_MILLIS)
        }
    }
}