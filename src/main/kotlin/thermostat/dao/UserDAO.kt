package thermostat.dao

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import thermostat.ThermostatException
import thermostat.utils.generateSalt
import thermostat.utils.hash
import thermostat.utils.isValidHash

class UserDAO(val user: String = "NO_USER") : TableManager {

    @Throws(ThermostatException::class)
    override fun createTable(conn:Connection) {
        createUserTable(conn)
        println("Adding a user")
        addData(conn)
        println("Done")
    }

    @Throws(ThermostatException::class)
    override fun dropTable(conn:Connection) {
        println("Deleting table users")
        try {
            conn.executeUpdate("drop table users")
        }catch (e: Exception){
            println("watch out!!!${e.message}")
        }
    }

    @Throws(ThermostatException::class)
    private fun createUserTable(conn:Connection) {
        println("trying to generate table Users")
        val sql = """CREATE TABLE USERS
                (USER           TEXT    NOT NULL,
                PASS           TEXT     NOT NULL,
                SALT			TEXT  	   NOT NULL)"""
        conn.executeUpdate(sql)
    }

    @Throws(ThermostatException::class)
    fun updatePassword(conn:Connection, user: String, pass: String) {
        val salt = generateSalt()
        var hash = hash(pass, salt)
        conn.executeUpdate("update users set pass = '$hash', salt= '$salt' where user = '$user'")
    }

    @Throws(ThermostatException::class)
    fun addData(conn:Connection, user: String= "inigo", password: String = "password") {
        val salt = generateSalt()
        var hash = hash(password, salt)
        conn.executeUpdate("insert into users (user, pass, salt) values ('$user', '$hash', '$salt')")
    }

    @Throws(ThermostatException::class)
    fun login(conn:Connection, username: String, pass: String): String {
        return find(conn, username, pass)
        /*if (isError) {
            return "login.jsp"
        } else {
            this.user = username
            return "site/index"
        }*/
    }

    @Synchronized
    @Throws(ThermostatException::class)
    fun find(conn:Connection, user: String, pass: String): String {
        try {
            createPreparedStatement(conn, user.toLowerCase(), pass).use {
                    stmt ->  stmt.executeQuery().use {
                        rs ->   var userdb: String = ""
                                if (rs.next() && isValidHash(rs.getString(2), rs.getString(3), pass)) {
                                    userdb = rs.getString(1)
                                }
                                return userdb
                    }
                }
        } catch (e: SQLException) {
            throw ThermostatException(e.message, e)
        }
    }

    @Throws(SQLException::class)
    private fun createPreparedStatement(conn: Connection, user: String, pass: String): PreparedStatement {
        val stmt = conn.prepareStatement("select user, pass, salt from users where user = ?")
        stmt.setString(1, user)
        return stmt
    }

}

data class User(val name: String = "NO_USER", val pass: String = "NO_PASS", val salt: String = "")