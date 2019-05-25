package thermostat.listeners

import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.Filter
import javax.servlet.FilterConfig
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@WebFilter("/site/*")
class LoginFilter: Filter {
    companion object {
        const val LOCAL_USER = "inigo"
        const val USER_KEY = "name"
        const val LOGIN_URL = "/Thermostat/login.jsp"

    }
    private val localAddresses = listOf("127.0.0.1", "localhost", "0:0:0:0:0:0:0:1")

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val session = (request as HttpServletRequest).session
        setDefaultUserIfInLocal(request, session)
        if (request.getSession(true)?.getAttribute("name") == null){
            (response as HttpServletResponse).sendRedirect(LOGIN_URL)
        }
    }

    fun setDefaultUserIfInLocal(request: ServletRequest?, session : HttpSession){
        if (localAddresses.any{ request?.remoteAddr?.contains(it) == true }){
            session.setAttribute(USER_KEY, LOCAL_USER)
        }
    }

    override fun destroy() {
        println("LoginFilter destroyed")
    }

    override fun init(filterConfig: FilterConfig?) {
        println("LoginFilter initializated")
    }
}

