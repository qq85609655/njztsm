<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<script type="text/javascript" src="${ctx}/static/thirdparty/ichart/ichart.1.2.min.js"></script>
		<script type="text/javascript">
			$(function(){
/*				
			var reports = ${reports} ;
			var systolic = ${systolic} ;
			var diastolic = ${diastolic} ;
			var health = ${health} ;
			var labels = ${labels} ;
			var vertical = ${vertical} ;
*/
			var reports = ["","","","您好，本次监测您的血压处于轻度一级的高血压低限，请定时测量血压，如无不适症状，建议非药物治疗，合理控制生活方式，戒烟限酒，清淡饮食。 ","","您好，本次监测您的血压处于轻度一级的高血压低限，请定时测量血压，如无不适症状，建议非药物治疗，合理控制生活方式，戒烟限酒，清淡饮食，少盐少油。 ","您好，本次监测您的血压处于轻度一级的高血压低限，请定时测量血压，如无不适症状，科学运动。 "] ;
			var systolic = [0,0,0,150,0,114,129] ;
			var diastolic = [0,0,0,104,0,94,80] ;
			var health = [0,0,0,129,0,119,100] ;
			var labels = ["2013-10-17","2013-10-18","2013-10-19","2013-10-20","2013-10-21","2013-10-22","2013-10-23"] ;
			var vertical = 6 ;
			var w ;
			var h ;
			
			var BLANK = true ;
				var data = [
				         	{
				         		name : '收缩压',
				         		value: systolic,
				         		color:'#0d8ecf',
				         		line_width:2
				         	},
				         	{
				         		name : '舒张压',
				         		value: diastolic,
				         		color:'#ef7707',
				         		line_width:2
				         	},
				         	{
				         		name : '健康指数',
				         		value: health,
				         		color:'#990099',
				         		line_width:2
				         	}
				         ];
		         
				var line = new iChart.LineBasic2D({
					border : false ,
					render : 'canvasDiv',
					data: data,
					align:'center',
					title : "会员-${member.name}",
					subtitle : '最近一周的体检评估报告',
					footnote : '数据来源：3G智能健康服务机',
					animation : false,//开启过渡动画
					animation_duration:10,//600ms完成动画
					width : 600,
					height : 460,
					listeners:{
						//
						// d:相当于data[0],即是一个线段的对象
						// v:相当于data[0].value
						// x:计算出来的横坐标
						// x:计算出来的纵坐标
						// j:序号 从0开始
						///
						parsePoint:function(d,v,x,y,j){
							//利用序号进行过滤春节休息期间
							if(BLANK&&(v==0))
							return {ignored:true}
						},
						click:function(l,e ,p){
							alert(1) ;
						}
					},
					sub_option:{
						label:true,
						point_hollow : true ,
						smooth : true,//平滑曲线
				//		point_size:10,
						/**
						 * r:iChart.Rectangle2D对象
						 * e:eventObject对象
						 * m:额外参数
						 */
						click:function(r,e,m){
							alert(r.get('name')+' '+r.get('value'));
						}
					},
					tip:{
						enable:true,
						shadow:false,
						showType: 'fixed' ,
						text : "dsdsd"
					/*
						listeners:{
							initialize:function(tip){
								
							},
							 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
							parseText:function(tip,name,value,text,i){
								
								if("健康指数" != name){
									return "<span style='color:#005268;font-size:12px;'>"+name +
									": </span><span style='color:#005268;font-size:20px;'>"+value+"</span>";
								}
								else
									return "<span style='color:#005268;font-size:12px;max-width:50px;'>"+name +
									": </span><span style='color:#005268;font-size:20px;max-width:50px;'>"+value+"</span> <span style='color:#005268;font-size:12px;max-width:200px;word-break:normal;display:inline; white-space:pre-wrap;word-wrap : break-word ;overflow: hidden ;'>医嘱: "+reports[i]+"</span>";
							}
						}
					*/
					},
					
					tipMockerOffset: 1,
					legend : {
						enable : true,
						row:1,//设置在一行上显示，与column配合使用
						column : 'max',
						valign:'top',
						sign:'bar',
						background_color:null,//设置透明背景
						offsetx:-10,//设置x轴偏移，满足位置需要
						border : true
					},
					crosshair:{
						enable:true,
						line_color:'#62bce9'
					},
					coordinate:{
						width:520,
						height:300,
						axis:{
							color:'#9f9f9f',
							width:[0,0,2,2]
						},
						grids:{
							vertical:{
								way:'share_alike',
						 		value:vertical
							}
						},
						scale:[{
							 position:'left',
							 basic_value:30,
							 start_scale:30,
							 end_scale:270,
							 scale_space:30,
							 scale_size:2,
							 scale_color:'#9f9f9f'
						},{
							 position:'bottom',	
							 labels:labels
						}]
					}
				});
			//开始画图
			line.draw();
			
			
});	


			</script>
			<div id='canvasDiv'></div>
