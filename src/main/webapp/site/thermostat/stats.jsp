<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:wrapper>
<link href="/Thermostat/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/Thermostat/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<div class="panel-group" ng-app="newApp" ng-controller="newCtrl">
            <div class="panel panel-default">
                <div class="panel-heading">Thermostate: stats</div>
                <div class="panel-body">
                    		<div class="col-sm-5">
		                        <label class="control-label col-sm-5" for="horainicio">Init date</label>
		                        <div class="input-group col-sm-6">
			                       <div class="input-append date form_date" id="horainicio" data-date="" data-date-format="dd-mm-yyyy">
								        <input class="span2" size="16" type="text" value="" readonly>
								        <span class="add-on"><i class="icon-remove"></i></span>
								        <span class="add-on"><i class="icon-th"></i></span>
								    </div>  
	                        </div>
	                        </div>
	                        <div class="col-sm-5">
	                        	 <label class="control-label col-sm-5" for="horafin">End date</label>
		                         <div class="input-group col-sm-6">
				                    <div class="input-append date form_date" id="horafin" data-date="" data-date-format="dd-mm-yyyy">
								        <input class="span2" size="16" type="text"  value="" readonly>
								        <span class="add-on"><i class="icon-remove"></i></span>
								        <span class="add-on"><i class="icon-th"></i></span>
								    </div>  
		                         </div>
	                        </div>
	                        <div class="col-sm-2 boton" ng-click="find()"><span class="btn-mas-menos">search</span></div>
                        </div>
                <div class="form-group-line">
       				<div id="chart_div" style="width: 100%; height: 500px;"></div>
                </div>
                
           </div>
        <script src="/Thermostat/js/jquery-2.2.0.min.js"></script>
        <script type="text/javascript" src="/Thermostat/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
		<script type="text/javascript" src="/Thermostat/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
		<script type="text/javascript" src="/Thermostat/js/locales/bootstrap-datetimepicker.es.js" charset="UTF-8"></script>
            <script>
            $('.form_date').datetimepicker({
            	format:'yyyy-mm-dd-hh:ii',
                weekStart: 1,
                todayBtn:  1,
        		autoclose: 1,
        		todayHighlight: 1,
        		minView: 0,
        		maxView: 2,
        		forceParse: 0,
        		startDate: "dateToday",
        		maxDate: "dateToday"
            	});
            var mod = angular.module('newApp', []).config(function($interpolateProvider) {
      		    $interpolateProvider.startSymbol('{%');
      		    $interpolateProvider.endSymbol('%}'); 	 
              });
            mod.controller("newCtrl",['$scope', '$timeout',  '$http', '$interval', function($scope, $timeout, $http, $interval){
            	$scope.find = function(value){
            	$http.get("/Thermostat/site/rest/stats/get/"+$("#horainicio").find("input").val()+"/"+$("#horafin").find("input").val())
            	.success($scope.doReturnOk);
            	};
            	$scope.doReturnOk = function(resp){
            		for (i = 0; i < resp.length; i++){
            			resp[i].date = new Date(resp[i].date);
            		}
            		$scope.drawChart(resp)
                }
            	$scope.drawChart = function(data){
            		var arr = []
            		arr = [['Date', 'Temp', 'Desired']]
            		for (i = 0; i < data.length; i++){
            			arr[i+1]=[data[i].date, parseFloat(data[i].temperature), parseFloat(data[i].desiredTemp)]
            		}
            		var data = google.visualization.arrayToDataTable(arr);
            		var yMin;
            	    var yMax;
            	    var columnRange = data.getColumnRange(1);
            	    var columnRange2 = data.getColumnRange(2);
            	    yMin = columnRange.min;
            	    yMax = columnRange.max;
            	    if (columnRange2.min < yMin){
            	    	yMin = columnRange2.min;
            	    }
            	    if (columnRange2.max > yMax){
            	    	yMax = columnRange2.max;
            	    }
            	    yMin-=1;
            	    yMax+=1;
            	    var options = {
                            title: 'Temperature',
                            hAxis: {title: 'Date',  titleTextStyle: {color: '#333'}},
                            vAxis: {viewWindow: {
                                max:yMax,
                                min:yMin
                              }}
                          };

                    var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                    chart.draw(data, options);
            	}
            }]);
            google.charts.load('current', {'packages':['corechart']});
            google.charts.setOnLoadCallback(drawChart);

            
            function drawChart() {
            	var data = google.visualization.arrayToDataTable([
            	                                                  ['Date', 'Temp', 'Desired'],
            	                                                  [new Date(2014, 10, 15, 7, 20, 45),  0, 0],
            	                                                  [new Date(2014, 10, 15, 7, 20, 45),  0, 0],
            	                                                  [new Date(2014, 10, 15, 7, 20, 45),  0, 0],
            	                                                  [new Date(2014, 10, 15, 7, 20, 45),  0, 0]
            	                                                                                          ]);
              var options = {
                title: 'Temperature',
                hAxis: {title: 'Date',  titleTextStyle: {color: '#333'}},
                vAxis: {minValue: 0}
              };

              var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
              chart.draw(data, options);
            }
            </script>
       </div>    
</t:wrapper>