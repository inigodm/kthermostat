import org.junit.Assert
import org.junit.Test
import thermostat.RefreshSchema

class Installation{
    @Test
    fun all_classes_are_found(){
        Assert.assertTrue(RefreshSchema()
            .getAllTableManagerClassesFrom("thermostat.dao")
            .size > 0)
    }
}