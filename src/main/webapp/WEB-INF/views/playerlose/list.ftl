<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>选手洗码盈亏</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<!--电话投注-->
		<div class="tableW PhoneBetting divbox pad20">
			<div class="phoneTop">
                <div inDiv>
                    <form action="/player_lose/pagination" id="form">
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
							<th>玩家昵称</th>
                            <th>靴局</th>
							<th>庄闲洗码总分</th>
							<th>三宝洗码总分</th>
							<th>庄闲盈亏总分</th>
							<th>三宝盈亏总分</th>
							<th>有效流水总分</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination??]
					[#list pagination.data as playerLose]
						<tr>
							<td>${playerLose.user.account.name}</td>
                            <td>${playerLose.boots}靴${playerLose.games}局</td>
							<td>${playerLose.bankPlayLose}</td>
							<td>${playerLose.triratnaLose}</td>
							<td>${playerLose.bankPlayProfit}</td>
							<td>${playerLose.triratnaProfit}</td>
							<td>${playerLose.effective}</td>
						</tr>
					[/#list]
					[/#if]

                    <tr>
                        <td>合计</td>
                        <td>---</td>
                        <td>${total.bankPlayLose!0}</td>
                        <td>${total.triratnaLose!0}</td>
                        <td>${total.bankPlayProfit!0}</td>
                        <td>${total.triratnaProfit!0}</td>
                        <td>${total.effective!0}</td>
                    </tr>
					</tbody>
				</table>
			</div>
			<!--分页-->
		[#if pagination??]
			[@mc.customPagination '/player_lose/pagination?name=${command.name}&boots=${command.boots}&games=${command.games}&startDate=${command.startDate}&endDate=${command.endDate}' /]
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