<%@tag description="User Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.user != null}">
<div class="navbar subnav" role="navigation">
    <div class="navbar-inner">
        <div class="container"> 
            <div class="btn-group-wrap">
                 <ul class="pager subnav-pager  navbar-left"> 
		        	<li>
			          <a href="/Thermostat/site/thermostat/schedules">Schedules</a>
			        </li>
			        <li>
			          <a href="/Thermostat/site/stats">Stats</a>
			        </li>
	        	</ul>
             </div>         
        </ul>	
	</div>
</div>
</c:if>