<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>凯盛软件CRM-首页</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 	<%@ include file="../include/css.jsp"%> 
<link rel="stylesheet" href="/static/plugins/tree/css/metroStyle/metroStyle.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

 <%@ include file="../include/header.jsp"%> 
 <%@ include file="../include/sider.jsp"%> 
  <!-- =============================================== -->

  <!-- 右侧内容部分 -->
  <div class="content-wrapper">

    <!-- Main content -->
    <section class="content">

      <div class="row">
        <div class="col-md-2">
            <div class="box">
              <div class="box-body">
                <button class="btn btn-default" id="addDept">添加部门</button>
                <ul id="ztree" class="ztree"></ul>
              </div>
            </div>
        </div>
        <div class="col-md-10">
            <!-- Default box -->
            <div class="box">
              <div class="box-header with-border">
                <h3 class="box-title">员工管理</h3>
                <div class="box-tools pull-right">
                  <button type="button" class="btn btn-box-tool" id="addAcc" title="Collapse">
                    <i class="fa fa-plus"></i> 添加员工</button>
                </div>
              </div>
              <div class="box-body">
                <table class="table">
                  <thead>
                    <tr>
                      <th>姓名</th>
                      <th>部门</th>
                      <th>手机</th>
                      <th>#</th>
                    </tr>
                  </thead>
				  <tbody id="body">
            		
				  </tbody>
                </table>
                <ul class="pagination pull-right" id="pagination"></ul>
              </div>
            </div>
            <!-- /.box -->
        </div>
      </div>

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
	
	<!-- Modal -->
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="myModalLabel">新增员工</h4>
        </div>
        <div class="modal-body">
        	<form id="addAccountForm">
                     <div class="form-group">
                         <label>姓名</label>
                         <input type="text" class="form-control" name="userName">
                     </div>
                     <div class="form-group">
                         <label>手机号码</label>
                         <input type="text" class="form-control" name="mobile">
                     </div>
                     <div class="form-group">
                         <label>密码(默认123123)</label>
                         <input type="password" class="form-control" name="password" value="123123">
                     </div>
                     <div class="form-group">
                         <label>所属部门</label>
                         <div id="checkboxList">
                         </div>
                     </div>
                 </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
        </div>
      </div>
    </div>
  </div>

  <!-- 底部 -->
  <%@ include file="../include/footer.jsp"%> 

</div>
<!-- ./wrapper -->

<%@ include file="../include/js.jsp"%> 
<script src="/static/plugins/tree/js/jquery.ztree.all.min.js"></script>
<script src="/static/plugins/jQuery/jquery.twbsPagination.js"></script>
<script>
  $(function(){
	   
	  var deptId = null;//根据点击，获得部门id
	    var $pagination = $('#pagination');
	    var defaultOpts = {
	        totalPages: 20,
	    };
	    //分页插件初始化
	    $pagination.twbsPagination(defaultOpts);
	    
	    pageload(deptId);
	    
	   //根据部门ID，异步加载，动态获得页面数据
	   function pageload(deptId){
		   $.ajax({
		        url:"/account/list.json",
		        type:"get",
		        data:{
		        	"deptId":deptId
		        },
		        success: function (data) {
		        	var page = data.data;
		            var totalPages = page.totalpage;
		            var currentPage = $pagination.twbsPagination('getCurrentPage');
		            //当前页面大于totalPages时，赋值为totalPages，否则为currentPage,不然会因为currentPage > totalPages产生bug
		            currentPage = currentPage > totalPages? totalPages:currentPage;
		            $pagination.twbsPagination('destroy');
		            $pagination.twbsPagination($.extend({}, defaultOpts, {
		                startPage: currentPage,
		                totalPages: totalPages,
		                visiblePages:3,
		    		    first:'首页',
		    		    last:'末页',
		    		    prev:'上一页',
		    		    next:'下一页',
		    		    onPageClick: function (event, page) {
		    		    	// 一旦分页插件被重新加载，分页插件就会自动执行第一页数据
		    		    	load(deptId,page);
		    	        }
		            }));
		        }
		    });
	   }
	   
	  //初始化，加载所有员工数据
	  //自定义函数异步加载
	  function load(deptId,p) {
		  $.ajax({
		        url:"/account/list.json",
		        type:"get",
		        data:{
		        	"deptId":deptId,
		        	"p":p
		        },
		        success: function (data) {
		        	var page = data.data;
		        	$("#body").html("");
		            for(var i = 0; i < page.items.length; i++) {
		            	var account = page.items[i];
		            	var html = "<tr> <td>" + account.username+"</td> <td>" + account.deptName + "</td> <td>"+account.tel+"</td> <td> <a href=''>禁用</a><a href=''>删除</a><a href=''>编辑</a></td></tr>";
		            	$("#body").append(html);
		            }
		        }
		    });
	  }  
	  
	  
	  
	  
	  $("#addAcc").click(function() {
		  $.get('/dept/list').done(function(data) {
			  $("#checkboxList").html("");
			  // 动态的显示deptList列表
			for(var i = 0; i < data.length; i++) {
				if(data[i].pId == 1) {
					var html = '<label class="checkbox-inline"><input type="checkbox" name="deptId" value="' + data[i].id + '"/>' + data[i].deptName + '</label>'
					$("#checkboxList").append(html);
				}
			}
			  $("#myModal").modal({
				  backdrop:'static',
				  keyboard:false
			  });
			  
		  }).error(function() {
			  layer.msg("系统异常")
		  })
		  
	  });
	  
	  $("#saveBtn").click(function() {
		  $("#addAccountForm").submit();
	  });
	  
	  
	  //表单校验
	  $("#addAccountForm").validate({
		  errorClass : 'text-danger',
		  errorElement: 'span',
		  rules:{
	    		userName : {
	    			required : true
	    		},
	    		mobile :{
	    			required : true
	    		},
	    		password :{
	    			required : true
	    		},
	    		deptId:{
	    			required : true
	    		}
	    	},
	    	messages:{
	    		userName : {
	    			required : "请填写员工姓名"
	    		},
	    		mobile :{
	    			required : "请填写员工电话"
	    		},
	    		password :{
	    			required : "请填写员工密码"
	    		},
	    		deptId:{
	    			required : "请填写员工部门"
	    		}
	    	},
		  
		  //表单校验通过之后触发
		  submitHandler:function() {
			 $.ajax({
				 url:'/account/add',
				 type:'post',
				 //序列化表单内元素，之后才能被后台获得
				 data: $("#addAccountForm").serialize(),
				 success : function(data) {
					 if(data.state == 'success') {
						 $('#myModal').modal('hide');
						 pageload(deptId);
					 } else {
						 layer.alert(data.messge);
					 }
				 },
				 error : function() {
					 layer.alert("添加失败");
				 }
				 
			 }) 
		  }
		  
		  
	  })
	  
	  
	  
	  
	  
	  
	  
	  $("#addDept").click(function() {
		  layer.prompt({title : '请输入部门名称'},function(text,index) {
			  layer.close(index);
			  
			  //发送ajax请求
			  $.post('/dept/add',{"deptName": text}).done(function(data) {
				  if(data.state == 'success') {
					  layer.msg('添加成功');
					// 刷新ztree
					  var treeObj = $.fn.zTree.getZTreeObj("ztree");
					  treeObj.reAsyncChildNodes(null, "refresh");
				  }
			  }).error(function() {
				  alert("系统异常");
			  })
		  });
		  
	  })
	  
	  
	  
	  
    var setting = {
			
		  async: {
			  enable:true,
			  url:'/dept/list',
			  type:'get',
			  dataFilter: ajaxDataFilter
		  },
		  
		  
		  data: {
				simpleData: {
					enable: true
				},
				key: {
					name: 'deptName',//配置json name节点的属性名称
				}
		  
			},
			
		  callback:{
				onClick:function(event,treeId,treeNode,clickFlag){
					var deptId = treeNode.id;
					//当点击总公司时，把deptId赋空，因为关联关系表中没有deptId等于1的数据
					if(deptId == 1) {
						deptId = null;
					} 
					// 当用户点击了部门节点后，重新加载分页插件，一旦分页插件重新加载后，那么就会执行
					pageload(deptId);
					
				}
			  }
		};

	  function ajaxDataFilter(treeId, parentNode, responseData) {
		  if (responseData) {
		      for(var i =0; i < responseData.length; i++) {
		      		if(responseData[i].id == 1) {
		      			responseData[i].open = true;
		      		}
		      }
		    }
		    return responseData;
	  }
		
    $.fn.zTree.init($("#ztree"), setting);
  });
</script>
</body>
</html>
    