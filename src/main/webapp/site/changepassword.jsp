<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:wrapper>
<div class="panel-group" ng-app="newApp" ng-controller="newCtrl">
            <div class="panel panel-default">
                <div class="panel-heading col-sm-12">
	                    <div class="col-sm-2"></div>
	                    <div class="col-sm-1">
	                    	<div ng-class="ngcircle"></div>
	                    </div>
                        <div class="col-sm-8">      
                        	Change password <span  class="lcd-text"></span>
                        </div>
                        <div class="col-sm-2"></div>
	            </div>
	            <form action="ChangePassword">
	                <div class="panel-body">
	                    <div class="col-sm-6">
	                       <label class="col-sm-6" for="pass">Password</label>
	                     </div>
	                    <div class="col-sm-6">
	                      <input id="pass" name="pass" class="span2" type="password" value="">
	                    </div>
	                    <div class="col-sm-6">
	                       <label class="col-sm-6" for="repass">Retype Password</label>
	                    </div>
	                    <div class="col-sm-6">
	                       <input id="repass" class="span2" type="password" value="">
	                    </div>
	                    <div class="col-sm-6">
	                       <input type="submit"/>
	                    </div>
	                </div>
                </form>
            </div>
            <script>
              var mod = angular.module('newApp', []).config(function($interpolateProvider) {
      		    $interpolateProvider.startSymbol('{%');
      		    $interpolateProvider.endSymbol('%}');
              });
              mod.controller("newCtrl",['$scope', '$timeout',  '$http', '$interval', function($scope, $timeout, $http, $interval){
               
              }]
            );
            </script>
         </div>
</t:wrapper>