package thermostat

import java.io.IOException
import java.io.InputStream
import java.util.*

object ThermostatProperties {
    internal var p: Properties? = null

    @Throws(ThermostatException::class)
    private fun loadProperties() {
        try {
            p = Properties()
            p!!.load(obtainPropertiesInputStream())
        } catch (e: IOException) {
            e.printStackTrace()
            throw ThermostatException("A problem has occured when opening thermostat.properties")
        }

    }

    @Throws(ThermostatException::class)
    private fun obtainPropertiesInputStream(): InputStream {
        return ThermostatProperties::class.java!!.getResourceAsStream("/thermostat.properties")
            ?: throw ThermostatException("thermostat.properties file does not exist")
    }

    @Throws(ThermostatException::class)
    operator fun get(key: String): String {
        if (p == null) {
            loadProperties()
        }
        return p!!.getProperty(key)
    }
}
