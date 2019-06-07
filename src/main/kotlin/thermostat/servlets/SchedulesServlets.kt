package thermostat.servlets

import thermostat.ThermostatException
import thermostat.dao.ThermostatDAO
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//TODO: this is only a redirect.
@WebServlet("/site/thermostat/schedules")
class ListSchedules : HttpServlet() {

    /**
     * @see HttpServlet.doGet
     */
    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        forward(req, resp, "/site/thermostat/addNew.jsp")
    }

    /**
     * @see HttpServlet.doPost
     */
    @Throws(ServletException::class, IOException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        doGet(req, resp)
    }
}

@WebServlet("/site/rest/tasks/*")
class ScheduleServlet : HttpServlet() {

    @Throws(IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val sm = ThermostatDAO()
        var res: Any? = null
        try {
            res = sm.getSchedules()
        } catch (e: ThermostatException) {
            e.printStackTrace()
        }
    }

    /*protected fun post(reqObject: Schedule, request: HttpServletRequest, response: HttpServletResponse): Any? {
        val sm = ThermostatDAO()
        var res: List<Schedule>? = null
        try {
            sm.add(reqObject)
            sm.refreshSchedules()
            res = sm.getSchedules()
        } catch (e: ThermostatException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return res
    }

    protected fun put(reqObject: Schedule, request: HttpServletRequest, response: HttpServletResponse): Any? {
        val sm = ThermostatDAO()
        var res: List<Schedule>? = null
        try {
            sm.update(reqObject)
            sm.refreshSchedules()
            res = sm.getSchedules()
        } catch (e: ThermostatException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return res
    }

    @Throws(IOException::class)
    protected fun delete(
        pathP: List<String>, queryP: Map<String, String>, request: HttpServletRequest,
        response: HttpServletResponse
    ): Any? {
        val sm = ThermostatDAO()
        var res: List<Schedule>? = null
        try {
            sm.delete(Integer.parseInt(pathP[0]))
            sm.refreshSchedules()
            res = sm.getSchedules()
        } catch (e: ThermostatException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: NumberFormatException) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST)
        } catch (e: NullPointerException) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST)
        }

        return res
    }*/
}
