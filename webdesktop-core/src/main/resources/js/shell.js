

var selectedTab = 0;




$(document).ready(function() {
	setInterval(function() {
		shellEdit();
	},1000);
	
	$('#shell').attr('contentEditable','true');
	
	
	$('#shell').keydown(function (e){
		if(e.keyCode === 13){
			
			var body = $('#shell').text(); 
			$('#shell').html('');
			shellEdit(body);
		} 
	});
	
	
	
	$( "#tabs" ).tabs({ 
		active: 0,
			beforeActivate: function( event, ui ) {
				var isShell = $(ui.newTab.find("a")).attr("shell");
				if(isShell){
					selectedTab = $(ui.newTab.find("a")).attr("index");
					var old = $(ui.oldTab.find("a")).attr("index");
					$('#command-line').show();
				}else{
					$('#command-line').hide();
				}
			}
		});
});


function shellEdit(body){
	
	var urlS = CONTEXT_PATH+'/shelledit?id='+selectedTab;
	if(body!=null){
		urlS = urlS+'&body='+encodeURIComponent(body);
	}
	
	$.ajax({
		url : urlS,
		success: function(data,textStatus){
			if(data!=''){
				var curr = $('#bodyshellcontent-'+selectedTab);
				var v = curr.text();
				curr.text(v+data);
				curr.scrollTop(curr[0].scrollHeight);
			}
		}
	});
}