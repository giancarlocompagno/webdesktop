function killprocess(action,pod){
	window.location=CONTEXT_PATH+action+'?killprocess='+pod;
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


function fnHideColumn(){
	var oTable = $('#taskmanager').dataTable();
	var length = oTable.fnSettings().aoColumns.length;
	for(var curr=5;curr<length-1;curr++){
		oTable.fnSetColumnVis(curr,false);
	}
}

/*$(document).ready(function() {
	$('#taskmanager').dataTable({
        "bJQueryUI": true,
        "sPaginationType": "full_numbers"
    });
}); */


/* Formating function for row details */
function fnFormatDetails ( oTable, nTr )
{
    
	var aData = oTable.fnGetData( nTr );
	var sOut = '';
	var columns = oTable.fnSettings().aoColumns;
	var length = columns.length;
	if(length>5){
		sOut += '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
		for(var curr=5;curr<length-1;curr++){
			sOut += '<tr><th>'+columns[curr].sTitle+':</th><td>'+aData[curr]+'</td></tr>';
		}
    sOut += '</table>';
	}
    return sOut;
}
 
$(document).ready(function() {
    /*
     * Insert a 'details' column to the table
     */
    var nCloneTh = document.createElement( 'th' );
    var nCloneTd = document.createElement( 'td' );
    nCloneTd.innerHTML = '<img src="'+CONTEXT_PATH+'/resources/css/datatable/images/details_open.png">';
    nCloneTd.className = "center";
     
    $('#taskmanager thead tr').each( function () {
        this.insertBefore( nCloneTh, this.childNodes[0] );
    } );
     
    $('#taskmanager tbody tr').each( function () {
        this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
    } );
     
    /*
     * Initialse DataTables, with no sorting on the 'details' column
     */
    var oTable = $('#taskmanager').dataTable( {
        "aoColumnDefs": [
            { "bSortable": false, "aTargets": [ 0 ] }
        ],
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "aaSorting": [[1, 'asc']]
    });
    
    
    fnHideColumn();
     
    /* Add event listener for opening and closing details
     * Note that the indicator for showing which row is open is not controlled by DataTables,
     * rather it is done here
     */
    $('#taskmanager tbody td img').click(function () {
        var nTr = $(this).parents('tr')[0];
        if ( oTable.fnIsOpen(nTr) )
        {
            /* This row is already open - close it */
            this.src = CONTEXT_PATH+"/resources/css/datatable/images/details_open.png";
            oTable.fnClose( nTr );
        }
        else
        {
            /* Open this row */
            this.src = CONTEXT_PATH+"/resources/css/datatable/images/details_close.png";
            oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
        }
    } );
} );
