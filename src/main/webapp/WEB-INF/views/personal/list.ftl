<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>个人占成</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20  TongjiTab">
			<div class="phoneTop">

                <div inDiv>
                    <form action="/personal/pagination" id="form">
                        <label class="mR10">
                            昵称：<input type="text" name="name" value="${command.name}"/>
                        </label>
                        <label class="mR10">
                            靴：<input type="text" name="boots" value="${command.boots}"/>
                        </label>
                        <label>
                            局：<input type="text" name="games" value="${command.games}"/>
                        </label>
                        <label>
                            时间：<input id="startDate" name="startDate" value="${command.startDate}"/>
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
							<th>靴局</th>
                            <th>昵称</th>
                            <th>闲占成</th>
                            <th>庄占成</th>
                            <th>闲对占成</th>
                            <th>庄对占成</th>
                            <th>和占成</th>
							<th>庄闲占成洗码</th>
							<th>庄闲占成盈亏</th>
							<th>三宝占成洗码</th>
							<th>三宝占成盈亏</th>
							<th>占成流水总分</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination??]
					[#list pagination.data as data]
						<tr>
							<td>${data.boots}靴${data.games}局</td>
							<td>${data.name}</td>
							<td>${data.playerPro}</td>
							<td>${data.bankerPro}</td>
							<td>${data.playerPir}</td>
							<td>${data.bankerPir}</td>
                            <td>${data.drawPro}</td>
                            <td>${data.bankPlayLose}</td>
                            <td>${data.bankPlayProfit}</td>
                            <td>${data.triratnaLose}</td>
                            <td>${data.triratnaProfit}</td>
                            <td>${data.effective}</td>
						</tr>
					[/#list]
					[/#if]
						[#--<tr>--]
							[#--<td>总计</td>--]
							[#--<td>0</td>--]
							[#--<td>0</td>--]
							[#--<td>0</td>--]
							[#--<td>0</td>--]
							[#--<td>0</td>--]
                            [#--<td>0</td>--]
                            [#--<td>0</td>--]
                            [#--<td>0</td>--]
                            [#--<td>0</td>--]
                            [#--<td>0</td>--]
                            [#--<td>0</td>--]
						[#--</tr>--]
					</tbody>
				</table>
			</div>
			<!--分页-->
		[#if pagination??]
			[@mc.customPagination '/personal/pagination?name=${command.name}&boots=${command.boots}&games=${command.games}&startDate=${command.startDate}&endDate=${command.endDate}' /]
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