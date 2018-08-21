<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>推广信息</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20 ">
			<div class="phoneTop">
                <div inDiv>
                    <form action="/spread/pagination" id="form">
                        <label class="mR10"> 用户昵称：
                            <input type="text" name="name" value="${command.name!}"/>
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
							<th>账户ID</th>
							<th>有效推广人数</th>
							<th>已推广人数</th>
							<th>昨日推广人数</th>
							<th>历史推广人数</th>
                            <th>累计未结算收益</th>
							<th>可领取收益</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination.data??]
						[#list pagination.data as spread ]

						<tr>
                            <td>${spread.name!}</td>
                            <td>${spread.userId!}</td>
                            <td>${spread.effective!}</td>
                            <td>${spread.total!}</td>
                            <td>${spread.yesterdayProfit!}</td>
                            <td>${spread.totalProfit!}</td>
                            <td>${spread.unsettledProfit!}</td>
                            <td>${spread.receiveProfit!}</td>
                            <td>
                                <a href="[@spring.url '/spread/report?userId=${spread.userId}'/]"
                                   data-toggle="tooltip" data-placement="top" title="收益报表">
                                    <span >收益报表</span>
                                </a>&nbsp;|&nbsp;
                                <a href="[@spring.url '/spread/detailed?userId=${spread.userId}'/]"
                                   data-toggle="tooltip" data-placement="top" title="下级详情">
                                    <span >下级详情</span>
                                </a>
                            </td>
						</tr>
						[/#list ]
					[/#if ]

					</tbody>
				</table>
			</div>
			<!--分页-->
            <div>
				[#if pagination??]
						[@mc.customPagination '/spread/pagination?name=${command.name}'/]
				[/#if]
            </div>


		</div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>

</html>