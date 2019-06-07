package thermostat.utils

import org.junit.Assert
import org.junit.Test

class SHA_working{
    @Test
    fun if_get_sha(){
        val text = "test"
        val salt = "aux"
        Assert.assertTrue(text != hash(text))
        Assert.assertTrue(text != hash(text, salt))
        Assert.assertTrue(hash(text) != hash(text, salt))
        Assert.assertTrue(isValidHash(hash(text, salt), salt, text))
    }

    @Test
    fun if_generates_UUID(){
        Assert.assertTrue(generateSalt() != "")
    }
}