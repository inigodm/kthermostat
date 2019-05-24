package thermostat.dao

import thermostat.ThermostatException
import java.sql.Connection
import java.sql.SQLException

/** Whoever use a table is responsable of create it
 * @author inigo
 */
interface TableManager {
    @Throws(ThermostatException::class)
    fun createTable()
    @Throws(ThermostatException::class)
    fun dropTable()
}

fun Connection.executeUpdate(sql: String){
    try {
        // TODO: preparedstatements...
        this.createStatement().use({ stmt -> stmt.executeUpdate(sql) })
    } catch (e: SQLException) {
        e.printStackTrace()
        throw ThermostatException(e.message, e)
    }
}
