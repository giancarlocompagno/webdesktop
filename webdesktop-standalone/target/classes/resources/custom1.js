
var TIMEOUT = 1000;

$(document).ready(function() {
	
	//document.getElementById('my-image').ondragstart = function() { return false; };
	$('img#screen').on('dragstart', function(event) { event.preventDefault(); });
	
	var refreshInterval = setInterval(function() {
		var random = Math.floor(Math.random() * Math.pow(2, 31));
		$('img#screen').attr('src', './screen?i='+Math.random());
		}, TIMEOUT);

});

