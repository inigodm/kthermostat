package thermostat.listeners

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Matchers
import org.mockito.Mockito.*
import thermostat.servlets.LogoutServlet
import javax.servlet.RequestDispatcher
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class Login_filter {
    private var sr = mock(HttpServletRequest::class.java)
    private var sess = mock(HttpSession::class.java)
    private var resp = mock(HttpServletResponse::class.java)

    @Before
    fun setup(){
        `when`(sr.session).thenReturn(sess)
        `when`(sr.remoteAddr).thenReturn("someip")
    }

    @Test
    fun no_user_external_to_login() {
        val ls = LoginFilter()
        ls.doFilter(sr, resp, null)
        verify(resp, only()).sendRedirect(LoginFilter.LOGIN_URL)
    }

    @Test
    fun no_user_internal_home_user_continues_as_local(){
        `when`(sr.remoteAddr).thenReturn("localhost")
        val ls = LoginFilter()
        ls.doFilter(sr, resp, null)
        verify(sess, only()).setAttribute(LoginFilter.USER_KEY, LoginFilter.LOCAL_USER)
        verify(resp, only()).sendRedirect(Matchers.anyString())
        `when`(sr.remoteAddr).thenReturn("localhost")
        ls.doFilter(sr, resp, null)
        // The previous call and current
        verify(sess, times(2)).setAttribute(LoginFilter.USER_KEY, LoginFilter.LOCAL_USER)
        verify(resp, times(2)).sendRedirect(Matchers.anyString())
    }

    @Test
    fun user_continue_to_page(){
        `when`(sess.getAttribute("name")).thenReturn("validName")
        val ls = LoginFilter()
        ls.doFilter(sr, resp, null)
        verify(resp, only()).sendRedirect(Matchers.anyString())
    }
}


class Logout_servlet{
    private var sr = mock(HttpServletRequest::class.java)
    private var sess = mock(HttpSession::class.java)
    private var resp = mock(HttpServletResponse::class.java)

    @Before
    fun setup(){
        `when`(sr.getSession(ArgumentMatchers.anyBoolean())).thenReturn(sess)
        `when`(sr.remoteAddr).thenReturn("someip")
    }


    fun user_erased_from_session_when_called(){
        val logout = mock(LogoutServlet::class.java);
        logout.logout(sr, resp)
        verify(sess, only()).removeAttribute(ArgumentMatchers.anyString())
    }
}

class Login_servlet{
    private var sr = mock(HttpServletRequest::class.java)
    private var sess = spy(HttpSession::class.java)
    private var resp = mock(HttpServletResponse::class.java)

    @Before
    fun setup(){
        `when`(sr.session).thenReturn(sess)
        `when`(sr.getSession(true)).thenReturn(sess)
    }

    @Test
    fun forwarded_to_login(){
    }
}