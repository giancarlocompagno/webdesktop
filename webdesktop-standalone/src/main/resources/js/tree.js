


$(document).ready(function() {
	
	
	$('#jstree').jstree({
		"plugins" : ["search","state", "types", "wholerow"],
		'core' : {
	    	"animation" : 0,
	        "check_callback" : true,
	        "themes" : { "stripes" : true },
	        
	    	'data' :{
	            'url' : function (node){
	            		return '/tree';
	            	},
	            'data' : function (node) {
	                return { 'id' : node.id };
	            }
		     },
		     "types" : {
		    	    "#" : {"max_children" : 1,"max_depth" : 4,"valid_children" : ["root"]},
		    	    "root" : {"icon" : "/resources/css/tree/hd.png","valid_children" : ["default"]},
		    	    "default" : {"valid_children" : ["default","file"]},
		    	    "file" : {"icon" : "glyphicon glyphicon-file","valid_children" : []}
		    	  }
	    	}
		}).bind("select_node.jstree",function(){
			var tree_ref = $('#jstree').jstree(true);
			var node = tree_ref.get_selected();
			if(tree_ref.is_leaf(node)){
				$('#upload').addClass('disabled');
				$('#download').removeClass('disabled');
				$('#createfolder').addClass('disabled');
				$('#renamefile').removeClass('disabled');
				$('#deletefile').removeClass('disabled');
			}else{
				$('#upload').removeClass('disabled');
				$('#download').addClass('disabled');
				$('#createfolder').removeClass('disabled');
				$('#renamefile').addClass('disabled');
				$('#deletefile').addClass('disabled');
			}
			
		}).bind("rename_node.jstree", function (event, data) {
	        alert('rename');
	    }).bind("create_node.jstree", function (event, data) {
	        alert('create_node');
	    });
	
	var to = false;
	$('#demo_q').keyup(function () {
		if(to) { clearTimeout(to); }
		to = setTimeout(function () {
			var v = $('#demo_q').val();
			$('#jstree').jstree(true).search(v);
		}, 250);
	});
	
	
	$('#file').change(function(){
		var fileName = $(this).val();
		if(fileName!=''){
			$('#formUpdate').submit();
		}
	});
	
});

function createfolder() {
	var ref = $('#jstree').jstree(true),
		sel = ref.get_selected();
	if(!sel.length) { return false; }
	sel = sel[0];
	sel = ref.create_node(sel, {"type":"file"});
	if(sel) {
		ref.edit(sel);
	}
};
function renamefile() {
	var ref = $('#jstree').jstree(true),
		sel = ref.get_selected();
	if(!sel.length) { return false; }
	sel = sel[0];
	ref.edit(sel);
};

function deletefile() {
	var ref = $('#jstree').jstree(true),
		sel = ref.get_selected();
	if(!sel.length) { return false; }
	var id = encodeURIComponent(sel[0]);
	if(confirm('sei sicuro di voler cancellare il file?')){
		$.ajax({
			url : '/deletefile?id='+id,
			
		})
		.fail(function() {
			alert( "impossibile cancellare il file" );
		})
		.success(function() {
			ref.delete_node(sel);
		});	
	}
};

function downloadnode() {
	var ref = $('#jstree').jstree(true),
		sel = ref.get_selected();
	var id = encodeURIComponent(sel[0]);
	window.open('/tree?id='+id);
};

function uploadnode() {
	var ref = $('#jstree').jstree(true),
		sel = ref.get_selected();
	var id = sel[0];
	$('#directory').val(id);
	$('#file').click();
};
