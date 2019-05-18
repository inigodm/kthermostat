<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:wrapper>
		<jsp:body>
	      	<!--  if error message -->
	       <form class="form-signin" method="post" action="./login">
		         <!--  csrf token! -->	
				<h2 class="form-signin-heading">Please log in</h2>
				<c:if test="${error}">
				<label class="error">Login incorrect</label>
				</c:if>
		        <label class="sr-only" for="inputEmail" class="sr-only">Username</label>
		        <input class="form-control" type="text" name="username" placeholder="username" required autofocus>
		        <label class="sr-only" for="inputPassword" class="sr-only">Password</label>
		        <input class="form-control" type="password" name="password" placeholder="password" required>
		        <div class="checkbox">
		          <label>
		            <input type="checkbox" value="remember-me"> Remember me
		          </label>
		        </div>
		        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		    </form>
	    </jsp:body>
</t:wrapper>
