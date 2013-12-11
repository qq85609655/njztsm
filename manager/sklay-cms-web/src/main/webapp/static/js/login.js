$(function($){
	var inited = {'login':false,'regist':false};
	var errorRendered= {'login':false,'regist':false};
	function initOnce(formEl,action){
		
		var btns = $(formEl).find(".btn-primary");
		btns.unbind("click");
		
		if(!inited[action]){
			btns.on('click',function(e){
				var btn = $(this);
				btn.button('加载中');
				var frm = $(formEl).find('form');
				if(!errorRendered[action]){
					$("<div class='alert alert-error fade in hide'></div>").insertBefore(frm).alert();
					errorRendered[action]=true;
				}
				$.post(frm.attr('action'),frm.serialize(),function(res){
					var alertEl = $(formEl).find(".alert");
					switch (res) {
						case 0: location.reload(); break;
						case 1: alertEl.html("sorry,亲 ! 您输入的帐号或密码不正确.").show(); break;
						case 2: alertEl.html("sorry,亲 ! 您来迟了,该帐号已被注册.").show(); break;
						case 3: alertEl.html("sorry,亲 ! 您输入的帐号不符合要求哦.").show(); break;
						default: alertEl.html("糟糕 ! 网络发生故障了.").show(); break;
					}
					btn.button('reset');
				},'json');
			});
			inited[action] = true;
			
			$(document).keydown(function(event){
			    var curKey = event.which; 
			    if(curKey==13){
			    	btns.click();
			    }
			 });
		}
	}
	SKLAY.modal({"id":"loginModal","header":"登入",onShown:function(){initOnce('#loginModal','login');}});
	SKLAY.modal({"id":"registModal","header":"注册",onShown:function(){initOnce("#registModal",'regist');}});
});