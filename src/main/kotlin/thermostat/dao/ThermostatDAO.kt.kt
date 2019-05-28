package thermostat.dao

import thermostat.ThermostatException
import java.sql.Connection

class ThermostatDAO: TableManager{

    @Throws(ThermostatException::class)
    override fun createTable(conn:Connection) {
        val sql = """CREATE TABLE schedules
                (WEEKDAYS           TEXT    NOT NULL,
                 STARTHOUR           TEXT    NOT NULL,
                 ENDHOUR           TEXT    NOT NULL,
                 DESIREDTEMP           INTEGER    NOT NULL,
                 ACTIVE           INTEGER    NOT NULL)"""
        conn.executeUpdate(sql)
    }

    @Throws(ThermostatException::class)
    override fun dropTable(conn:Connection) {
        try {
            println("Deleting table schedules")
            conn.executeUpdate("drop table schedules")
        } catch (e: Exception) {
            println("watch out!!!${e.message}")
        }
    }


}