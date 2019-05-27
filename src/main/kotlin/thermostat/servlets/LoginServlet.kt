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

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        var next = req!!.run { tryLogin(getParameter("username"), req!!.getParameter("password"))}
        forward(req!!, resp!!, next)
    }

    private fun tryLogin(user: String?, pass: String?): String {
        return "/site/index.jsp"
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