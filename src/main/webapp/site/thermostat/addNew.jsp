<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:wrapper>
<link href="/Thermostat/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/Thermostat/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">
<div class="panel-group" ng-app="newApp" ng-controller="newCtrl" ng-init="findSchedules()">
            <div class="panel panel-default">
                <div class="panel-heading">Thermostate: add new Schedule</div>
                <div class="panel-body">
                    <form class="form-inline" id="addform" role="form">
                    	<input id="id" type="hidden"/>
                    	<div class="form-group-line">
                    		<div class="col-sm-6">
		                        <label class="control-label col-sm-5" for="fechainicio">Init time</label>
		                        <div class="input-group col-sm-6">
			                        <div id="minHour" class="controls input-append date form_time" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
								        <input class="span2" size="16" type="text" value="" readonly required>
								        <span class="add-on"><i class="icon-remove"></i></span>
								        <span class="add-on"><i class="icon-th"></i></span>
								    </div>     
								    <input type="hidden" id="dtp_input3" value="" />       
	                        </div>
	                        </div>
	                        <div class="col-sm-6">
	                        	 <label class="control-label col-sm-5" for="fechafin">End time</label>
		                         <div class="input-group col-sm-6">
				                    <div id="maxHour" class="controls input-append date form_time" data-date="" data-date-format="hh:ii" data-link-field="dtp_input4" data-link-format="hh:ii">
								        <input class="span2" size="16" type="text" value="" readonly required>
								        <span class="add-on"><i class="icon-remove"></i></span>
								        <span class="add-on"><i class="icon-th"></i></span>
								    </div>  
		                         </div>
		                         <input type="hidden" id="dtp_input4" value="" />   
	                        </div>
                        </div>
                        <div class="form-group-line">
                    		<div class="col-sm-6">
		                        <label class="control-label col-sm-5" for="mintemp">Min Temp</label>
		                        <div class="input-append col-sm-6">
			                        <input id="mintemp" type="number" value="15" class="form-control input-small" required/>
	                        	</div>
	                        </div>
	                        <div class="col-sm-6">
	                        	<label class="control-label col-sm-5" for="weekdays">Days</label>
		                        <div class="input-append">
		                           <select id="weekdays" class="form-control input-small" multiple>
									  <option value="L" default>Monday</option>
									  <option value="M">Tuesday</option>
									  <option value="X">Wednesday</option>
									  <option value="J">Thursday</option>
									  <option value="V">Friday</option>
									  <option value="S">Saturday</option>
									  <option value="D">Sunday</option>
									</select>
	                        </div>
	                        </div>
	                    </div>
                        <div class="form-group-inline">
                            <div class="col-sm-offset-0"/>
		                    <div class="col-sm-4">
		                        <label class="control-label col-sm-5" for="active">Active
		                        <input id="active" type="checkbox" value="true" class="form-control input-small"/>
		                        </label>
	                        </div>
                        
						    <div class="col-sm-offset-0 col-sm-6">
						      <button id="post" ng-show="post" class="btn btn-default" ng-click="dopost()">Submit</button>
						      <button id="put" ng-show="put" class="btn btn-default" ng-click="doput()">Save</button>
						      <button id="cancel"  class="btn btn-default" ng-click="docancel()">Cancel</button>
						    </div>
						  </div>
                    </form>
                    <div class="col-sm-12">
                        <table class="table table-striped">
						    <thead>
						      <tr>
						        <th>Active</th>
						        <th>Days</th>
						        <th>Time range</th>
						        <th>Temperature</th>
						      </tr>
						    </thead>
						    <tbody>
						      <tr ng-repeat="x in schedules" ng-click="setFields(x)">
						        <td>{%x.active%}</td>
						        <td>{%x.weekdays%}</td>
						        <td>{%x.minHour%} - {%x.maxHour%}</td>
						        <td>{%x.desiredTemp%}</td>
						        <td>
									<span class="glyphicon glyphicon-trash" ng-click="del(x.id)"></span>
								</td>
						      </tr>
						    </tbody>
						  </table>
                    </div>
                </div>
           </div>
        <script src="/Thermostat/js/jquery-2.2.0.min.js"></script>
		<script type="text/javascript" src="/Thermostat/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/Thermostat/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
		<script type="text/javascript" src="/Thermostat/js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/4.4.0/bootbox.min.js"></script>
            <script>
            $('.form_time').datetimepicker({
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 1,
    		minView: 0,
    		maxView: 1,
    		forceParse: 0
       		 });
            //$('.form_date').datetimepicker({format: 'yyyy-mm-dd hh:ii'});
            $('.form_date').datetimepicker({
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 2,
    		minView: 2,
    		forceParse: 0
        	});
            $('.selectpicker').selectpicker({
            	  style: 'btn-info',
            	  size: 4
            	});
            
            var mod = angular.module('newApp', []).config(function($interpolateProvider) {
      		    $interpolateProvider.startSymbol('{%');
      		    $interpolateProvider.endSymbol('%}');
            });
                 
            mod.controller("newCtrl",['$scope','$http', function($scope, $http){
            	$scope.dopost = function(value){
            		$http.post("/Thermostat/site/rest/tasks/",
                			JSON.stringify({"maxHour":$("#maxHour").find("input").val(),
                				"minHour":$("#minHour").find("input").val(),
                				"desiredTemp":$("#mintemp").val(),
                				"weekdays":$("#weekdays").val().join(","),
                				"active":$("#active").is(":checked")?1:0}
                			)).success($scope.doReturnOk);
                	};
                	
                $scope.doReturnOk = function(resp){
                	$scope.schedules = resp;
                	$scope.docancel();
                }
                
                $scope.doput = function(value){
                		$http.put("/Thermostat/site/rest/tasks/",
                    			JSON.stringify({"maxHour":$("#maxHour").find("input").val(),
                    				"minHour":$("#minHour").find("input").val(),
                    				"desiredTemp":$("#mintemp").val(),
                    				"weekdays":$("#weekdays").val().join(","),
                    				"active":$("#active").is(":checked")?1:0,
                    				"id":$("#id").val()}
                    			)).success($scope.doReturnOk);
                    	};
                    	
                $scope.loadweekdays = function(x){
                	$("#weekdays").val(x.split(","));
                    //$("#weekdays").multiselect("refresh");
                }
                $scope.put=false;
                $scope.post=true;
                $scope.setFields = function(x){
                	$("#minHour").find("input").val(x.minHour);
        			$("#maxHour").find("input").val(x.maxHour);
        			$("#mintemp").val(x.desiredTemp);
        			$scope.loadweekdays(x.weekdays);
        			$("#active").prop('checked', x.active==1);
        			$("#id").val(x.id);
        			$scope.put=true;
        			$scope.post=false;
                }
                
                $scope.docancel = function(){
                	$("#minHour").find("input").val("");
        			$("#maxHour").find("input").val("");
        			$("#mintemp").val(15);
        			$scope.loadweekdays("");
        			$("#active").val(0);
        			$("#id").val("");
        			$scope.put=false;
                    $scope.post=true;
                }
                	
            	$scope.findSchedules = function(){
                	$http.get("/Thermostat/site/rest/tasks/").success($scope.doReturnOk);
            	};
            	
            	$scope.del = function(id){
            	    bootbox.confirm({
            	    	title:"Atention",
            	        message: "You are going to delete selected schedule. Are you sure?",
            	        buttons: {
            	            cancel: {
            	                label: '<i class="fa fa-times"></i> Cancel'
            	            },
            	            confirm: {
            	                label: '<i class="fa fa-check"></i> Confirm'
            	            }
            	        },
            	        callback: function (result) {
            	            console.log('This was logged in the callback!' + result + (result==true));
            	            if (result){
            	            	$http.delete("/Thermostat/site/rest/tasks/"+id+"/").success($scope.doReturnOk);
            	            }
            	        }
            	    });
                }
            }]);
            </script>
       </div>    
</t:wrapper>