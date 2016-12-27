<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String basePath = pageContext.getRequest().getServletContext().getContextPath();
String baseRolePath = basePath+"/role/";
pageContext.setAttribute("basePath", basePath);
pageContext.setAttribute("baseRolePath", baseRolePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理页面</title>
<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	var url;//全局url,用来控制发送请求的地址
	//打开添加角色的页面
	function openRoleAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加新角色");
		url="${baseRolePath}/RoleAddAction.do";
	}
	
	//打开编辑角色的页面
	function openRoleModifyDialog(){
		//调用easyUi框架的getSelections方法获得被选中的单元行
		var selectRows = $("#dg").datagrid('getSelections');
		
		//如果没有选择或者选择了多行
		if (selectRows.length != 1) {
			$.messager.alert("系统提示","请选择一条要编辑得数据");
			return;
		}
		
		var row = selectRows[0];
		//弹出编辑框,并设置标题
		$("#dlg").dialog("open").dialog("setTitle","编辑角色信息");
		//将选中的单元格的数据填充到form表单中
		$("#fm").form("load",row);
		
		//改变全局url
		url =  "${baseRolePath}/RoleUpdateAction.do?id="+row.id;
	}
	
	//执行角色删除的动作,删除不用打开弹出窗
	function deleteRole(){
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
						$.post("${baseRolePath}/RoleDeleteAction.do",//提交的请求地址
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
		$("#roleName").val("");
		$("#roleNo").val("");
	}
	
	//保存
	function addRole(){
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
	function closeRoleDialog(){
		$("#dlg").dialog("close");//关闭弹框
		//TODO 清除弹框里填写的值
	}
	//打开编辑权限分配的弹框
	function openRoleMenuDialog(){
		
		//调用easyUi框架的getSelections方法获得被选中的单元行
		var selectRows = $("#dg").datagrid('getSelections');
		
		//如果没有选择或者选择了多行
		if (selectRows.length != 1) {
			$.messager.alert("系统提示","请选择一条要编辑得数据");
			return;
		}
		var row = selectRows[0];
		//下面这句话的作用是让权限编辑框打开时
		//根据row.id 请求后台获得索要编辑得角色所对应的权限列表
		//并以json格式数据返回
		$("#tt").tree("options").url="${baseRolePath}/RoleMenuQueryAction.do?id="+row.id;
		//下面这句话的意思是刷新列表
		$("#tt").tree("reload");
		//打开编辑全下列表
		$("#rmdlg").dialog("open").dialog("setTitle","编辑角色权限");
		//改变全局url,这个url指的是重新分配权限后,需要请求的业务地址
		url = "${baseRolePath}/RoleMenuUpdateAction.do?id="+row.id;
	}
	//关闭弹出框
	function closeRoleMenuDialog(){
		$("#rmdlg").dialog("close");//关闭弹框
	}
	
	//新增角色-菜单关系
	function addRoleMenu(){
		var nodes = $('#tt').tree('getChecked');
		var s = '';
		for(var i=0; i<nodes.length; i++){
			if (s != '') s += ',';
			s += nodes[i].id;
		}
		
		//调用jquery的ajax异步提交
		$.ajax({
			type:'post',
			url:url,
			data:{menuIds:s},
			//下面这句话的意思是,执行完成后对返回的结果进行处理...执行完成并不代表操作成功,也可能是失败
			//result 是执行完成后系统返回来的
			success:function(result){
				var result = eval("("+result+")");
				if (result.success) {
					$.messager.alert("系统提示","保存成功");//这句话错了,已修改
					$("#rmdlg").dialog("close");
					//下面这句话的意思是刷新列表
					$("#tt").tree("reload");
				} else {
					$.messager.alert("系统提示","保存失败");
				}
			}
		});
	}
</script>

</head>
<body>
	<table id="dg" title="角色管理" class="easyui-datagrid"
		fitColumns="true" pagination="true" rownumbers="false"
		url="${baseRolePath}/RoleListAction.do" fit="true" toolbar="#tb"	
	>
		<thead>
			<tr>
				<th field="cb" checkbox="true" align="center"></th>
				<th field="id" width="50" align="center">编号</th>
				<th field="roleName" width="100" align="center">角色名</th>
				<th field="roleNo" width="100" align="center">角色编号</th>
				
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<a href="javascript:openRoleAddDialog()" class="easyui-linkbutton" 
			iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openRoleModifyDialog()" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteRole()" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:openRoleMenuDialog()" class="easyui-linkbutton"
			iconCls="icon-add" plain="true">权限分配</a>
		</div>
	</div>
	
	<!-- 下面的div用来显示添加和修改角色 -->
	<div id="dlg" class="easyui-dialog" style="width:620px;height=250px;padding:10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>角色名:</td>
					<td><input type="text" id="roleName" name="roleName"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>角色编号:</td>
					<td><input type="text" id="roleNo" name="roleNo"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
			</table>
		</form>		
	</div>
	<!-- 添加角色的弹出框编写结束 -->
	
	<!-- 下面的div用来显示分配角色权限 -->
	<div id="rmdlg" class="easyui-dialog" style="width:320px;height=250px;padding:10px 20px"
		closed="true" buttons="#rm-dlg-buttons">
		<ul id="tt" class="easyui-tree" data-options="animate:true,checkbox:true" url=""></ul>		
	</div>
	
	<!-- 下面这个div是用来对上面的div框补充 提交按钮和重置按钮的 -->
	<div id="dlg-buttons"><!-- 注意这里的div id 和上面那个div的 buttons="#dlg-buttons"相对应-->
		<a href="javascript:addRole();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeRoleDialog();" class="easyui-linkbutton"
		iconCls="icon-cancel">关闭</a>
	</div>
	
	<!-- 下面这个div是用来对上面的div框补充 提交按钮和重置按钮的 -->
	<div id="rm-dlg-buttons"><!-- 注意这里的div id 和上面那个div的 buttons="#dlg-buttons"相对应-->
		<a href="javascript:addRoleMenu();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeRoleMenuDialog();" class="easyui-linkbutton"
		iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>