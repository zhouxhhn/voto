<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">

	<head>
		<meta charset="UTF-8">
		<title>操作日志</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20 ">
			<div class="phoneTop">
                <div inDiv>
                    <form action="/logger/pagination" id="form">
                        <label class="mR10">
                            <input type="text" name="operationUser" value="${command.operationUser}"/>&nbsp;&nbsp;操作用户
                        </label>
                        <label>
                            <select name="loggerType">
							[#assign loggerType = (command.loggerType!)?default("") /]
                                <option value="">所有类型</option>
                                <option value="APP_LOGGER" [@mc.selected loggerType "APP_LOGGER" /]>APP日志</option>
                                <option value="OPERATION" [@mc.selected loggerType "OPERATION" /]>操作日志</option>
                            </select>&nbsp;&nbsp;
                        </label>
                        <label>
                            <input id="startDate" name="startDate" value="${command.startDate}"/>
                        </label>
                        <label class="mLR5">到</label>
                        <label>
                            <input id="endDate" name="endDate" value="${command.endDate}"/>
                        </label>
                    </form>
                </div>

                <div class="chaxun mLR5" onclick="$('#form').submit()">查询</div>
			</div>
			<div class="divW w100">
				<table class="layerTab">
					<thead>
						<tr>
							<th>操作用户</th>
							<th>时间</th>
							<th>日志类型</th>
							<th>日志内容</th>
							<th>IP</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination.data??]
						[#list pagination.data as logger ]
                        <tr>
                            <td>${logger.userName!}</td>
                            <td>${logger.createDate!}</td>
                            <td>${logger.loggerType.getName()!}</td>
                            <td>${logger.loggerContent!}</td>
                            <td>${logger.ip!}</td>
                        </tr>
						[/#list]
					[/#if]
					</tbody>
                </table>

            </div>
            <!--分页-->
            <div>
					[#if pagination??]
						[@mc.customPagination '/logger/pagination?operationUser=${command.operationUser}&loggerType=${command.loggerType}&startDate=${command.startDate}&endDate=${command.endDate}'/]
				[/#if]
			</div>
        </div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
    <script src="/resources/datetime/jquery.jedate.js"></script>
	<script src="/resources/main/js/home.js"></script>
	<script type="text/javascript">
		$(".layerTab tbody tr,.pageWrap a").click(function() {
			$(this).addClass("on").siblings().removeClass("on");
		})
	</script>


</html>