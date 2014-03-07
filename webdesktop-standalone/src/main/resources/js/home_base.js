
var TIMEOUT = 1000;

$(document).ready(function() {
	
	//document.getElementById('my-image').ondragstart = function() { return false; };
	$('img#screen').on('dragstart', function(event) { event.preventDefault(); });
	
	
	playMJPEG();

});


var id = null;

function playMJPEG(){
	if(id == null){
		id = setInterval(function() {
			var random = Math.floor(Math.random() * Math.pow(2, 31));
			$('img#screen').attr('src', '/screen?i='+Math.random());
			}, TIMEOUT);
		$('#stop').removeClass('disabled');
		$('#play').addClass('disabled');
	}
}

function stopMJPEG(){
	if(id!=null){
		clearInterval(id);
		id = null;
		$('#stop').addClass('disabled');
		$('#play').removeClass('disabled');
	}
}