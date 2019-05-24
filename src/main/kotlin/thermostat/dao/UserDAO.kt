package thermostat.dao

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import thermostat.ThermostatException
import thermostat.utils.SHA256

class UserDAO(val user: String = "NO_USER", var conn:Connection) : TableManager {

    @Throws(ThermostatException::class)
    override fun createTable() {
        dropTable()
        createUserTable()
        println("Adding a user")
        addData()
        println("Done")
    }

    @Throws(ThermostatException::class)
    override fun dropTable() {
        try {
            println("Deleting table users")
            conn.executeUpdate("drop table users")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(ThermostatException::class)
    private fun createUserTable() {
        println("trying to generate table Users")
        val sql = "CREATE TABLE USERS " +
                "(USER           TEXT    NOT NULL, " +
                " PASS           TEXT     NOT NULL," +
                " SALT			TEXT  	   NOT NULL)"
        conn.executeUpdate(sql)
    }

    @Throws(ThermostatException::class)
    fun updatePassword(user: String, pass: String) {
        val sha = SHA256()
        val salt = sha.generateSalt()
        var hash = sha.hash(pass, salt)
        val sql =
            "update users set pass = '" + hash + "', salt= '" + salt + "' where user = '" + user + "'"
        conn.executeUpdate(sql)
    }

    @Throws(ThermostatException::class)
    private fun addData() {
        val sha = SHA256()
        val salt = sha.generateSalt()
        var hash = sha.hash("password", salt)
        val sql =
            "insert into users (user, pass, salt) values ('inigo', '" + hash + "', '" + salt + "')"
        conn.executeUpdate(sql)
    }

    @Throws(ThermostatException::class)
    fun login(username: String, pass: String): String {
        return findUser(username, pass)
        /*if (isError) {
            return "login.jsp"
        } else {
            this.user = username
            return "site/index"
        }*/
    }

    @Synchronized
    @Throws(ThermostatException::class)
    private fun findUser(user: String, pass: String): String {
        try {
            createPreparedStatement(conn, user.toLowerCase(), pass).use {
                    stmt ->  stmt.executeQuery().use {
                        rs ->   var userdb: String = ""
                                if (rs.next() && isValidUser(rs.getString(2), rs.getString(3), pass)) {
                                    userdb = rs.getString(1)
                                }
                                return userdb
                    }
                }
        } catch (e: SQLException) {
            throw ThermostatException(e.message, e)
        }
    }

    private fun isValidUser(hash: String, salt: String, pass: String): Boolean {
        return SHA256().isValidHash(hash, salt, pass)
    }

    @Throws(SQLException::class)
    private fun createPreparedStatement(conn: Connection, user: String, pass: String): PreparedStatement {
        val sql = "select user, pass, salt from users where user = ?"
        val stmt = conn.prepareStatement(sql)
        stmt.setString(1, user)
        return stmt
    }

}


data class User(val name: String = "NO_USER", val pass: String = "NO_PASS", val salt: String = "")