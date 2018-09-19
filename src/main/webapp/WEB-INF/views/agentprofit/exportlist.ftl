<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>代理导出</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20 ">
			<div class="phoneTop">
                <div inDiv>
                    <form action="/agent_export/pagination" id="form">
                        <label class="mR10">
                            玩家名：<input type="text" id="playerName" name="playerName" value="${command.playerName}"/>
                        </label>
                        <label>
                            一级代理：<input type="text" id="firstName"  name="firstName" value="${command.firstName}"/>
                        </label>
                        <label>
                            二级代理：<input type="text" id="secondName" name="secondName" value="${command.secondName}"/>
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
                <div class="chaxun mLR5" onclick="exportExcel()">导出</div>

			</div>
			<div class="divW w100">
				<table class="layerTab">
					<thead>
						<tr>
							<th>开工点</th>
							<th>玩家名</th>
							<th>一级代理人</th>
							<th>二级代理人</th>
							<th>转码数</th>
							<th>上下数</th>
							<th>一级最高</th>
                            <th>一级占比</th>
                            <th>二级最高</th>
                            <th>二级占比</th>
                            <th>公司占比</th>
                            <th>交收方式</th>
                            <th>三方上分</th>
                            <th>银行上分</th>
                            <th>一级手续费</th>
                            <th>二级手续费</th>
                            <th>公司手续费</th>
                            <th>一级收益</th>
                            <th>二级收益</th>
                            <th>公司收益</th>
                            <th>一级R</th>
                            <th>二级R</th>
                            <th>玩家R</th>
                            <th>一级余额</th>
                            <th>二级余额</th>
                            <th>玩家余额</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination??]
						[#list pagination.data as agentProfit]

                        <tr>
                            <td>
                                [#if agentProfit.place == 1]微投
                                [#elseif agentProfit.place == 2]电投
                                [/#if]
                            </td>
                            <td>${agentProfit.play.name}</td>
                            <td>${agentProfit.firstAgent.name}</td>
                            <td>${agentProfit.secondAgent.name}</td>
                            <td>${agentProfit.loseScore}</td>
                            <td>${agentProfit.intervalScore}</td>
                            <td>${agentProfit.supHigh}</td>
                            <td>${agentProfit.supRatio}</td>
                            <td>${agentProfit.subHigh}</td>
                            <td>${agentProfit.subRatio}</td>
                            <td>${agentProfit.comRatio}</td>
                            <td>平台交收</td>
							<td>${agentProfit.addScore}</td>
                            <td>${agentProfit.rechargeScore}</td>
                            <td>${agentProfit.firstFee}</td>
                            <td>${agentProfit.secondFee}</td>
                            <td>${agentProfit.comFee}</td>
                            <td>${agentProfit.supIncome}</td>
                            <td>${agentProfit.subIncome}</td>
                            <td>${agentProfit.comIncome}</td>
                            <td>${agentProfit.supR}</td>
                            <td>${agentProfit.subR}</td>
                            <td>${agentProfit.playR}</td>
                            <td>${agentProfit.supBalance}</td>
                            <td>${agentProfit.subBalance}</td>
                            <td>${agentProfit.playBalance}</td>

                        </tr>

						[/#list]
					[/#if]
					</tbody>
				</table>
			</div>
            <!--分页-->
		[#if pagination??]
			[@mc.customPagination '/agent_export/pagination?playerName=${command.playerName}&agentName=${command.agentName}&startDate=${command.startDate}&endDate=${command.endDate}' /]
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

        function exportExcel() {
            var playerName = $("#playerName").val();
            var firstName = $("#firstName").val();
            var secondName = $("#secondName").val();
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            location.href="${pageContext.request.contextPath}/agent_export/exportExcel.do?playerName="+playerName
            +"&firstName="+firstName+"&secondName="+secondName+"&startDate="+startDate+"&endDate="+endDate;
        }

	</script>

</html>