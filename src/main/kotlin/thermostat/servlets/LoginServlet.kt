package thermostat.servlets

import thermostat.dao.UserDAO
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import javax.servlet.ServletException

@WebServlet("/login")
class LoginServlet: HttpServlet() {

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        resp!!.writer.append("Server at: ").append(req!!.contextPath)
        val dispatcher = servletContext.getRequestDispatcher("/login.jsp")
        dispatcher.forward(req, resp)
    }
}

@WebServlet("/logout")
class LogoutServlet: HttpServlet() {
    val loginUrl = "/login.jsp"
    /**
     * @see HttpServlet.doGet
     */
    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.getSession(true).removeAttribute("user")
        servletContext.getRequestDispatcher(loginUrl).forward(req, resp)
    }

    /**
     * @see HttpServlet.doPost
     */
    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        doGet(request, response)
    }
}