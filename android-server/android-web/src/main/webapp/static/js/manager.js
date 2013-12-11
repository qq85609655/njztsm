$(function() {
	
	var editor = KindEditor.create('textarea[name="newsCategory.news.body"]', {
//		items : [ 'bold', 'italic', 'underline', 'strikethrough',
//		'removeformat', '|', 'insertorderedlist',
//		'insertunorderedlist', 'forecolor', 'hilitecolor', 'fontname',
//		'fontsize', '|', 'link', 'unlink', 'emoticons', 'shcode',
//		'image', 'flash', 'quote', '|', 'code', 'source', 'about' ],
		items: ['source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
			'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
			'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
			'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
			'anchor', 'link', 'unlink', '|', 'about'] ,
		cssPath : newsParam.cssPath,
		uploadJson : newsParam.uploadJson,
		filePostName : 'file',
		fileManagerJson : newsParam.fileManagerJson,
		allowFileManager : true
	});
	prettyPrint();
	var _w = 'widget' + newsParam.widgetId;
	var tableId = _w + '-table';
	var _chkeds = [];

	var afterClick = function() {
		if (_chkeds.length > 0) {
			$('#' + _w + '-remove').removeAttr('disabled');
			$('#' + _w + '-pass').removeAttr('disabled');
			$('#' + _w + '-nopass').removeAttr('disabled');
			
			if (_chkeds.length == 1) {
				$('#' + _w + '-edit').removeAttr('disabled');
			}else{
				$('#' + _w + '-edit').attr('disabled', 'disabled');
			}
		} else {
			$('#' + _w + '-remove').attr('disabled', 'disabled');
			$('#' + _w + '-pass').attr('disabled', 'disabled');
			$('#' + _w + '-nopass').attr('disabled', 'disabled');
			$('#' + _w + '-edit').attr('disabled', 'disabled');
		}
	};

	$("#" + tableId).find(":checkbox:first").click(function() {
		var _chks = $(this).closest("tr").nextAll().find(":checkbox");
		if (_chks && _chks.length > 0) {
			for ( var i = 0; i < _chks.length; i++) {
				var _chk = _chks[i];
				var _newsId = $(_chk).attr("newsId");
				if (_chk.checked) {
					_chkeds = $.grep(_chkeds, function(n) {
						return n != _newsId;
					});
					_chk.checked = false;
				} else {
					_chkeds.push(_newsId);
					_chk.checked = true;
				}
			}
			afterClick();
		}
	});

	$("#" + tableId).find(":checkbox:not(:first)").click(function() {
		var _chk = this;
		var _newsId = $(_chk).attr("newsId");
		if (_chk.checked) {
			_chkeds.push(_newsId);
		} else {
			_chkeds = $.grep(_chkeds, function(n) {
				return n != _newsId;
			});
		}
		if (_chkeds.length == 0) {
			$("#" + tableId).find(":checkbox:first").removeAttr("checked");
		}
		afterClick();
	});
	$('#' + _w + '-remove').click(function() {
		if(!$(this).is(":disabled")){
			var newsIdsStr = "?";
			for(var i=0;i<_chkeds.length;i++){
				newsIdsStr+="newscates="+_chkeds[i]+"&";
			}
			$.post($(this).attr("rel")+newsIdsStr,function(){
				location.reload();
			});
		}
	});
	
	$('#' + _w + '-pass').click(function() {
		if(!$(this).is(":disabled")){
			var newsIdsStr = "?";
			for(var i=0;i<_chkeds.length;i++){
				newsIdsStr+="newscates="+_chkeds[i]+"&";
			}
			$.post($(this).attr("rel")+newsIdsStr,function(){
				location.reload();
			});
		}
	});
	
	$('#' + _w + '-nopass').click(function() {
		if(!$(this).is(":disabled")){
			var newsIdsStr = "?";
			for(var i=0;i<_chkeds.length;i++){
				newsIdsStr+="newscates="+_chkeds[i]+"&";
			}
			$.post($(this).attr("rel")+newsIdsStr,function(){
				location.reload();
			});
		}
	});
	
	$('#' + _w + '-add').click(function() {
		$('#categoryLabel').show() ;
		$('#categorySelect').removeAttr('disabled').show() ;
		$('#newsManagerForm').attr('action',newsParam.addNews) ;
		$("#newsManagerForm").get(0).reset();
		editor.html("");
		var titleEl = $("#newsTitle");
		 $('body').scrollToID(titleEl);
		 titleEl.focus();
	});
	
	$('#' + _w + '-edit').click(function() {
		var _f = $("#newsManagerForm");
		_f.attr('action',newsParam.editNews) ;
		$('#categoryLabel').hide() ;
		$('#categorySelect').attr('disabled','disabled').hide() ;
		
		$.post($(this).attr("rel")+"/"+_chkeds[0],function(newscate){
			if(newscate.code==1){
				var newscateDate = $.parseJSON(newscate.data);
				_f.find("#newsId").val(newscateDate.id);
				_f.find("#newsTitle").val(newscateDate.title);
				editor.html(newscateDate.body);
			}else{
				alert(newscate.msg) ;
			}
		});
	});
	
	$('#' + _w + '-table select').change(function() {
		var _select = $(this);
		var _newsId = _select.closest("tr").attr("newsId");
		newsParam.changeTypeUrl;
		$.post(newsParam.changeTypeUrl,{id:_newsId,owner:_select.val()});
	});
});