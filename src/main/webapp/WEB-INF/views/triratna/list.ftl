<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>三宝盈亏汇总</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20  TongjiTab">
			<div class="phoneTop">

                <div inDiv>
                    <form action="/triratna/pagination" id="form">
                        <label class="mR10">
                            用户昵称：<input type="text" id="userName" name="userName" value="${command.userName}"/>
                        </label>
                        <label class="mR10">
                            靴：<input type="text" id="xueInput" name="boots" value="${command.boots}"/>
                        </label>
                        <label>
                            局：<input type="text" id="juInput" name="games" value="${command.games}"/>
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
                <div class="chaxun mLR5" onclick="exportTriratna()">导出</div>
			</div>
			<div class="divW w100">
				<table class="layerTab">
					<thead>
						<tr>
                            <th>用户昵称</th>
							<th>靴局</th>
							<th>闲对</th>
							<th>庄对</th>
							<th>和</th>
							<th>三宝总投注</th>
							<th>选手总盈亏</th>
							<th>公司总利润</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination??]
					[#list pagination.data as data]
						<tr>
                            <td>${data.userName}</td>
							<td>${data.boots}靴${data.games}局</td>
							<td>${data.player_pair}</td>
							<td>${data.bank_pair}</td>
							<td>${data.draw}</td>
							<td>${data.player_pair+data.getBank_pair()+data.draw}</td>
							<td>${data.triratna_profit}</td>
							<td>[#if data.triratna_profit >0]0[#else ]${-data.triratna_profit}[/#if]</td>
						</tr>
					[/#list]
					[/#if]
						<tr>
							<td></td>
							<td>总计</td>
							<td>${total.playerPair}</td>
							<td>${total.bankPair}</td>
							<td>${total.draw}</td>
							<td>${total.playerPair+total.bankPair+total.draw}</td>
							<td>${total.triratnaProfit}</td>
							<td>[#if total.triratnaProfit >0]0
							[#else ]${-total.triratnaProfit}[/#if]</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!--分页-->
		[#if pagination??]
			[@mc.customPagination '/triratna/pagination?boots=${command.boots}&games=${command.games}&startDate=${command.startDate}&endDate=${command.endDate}&userName=${command.userName}' /]
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

		function exportTriratna() {
		    var xueInput = $("#xueInput").val();
		    var juInput = $("#juInput").val();
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var userName = $("#userName").val();
            location.href="${pageContext.request.contextPath}/triratna/exportTriratna.do?xueInput="+xueInput
						  +"&juInput="+juInput+"&startDate="+startDate+"&endDate="+endDate+"&userName="+userName;
        }

	</script>

</html>