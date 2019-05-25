package thermostat.listeners

import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito
import thermostat.servlets.LogoutServlet
import javax.servlet.RequestDispatcher
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class Login_filter {
    private var sr = Mockito.mock(HttpServletRequest::class.java)
    private var sess = Mockito.mock(HttpSession::class.java)
    private var resp = Mockito.mock(HttpServletResponse::class.java)

    @Before
    fun setup(){
        Mockito.`when`(sr.session).thenReturn(sess)
        Mockito.`when`(sr.remoteAddr).thenReturn("someip")
    }

    @Test
    fun no_user_external_to_login() {
        val ls = LoginFilter()
        ls.doFilter(sr, resp, null)
        Mockito.verify(resp, Mockito.only()).sendRedirect(LoginFilter.LOGIN_URL)
    }

    @Test
    fun no_user_internal_home_user_continues_as_local(){
        Mockito.`when`(sr.remoteAddr).thenReturn("localhost")
        val ls = LoginFilter()
        ls.doFilter(sr, resp, null)
        Mockito.verify(sess, Mockito.only()).setAttribute(LoginFilter.USER_KEY, LoginFilter.LOCAL_USER)
        Mockito.verify(resp, Mockito.only()).sendRedirect(Matchers.anyString())
        Mockito.`when`(sr.remoteAddr).thenReturn("localhost")
        ls.doFilter(sr, resp, null)
        // The previous call and current
        Mockito.verify(sess, Mockito.times(2)).setAttribute(LoginFilter.USER_KEY, LoginFilter.LOCAL_USER)
        Mockito.verify(resp, Mockito.times(2)).sendRedirect(Matchers.anyString())
    }

    @Test
    fun user_continue_to_page(){
        Mockito.`when`(sess.getAttribute("name")).thenReturn("validName")
        val ls = LoginFilter()
        ls.doFilter(sr, resp, null)
        Mockito.verify(resp, Mockito.only()).sendRedirect(Matchers.anyString())
    }
}

class Logout_servlet{
    private var sr = Mockito.mock(HttpServletRequest::class.java)
    private var sess = Mockito.mock(HttpSession::class.java)
    private var resp = Mockito.mock(HttpServletResponse::class.java)
    private var ctx = Mockito.mock(ServletContext::class.java)
    private var disp = Mockito.mock(RequestDispatcher::class.java)

    @Before
    fun setup(){
        Mockito.`when`(sr.session).thenReturn(sess)
        Mockito.`when`(sr.remoteAddr).thenReturn("someip")
        Mockito.`when`(ctx.getRequestDispatcher(LoginFilter.LOGIN_URL)).thenReturn(disp)
    }

    //Test
    fun user_erased_when_called(){
        val logout = Mockito.mock(LogoutServlet::class.java);
        Mockito.`when`(logout.servletContext).thenReturn(ctx)
        logout.logout(sr, resp)
        Mockito.verify(sess, Mockito.only()).removeAttribute("name")
    }
}

class Login_servlet{
    private var sr = Mockito.mock(HttpServletRequest::class.java)
    private var sess = Mockito.mock(HttpSession::class.java)
    private var resp = Mockito.mock(HttpServletResponse::class.java)
    private var ctx = Mockito.mock(ServletContext::class.java)
    private var disp = Mockito.mock(RequestDispatcher::class.java)

    @Before
    fun setup(){
        Mockito.`when`(sr.session).thenReturn(sess)
        Mockito.`when`(sr.remoteAddr).thenReturn("someip")
        Mockito.`when`(ctx.getRequestDispatcher(LoginFilter.LOGIN_URL)).thenReturn(disp)
    }

    //Test
    fun forwarded_to_login(){
        val logout = Mockito.mock(LogoutServlet::class.java);
        Mockito.`when`(logout.servletContext).thenReturn(ctx)
        logout.logout(sr, resp)
        Mockito.verify(sess, Mockito.only()).removeAttribute("name")
    }
}