package thermostat.threads

import org.junit.Assert
import org.junit.Test
import thermostat.thermostat.threads.*

class Weather_ws_works_if{
    @Test
    fun reads_a_json_from_each_origin(){
        var resp = OpenWeatherReader().read()
        println("OWM: $resp")
        Assert.assertFalse(resp == null)
        Assert.assertTrue(resp.contains("temp_min"))
        resp = ApixuReader().read()
        println("Apixu: $resp")
        Assert.assertFalse(resp == null)
        Assert.assertTrue(resp.contains("localtime_epoch"))
        resp = CPUTempReader().read()
        println("CPU: $resp")
        Assert.assertFalse(resp == null)
        Assert.assertTrue(resp.length < 10)
    }
}