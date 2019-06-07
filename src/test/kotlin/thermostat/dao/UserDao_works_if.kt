package thermostat.dao

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.spy
import thermostat.getConnection
import java.sql.Connection

class UserDao_works_if{
    lateinit var dao: UserDAO

    @Before
    fun init(){
        dao = spy(UserDAO::class.java)
    }

    @Test
    fun db_conn_succeed(){
        Assert.assertFalse(getConnection("testdb.db").use { it.isClosed } )
    }

    @Test
    fun user_table_is_created(){
        getConnection("testdb.db").use {
            dao.dropTable(it)
            dao.createTable(it)
            verify(dao, times(1)).addData(it)
        }
    }

    @Test
    fun user_is_getted_when_only_correct_password_is_given(){
        getConnection("testdb.db").use {
            dao.dropTable(it)
            dao.createTable(it)
            var user = dao.find(it, "inigo", "password")
            verify(dao, times(1)).addData(it)
            Assert.assertEquals(dao.find(it, "inigo", "password"), "inigo")
            Assert.assertNotEquals(dao.find(it, "inigo", "random"),"inigo")
        }
    }
}