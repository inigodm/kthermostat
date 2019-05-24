package thermostat.utils

import org.apache.commons.codec.digest.DigestUtils
import java.util.UUID

class SHA256 {
    var salt = ""
    var hash = ""

    private fun generateRandomString(): String {
        return UUID.randomUUID().toString()
    }

    fun generateSalt(): String {
        return generateRandomString()
    }

    fun hash(word: String="", salt: String=""): String {
        return DigestUtils.sha256Hex(word + salt)
    }

    fun isValidHash(hashed: String?, salt: String, pass: String): Boolean {
        hash(pass, salt)
        println("Is '$hashed' equals to '$hash'?")
        return hashed?.equals(hash)?:false
    }
}
