package thermostat

import java.io.IOException

import org.reflections.Reflections
import thermostat.dao.TableManager
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class RefreshSchema {

    @Throws(IOException::class)
    fun buildDB() {
        getConnection().use { conn ->
            val tableManagersInPackage = getAllTableManagerClassesFrom("thermostat.dao")
            tableManagersInPackage.filter { !it.isInterface() }.forEach {
                println("${it.name}")
                var dao = it.newInstance()
                dao.dropTable(conn)
                dao.createTable(conn)
            }
        }
    }

    fun getAllTableManagerClassesFrom(packa: String) =
        Reflections(packa).getSubTypesOf(TableManager::class.java)

    @Throws(ThermostatException::class)
    fun getConnection(): Connection {
        try {
            Class.forName("org.sqlite.JDBC")
            return DriverManager.getConnection("jdbc:sqlite:" + ThermostatProperties.get("db"))
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            throw ThermostatException(e.message, e)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw ThermostatException(e.message, e)
        }

    }

}

fun main(){
    RefreshSchema().buildDB()
}