package thermostat


class Log(var date: String, var temperature: Double = 0.0,
    var outsideTemp: Double = 0.0, var desiredTemp: Int = 0,
    var active: Int = 0){

    fun toJson(): String {
        return """{'date':'$date',
              'temperature':'$temperature',
              'desiredTemp':'$desiredTemp',
              'active':'$active',
              'outsideTemp':'$outsideTemp'}\n"""
    }
}

