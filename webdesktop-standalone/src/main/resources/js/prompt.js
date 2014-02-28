


$(document).ready(function() {
	setInterval(function() {
		shellEdit();
	},1000);
	
	$('#prompt').attr('contentEditable','true');
	
	
	$('#prompt').keydown(function (e){
		if(e.keyCode === 13){
			var body = $('#prompt').text(); 
			$('#prompt').html('');
			shellEdit(body);
		} 
	});
	
	
});


function shellEdit(body){
	
	var urlS = '/shelledit';
	if(body!=null){
		urlS = urlS+'?body='+encodeURIComponent(body);
	}
	
	$.ajax({
		url : urlS,
		success: function(data,textStatus){
			if(data!=''){
				var v = $('#bodyshellcontent').text();
				$('#bodyshellcontent').text(v+data);
				$("#bodyshellcontent").scrollTop($("#bodyshellcontent")[0].scrollHeight);
			}
		}
	});
}