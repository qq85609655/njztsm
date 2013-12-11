<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<style>
#modalTemplate{
/* SET THE WIDTH OF THE MODAL 设置Modal的宽度*/
width: 900px;
/* CHANGE MARGINS TO ACCOMODATE THE NEW WIDTH (original = margin: -250px 0 0 -280px;) */
margin: auto 0 0 -450px;
}
</style>
<script type="text/javascript" src="${ctx}/static/thirdparty/ichart/ichart.1.2.min.js"></script>
		<script type="text/javascript">
			$(function(){
				var BLANK = true ;
			var reports = ${reports} ;
				var data = [
				         	{
				         		name : '收缩压（Systolic pressure）（高压）',
				         		value: ${systolic},
				         		color:'#0d8ecf',
				         		line_width:2
				         	},
				         	{
				         		name : '舒张压（Diastolic pressure）（低压）',
				         		value: ${diastolic},
				         		color:'#ef7707',
				         		line_width:2
				         	},
				         	{
				         		name : '健康指数',
				         		value: ${health},
				         		color:'#990099',
				         		line_width:2
				         	}
				         ];
		         
			//	var labels = ["2012-12-01","2012-12-02","2012-12-03","2012-12-04","2012-12-05","2012-12-06"];
				var labels = ${labels} ;
				var line = new iChart.LineBasic2D({
					render : 'canvasDiv',
					data: data,
					align:'center',
					title : "会员-${member.name}",
					subtitle : '最近一段时间体检评估报告',
					footnote : '数据来源：3G智能健康服务机',
					animation : true,//开启过渡动画
					animation_duration:600,//600ms完成动画
					width : 800,
					height : 400,
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
							return {ignored:true}//ignored为true表示忽略该点
						}
					},
					sub_option:{
						smooth : true,//平滑曲线
						point_size:10
					},
					tip:{
						enable:true,
						shadow:true,
						listeners:{
							 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
							parseText:function(tip,name,value,text,i){
								
								if("健康指数" != name)
								return "<span style='color:#005268;font-size:12px;'>"+name +
								": </span><span style='color:#005268;font-size:20px;'>"+value+"</span>";
								else
									return "<span style='color:#005268;font-size:12px;'>"+name +
									": </span><span style='color:#005268;font-size:20px;'>"+value+"</span> <span style='color:#005268;font-size:12px;'>报告: "+reports[i]+"</span>";
							}
						}
					},
					legend : {
						enable : true,
						row:1,//设置在一行上显示，与column配合使用
						column : 'max',
						valign:'top',
						sign:'bar',
						background_color:null,//设置透明背景
						offsetx:-80,//设置x轴偏移，满足位置需要
						border : true
					},
					crosshair:{
						enable:true,
						line_color:'#62bce9'
					},
					sub_option : {
						label:false,
						point_hollow : false
					},
					coordinate:{
						width:640,
						height:240,
						axis:{
							color:'#9f9f9f',
							width:[0,0,2,2]
						},
						grids:{
							vertical:{
								way:'share_alike',
						 		value:${vertical}
							}
						},
						scale:[{
							 position:'left',	
							 basic_value:30,
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
