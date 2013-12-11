<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>

<script type="text/javascript" src="${ctx }/static/thirdparty/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${ctx }/static/thirdparty/swfupload/swfupload.queue.js"></script>
<script type="text/javascript" src="${ctx }/static/thirdparty/swfupload/fileprogress.js"></script>
<script type="text/javascript" src="${ctx }/static/thirdparty/swfupload/handlers.js"></script>
<script type="text/javascript">
		var upload1;

		window.onload = function() {
			upload1 = new SWFUpload({
				// Backend Settings
				upload_url: "upload.php",
				post_params: {"PHPSESSID" : "s5cl0om1v897710iddq54uq2d1"},

				// File Upload Settings
				file_size_limit : "102400",	// 100MB
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : "10",
				file_queue_limit : "0",

				// Event Handler Settings (all my handlers are in the Handler.js file)
	
		//		file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
		//		file_dialog_complete_handler : fileDialogComplete,
				
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
	//			upload_complete_handler : uploadComplete,
			
				// Button Settings
				button_image_url: "${ctx }/static/thirdparty/swfupload/TestImageNoText_65x29.png",
				button_width: "65",
				button_height: "29",
				button_placeholder_id: "spanButtonPlaceholder1",
				button_text: '<span class="theFont">选择文件</span>',
				button_text_style: ".theFont { font-size: 16; }",
				button_text_left_padding: 12,
				button_text_top_padding: 3,

				
				
				
				// Flash Settings
				flash_url : "${ctx }/static/thirdparty/swfupload/swfupload.swf",
				

				custom_settings : {
					progressTarget : "fsUploadProgress1",
					cancelButtonId : "btnCancel1"
				},
				
				// Debug Settings
				debug: false
			});

	     }
	</script>
<div id="content">
	<h2>Multi-Instance Demo</h2>
	<form id="form1" action="index.php" method="post" enctype="multipart/form-data">
		<table>
			<tr valign="top">
				<td>
					<div>
						<div class="fieldset flash" id="fsUploadProgress1">
							<span class="legend">Large File Upload Site</span>
						</div>
						<div style="padding-left: 5px;">
							<span id="spanButtonPlaceholder1"></span>
							<input id="btnCancel1" type="button" value="Cancel Uploads" onclick="cancelQueue(upload1);" disabled="disabled" style="margin-left: 2px; height: 22px; font-size: 8pt;" />
							<input type="button" value="Start Upload" onclick="upload1.startUpload();" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
							
							<br />
						</div>
					</div>
				</td>
				<td>
				</td>
			</tr>
		</table>
	</form>
</div>


<div class="widget-box">	
	<div class="widget-content tab-content nopadding hide">
		<div id="alert-content" class="alert alert-info"><!--?=base_url();?--><!--?=$currentPage; ?-->1</div>
		<div id="myCarousel" class="carousel slide">
                <ol class="carousel-indicators">
                  <li data-target="#myCarousel" data-slide-to="0" class=""></li>
                  <li data-target="#myCarousel" data-slide-to="1" class=""></li>
                  <li data-target="#myCarousel" data-slide-to="2" class="active"></li>
                </ol>
                <div class="carousel-inner">
                  <div class="item">
                    <img src="http://www.bootcss.com/assets/img/bootstrap-mdo-sfmoma-01.jpg" alt="">
                    <div class="carousel-caption">
                      <h4>First Thumbnail label</h4>
                      <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    </div>
                  </div>
                  <div class="item">
                    <img src="http://www.bootcss.com/assets/img/bootstrap-mdo-sfmoma-02.jpg" alt="">
                    <div class="carousel-caption">
                      <h4>Second Thumbnail label</h4>
                      <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    </div>
                  </div>
                  <div class="item active">
                    <img src="http://www.bootcss.com/assets/img/bootstrap-mdo-sfmoma-03.jpg" alt="">
                    <div class="carousel-caption">
                      <h4>Third Thumbnail label</h4>
                      <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    </div>
                  </div>
                </div>
                <a class="left carousel-control" href="#myCarousel" data-slide="prev">‹</a>
                <a class="right carousel-control" href="#myCarousel" data-slide="next">›</a>
              </div>
	</div>
</div>