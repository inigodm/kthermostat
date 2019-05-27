package thermostat.thermostat.threads

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

interface Reader {
    fun read(): String
}

class CPUTempReader: Reader{
    // this is the file in which raspbian saves the temperature of the CPU
    var CPU_TEMP = "/sys/class/thermal/thermal_zone0/temp"

    override fun read() = (File(CPU_TEMP)?.readLines().get(0).toDouble() / 1000).toString()
}

class RoomTempReader: Reader{
    // this is the file in which raspbian saves the temperature of the CPU
    var CPU_TEMP = "/sys/bus/w1/devices/28-0415a4ddf9ff/w1_slave"

    override fun read() : String {
        return File(CPU_TEMP)?.inputStream().bufferedReader().use{
            var res = ""
                it.readLines().forEach{ item ->
                    if (item.contains("t=")) {
                        res = item.substring(item.indexOf("t=") + 2)
                    }
                }
            res
        }
    }
}

@Deprecated( message = "Not working, Yahoo has retired the service")
class YahooWeatherReader: Reader{
    override fun read(): String{
        val conn = URL(prepareURL()).openConnection() as HttpURLConnection
        val resp = conn.inputStream.bufferedReader().use(BufferedReader::readText)
        println("Response from Yahoo: ${resp}")
        return resp
    }
    fun prepareURL(cityName: String = "arkaia") = "https://query.yahooapis.com/v1/public/yql?q=${query(cityName)}&format=json"
    fun query(cityName: String) = URLEncoder.encode("select item.condition from weather.forecast where woeid in (select woeid from geo.places(1) where text='${cityName}') and u = 'c'")
}

class OpenWeatherReader: Reader{
    override fun read(): String{
        val conn = URL(prepareURL()).openConnection() as HttpURLConnection
        val resp = conn.inputStream.bufferedReader().use(BufferedReader::readText)
        println("Response from OpenWeatherMap: ${resp}")
        return resp
    }
    //V-G: 6355286
    //Zurbano 3104209
    val API_KEY = "1be8e44c4d7eebfd0ba617a4feaf3585"
    fun prepareURL(cityId: String = "3104209") = "http://api.openweathermap.org/data/2.5/forecast?id=$cityId&APPID=$API_KEY"
}

class ApixuReader: Reader{
    override fun read(): String{
        val conn = URL(prepareURL()).openConnection() as HttpURLConnection
        val resp = conn.inputStream.bufferedReader().use(BufferedReader::readText)
        println("Response from Apixu: ${resp}")
        return resp
    }
    val API_KEY = "54e20d5a33a34418aab150926192605"
    fun prepareURL(cityName: String = "zurbano") = "http://api.apixu.com/v1/current.json?key=$API_KEY&q=$cityName"
}
