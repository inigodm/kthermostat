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

}

fun main(){
    RefreshSchema().buildDB()
}

@Throws(ThermostatException::class)
fun getConnection(db: String = ThermostatProperties.get("db")): Connection {
    try {
        println("From db: $db")
        Class.forName("org.sqlite.JDBC")
        return DriverManager.getConnection("jdbc:sqlite:$db")
    } catch (e: Exception) {
        e.printStackTrace()
        throw ThermostatException(e.message, e)
    }

}