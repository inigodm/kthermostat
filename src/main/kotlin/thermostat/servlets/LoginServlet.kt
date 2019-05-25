package thermostat.servlets

import thermostat.listeners.LoginFilter
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import javax.servlet.ServletException

@WebServlet("/login")
class LoginServlet: HttpServlet() {
    companion object{
        val LOGIN_JSP = "/login.jsp"
    }
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        forward(req!!, resp!!, LOGIN_JSP)
    }
}

@WebServlet("/logout")
class LogoutServlet: HttpServlet() {
    /**
     * @see HttpServlet.doGet
     */
    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        logout(req, resp)
    }

    /**
     * @see HttpServlet.doPost
     */
    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        logout(request, response)
    }

    fun logout(req: HttpServletRequest, resp: HttpServletResponse){
        req.getSession(true).removeAttribute("user")
        forward(req, resp, LoginServlet.LOGIN_JSP)
    }
}

fun HttpServlet.forward(request: HttpServletRequest, response: HttpServletResponse, destiny: String){
    val dispatcher = servletContext.getRequestDispatcher(destiny)
    dispatcher.forward(request, response)

}