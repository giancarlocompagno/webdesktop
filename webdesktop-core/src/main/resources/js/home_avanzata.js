var url = null;
function opengestionefolder(){
	url = CONTEXT_PATH+'/pagetree.html';
	$('#dialog').dialog('open');
}

function openshellprompt(){
	url = CONTEXT_PATH+'/shell.html';
	$('#dialog').dialog('open');
}

function opentaskmanager(){
	url = CONTEXT_PATH+'/taskmanager.html';
	$('#dialog').dialog('open');
}

var commands = '';

function addCommand(curr){
	if(idCommand!=null){
		commands +="_"+curr;	
	}
}

function getCommands(){
	var tmp = commands;
	commands = '';
	return tmp;
}


var idCommand = null;

function playCommand(){
	if(idCommand == null){
		idCommand = setInterval(function() {
			var commandToSend = getCommands();
			if(commandToSend!=''){
				$.ajax({
					url : CONTEXT_PATH+'/command?command='+commandToSend,
				});
			}
			
		},TIMEOUT/2);
	}
}

function stopCommand(){
	if(idCommand!=null){
		clearInterval(idCommand);
		idCommand = null;
	}
}


function FindPosition(oElement)
{
  if(typeof( oElement.offsetParent ) != "undefined")
  {
    for(var posX = 0, posY = 0; oElement; oElement = oElement.offsetParent)
    {
      posX += oElement.offsetLeft;
      posY += oElement.offsetTop;
    }
      return [ posX, posY ];
    }
    else
    {
      return [ oElement.x, oElement.y ];
    }
}


function GetCoordinates(e)
{
  var myImg = document.getElementById('screen');	
  var PosX = 0;
  var PosY = 0;
  var ImgPos;
  ImgPos = FindPosition(myImg);
  if (!e) var e = window.event;
  if (e.pageX || e.pageY)
  {
    PosX = e.pageX;
    PosY = e.pageY;
  }
  else if (e.clientX || e.clientY)
    {
      PosX = e.clientX + document.body.scrollLeft
        + document.documentElement.scrollLeft;
      PosY = e.clientY + document.body.scrollTop
        + document.documentElement.scrollTop;
    }
  PosX = PosX - ImgPos[0];
  PosY = PosY - ImgPos[1];
  
  return PosX+','+PosY;
}



$(document).ready(function() {
	
	playCommand();

	$(document).keydown(function (e){
		if(keydownup[e.keyCode]==null){
			addCommand("KP"+e.keyCode); 
		}
	});
	
	$(document).keydown(function (e){
		if(keydownup[e.keyCode]!=null && keydownup[e.keyCode]==false){
			keydownup[e.keyCode]=true;
			addCommand("KD"+e.keyCode);
		}
		
	});
	
	$(document).keyup(function (e){
		if(keydownup[e.keyCode]!=null){
			addCommand("KU"+e.keyCode);
			keydownup[e.keyCode]=false;
		}
	});
	
	$(document).mouseup(function(e){
		var c  = GetCoordinates(e);
		addCommand("MU"+c);
		
	});
	
	$(document).mousedown(function(e){
		var c  = GetCoordinates(e);
		addCommand("MD"+c);
	});
	
	$(document).bind("contextmenu",function(e){
		var c  = GetCoordinates(e);
		addCommand("RC"+c);
		return false;
	});
	
	
	


	$("#dialog").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 900,
	    height: 700,
	    position: ['center',20],
	    open: function(ev, ui){
	             $('#myIframe').attr('src',url);
	          },
	    close: function(ev, ui){
	    	$('#myIframe').attr('src','');
        }
	
	});
	
	
	
});


