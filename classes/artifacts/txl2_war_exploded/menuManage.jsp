<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String basePath = pageContext.getRequest().getServletContext().getContextPath();
String baseMenuPath = basePath+"/menu/";
pageContext.setAttribute("basePath", basePath);
pageContext.setAttribute("baseMenuPath", baseMenuPath);
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理页面</title>
<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;//全局url,用来控制发送请求的地址
	
	//打开添加菜单的页面
	function openMenuAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加新菜单");
		url="${baseMenuPath}/MenuAddAction.do";
	}
	
	//打开编辑菜单的页面
	function openMenuModifyDialog(){
		//调用easyUi框架的getSelections方法获得被选中的单元行
		var selectRows = $("#dg").datagrid('getSelections');
		
		//如果没有选择或者选择了多行
		if (selectRows.length != 1) {
			$.messager.alert("系统提示","请选择一条要编辑得数据");
			return;
		}
		
		var row = selectRows[0];
		//弹出编辑框,并设置标题
		$("#dlg").dialog("open").dialog("setTitle","编辑菜单信息");
		//将选中的单元格的数据填充到form表单中
		$("#fm").form("load",row);
		
		//改变全局url
		url =  "${baseMenuPath}/MenuUpdateAction.do?id="+row.id;
	}
	
	//执行菜单删除的动作,删除不用打开弹出窗
	function deleteMenu(){
		//调用easyUi框架的getSelections方法获得被选中的单元行
		var selectRows = $("#dg").datagrid('getSelections');
		
		//如果没有选择
		if (selectRows.length == 0) {
			$.messager.alert("系统提示","请选择一条要删除的数据");
			return;
		}
		
		var selectIs = [];
		
		for (var i = 0;i<selectRows.length;i++) {
			selectIs.push(selectRows[i].id);//把所选中的数据行的id值设置到selectIds 数组
		}
		
		var ids = selectIs.join(",");//将数组元素拼接成一条字符串,用","号分隔
		//弹出删除询问确认框
		$.messager.confirm("系统提示",
				"您确认要删除这<font color=red>"+selectRows.length+"</font>条数据吗?",
				//result 表示的是用户的选择结果,确定是true,取消是false
				function(result){
					//如果用户点击了确定删除
					if (result) {
						//post 提交删除请求到后台,删除数据
						$.post("${baseMenuPath}/MenuDeleteAction.do",//提交的请求地址
							{ids:ids},//提交的参数
							function(res){
								if (res.success) {
									$.messager.alert("系统提示","数据已经成功删除");
									$("#dg").datagrid("reload");//重新加载表格数据
								} else{
									$.messager.alert("系统提示","数据删除失败");
								}
							},//对返回结果的处理函数
							"json");//提交的数据格式
					}
				}		
		);
		
	}
	
	//设置字段值为""
	function resetValue(){
		$("#menuName").val("");
		$("#menuNo").val("");
		$("#parentId").val("");
		$("#sortNo").val("");
		$("#menuDesc").val("");
		$("#action").val("");
	}
	
	//保存
	function addMenu(){
		//这句话的意思是提交id是fm的表单时调用的
		$("#fm").form("submit",{
			url:url,//请求所提交到的路径,这个url是全局参数,在打开弹窗的时候已经指定了.
			//下面这句话的意思是提交的时候执行是否为空的校验
			onSubmit:function(){
				return $(this).form("validate");
			},
			//下面这句话的意思是,执行完成后对返回的结果进行处理...执行完成并不代表操作成功,也可能是失败
			//result 是执行完成后系统返回来的
			success:function(result){
				var result = eval("("+result+")");
				if (result.success) {
					$.messager.alert("系统提示","保存成功");//这句话错了,已修改
					//重设弹出框的表单值
					resetValue();
					$("#dlg").dialog("close");
					//下面这句话的意思是刷新列表
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("系统提示","保存失败");
				}
			}
		});
	}
	
	//关闭弹出框
	function closeMenuDialog(){
		$("#dlg").dialog("close");//关闭弹框
		//清除弹框里填写的值
		resetValue();
	}
	
	//
	
	function searchMenu(){
		url="${baseMenuPath}/SearchMenuAction.do";//提交的请求地址
		$("#search-fm").form("submit",{
			url:url,			
			success:function(result){
				var result = eval("("+result+")");
				if (result.success) {
					$("#dg").datagrid("loadData",result.rows);
				} else {
					$.messager.alert("查找失败");
				}
			}
		});
	}
	
	
</script>

</head>
<body>
	<table id="dg" title="菜单管理" class="easyui-datagrid"
		fitColumns="true" pagination="true" rownumbers="false"
		url="${baseMenuPath}/MenuListAction.do" fit="true" toolbar="#tb"	
	>
		<thead>
			<tr>
				<th field="cb" checkbox="true" align="center"></th>
				<th field="id" width="50" align="center">编号</th>
				<th field="menuName" width="100" align="center">菜单名</th>
				<th field="menuNo" width="100" align="center">菜单编号</th>
				<th field="parentId" width="100" alighn="center">父节点</th>
				<th field="sortNo" width="100" align="center">列表显示顺序</th>
				<th field="menuDesc" width="100" align="left">菜单描述</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<a href="javascript:openMenuAddDialog()" class="easyui-linkbutton" 
			iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openMenuModifyDialog()" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteMenu()" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			<form id="search-fm" method="post">
				菜单编号:<input name="menuNo" value="" size="4" class="easyui-validatebox">
				菜单名:<input name="menuName" value="" size="6" class="easyui-validatebox">
				菜单描述:<input name="menuDesc" value="" size="6" class="easyui-validatebox">
			<a href="javascript:searchMenu();" class="easyui-linkbutton"
			iconCls="icon-search" plain="true">查询</a>
			</form>
		</div>
	</div>
	
	<!-- 下面的div用来显示添加和修改菜单 -->
	<div id="dlg" class="easyui-dialog" style="width:620px;height=250px;padding:10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>菜单名:</td>
					<td><input type="text" id="menuName" name="menuName"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>菜单编号:</td>
					<td><input type="text" id="menuNo" name="menuNo"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>父节点编号:</td>
					<td><input type="text" id="parentId" name="parentId"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>请求名称:</td>
					<td><input type="text" id="action" name="action"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>显示顺序:</td>
					<td><input type="text" id="sortNo" name="sortNo"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>菜单描述:</td>
					<td><input type="text" id="menuDesc" name="menuDesc"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
			</table>
		</form>		
	</div>
	<!-- 添加菜单的弹出框编写结束 -->
	
	<!-- 下面这个div是用来对上面的div框补充 提交按钮和重置按钮的 -->
	<div id="dlg-buttons"><!-- 注意这里的div id 和上面那个div的 buttons="#dlg-buttons"相对应-->
		<a href="javascript:addMenu();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeMenuDialog();" class="easyui-linkbutton"
		iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>