$(function($){
	var inited = {'create':false ,'modalForm':false ,'widget-api':false ,'widget-phone':false,'widget-server':false ,'widget-client':false};
	var errorRendered= {'create':false ,'modalForm':false};
	
	 $('[data-name="tooltip"]').tooltip() ;
	
	 $(".modal-link").on('click',function(event) {
	    	event.preventDefault() ;
	    	target = $(this).attr('data-target') ;
	    	
	    	$target = $(target) ;
	        $header = $(this).attr('data-title') ;
	        $nofooter = $(this).attr('data-nofooter') ;
	        $delete = $(this).attr('data-delete') ;
	        $msg = $(this).attr('data-msg') ;
	        $href= $(this).attr("href") ;
	        
	        $target.find(".modal-header").html("<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button><h3>"+$header+"</h3>") ;        	
	        
	        if($nofooter){
	        	$target.find(".modal-header").html("<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button><h3>"+$header+"</h3>") ;
	        	$target.find(".modal-footer").hide() ;
	        }
	        else{
	        	$target.find(".modal-footer").show() ;
	        }
	        $target.removeData("modal") ;
	        
	        if($delete){
	        	$href= $(this).attr("data-href") ;
	        	var form  = '<form class="form-horizontal" action="'+$href+'" method="post">' ;
	        	form  += "<h3>"+$msg+"</h3>" ;
	        	form  += "</form>" ;
	        	$target.find('.modal-body').html(form) ;
	        }else{
	        	$target.modal({remote: $href});
	        }
	        $target.modal("show");
	    }) ;
	
	function popoverDestory (btn ,form){
		
		$('.btn-primary').popover('destroy') ;
		var $href = btn.attr('data-href') ;
		var $reload = $('#pagination').find('.active') ;
		$('.btn-close').click() ;
		if('reload' == $href){
			
			if($reload)
				window.location.href = $reload.find('a').attr('href') ;
			return  ;
			window.location.reload() ;
		}else{
			window.location.href = $href ;
		} ;
	}
	
	function callLater(fRef, argu1 ,argu2) 
	{
		return (function() {
				fRef(argu1 , argu2);
		});
	};
	function initOnce(formEl,action){
		if(!inited[action]){
			$(formEl).find('.btn-primary').on('click',function(e){
				var btn = $(this);
				btn.button('loading');
				var frm = $(formEl).find('form');
				if(!errorRendered[action]){
					$("<div class='alert alert-error fade in hide'></div>").insertBefore(frm).alert();
					errorRendered[action]=true;
				}
				
				$.post(frm.attr('action'),frm.serialize(),function(res){
					var alertEl = $(formEl).find(".alert");
					switch (res.code) {
						case 0: {btn.attr('data-content',res.msg) ;$('.btn-primary').popover('show');window.setTimeout(callLater(popoverDestory, btn,frm),500); break;}
						default: alertEl.html(res.msg).show(); break;
					}
					btn.button('reset');
				},"json");
			});
			inited[action] = true;
		}
	}
	
	initOnHidden = function(formEl){
		$(formEl).find('.btn-primary').button('reset');
	} ;
	
	SKLAY.modal({"id":"modalTemplate","header":"modalTemplateHeader",onShown:function(){initOnce(this,'modalForm');},onHidden:function(){initOnHidden(this);}});
	
	initOnce($('#createForm'), "create") ;
	initOnce($('#widget-api'), "widget-api") ;
	initOnce($('#widget-phone'), "widget-phone") ;
	initOnce($('#widget-server'), "widget-server") ;
	
	 $.scrollUp({
         scrollName: 'scrollUp', // Element ID
         topDistance: '10', // Distance from top before showing element (px)
         topSpeed: 300, // Speed back to top (ms)
         animation: 'slide', // Fade, slide, none
         animationInSpeed: 200, // Animation in speed (ms)
         animationOutSpeed: 200, // Animation out speed (ms)
         scrollText: '', // Text for element
         activeOverlay: false  // Set CSS color to display scrollUp active point, e.g '#00FFFF'
    });
});