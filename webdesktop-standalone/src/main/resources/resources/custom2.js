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


var command = '';


$(document).ready(function() {
	
	

	setInterval(function() {
		var commandToSend = command;
		command = '';
		if(commandToSend!=''){
			$.ajax({
				url : './command?'+commandToSend,
			});
		}
		
	},TIMEOUT/2);

	$("#screen").keydown(function (e){
		if(keydownup[e.keyCode]==null){
			command += "_KP"+e.keyCode; 
		}
	});
	
	$("#screen").keydown(function (e){
		if(keydownup[e.keyCode]!=null && keydownup[e.keyCode]==false){
			keydownup[e.keyCode]=true;
			command += "_KD"+e.keyCode;
		}
		
	});
	
	$("#screen").keyup(function (e){
		if(keydownup[e.keyCode]!=null){
			command += "_KU"+e.keyCode;
			keydownup[e.keyCode]=false;
		}
	});
	
	$("#screen").mouseup(function(e){
		var c  = GetCoordinates(e);
		command += "_MU"+c;
		
	});
	
	$("#screen").mousedown(function(e){
		var c  = GetCoordinates(e);
		command += "_MD"+c;
	});
	
	$("#screen").bind("contextmenu",function(e){
		var c  = GetCoordinates(e);
		command += "_RC"+c;
		return false;
	});
	
	
	
	
	
	
});
