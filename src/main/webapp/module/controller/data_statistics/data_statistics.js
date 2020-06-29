App.controller("dataStatistics",function($scope, $rootScope, $location, $http, $compile,path) {
	
	
/*	$scope.createChart=function(){
		 $("#chart").kendoChart({
             title: {
                 text: "经营状况"
             },
             legend: {
                 position: "bottom"
             },
             chartArea: {
                 background: ""
             },
             seriesDefaults: {
                 type: "line",
                 style: "smooth"
             },
             series: [{
                 name: "India",
                 data: [3.907, 7.943, 7.848, 9.284, 9.263, 9.801, 3.890, 8.238, 9.552, 6.855]
             },{
                 name: "World",
                 data: [1.988, 2.733, 3.994, 3.464, 4.001, 3.939, 1.333, -2.245, 4.339, 2.727]
             },{
                 name: "Russian Federation",
                 data: [4.743, 7.295, 7.175, 6.376, 8.153, 8.535, 5.247, -7.832, 4.3, 4.3]
             },{
                 name: "Haiti",
                 data: [-0.253, 0.362, -3.519, 1.799, 2.252, 3.343, 0.843, 2.877, -5.416, 5.590]
             }],
             valueAxis: {
                 labels: {
                     format: "{0}%"
                 },
                 line: {
                     visible: false
                 },
                 axisCrossingValue: -10
             },
             categoryAxis: {
                 categories: [2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011],
                 majorGridLines: {
                     visible: false
                 },
                 labels: {
                     rotation: "auto"
                 }
             },
             tooltip: {
                 visible: true,
                 format: "{0}%",
                 template: "#= series.name #: #= value #"
             }
         });
	}
	$scope.createChart();*/
	$scope.createChart=function(){
		 var myChart = echarts.init(document.getElementById('echars'));
			 option = {
				backgroundColor:"#FFFFFF",
			    title: {
			        text: '经营概况'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['访问数','浏览量','支付金额','客单价']
			    },
			    toolbox: {
			        feature: {
			            saveAsImage: {}
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            data : ['周一','周二','周三','周四','周五','周六','周日']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'访问数',
			            type:'line',
			            stack: '总量',
			            areaStyle: {normal: {}},
			            data:[120, 132, 101, 134, 90, 230, 210]
			        },
			        {
			            name:'浏览量',
			            type:'line',
			            stack: '总量',
			            areaStyle: {normal: {}},
			            data:[220, 182, 191, 234, 290, 330, 310]
			        },
			        {
			            name:'支付金额',
			            type:'line',
			            stack: '总量',
			            areaStyle: {normal: {}},
			            data:[150, 232, 201, 154, 190, 330, 410]
			        },
			        {
			            name:'客单价',
			            type:'line',
			            stack: '总量',
			            areaStyle: {normal: {}},
			            data:[320, 332, 301, 334, 390, 330, 320]
			        }
			    ]
		};
	    // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
	}
	$scope.createChart();
});