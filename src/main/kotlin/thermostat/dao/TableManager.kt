package com.inigo.domotik.db.dao

import com.inigo.domotik.exceptions.ThermostatException

/** Whoever use a table is responsable of create it
 * @author inigo
 */
interface TableManager {
    @Throws(ThermostatException::class)
    fun createTable()
}
