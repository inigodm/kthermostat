var MAX_COLOR = 255;
var MIN_COLOR = 50;
var FIXED_COLOR = 255;
var initColor = [ MIN_COLOR, MIN_COLOR, MAX_COLOR ];
var tempmin = 10;
var tempmax = 30;
var flag = false;
var gap = (MAX_COLOR - MIN_COLOR) / (tempmax - tempmin);
var mod = angular.module('newApp', []).config(function($interpolateProvider) {
	$interpolateProvider.startSymbol('{%');
	$interpolateProvider.endSymbol('%}');
});
mod.controller("newCtrl", [ '$scope', '$timeout', '$http', '$interval',
		function($scope, $timeout, $http, $interval) {
			$scope.valor = "" + tempmin;
			$scope.val = tempmin;
			$scope.ponerTemperaturaMarcador = function() {
				$scope.valor = paddy($scope.val, 2);
				$scope.color = calcColor($scope.val);
				$('#temp').css('background-color', $scope.color);
			};
			$scope.getTemps = check;
			function check() {
				$scope.add(0);
			}
			$scope.add = function(value) {
				$http.post("/Thermostat/site/rest/thermostat/changer", {
					"method" : "changetemp",
					"data" : value
				}).success($scope.manage_thermostatInfo);
			};
			$scope.manage_thermostatInfo = function(resp) {
				$scope.val = resp.desiredTemp;
				$scope.roomTemp = resp.roomTemp;
				$scope.outTemp = resp.outsideTemp;
				var isOn = resp.isOn;
				if (isOn || isOn == "true") {
					$scope.ngcircle = "circle-red";
				} else {
					$scope.ngcircle = "circle-green";
				}
				$scope.ponerTemperaturaMarcador();
			}
			//TODO: Esto no lo hace bien, probar en el interval
			$scope.add(0);
			$interval(function() {
				$scope.getTemps();
			}, 10000);
			//TODO: en la raspberry solo
			//$interval(function(){$scope.getRoomTemp();}, 1000);
		} ]);

function paddy(n, p, c) {
	var pad_char = typeof c !== 'undefined' ? c : '0';
	var pad = new Array(1 + p).join(pad_char);
	return (pad + n).slice(-pad.length);
}

function calcColor(valor) {
	if (valor > tempmax) {
		return arrayToColor([ MAX_COLOR, FIXED_COLOR, MIN_COLOR ]);
	}
	if (valor > tempmin) {
		var toadd = Math.floor(((valor - tempmin) * gap));
		return arrayToColor([ (MIN_COLOR + toadd), FIXED_COLOR,
				(MAX_COLOR - toadd) ]);
	}
	return arrayToColor([ MIN_COLOR, FIXED_COLOR, MAX_COLOR ]);
}

function arrayToColor(arr) {
	return 'rgb(' + arr[0] + ',' + arr[1] + ',' + arr[2] + ')'
}