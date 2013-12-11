var lineChart;
var BLANK = true;

function execute(chartData, w, h) {

	lineChart = new iChart.LineBasic2D({
		// turn_off_touchmove:true ,
		border : true,
		render : 'chart',
		data : chartData.data,
		align : 'center',
		animation : false,// 开启过渡动画
		animation_duration : 600,// 600ms完成动画
		width : w - 10,
		height : h - 220,
		title : {
			text : "会员-" + chartData.member,
			fontsize : 11,
			offsety : -19
		},
		subtitle : {
			text : '(最近一周的体检评估报告)',
			fontsize : 10,
			offsety : -33
		},
		footnote : {
			text : '数据来源：3G智能健康服务机',
			fontsize : 10,
			offsetx : 5,
			offsety : 7
		},
		listeners : {
			//
			// d:相当于data[0],即是一个线段的对象
			// v:相当于data[0].value
			// x:计算出来的横坐标
			// x:计算出来的纵坐标
			// j:序号 从0开始
			// /
			parsePoint : function(d, v, x, y, j) {
				// 利用序号进行过滤春节休息期间
				if (BLANK && (v == 0))
					return {
						ignored : true
					}// ignored为true表示忽略该点
			},
		},
		sub_option : {
			smooth : true,// 平滑曲线
			point_size : 22
		},
		tip : {
			enable : true,
			shadow : true,
			listeners : {
				// tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
				parseText : function(tip, name, value, text, i) {

					if ("健康指数" != name) {
						return name + ":" + value;
					} else {
						var result = chartData.reports[i].replace('您好，本次监测您的',
								'');
						return name + ":" + value + "医嘱: " + result;
					}
				}
			}
		},
		// tipMocker:function(tips,i){
		// return tips.join("<br/>");
		// },
		legend : {
			enable : true,
			row : 1,// 设置在一行上显示，与column配合使用
			column : 'max',
			valign : 'top',
			fontsize : 9,
			sign : 'bar',
			background_color : null,// 设置透明背景
			offsetx : -5,// 设置x轴偏移，满足位置需要
			offsety : -50,// 设置y轴偏移，满足位置需要
			border : true
		},
		crosshair : {
			enable : true,
			line_color : '#62bce9'
		},
		sub_option : {
			label : false,
			point_hollow : false
		},
		coordinate : {
			width : w - 50,
			height : h - 300,
			offsety : -20,
			// offsetx: 10,
			axis : {
				color : '#9f9f9f',
				width : [ 1, 0, 0, 1 ]
			},
			grids : {
				vertical : {
					way : 'share_alike',
					value : chartData.vertical
				}
			},
			scale : [ {
				position : 'left',
				start_scale : 30,
				end_scale : 270,
				scale_space : 30,
				scale_size : 2,
				scale_color : '#9f9f9f'
			}, {
				position : 'bottom',
				labels : chartData.labels
			} ]
		}
	});
	// 利用自定义组件构造左侧说明文本
	lineChart
			.plugin(new iChart.Custom(
					{
						drawFn : function() {
							// 计算位置
							var coo = lineChart.getCoordinate(), x = coo
									.get('originx'), y = coo.get('originy'), w = coo.width, h = coo.height;
							// 在左上侧的位置，渲染一个单位的文字
							lineChart.target.textAlign('start').textBaseline(
									'bottom').textFont('600 10px 微软雅黑')
									.fillText('血压(mmHg)', x - 20, y - 10,
											false, '#339999').textBaseline(
											'top').fillText(
											'时间(' + chartData.yeah + ')',
											x + w - 40, y + h + 20, false,
											'#339999');
						}
					}));
	// 开始画图
	lineChart.draw();
}