<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通信录管理系统登录</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<STYLE type=text/css>
BODY {
	TEXT-ALIGN: center;
	PADDING-BOTTOM: 0px;
	BACKGROUND-COLOR: #ddeef2;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	PADDING-TOP: 0px
}

A:link {
	COLOR: #000000;
	TEXT-DECORATION: none
}

A:visited {
	COLOR: #000000;
	TEXT-DECORATION: none
}

A:hover {
	COLOR: #ff0000;
	TEXT-DECORATION: underline
}

A:active {
	TEXT-DECORATION: none
}

.input {
	BORDER-BOTTOM: #ccc 1px solid;
	BORDER-LEFT: #ccc 1px solid;
	LINE-HEIGHT: 20px;
	WIDTH: 182px;
	HEIGHT: 20px;
	BORDER-TOP: #ccc 1px solid;
	BORDER-RIGHT: #ccc 1px solid
}

.input1 {
	BORDER-BOTTOM: #ccc 1px solid;
	BORDER-LEFT: #ccc 1px solid;
	LINE-HEIGHT: 20px;
	WIDTH: 120px;
	HEIGHT: 20px;
	BORDER-TOP: #ccc 1px solid;
	BORDER-RIGHT: #ccc 1px solid;
}
</STYLE>
<script type="text/javascript">
	var isUsernameExists = false;//默认设置用户注册的用户名不存在
	var confirmpassword=false;//校验确定密码是否输入一致
	
	function login(){
		//相当于document.getElementById("username").value;
		var username=$("#username").val();
		var password=$("#password").val();
		var imageCode=$("#imageCode").val();
		
		if(username==null||username==""){
			alert("用户名不能为空！");
			return;
		}
		if(password==null||password==""){
			alert("密码不能为空！");
			return;
		}
		if(imageCode==null||imageCode==""){
			alert("验证码不能为空");
			return;
		}
		
		//提交表单
		$("#adminlogin").submit();			
		
	}
	//点击刷新验证码
	function changeCode(){
		//生成新验证码的地址
		var src='imageCode.do?temp='+(
				new Date().getTime().toString(16));
		
		//调用jquery的attr方法改变id是imageCode的img的src属性
		$("#imageCode").attr("src",src);
		return false;
	}
	
	var url;//全局url,用来控制发送请求的地址
	
	//打开添加用户的页面
	function openRegDialog(){
		$("#reg-dlg").dialog("open").dialog("setTitle","一分钟注册新用户");
		url="${pageContext.request.contextPath}/user/UserRegAction.do";
	}
	
	//注册
	function regUser(){
		//这句话的意思是提交id是fm的表单时调用的
		$("#reg-fm").form("submit",{
			url:url,//请求所提交到的路径,这个url是全局参数,在打开弹窗的时候已经指定了.
			//下面这句话的意思是提交的时候执行是否为空的校验
			onSubmit:function(){
				return $(this).form("validate") && !isUsernameExists && confirmpassword;
			},
			//下面这句话的意思是,执行完成后对返回的结果进行处理...执行完成并不代表操作成功,也可能是失败
			//result 是执行完成后系统返回来的
			success:function(result){
				var result = eval("("+result+")");
				if (result.success) {
					$.messager.alert("系统提示","注册成功,请等待审核");//这句话错了,已修改
					//重设弹出框的表单值
					resetValue();
					$("#reg-dlg").dialog("close");
					//下面这句话的意思是刷新列表
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("系统提示","保存失败");
				}
			}
		});
	}
	
	//关闭弹出框
	function closeRegDialog(){
		$("#reg-dlg").dialog("close");//关闭弹框
		//清除弹框里填写的值
		resetValue();
	}
	
	//设置字段值为""
	function resetValue(){
		$("#reg-username").val("");
		$("#reg-password").val("");
		$("#confirmpassword").val("");
		$("#phone").val("");
		$("#email").val("");
		$("#age").val("");
		$("#userStatus").val("");
	}
	
	//查询用户名是否已经存在
	function checkUserIsExists(){
		//
		var username = $("#reg-username").val();
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/user/UserRegCheckAction.do",
			data:{username:username},
			success:function(result){
				var res = eval("("+result+")");
				if (res.success) {
					isUsernameExists == false;//如果返回成功,说明用户名不存在
					$("#msg").html("<font color='green'>用户名可以注册</font>");
				} else {
					isUsernameExists = true;//用户名存在
					$("#msg").html("<font color='red'>用户名已存在</font>");
				}
			}
			
		});
	}
	
	//校验确定密码	
    function checkconfirpassword() {
        var pwd1 = document.getElementById("reg-password").value;
        var pwd2 = document.getElementById("confirmpassword").value;
        if(pwd1 == pwd2) {
            document.getElementById("passwordmsg").innerHTML="<font color='green'>两次密码相同</font>";
            confirmpassword=true;
        }
        else {
            document.getElementById("passwordmsg").innerHTML="<font color='red'>两次密码不相同</font>";
            confirmpassword=false;
        }
    }
	
</script>
</head>
<body>
<FORM id=adminlogin  method=post
	name=adminlogin action="${pageContext.request.contextPath}/user/LoginAction.do"  >
<DIV></DIV>
<TABLE style="MARGIN: auto; WIDTH: 100%; HEIGHT: 100%" border=0
	cellSpacing=0 cellPadding=0>
	<TBODY>
		<TR>
			<TD height=150>&nbsp;</TD>
		</TR>
		<TR style="HEIGHT: 254px">
			<TD>
			<DIV style="MARGIN: 0px auto; WIDTH: 936px"><IMG
				style="DISPLAY: block" src="${pageContext.request.contextPath}/images/body_03.jpg"></DIV>
			<DIV style="BACKGROUND-COLOR: #278296">
			<DIV style="MARGIN: 0px auto; WIDTH: 936px">
			<DIV
				style="BACKGROUND: url(${pageContext.request.contextPath}/images/body_05.jpg) no-repeat; HEIGHT: 155px">
			<DIV
				style="TEXT-ALIGN: left; WIDTH: 265px; FLOAT: right; HEIGHT: 125px; _height: 95px">
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
				<TBODY>
					<TR>
						<TD style="HEIGHT: 45px"><INPUT type="text" class=input value="${user.username }" name="username" id="username"></TD>
					</TR>
					<TR>
						<TD><INPUT type="password" class=input value="${user.password }" name="password" id="password"/></TD>
					</TR>
					<TR>
						<td>
							<input id="imageCode" type="text" class="input" value="" name="imageCode"/>
						</td>
					</TR>
					<TR>
						<td>
							<img id="imageCode" alt="验证码" src="imageCode.do" 
							width="100" height="25" onclick="changeCode()">
						</td>
					</TR>
				</TBODY>
			</TABLE>
			</DIV>
			<DIV style="HEIGHT: 1px; CLEAR: both"></DIV>
			<DIV style="WIDTH: 380px; FLOAT: right; CLEAR: both">
			<TABLE border=0 cellSpacing=0 cellPadding=0 width=300>
				<TBODY>
					
					<TR>
						<TD width=100 align=right><INPUT
							style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px"
							id=btnLogin src="${pageContext.request.contextPath}/images/btn1.jpg" type=image name=btnLogin onclick="javascript:login();return false;"></TD>
						<TD width=100 align=middle><INPUT
							style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px"
							id=btnReset src="${pageContext.request.contextPath}/images/btn2.jpg" type=image name=btnReset onclick="javascript:adminlogin.reset();return false;"></TD>
							
						<TD width=100 align=right><a onclick="openRegDialog();">立即注册</a></TD>	
					</TR>
				</TBODY>
			</TABLE>
			</DIV>
			</DIV>
			</DIV>
			</DIV>
			<DIV style="MARGIN: 0px auto; WIDTH: 936px"><IMG
				src="${pageContext.request.contextPath}/images/body_06.jpg"></DIV>
			</TD>
		</TR>
		<TR style="HEIGHT: 30%">
			<TD>&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
</FORM>

<!-- 下面的div用来显示添加用户 -->
	<div id="reg-dlg" class="easyui-dialog" style="width:620px;height=250px;padding:10px 20px"
		closed="true" buttons="#reg-dlg-buttons">
		<form id="reg-fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>用户名:</td>
					<td><input type="text" id="reg-username" name="username"
						class="easyui-validatebox" required="required"
						onblur="checkUserIsExists();">
						&nbsp;<font color="red">*</font><span id="msg"></span>
					</td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type="password" id="reg-password" name="password"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>确认密码:</td>
					<td><input type="password" id="confirmpassword" name="confirmpassword"
						class="easyui-validatebox" required="required" onblur="checkconfirpassword();">
						&nbsp;<font color="red">*</font><span id="passwordmsg"></span>
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
					<td>用户简介:</td>
					<td> 
						<textarea rows="5" cols="21" id="userInfo" 
						name="userInfo" class="easyui-validatebox" required="required">
						</textarea>&nbsp;<font color="red">*</font>
					</td>
				</tr>
			</table>
		</form>		
	</div>
	<!-- 添加用户的弹出框编写结束 -->
	<!-- 下面这个div是用来对上面的div框补充 提交按钮和重置按钮的 -->
	<div id="reg-dlg-buttons"><!-- 注意这里的div id 和上面那个div的 buttons="#dlg-buttons"相对应-->
		<a id="submit" href="javascript:regUser();" class="easyui-linkbutton" iconCls="icon-ok">注册</a>
		<a href="javascript:closeRegDialog();" class="easyui-linkbutton"
		iconCls="icon-cancel">关闭</a>
	</div>
	
</body>
</html>
<script type=text/javascript>
	if('${errorMsg}'!=''){
		alert('${errorMsg}');
	}
</script>