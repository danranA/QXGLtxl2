<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String basePath = pageContext.getRequest().getServletContext().getContextPath();
String baseUserPath = basePath+"/user/";
pageContext.setAttribute("basePath", basePath);
pageContext.setAttribute("baseUserPath", baseUserPath);
%> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理页面</title>
<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	var url;//全局url,用来控制发送请求的地址
	
	//打开添加用户的页面
	function openUserAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加新用户");
		url="${baseUserPath}/UserAddAction.do";
	}
	
	//打开编辑用户的页面
	function openUserModifyDialog(){
		//调用easyUi框架的getSelections方法获得被选中的单元行
		var selectRows = $("#dg").datagrid('getSelections');
		
		//如果没有选择或者选择了多行
		if (selectRows.length != 1) {
			$.messager.alert("系统提示","请选择一条要编辑得数据");
			return;
		}
		
		var row = selectRows[0];
		//根据要修改的用户的id,查询角色列表
		$("#upd-roleno").combobox("options").url="${baseUserPath}/UserRoleQueryAction.do?id="+row.id;
		$("#upd-roleno").combobox("reload");
		//弹出编辑框,并设置标题
		$("#upd-dlg").dialog("open").dialog("setTitle","编辑用户信息");
		//将选中的单元格的数据填充到form表单中
		$("#upd-fm").form("load",row);
		
		//改变全局url为请求修改用户基本信息的地址
		url =  "${baseUserPath}/UserUpdateBaseAction.do?id="+row.id;
	}
	
	//执行用户删除的动作,删除不用打开弹出窗
	function deleteUser(){
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
						$.post("${baseUserPath}/UserDeleteAction.do",//提交的请求地址
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
		$("#username").val("");
		$("#password").val("");
		$("#confirmpassword").val("");
		$("#phone").val("");
		$("#email").val("");
		$("#age").val("");
		//根据要修改的用户的id,查询角色列表
		$("#roleno").combobox("options").url="${baseUserPath}/UserRoleQueryAction.do";
		$("#roleno").combobox("reload");
		
		//根据要修改的用户的id,查询角色列表
		$("#upd-roleno").combobox("options").url="${baseUserPath}/UserRoleQueryAction.do";
		$("#upd-roleno").combobox("reload");
	}
	
	//保存
	function addUser(){
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
	
	//修改用户密码
	function updateUserPass(){
		//这句话的意思是提交id是fm的表单时调用的
		$("#pass-fm").form("submit",{
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
					$("#pass-dlg").dialog("close");
					//下面这句话的意思是刷新列表
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("系统提示","保存失败");
				}
			}
		});
	}
	
	//保存
	function updateUser(){
		//这句话的意思是提交id是fm的表单时调用的
		$("#upd-fm").form("submit",{
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
					$("#upd-dlg").dialog("close");
					//下面这句话的意思是刷新列表
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("系统提示","保存失败");
				}
			}
		});
	}
	
	//关闭弹出框
	function closeUserDialog(){
		$("#dlg").dialog("close");//关闭弹框
		$("#upd-dlg").dialog("close");//关闭弹框
		$("#pass-dlg").dialog("close");//关闭弹框
		//清除弹框里填写的值
		resetValue();
	}
	
	
	//打开修改用户密码的页面
	function openUserPassModifyDialog(){
		//调用easyUi框架的getSelections方法获得被选中的单元行
		var selectRows = $("#dg").datagrid('getSelections');
		
		//如果没有选择或者选择了多行
		if (selectRows.length != 1) {
			$.messager.alert("系统提示","请选择一条要编辑得数据");
			return;
		}
		
		var row = selectRows[0];
		
		//弹出编辑框,并设置标题
		$("#pass-dlg").dialog("open").dialog("setTitle","修改用户密码");
		//将选中的单元格的数据填充到form表单中
		$("#pass-fm").form("load",row);
		
		//改变全局url
		url =  "${baseUserPath}/UserPassUpdateAction.do?id="+row.id;
	}
	
	//当用户点击了下拉框，下拉框里的值发生了变化，就会触发该方法
	function searchUserByStatus(){
		var status=$("#select-userStatus").val();
		$("#dg").datagrid("options").url="${baseUserPath}/UserSerchByStatusAction.do?status="+status;
		$("#dg").datagrid("reload");
	
	}
	//激活用户
	
	function activeUser(){
		//调用easyUi框架的getSelections方法获得被选中的单元行
		var selectRows = $("#dg").datagrid('getSelections');
		
		//如果没有选择
		if (selectRows.length == 0) {
			$.messager.alert("系统提示","请选择一条要处理的数据");
			return;
		}
		
		var selectIs = [];
		
		for (var i = 0;i<selectRows.length;i++) {
			selectIs.push(selectRows[i].id);//把所选中的数据行的id值设置到selectIds 数组
		}
		
		var ids = selectIs.join(",");//将数组元素拼接成一条字符串,用","号分隔
		//弹出删除询问确认框
		$.messager.confirm("系统提示",
				"您确认要激活这<font color=red>"+selectRows.length+"</font>条数据吗?",
				//result 表示的是用户的选择结果,确定是true,取消是false
				function(result){
					//如果用户点击了确定删除
					if (result) {
						//post 提交删除请求到后台,删除数据
						$.post("${baseUserPath}/UserActiveAction.do",//提交的请求地址
							{ids:ids},//提交的参数
							function(res){
								if (res.success) {
									$.messager.alert("系统提示","用户已经成功激活");
									$("#dg").datagrid("reload");//重新加载表格数据
								} else{
									$.messager.alert("系统提示","用户激活失败");
								}
							},//对返回结果的处理函数
							"json");//提交的数据格式
					}
				}		
		);
	}
	//当用户点击了查询按钮后，就会触发该方法
	function searchUserByName(){
		var username=$("#select-username").val();
		$("#dg").datagrid("options").url="${baseUserPath}/UserSerchByNameAction.do?username="+username;
		$("#dg").datagrid("reload");
	
	}
	//根据用户表单进行查询
	function searchUser(){
		//这句话的意思是提交id是fm的表单时调用的
		$("#search-fm").form("submit",{
			url:"${baseUserPath}/UserSerchAction.do",//请求所提交到的路径,这个url是全局参数,在打开弹窗的时候已经指定了.
		
			//下面这句话的意思是,执行完成后对返回的结果进行处理...执行完成并不代表操作成功,也可能是失败
			//result 是执行完成后系统返回来的
			success:function(result){
				var result = eval("("+result+")");
				if (result.success) {
					//下面这句话的意思是把结果赋值给dg的form表单，并且让dg重新赋值
					$("#dg").datagrid("loadData",result.rows);
				} else {
					$.messager.alert("系统提示","查询失败，请重新查询");
				}
			}
		});
	}
</script>

</head>
<body>
	<table id="dg" title="用户管理" class="easyui-datagrid"
		fitColumns="true" pagination="true" rownumbers="false"
		url="${baseUserPath}/UserListAction.do" fit="true" toolbar="#tb"	
	>
		<thead>
			<tr>
				<th field="cb" checkbox="true" align="center"></th>
				<th field="id" width="50" align="center">编号</th>
				<th field="username" width="100" align="center">用户名</th>
				<th field="age" width="100" alighn="center">年龄</th>
				<th field="gender" width="100" align="center">性别</th>
				<th field="phone" width="100" align="center">电话</th>
				<th field="email" width="100" align="center">邮箱</th>
				<th field="isDelete" width="100" align="center">状态</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<a href="javascript:openUserAddDialog()" class="easyui-linkbutton" 
			iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openUserModifyDialog()" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteUser()" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:openUserPassModifyDialog()" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true">修改密码</a>
			<a href="javascript:activeUser()" class="easyui-linkbutton"
			iconCls="icon-ok" plain="true">激活</a>
			
			
			<br>
			<form id="search-fm" method="post">
			<input  id="select-username" name="username"  class="easyui-validatebox" type="text" id="search-username" value="" size="7" placeholder="输入用户名">
			用户状态:
			<select id="select-userStatus" name="userStatus"  onchange="searchUserByStatus();">
				<option value="-1" selected="selected" >所有用户</option>
				<option value="0">正常用户</option>
				<option value="1">受限用户</option>
				<option value="2">新注册用户</option>
			</select>
			年龄:<input  id="select-age" name="age"  class="easyui-validatebox" type="text" id="search-age" value="" size="5" placeholder="">
			性别:<input  id="select-gender" name="gender"  class="easyui-validatebox" type="text" id="search-age" value="男" size="5" >
			电话:<input  id="select-phone" name="phone"  class="easyui-validatebox" type="text" id="search-phone" value="" size="10" >
			邮箱:<input  id="select-email" name="email"  class="easyui-validatebox" type="text" id="search-email" value="" size="10" >
			<a href="javascript:searchUser();" class="easyui-linkbutton"
			iconCls="icon-search" plain="true">查询</a>
			</form>
		</div>
	</div>
	
	<!-- 下面的div用来显示添加用户 -->
	<div id="dlg" class="easyui-dialog" style="width:620px;height=250px;padding:10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>用户名:</td>
					<td><input type="text" id="username" name="username"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type="password" id="password" name="password"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>确认密码:</td>
					<td><input type="password" id="confirmpassword" name="confirmpassword"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>性别:</td>
					<td>
						<input type="radio" name="gender" value="男" checked="checked">男<br/>
						<input type="radio" name="gender" value="女">女<br/>
					</td>
				</tr>
				<tr>
					<td>年龄:</td>
					<td><input type="text" id="age" name="age"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>电话:</td>
					<td><input type="text" id="phone" name="phone"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>邮箱:</td>
					<td><input type="text" id="email" name="email"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>用户状态:</td>
					<td> 
						<input type="radio" name="isDeleted" value="0" checked="checked">正常<br/>
						<input type="radio" name="isDeleted" value="1">锁定<br/>
					</td>
				</tr>
				<tr>
					<td>用户角色:</td>
					<td>
						<input class="easyui-combobox"
						id="roleno" name="roleno"
						data-options="valueField:'id',
						textField:'text'"
						url="UserRoleQueryAction.do">
					</td>
				</tr>
			</table>
		</form>		
	</div>
	<!-- 添加用户的弹出框编写结束 -->
	
	<!-- 下面这个div是用来对上面的div框补充 提交按钮和重置按钮的 -->
	<div id="dlg-buttons"><!-- 注意这里的div id 和上面那个div的 buttons="#dlg-buttons"相对应-->
		<a href="javascript:addUser();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeUserDialog();" class="easyui-linkbutton"
		iconCls="icon-cancel">关闭</a>
	</div>
	
	
	<!-- 下面的div用来显示修改用户 -->
	<div id="upd-dlg" class="easyui-dialog" style="width:620px;height=250px;padding:10px 20px"
		closed="true" buttons="#upd-dlg-buttons">
		<form id="upd-fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>用户名:</td>
					<td><input type="text" id="username" name="username"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>性别:</td>
					<td>
						<input type="radio" name="gender" value="男" checked="checked">男<br/>
						<input type="radio" name="gender" value="女">女<br/>
					</td>
				</tr>
				<tr>
					<td>年龄:</td>
					<td><input type="text" id="age" name="age"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>电话:</td>
					<td><input type="text" id="phone" name="phone"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>邮箱:</td>
					<td><input type="text" id="email" name="email"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>用户状态:</td>
					<td> 
						<input type="radio" name="isDeleted" value="0" checked="checked">正常<br/>
						<input type="radio" name="isDeleted" value="1">锁定<br/>
					</td>
				</tr>
				<tr>
					<td>用户角色:</td>
					<td>
						<input class="easyui-combobox"
						id="upd-roleno" name="roleno"
						data-options="valueField:'id',
						textField:'text'"
						url="UserRoleQueryAction.do">
					</td>
				</tr>
			</table>
		</form>		
	</div>
	<!-- 添加用户的弹出框编写结束 -->
	<!-- 下面这个div是用来对上面的div框补充 提交按钮和重置按钮的 -->
	<div id="upd-dlg-buttons"><!-- 注意这里的div id 和上面那个div的 buttons="#dlg-buttons"相对应-->
		<a href="javascript:updateUser();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeUserDialog();" class="easyui-linkbutton"
		iconCls="icon-cancel">关闭</a>
	</div>
	
	
	<!-- 下面的div用来显示修改用户密码 -->
	<div id="pass-dlg" class="easyui-dialog" style="width:620px;height=250px;padding:10px 20px"
		closed="true" buttons="#pass-dlg-buttons">
		<form id="pass-fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>密码:</td>
					<td><input type="password" id="password" name="password"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>确认密码:</td>
					<td>
						<input type="password" id="password" name="password"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
			</table>
		</form>		
	</div>
	<!-- 修改用户密码的弹出框编写结束 -->
	<!-- 下面这个div是用来对上面的div框补充 提交按钮和重置按钮的 -->
	<div id="pass-dlg-buttons"><!-- 注意这里的div id 和上面那个div的 buttons="#dlg-buttons"相对应-->
		<a href="javascript:updateUserPass();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeUserDialog();" class="easyui-linkbutton"
		iconCls="icon-cancel">关闭</a>
	</div>
	
</body>
</html>