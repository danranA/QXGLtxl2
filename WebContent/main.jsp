<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.*,com.cz.txl.model.*"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>通信录管理系统主页</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	
	function openTab(text,url,iconCls){
		if($("#tabs").tabs("exists",text)){
			$("#tabs").tabs("select",text);
		}else{
			var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/"+url+"'></iframe>";
			$("#tabs").tabs("add",{
				title:text,
				iconCls:iconCls,
				closable:true,
				content:content
			});
		}
	}
	
	function openPasswordModifyDialog(){
		$("#dlg").dialog("open").dialog("setTitle","修改密码");
		url="${pageContext.request.contextPath}/user/modifyPassword.do?id=${currentUser.id}";
	}
	
	function closePasswordModifyDialog(){
		$("#dlg").dialog("close");
		$("#oldPassword").val("");
		$("#newPassword").val("");
		$("#newPassword2").val("");
	}
	
	function modifyPassword(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				var oldPassword=$("#oldPassword").val();
				var newPassword=$("#newPassword").val();
				var newPassword2=$("#newPassword2").val();
				if(!$(this).form("validate")){
					return false;
				}
				if(oldPassword!='${currentUser.password}'){
					$.messager.alert("系统提示","用户密码输入错误！");
					return false;
				}
				if(newPassword!=newPassword2){
					$.messager.alert("系统提示","确认密码输入错误！");
					return false;
				}
				return true;
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.success){
					$.messager.alert("系统提示","密码修改成功，下一次登录生效！");
					closePasswordModifyDialog();
				}else{
					$.messager.alert("系统提示","密码修改失败");
					return;
				}
			}
		});
	}
	
	function logout(){
		$.messager.confirm("系统提示","您确定要退出系统吗",function(r){
			if(r){
				window.location.href="${pageContext.request.contextPath}/user/LogoutAction.do";
			}
		});
	}
	
	$(function(){
		var url = "${pageContext.request.contextPath}/user/QueryScheduleListAction.do";
		function show(){
			//调用jquery的ajax异步提交
			$.ajax({
				type:'post',
				url:url,
				//下面这句话的意思是,执行完成后对返回的结果进行处理...执行完成并不代表操作成功,也可能是失败
				//result 是执行完成后系统返回来的
				success:function(result){
					var result = eval("("+result+")");
					if (result.success) {
						var schList = result.schList;
						var content = "";
						if (schList.length>0) {
							content += "您当前未完成行程共(<font color='red'>" + schList.length + "</font>)"
							content += "<br>";
							$.each(schList,function(i,temp){
								content += "日期:" + temp.schTime + "行程:" + temp.schName;
								content += "<br>";
							});
						} else {
							content += "您当前没有未完成的行程";
						}
							
							$("#schresult").html(content);
					} else {
						$.messager.alert("系统提示","获取数据失败");
					}
				}
			});
		}
		window.onload = show();//这条语句确保程序加载完成后立即执行一次请求
		setInterval(show,3000000);// 这句话确保show函数每3秒执行一次.
	});
	
	/* $(function (){
		$.ajaxSetup({
			contentType:"applicaction/x-www-form-urlencoded;charset=utf-8",
			cache:false,
			complete:function(xhr,ts){
				var resText = xhr.responseText; 
				var sessionstatus = xhr.getResponseHeader("sessionstatus");
				if (sessionstatus == 911) {
					$.messager.confirm('登录已过期','登录失效,请重新登录',
							function(confirm){
								if (confirm) {
									window.location.replace("login.jsp");
								}
					});
				} 
			}
		});
	}); */
</script>
</head>
<body class="easyui-layout">
<div region="north" style="height: 78px;background-color: #E0ECFF">
	<table style="padding: 5px" width="100%">
		<tr>
			<td width="50%">
				<img src="${pageContext.request.contextPath}/images/bglogo.png"/>
			</td>
			<td valign="bottom" align="right" width="50%">
				<font size="3">&nbsp;&nbsp;<strong>欢迎：</strong>${user.username}</font>
				<font size="3">&nbsp;&nbsp;<strong></strong>${user.role.roleName}</font>
				<button onclick="logout();">注销</button>
			</td>
			
		</tr>
	</table>
</div>
<div region="center">
	<div class="easyui-tabs" fit="true" border="false" id="tabs">
		<div title="首页" data-options="iconCls:'icon-home'">
			<div align="center" style="padding-top: 10px;padding-left:10px;">
				<div id="schedule-div" class="easyui-draggable" data-options="handle:'#myschedule'" style="width:48%;;height:300px;border:1px solid #ccc;float: left;">
					<div id="myschedule" style="background:#ddd;padding:5px;">我的行程</div>
					<div style="padding:20px" id="schresult">
						<%-- <%
							List<Schedule> schList = (List<Schedule>)request.getAttribute("schList");
							if (schList != null && schList.size() > 0) {
						%>
								您当前有未完成的行程共(<font color="red"><%=schList.size() %></font>个)
								<br>
								<%
									for (int i = 0;i<schList.size();i++) {
										Schedule sch = schList.get(i);	
									%>
										<span>日期:<%=sch.getSchTime()%> 安排:<%=sch.getSchName() %></span>
									<%
										
									}
								%> 
						<%		
							} else {
						%>
							您当前没有未完成的行程!
						<%} %> --%>
					</div>
				</div>
				
				<div id="info-div" class="easyui-draggable" data-options="handle:'#myinfo'" style="width:48%;;height:300px;border:1px solid #ccc;float: right;">
					<div id="myinfo" style="background:#ddd;padding:5px;">我的消息</div>
					<div style="padding:20px">Drag and Resize Me</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div region="west" style="width: 200px" title="导航菜单" split="true">
	<div class="easyui-accordion" data-options="fit:true,border:false">
		
		<%
			User user = (User)request.getSession().getAttribute("user");
			List<Menu> menuList = user.getRole().getMenuList();
			List<String> actionList = new ArrayList<String>();
			for (int i = 0;i<menuList.size();i++) {
				actionList.add(menuList.get(i).getAction());
			}
		%>
		
		<div title="基础数据管理"  data-options="iconCls:'icon-jcsjgl'" style="padding:10px">
			<%if (actionList.contains("MenuManageAction")){ %>
				<a href="javascript:openTab('菜单管理','/menu/MenuManageAction.do','icon-sjzdgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-sjzdgl'" style="width: 150px;">菜单管理</a>
			<%} %>
			<%if (actionList.contains("RoleManageAction")){ %>
				<a href="javascript:openTab('角色管理','/role/RoleManageAction.do','icon-cpxxgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cpxxgl'" style="width: 150px;">角色管理</a>
			<%} %>
			<%if (actionList.contains("UserManageAction")){ %>
				<a href="javascript:openTab('用户管理','/user/UserManageAction.do','icon-user')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-user'" style="width: 150px;">用户管理</a>
			<%} %>
			
		</div>
		
		<div title="任务管理"  data-options="iconCls:'icon-help'" style="padding:10px">
			<a href="javascript:openTab('我的行程','ScheduleManageAction.do','icon-tip')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-tip'" style="width: 150px;">我的行程</a>
			<a href="javascript:openTab('我的消息','InfoManageAction.do','icon-cpxxgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-fwfk'" style="width: 150px;">我的消息</a>
		</div>
		
		<div title="系统管理"  data-options="iconCls:'icon-item'" style="padding:10px">
			<a href="javascript:openPasswordModifyDialog()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-modifyPassword'" style="width: 150px;">修改密码</a>
			<a href="javascript:logout()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'" style="width: 150px;">安全退出</a>
		</div>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 400px;height:250px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 	<form id="fm" method="post">
 		<table cellspacing="8px">
 			<tr>
 				<td>用户名：</td>
 				<td><input type="text" id="userName" name="userName" value="${currentUser.userName }" readonly="readonly" style="width: 200px"/></td>
 			</tr>
 			<tr>
 				<td>原密码：</td>
 				<td><input type="password" id="oldPassword" class="easyui-validatebox" required="true" style="width: 200px"/></td>
 			</tr>
 			<tr>
 				<td>新密码：</td>
 				<td><input type="password" id="newPassword" name="password" class="easyui-validatebox" required="true" style="width: 200px"/></td>
 			</tr>
 			<tr>
 				<td>确认新密码：</td>
 				<td><input type="password" id="newPassword2"  class="easyui-validatebox" required="true" style="width: 200px"/></td>
 			</tr>
 		</table>
 	</form>
</div>

<div id="dlg-buttons">
	<a href="javascript:modifyPassword()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closePasswordModifyDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>