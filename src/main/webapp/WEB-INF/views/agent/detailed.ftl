<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>输赢明细</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<!--玩家管理-->
		<div class="tableW PhoneBetting divbox pad20 TongjiTab">
			<div class="phoneTop">
				<div inDiv>
                    <form action="/game_detailed/paginationAgent" id="form">
                        <label class="mR10">
                            <input type="text" name="boots" value="${command.boots}"/>&nbsp;&nbsp;靴
                        </label>
                        <label>
                            <input type="text" name="games" value="${command.games}"/>&nbsp;&nbsp;局&nbsp;&nbsp;
                        </label>
                        <label>
                            <select name="hallType">
                                <option value="0">全部</option>
                                <option value="1" [#if command.hallType == 1]selected="selected" [/#if]>菲律宾</option>
                                <option value="2" [#if command.hallType == 2]selected="selected" [/#if]>越南</option>
                                <option value="3" [#if command.hallType == 3]selected="selected" [/#if]>澳门</option>
                            </select>&nbsp;&nbsp;厅
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
							<th>靴局数</th>
                            <th>玩家</th>
                            <th>大厅</th>
							<th>闲</th>
							<th>庄</th>
							<th>闲对</th>
							<th>庄对</th>
							<th>和</th>
							<th>庄闲盈亏</th>
							<th>三宝盈亏</th>
						</tr>

					</thead>
					<tbody>

					[#if pagination??]
						[#list pagination.data as gameDetailed]

						<tr>
							<td>${gameDetailed.xjNum}</td>
                            <td>${gameDetailed.name}</td>
                            <td>
								[#if gameDetailed.hallType == 1]菲律宾厅
									[#elseif gameDetailed.hallType == 2]越南厅
									[#else ]澳门厅
								[/#if]
							</td>
							<td>${gameDetailed.player}</td>
                            <td>${gameDetailed.banker}</td>
                            <td>${gameDetailed.playerPair}</td>
                            <td>${gameDetailed.bankPair}</td>
                            <td>${gameDetailed.draw}</td>
                            <td>${gameDetailed.bankPlayProfit}</td>
                            <td>${gameDetailed.triratnaProfit}</td>
						</tr>

						[/#list]
					[/#if]

                    <tr>
                        <td>合计:</td>
                        <td>${pagination.count}</td>
                        <td>---</td>
                        <td>${total.player!0}</td>
                        <td>${total.banker!0}</td>
                        <td>${total.playerPair!0}</td>
                        <td>${total.bankPair!0}</td>
                        <td>${total.draw!0}</td>
                        <td>${total.bankPlayProfit!0}</td>
                        <td>${total.triratnaProfit!0}</td>
                    </tr>

					</tbody>
				</table>
			</div>
			<!--分页-->
			[#if pagination??]
            	[@mc.customPagination '/game_detailed/paginationAgent?boots=${command.boots!}&games=${command.games!}&hallType=${command.hallType}&startDate=${command.getStartDate()}&endDate=${command.getEndDate()}' /]
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