angular.module('starter.controllers',[])


.controller('myCtrl',function($scope,$http){
	var url="http://api.openweathermap.org/data/2.5/weather?q=";
	var param="&lang=es&units=metric&appid=";
	var KEY="b5bba053e2710075bb43d91499ed270a";

		$scope.QueryCity="";
		$scope.data={
			temp:"30",
			ciudad:"medellin",
			humedad:"98 %",
			descripcion: "dia lluvioso"

		};
		$scope.buscardato=function(name){
			$http({
				method:"GET",
				url:url+name+param+KEY
			}).then(function(response){
				console.log(response.data);
				$scope.data={
					temp:response.data.main.temp,
					ciudad:response.data.name,
					humedad:response.data.main.humidity,
					descripcion: response.data.weather[0].description

				};
			    //success
			    //do something with the response
			}, function(response){
			    //error
			    alert("Ingrese una ciudad");
			    //show an appropriate message
			});


		};

		//$http();



});