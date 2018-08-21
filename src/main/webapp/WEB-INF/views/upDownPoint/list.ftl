<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>上下分明细</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
    <div class="tableW PhoneBetting divbox pad20 ">

        <div class="phoneTop">
            <div inDiv>
                <form action="/upDownPoint/list" id="form">
                    <label class="mR10">
                        <input type="text" name="userName" value="${command.userName}" placeholder="玩家ID"/>
                    </label>
                    <label>
                        <select name="upDownPointType">
                            <option value="0" >全部</option>
                            <option value="1" [#if command.upDownPointType == 1]selected="selected" [/#if]>上分</option>
                            <option value="2" [#if command.upDownPointType == 2]selected="selected" [/#if]>下分</option>
                        </select>
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
                            <th>玩家ID</th>
							<th>玩家昵称</th>
							<th>操作时间</th>
							<th>操作类型</th>
							<th>金额</th>
							<th>操作员</th>
						</tr>
					</thead>

					<tbody>
				[#if pagination.data ??]
					[#list pagination.data as player ]
    					<tr>
                            <td>${player.userName}</td>
							<td>${player.name}</td>
							<td>${player.createDate}</td>
                            <td>[#if player.upDownPointType==1]上分
									[#else]下分
								[/#if]
                            </td>
							<td>${player.upDownPoint}</td>
							<td>${player.operationUser}</td>
						</tr>
					[/#list ]
				[/#if]
					</tbody>
				</table>
			</div>
			<!--分页-->
		[#if pagination!]
			[@mc.customPagination '/upDownPoint/list?userName=${command.userName}&upDownPointType=${command.upDownPointType}&startDate=${command.startDate}&endDate=${command.endDate}' /]
		[/#if]
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