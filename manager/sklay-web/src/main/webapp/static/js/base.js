var SKLAY = {
	createNs : function(parent,name){
		if(!parent[name]){
			parent[name] = {};
		}
	}
};
SKLAY.NS = function(namespace){
	var nsa = namespace.split('.');
	var parent = SKLAY;
	for ( var i = 0; i < nsa.length; i++) {
		SKLAY.createNs(parent,nsa[i]);
		parent = nsa[i];
	}
};
SKLAY.modal = function(args){
	var header='',footer=args.footer;
	if(args.header){
		if(footer)
			header = "<div class='modal-header'><button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button><h3>"+args.header+"</h3></div>";
		else{
			header = "<div class='modal-header'><button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>'" ;
			header += "'<h3>"+args.header+"</h3></div>";
		}
	}
	if(!footer){
		footer = "<div class='modal-footer'><button class='btn btn-close' data-dismiss='modal' aria-hidden='true'>关闭</button><button class='btn btn-primary' data-loading-text='Loading...'  data-html='true' data-href='reload' data-placement='right' data-content='' data-original-title='消息'>提交</button></div>";
	}
	var modal =  $("<div id='"+args.id+"' class='modal hide fade' tabindex='-1' role='dialog' aria-hidden='true' aria-keyboard='true'  data-backdrop='static'>" +
				header+
				"<div class='modal-body'></div>" +
				footer+
			"</div>");
	if(args.onShown){
		modal.on('shown',args.onShown);
	}
	if(args.onHidden){
		modal.on('hidden',args.onHidden);
	}
	modal.appendTo("body");
	
	return modal ;
};