<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String basePath = pageContext.getRequest().getServletContext().getContextPath();
String baseSchedulePath = basePath+"/schedule/";
pageContext.setAttribute("basePath", basePath);
pageContext.setAttribute("baseSchedulePath", baseSchedulePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>行程管理页面</title>
<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	var url;//全局url,用来控制发送请求的地址
	
	//打开添加行程的页面
	function openScheduleAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加新行程");
		url="${baseSchedulePath}/ScheduleAddAction.do";
	}
	
	//打开编辑行程的页面
	function openScheduleModifyDialog(){
		//调用easyUi框架的getSelections方法获得被选中的单元行
		var selectRows = $("#dg").datagrid('getSelections');
		
		//如果没有选择或者选择了多行
		if (selectRows.length != 1) {
			$.messager.alert("系统提示","请选择一条要编辑得数据");
			return;
		}
		
		var row = selectRows[0];
		//弹出编辑框,并设置标题
		$("#dlg").dialog("open").dialog("setTitle","编辑行程信息");
		//将选中的单元格的数据填充到form表单中
		
		//修改的时候也需要将毫秒值正确的显示到编辑框中
		var time = row.schTime;
		row.schTime = formatTime(parseInt(time),"yyyy-MM-dd");
			
		$("#fm").form("load",row);
		
		//改变全局url
		url =  "${baseSchedulePath}/ScheduleUpdateAction.do?id="+row.id;
	}
	
	//执行行程删除的动作,删除不用打开弹出窗
	function deleteSchedule(){
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
						$.post("${baseSchedulePath}/ScheduleDeleteAction.do",//提交的请求地址
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
		$("#schName").val("");
		$("#schDesc").val("");
		$("#schTime").val("");
		$("#isSendMail").val("");
	}
	
	//保存
	function addSchedule(){
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
	function closeScheduleDialog(){
		$("#dlg").dialog("close");//关闭弹框
		//清除弹框里填写的值
		resetValue();
	}
	
	//将数据行里的0或1显示为用户能识别的有意义文字
	function formatShow(value,row,index){
		if(value==1){
		        return "是";
		    }else{
		        return "否";
		}
		}
	//获得"yyyy-MM-dd"的格式输出日期
	function formatTime(value,row,index){
		return format(parseInt(value),"yyyy-MM-dd");
	}
	
	function format(time, format){ 
		var t = new Date(time); 
		var tf = function(i){return (i < 10 ? '0' : '') + i}; 
		return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){ 
		switch(a){ 
		case 'yyyy': 
		return tf(t.getFullYear()); 
		break; 
		case 'MM': 
		return tf(t.getMonth() + 1); 
		break; 
		case 'mm': 
		return tf(t.getMinutes()); 
		break; 
		case 'dd': 
		return tf(t.getDate()); 
		break; 
		case 'HH': 
		return tf(t.getHours()); 
		break; 
		case 'ss': 
		return tf(t.getSeconds()); 
		break; 
		}; 
		}); 
		} 
</script>

</head>
<body>
	<table id="dg" title="行程管理" class="easyui-datagrid"
		fitColumns="true" pagination="true" rownumbers="false"
		url="${baseSchedulePath}/ScheduleListAction.do" fit="true" toolbar="#tb"	
	>
		<thead>
			<tr>
				<th field="cb" checkbox="true" align="center"></th>
				<th field="id" width="50" align="center">行程编号</th>
				<th field="schName" width="100" align="center">行程标题</th>
				<th field="schDesc" width="100" alighn="center">行程描述</th>
				<th field="schTime" width="100" align="center" formatter="formatTime">行程计划时间</th>
				<th field="isSendMail" width="100" align="center" formatter="formatShow">是否邮件提醒</th>
				<th field="isFinish" width="100" align="center" formatter="formatShow">行程是否完成</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<a href="javascript:openScheduleAddDialog()" class="easyui-linkbutton" 
			iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openScheduleModifyDialog()" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteSchedule()" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>
	
	<!-- 下面的div用来显示添加和修改行程 -->
	<div id="dlg" class="easyui-dialog" style="width:620px;height=250px;padding:10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>行程名:</td>
					<td><input type="text" id="schName" name="schName"
						class="easyui-validatebox" required="required">
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
				
				<tr>
					<td>行程描述:</td>
					<td><textarea rows="5" cols="20"
						class="easyui-validatebox"
						id="schDesc" name="schDesc"
						required="required"
						text-align="left"
					>
						
					</textarea><font color="red">*</font>
				</tr>
				<tr>
					<td>行程日期:</td>
					<td><input id="schTime" name="schTime" type="text" class="easyui-datebox" required="required" >
					</td>
				</tr>
				
				<tr>
					<td>是否发送邮件:</td>
					<td><input type="radio" name="isSendMail" value="1" checked="checked">是<br/>
						<input type="radio" name="isSendMail" value="0">否<br/>
					</td>
				</tr>
			</table>
		</form>		
	</div>
	<!-- 添加行程的弹出框编写结束 -->
	
	<!-- 下面这个div是用来对上面的div框补充 提交按钮和重置按钮的 -->
	<div id="dlg-buttons"><!-- 注意这里的div id 和上面那个div的 buttons="#dlg-buttons"相对应-->
		<a href="javascript:addSchedule();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeScheduleDialog();" class="easyui-linkbutton"
		iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>