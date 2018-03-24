<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-首页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<%@ include file="../include/css.jsp"%>
	<!-- 下拉框筛选插件 -->
	<link rel="stylesheet" href="/static/plugins/select2/select2.min.css">
    <style>
        .td_title {
            font-weight: bold;
        }
    </style>
    
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../include/header.jsp"%>
    <jsp:include page="../include/sider.jsp">
    	<jsp:param value="customer_public" name="param"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户资料</h3>
                    <div class="box-tools">
						<a class="btn btn-primary btn-sm" href="/customer/public/list"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <a class="btn bg-purple btn-sm" href="/customer/public/edit?id=${customer.id}"><i class="fa fa-pencil"></i> 编辑</a>
                        <button class="btn bg-orange btn-sm" id="toBtn"><i class="fa fa-exchange"></i>分配给</button>
                        <button class="btn btn-danger btn-sm" id="delBtn"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">姓名</td>
                            <td>${customer.custName}</td>
                            <td class="td_title">职位</td>
                            <td>${customer.jobTitle}</td>
                            <td class="td_title">联系电话</td>
                            <td>${customer.mobile}</td>
                        </tr>
                        <tr>
                            <td class="td_title">所属行业</td>
                            <td>${customer.trade}</td>
                            <td class="td_title">客户来源</td>
                            <td>${customer.source}</td>
                            <td class="td_title">级别</td>
                            <td>${customer.level}</td>
                        </tr>
                        <tr>
                            <td class="td_title">地址</td>
                            <td colspan="5">${customer.address}</td>
                        </tr>
                        <tr>
                            <td class="td_title">备注</td>
                            <td colspan="5">${customer.mark}</td>
                        </tr>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">创建日期：<fmt:formatDate value="${customer.createTime}" pattern="yyyy年MM月dd日"></fmt:formatDate> 
                    &nbsp;&nbsp;&nbsp;&nbsp;
                        最后修改日期：<fmt:formatDate value="${customer.updateTime}" pattern="yyyy年MM月dd日"></fmt:formatDate></span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">跟进记录</h3>
                        </div>
                        <div class="box-body">
							<ul class="list-group">
								<c:forEach items="${chanceList}" var="chance"> 
									<li class="list-group-item"><a href="/sale/detail?saleId=${chance.id}">${chance.name}</a></li>									
								</c:forEach>
                        	</ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@ include file="../include/footer.jsp"%>

	<!-- 转交给其他员工的选择模态框 -->
	<div class="modal fade" id="chooseAccount" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">分配客户</h4>
	      </div>
	      <div class="modal-body">
	       		
				<!-- 除了accountId=0之外的员工列表 -->
	       		<select id="accountList" class="form-control" style="width:100%">
		       		<c:forEach items="${accountList}" var="account">
		       			<c:if test="${account.id != 0}">
			       			<option value="${account.id}">${account.username} (${account.tel})</option>
			       		</c:if>
		       		</c:forEach>
	       		</select>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

</div>
<!-- ./wrapper -->
<%@ include file="../include/js.jsp"%>
<!-- 下拉框筛选插件 -->
<script src="/static/plugins/select2/select2.min.js"></script>
<script src="/static/plugins/select2/select2.full.min.js"></script>

	<script>
		$(function() {
			var custId = ${customer.id};
			
			
			$("#accountList").select2({
			});
			
			//删除
			$("#delBtn").click(function() {
				layer.confirm("确定要删除吗？",function() {
					layer.close();
					location.href = '/customer/my/del?custId=' + custId;
				})
			});
			//分配给他人
			$("#toBtn").click(function() {
				$("#chooseAccount").modal({
					backdrop : 'static',
					show : 'true',
				})
			});
			//保存要转交时的设置
			$("#saveBtn").click(function() {
				$("#chooseAccount").modal('hide');
				var toAccountId = $("#accountList").val();
				
				//                       选择器                   被选中的           来自EE  API
				var toAccountName = $("#accountList option:selected").text();
				layer.confirm("确定要转交给" + toAccountName + "吗？",function(){
					layer.close();
					location.href = '/customer/my/trans?custId=' + custId + '&toAccountId=' + toAccountId;
				})
			});
			
			
		})
		
		
	</script>
	
</body>
</html>