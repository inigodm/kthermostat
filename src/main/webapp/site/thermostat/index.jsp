<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 


this page will override the default page when changed the default gidget 

-->
<t:wrapper>
<div class="panel-group" ng-app="newApp" ng-controller="newCtrl">
            <div class="panel panel-default">
                <div class="panel-heading col-sm-12">
	                    <div class="col-sm-2"></div>
	                    <div class="col-sm-1">
	                    	<div ng-class="ngcircle"></div>
	                    </div>
                        <div class="col-sm-8">      
                        	Thermostate <span  class="lcd-text">Inside: <c:out value="{%roomTemp%}"/> Outside: <c:out value="{%outTemp%}"/></span>ºC
                        </div>
                        <div class="col-sm-2"></div>
	            </div>
                <div class="panel-body">
                    <div class="col-sm-12" style="line-height: 10em;">
                        <div class="col-sm-2">
                        </div>
                        <div class="col-sm-2 boton big linea boton-left" ng-click="add(1)"><span class="btn-mas-menos">+</span></div>
                        <div id="temp" class="col-sm-4 lcd-text-lg big linea">
                            <span id="valor1" class="digito">{%valor.substring(0,1)%}</span>
                            <span id="valor2" class="digito">{%valor.substring(1)%}</span>
                        </div>
                        <div class="col-sm-2 boton big linea boton-rigth"  ng-click="add(-1)"><span class="btn-mas-menos">-</span></div>
                        <div class="col-sm-2">
                        	<span>{%state%}</span>
                        </div>
                    </div>
                </div>
            </div>
         </div>
<script src="/Thermostat/js/thermostat/facade.js"></script>
</t:wrapper>