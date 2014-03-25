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
	    var chartLabels = ${labels} ;
        var sysTolic = ${systolic} ;
        var diasTolic = ${diastolic} ;
        var health = ${health} ;
        var userMember = "${member.name}" ;
        var chartVertical = ${vertical} ;
        var first_label = "${first_label}" ;
        var last_label = "${last_label}" ;
        
       splitData = function(sysTolic, diasTolic, health){
       	return [
                   {
                       name : '收缩压（Systolic pressure）（高压）',
                       value: sysTolic,
                       color:'#0d8ecf',
                       line_width:2
                   },
                   {
                       name : '舒张压（Diastolic pressure）（低压）',
                       value: diasTolic,
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
       }
		 
	 var config = function(userMember , chartData ,chartVertical ,chartLabels){
		return	{
               render : 'canvasDiv',
               data: chartData,
               align:'center',
               title : "会员-"+userMember,
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
                           value: chartVertical
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
                        labels: chartLabels
                   }]
               }
         }
	} ;
		
		
	var firstData = splitData(sysTolic, diasTolic, health) ;
	var firstConfig = config(userMember, firstData, chartVertical ,chartLabels) ;
	
	var line = new iChart.LineBasic2D(firstConfig);
	//开始画图
	line.draw();
	
    preview = function(){
    	var url = '${ctx}/admin/member/lineChart/${userId}/-/'+first_label ;
    	$.post(url,function(data){
    		
    		if(!data.data){
    			alert("好啦，别点了 亲 已经没有数据啦！") ;
                return  ;
            }
    		
    		var chartData = data.data ;
            chartData = eval("("+chartData+")") ;
            
    		reports = eval("("+chartData.reports+")") ;
            chartLabels = eval("("+chartData.labels+")") ;
            sysTolic = eval("("+chartData.systolic+")") ;
            diasTolic = eval("("+chartData.diastolic+")") ;
            health = eval("("+chartData.health+")") ;
            userMember = chartData.member.name ;
            chartVertical = chartData.vertical ;
            first_label = chartData.first_label ;
            last_label = chartData.last_label ;
            	
            var firstData = splitData(sysTolic, diasTolic, health) ;
            var firstConfig = config(userMember, firstData, chartVertical ,chartLabels) ;
            var line = new iChart.LineBasic2D(firstConfig);
            //开始画图
            line.draw();
            
    	}) ;
    	
    } ;
          
    nextview = function(){
  	  var url = '${ctx}/admin/member/lineChart/${userId}/+/'+last_label ;
        $.post(url,function(data){
        	if(!data.data){
        		alert("好啦，别点了 亲 已经到头了！") ;
        		return  ;
        	}
            var chartData = data.data ;
            chartData = eval("("+chartData+")") ;
            
            reports = eval("("+chartData.reports+")") ;
            chartLabels = eval("("+chartData.labels+")") ;
            sysTolic = eval("("+chartData.systolic+")") ;
            diasTolic = eval("("+chartData.diastolic+")") ;
            health = eval("("+chartData.health+")") ;
            userMember = chartData.member.name ;
            chartVertical = chartData.vertical ;
            first_label = chartData.first_label ;
            last_label = chartData.last_label ;
            
            var firstData = splitData(sysTolic, diasTolic, health) ;
            var firstConfig = config(userMember, firstData, chartVertical ,chartLabels) ;
            var line = new iChart.LineBasic2D(firstConfig);
            //开始画图
            line.draw();
            
        }) ;
    } ;
	
});
	
</script>
			<div>
				<button onclick="preview()">上一周</button>
				<button onclick="nextview()">下一周</button>
			</div>
			<div id='canvasDiv'></div>
