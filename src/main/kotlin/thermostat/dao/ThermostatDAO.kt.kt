package thermostat.dao

import thermostat.ThermostatException
import thermostat.getConnection
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException

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

    @Throws(ThermostatException::class)
    fun getSchedules(limit: Int = -1, offset: Int = -1): List<Schedule> {
        return getConnection().use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeQuery(buildSql(limit, offset)).use { rs ->
                    rs.use {
                        generateSequence {
                            if (rs.next()) rs.getSchedule() else null
                        }.toList()  // must be inside the use() block
                    }
                }
            }
        }
    }

    fun buildSql(limit: Int = -1, offset: Int = -1): String{
        var sql = ""
        if (limit != -1 && offset != -1) {
            sql = "LIMIT $limit OFFSET $offset"
        }
        return """select rowid, active, weekdays, starthour, endhour,
                desiredtemp from schedules $sql"""
    }
}

fun ResultSet.getSchedule() = Schedule(weekDays=getString("weekdays"),
        startHour= getString("starthour"),
        endHour = getString("endhour"),
        desiredTemp = getInt("desiredTemp"),
        active = getInt("active"))

data class Schedule(var weekDays : String, var startHour: String,
                          var endHour: String, var desiredTemp: Int,
                          var active: Int)