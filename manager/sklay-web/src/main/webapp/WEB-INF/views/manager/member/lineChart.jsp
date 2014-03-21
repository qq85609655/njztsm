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
    	
    	reports = ["1","1","1","1","1","","1"] ;
        chartLabels = ["2017-12-29","2017-12-30","2017-12-31","2018-01-01","2018-01-02","2018-01-03","2018-01-04"] ;
        sysTolic = [125,123,111,125,125,0,125] ;
        diasTolic = [79,50,60,79,79,0,79] ;
        health = [94,80,60,84,94,0,74] ;
        userMember = "超级管理员" ;
        chartVertical = 6 ;
    	
    	
    	var firstData = splitData(sysTolic, diasTolic, health) ;
        var firstConfig = config(userMember, firstData, chartVertical ,chartLabels) ;
        var line = new iChart.LineBasic2D(firstConfig);
        //开始画图
        line.draw();
    } ;
          
          netview = function(){
        	  reports = ["2","2","2","2","2","","2"] ;
              chartLabels = ["2018-12-29","2018-12-30","2018-12-31","2019-01-01","2019-01-02","2019-01-03","2019-01-04"] ;
              sysTolic = [145,113,101,135,115,0,185] ;
              diasTolic = [79,50,60,89,79,0,79] ;
              health = [74,80,60,84,54,0,74] ;
              userMember = "超级管理员" ;
              chartVertical = 6 ;
              
              
              var firstData = splitData(sysTolic, diasTolic, health) ;
              var firstConfig = config(userMember, firstData, chartVertical ,chartLabels) ;
              var line = new iChart.LineBasic2D(firstConfig);
              //开始画图
              line.draw();
          } ;
	
});
	var labels1 = ["2012-12-01","2012-12-02","2012-12-03","2012-12-04","2012-12-05","2012-12-06","2012-12-07"];
	var data3 = [
                       {
                           name : '收缩压（Systolic pressure）（高压）',
                           value: [135,0,0,135,135,0,115],
                           color:'#0d8ecf',
                           line_width:2
                       },
                       {
                           name : '舒张压（Diastolic pressure）（低压）',
                           value: [90,0,0,90,90,0,70],
                           color:'#ef7707',
                           line_width:2
                       },
                       {
                           name : '健康指数',
                           value: [75,0,0,70,70,0,70],
                           color:'#990099',
                           line_width:2
                       }
                    ];
	 var data2 = [
                        {
                            name : '收缩压（Systolic pressure）（高压）',
                            value: [155,0,0,155,155,0,155],
                            color:'#0d8ecf',
                            line_width:2
                        },
                        {
                            name : '舒张压（Diastolic pressure）（低压）',
                            value: [60,0,0,60,60,0,70],
                            color:'#ef7707',
                            line_width:2
                        },
                        {
                            name : '健康指数',
                            value: [85,0,0,80,80,0,80],
                            color:'#990099',
                            line_width:2
                        }
                     ];
            
</script>
			<div>
			<button onclick="preview()">上一周</button>
			<button onclick="netview()">下一周</button>
			</div>
			<div id='canvasDiv'></div>
