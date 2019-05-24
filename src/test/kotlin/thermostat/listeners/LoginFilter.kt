package thermostat.listeners

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

class Login_filter {
    var sr = Mockito.mock(HttpServletRequest::class.java)
    var sess = Mockito.mock(HttpSession::class.java)

    @Before
    fun setup(){
        Mockito.`when`(sr.session).thenReturn(sess)
        Mockito.`when`(sr.remoteAddr).thenReturn("127.0.0.1")
    }

    fun no_user_external_to_login() {

    }

    fun no_user_internal_home_user(){
    }


    @Test
    fun user_continue_to_page(){
        val ls = LoginFilter()
        //assert("nextPage" == ls.doFilter(sr, null, null))
    }
}