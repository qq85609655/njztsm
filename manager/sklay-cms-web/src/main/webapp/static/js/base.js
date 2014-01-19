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
	createNs : function(parent, name) {
		if (!parent[name]) {
			parent[name] = {};
		}
	}
};
SKLAY.NS = function(namespace) {
	var nsa = namespace.split('.');
	var parent = SKLAY;
	for ( var i = 0; i < nsa.length; i++) {
		SKLAY.createNs(parent, nsa[i]);
		parent = nsa[i];
	}
};

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"H+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

SKLAY.modal = function(args) {
	var header = '', footer = args.footer || '';
	if (args.header) {
		header = "<div class='modal-header'><h4 class='modal-title' id='modalLabel"
				+ args.id + "'>" + args.header + "</h4></div>";
	}
	if (!footer && !args.nofooter) {
		footer = "<div class='modal-footer'><button class='btn' data-dismiss='modal' aria-hidden='true'>关闭</button><button class='btn btn-primary' data-loading-text='Loading...'>确定</button></div>";
	}
	var modal = $("<div id='"
			+ args.id
			+ "' class='modal fade easein hide' aria-hidden='false' aria-labelledby='modalLabel"
			+ args.id + "' role='dialog' tabindex='-1'>" + header
			+ "<div class='modal-body'>loading......</div>" + footer + "</div>");

	modal.on('show', function(a, b, c, d) {
		if (args.onShow) {
			return args.onShow(this);
		}
		return true;
	});

	modal.on('shown', function() {
		if (!args.nofooter) {
			$(this).find(".modal-footer .btn-primary").on("click", function() {
				if (args.onSubmit) {
					args.onSubmit();
				} else {
					$(this).attr('disabled', "disabled");
					modal.find("form").submit();
					// $(this).button('reset');
				}
			});
		}
		if (args.onShown) {
			args.onShown(this);
		}
		if (args.onHidden) {
			modal.on('hidden', args.onHidden);
		}

	});
	modal.appendTo("body");
	return modal;
};

$(".modal-link-dialog").click(
				function(event) {
					event.preventDefault();
					$target = $(this).attr('data-target');
					$header = $(this).attr('data-title');
					$nofooter = $(this).attr('data-nofooter');
					$delete = $(this).attr('data-delete');

					if ($nofooter) {
						$($target)
								.find(".modal-header")
								.html(
										"<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button><h3>"
												+ $header + "</h3>");
						$($target).find(".modal-footer").hide();
					} else {
						$($target).find(".modal-header").html(
								"<h3>" + $header + "</h3>");
						$($target).find(".modal-footer").show();
					}

					$($target).removeData("modal");
					$($target).modal({
						remote : $(this).attr("href")
					});
					$($target).modal("show");
				});