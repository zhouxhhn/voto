<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>财务账面汇总</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20  TongjiTab">
            <div class="phoneTop">
                <div inDiv>
                    <form action="/total/pagination" id="form">
                        <label class="mR10">
                            <input type="text" name="boots" value="${command.boots}"/>&nbsp;&nbsp;靴
                        </label>
                        <label>
                            <input type="text" name="games" value="${command.games}"/>&nbsp;&nbsp;局&nbsp;&nbsp;
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
			<div class="divW w100">
				<table class="layerTab">
					<thead>
						<tr>
							<th>第1靴</th>
							<th>闲投注表分</th>
							<th>庄投注表分</th>
							[#--<th>闲个人占成</th>--]
							[#--<th>庄个人占成</th>--]
							<th>闲台面分</th>
							<th>庄台面分</th>
							<th>开奖结果</th>
							<th>台面分盈亏</th>
							<th>台面分洗码</th>

							<th>零数利润</th>
							<th>对冲利润</th>
							<th>占成利润</th>
							<th>利润汇总</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination.data ??]
						[#list pagination.data as total]
						<tr>
							<td>${total.boot}靴${total.bureau}局</td>
							<td>${total.playerScore}</td>
							<td>${total.bankerScore}</td>
							[#--<td>${total.playerProportion}</td>--]
							[#--<td>${total.bankerProportion}</td>--]
							<td>${total.playerMesaScore}</td>
							<td>${total.bankerMesaScore}</td>
							<td>${total.result}</td>
							<td>${total.mesaWinLoss}</td>
							<td>${total.mesaWashCode}</td>
							<td>${total.zeroProfit}</td>
							<td>${total.hedgeProfit}</td>
							<td>${total.proportionProfit}</td>
							<td>${total.profitSummary}</td>
						</tr>
						[/#list]
					[/#if]
						<tr>
							<td>总计</td>
							<td>${sum[0]!0}</td>
							<td>${sum[1]!0}</td>
							[#--<td>${sum[2]!0}</td>--]
							[#--<td>${sum[3]!0}</td>--]
							<td>${sum[4]!0}</td>
							<td>${sum[5]!0}</td>
							<td></td>
							<td>${sum[6]!0}</td>
							<td>${sum[7]!0}</td>

							<td>${sum[8]!0}</td>
							<td>${sum[9]!0}</td>
							<td>${sum[10]!0}</td>
							<td>${sum[11]!0}</td>
						</tr>

					</tbody>
				</table>
			</div>
			<!--分页-->
                <!--分页-->
			[#if pagination??]
				[@mc.customPagination '/total/pagination?boots=${command.boots!}&games=${command.games!}&startDate=${command.getStartDate()}&endDate=${command.getEndDate()}' /]
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