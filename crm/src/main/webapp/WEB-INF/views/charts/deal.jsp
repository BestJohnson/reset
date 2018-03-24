<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-客户级别统计</title>
    <%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="param" value="charts_deal"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户成交比统计</h3>
                </div>
                <div class="box-body">
                    <div id="Funnel" style="height: 300px;width: 100%"></div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

   <%@ include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

   <%@include file="../include/js.jsp"%>
<script src="/static/plugins/echarts/echarts.common.min.js"></script>
<script src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
<script src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
<script src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
<script src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
<script src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
<script src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
<script src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
<script src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>

<script>
	$(function() {
		
		var Funnel = echarts.init(document.getElementById("Funnel"));
		
		option = {
		    title: {
		        text: '客户成交比统计',
		        left: 'left'
		    },
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    toolbox: {
		        feature: {
		            dataView: {readOnly: false},
		            restore: {},
		            saveAsImage: {}
		        }
		    },
		    legend: {
		        data: []
		    },
		    calculable: true,
		    series: [
		        {
	            name:'客户成交比统计',
	            type:'funnel',
	            left: '10%',
	            top: 60,
	            //x2: 80,
	            bottom: 60,
	            width: '80%',
	            // height: {totalHeight} - y - y2,
	            min: 0,
	            max: 100,
	            minSize: '0%',
	            maxSize: '100%',
	            sort: 'descending',
	            gap: 2,
	            label: {
	                normal: {
	                    show: true,
	                    position: 'inside'
	                },
	                emphasis: {
	                    textStyle: {
	                        fontSize: 20
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    length: 10,
	                    lineStyle: {
	                        width: 1,
	                        type: 'solid'
	                    }
	                }
	            },
	            itemStyle: {
	                normal: {
	                    borderColor: '#fff',
	                    borderWidth: 1
	                }
	            },
		            data: [
		                   
		                {value: 60, name: '访问'},
		                {value: 40, name: '咨询'},
		                {value: 20, name: '订单'},
		                {value: 80, name: '点击'},
		                {value: 100, name: '展现'} 
		            ]
		        }
		    ]
	   };
	
		Funnel.setOption(option);
		
		$.get("/charts/deal/count",function(json){
			if(json.state == 'success') {
				var processArray = [];
				var countArray = [];
				var array = json.data;
				for(var i = 0; i < array.length; i++) {
					var obj = array[i];
					processArray.push(obj.process);
					countArray.push(obj.count);
				}
			Funnel.setOption({
				legend: {
			        data: processArray 
			    },
				series:{
					data: countArray 
				}
			})	
			
			}
			
		});
		
	})


</script>
</body>
</html>