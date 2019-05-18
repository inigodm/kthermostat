<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="navigation" fragment="true" %>
<%@attribute name="jumbotron" fragment="true" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/Thermostat/img/favicon.ico">
	<script src="/Thermostat/js/angular.min.js"></script>
            
    <title>DOMOENEA</title>
    <!-- Bootstrap core CSS -->
    <link href="/Thermostat/css/bootstrap.css" rel="stylesheet"/>
    <link href="/Thermostat/css/main.css" rel="stylesheet">
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/Thermostat/site/index.jsp">DOMOENEA</a>
        </div>
        <t:nav/>
      </div>
    </nav>
    <div class="container">
      <!--div class="jumbotron"-->
         <!-- jsp:invoke fragment="jumbotron"/-->
      <!--/div-->

    </div>
    <jsp:doBody/>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="/Thermostat/js/jquery-2.2.0.min.js"></script>
    <script src="/Thermostat/js/bootstrap.js"></script>
    </body>
</html>