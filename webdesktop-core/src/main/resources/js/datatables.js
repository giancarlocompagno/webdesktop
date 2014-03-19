function killprocess(pod){
	window.location=CONTEXT_PATH+'/taskmanager.html?killprocess='+pod;
}

function fnShowHide(obj, iCol ){
	var oTable = $('#taskmanager').dataTable();
    var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
    oTable.fnSetColumnVis( iCol, bVis ? false : true );
    var button = $(obj);
    if(bVis){
    	button.addClass('btn-danger');
    	button.removeClass('btn-success');
    }else{
    	button.addClass('btn-success');
    	button.removeClass('btn-danger');
    }
}

$(document).ready(function() {
	$('#taskmanager').dataTable({
        "bJQueryUI": true,
        "sPaginationType": "full_numbers"
    });
}); 

