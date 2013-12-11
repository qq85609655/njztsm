$.scrollToID = $.fn.scrollToID = function(id, gap) {
	gap = !isNaN(Number(gap)) ? gap : 50;
	var x = $(id).offset().left + $(this).scrollLeft() - gap;
	var y = $(id).offset().top + $(this).scrollTop() - gap;

	if (!(this instanceof $))
		return $.fn.scrollToID.apply($('html, body'), arguments);

	return $(this).stop().animate({
		scrollLeft : x,
		scrollTop : y
	});
};
var SKLAY = {
	createNs : function(parent,name){
		if(!parent[name]){
			parent[name] = {};
		}
	}
};
SKLAY.NS = function(namespace){
	var nsa = namespace.split('.');
	var parent = sklay;
	for ( var i = 0; i < nsa.length; i++) {
		sklay.createNs(parent,nsa[i]);
		parent = nsa[i];
	}
};
SKLAY.modal = function(args){
	var header='',footer=args.footer;
	if(args.header){
		header = "<div class='modal-header'><h3>"+args.header+"</h3></div>";
	}
	if(!footer){
		footer = "<div class='modal-footer'><button class='btn' data-dismiss='modal' aria-hidden='true'>Close</button><button class='btn btn-primary' data-loading-text='Loading...'>Submit</button></div>";
	}
	var modal =  $("<div id='"+args.id+"' class='modal hide fade' tabindex='-1' role='dialog' aria-hidden='true' aria-keyboard='true'>" +
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
};