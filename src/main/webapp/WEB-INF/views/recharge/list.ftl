<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>充值记录</title>
        <link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
        <link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
    <div class="tableW PhoneBetting divbox pad20 ">

        <div class="phoneTop">
            <div inDiv>
                <form action="/recharge/pagination" id="form">
                    <label class="mR10">
                        <input type="text" name="name" value="${command.name}" placeholder="玩家昵称"/>
                    </label>
                    <label>类型：
                        <select name="isSuccess">
                            <option value="ALL" >全部</option>
                            <option value="YES" [#if command.isSuccess == 'YES']selected="selected" [/#if]>成功</option>
                            <option value="NO" [#if command.isSuccess == 'NO']selected="selected" [/#if]>失败</option>
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
                            <th>玩家昵称</th>
							<th>创建时间</th>
							<th>充值金额</th>
							<th>支付状态</th>
							<th>支付方式</th>
						</tr>
					</thead>

					<tbody>
				[#if pagination.data ??]
					[#list pagination.data as recharge ]
    					<tr>
                            <td>${recharge.account.name}</td>
							<td>${recharge.createDate}</td>
							<td>${recharge.money}</td>
                            [#if recharge.isSuccess == 'YES']<td style="color: #00dd1c">成功</td>
							[#else]<td style="color: #98343b">失败</td>
							[/#if]
							<td>${recharge.payType.getName()}</td>
						</tr>
					[/#list ]
				[/#if]
					</tbody>
				</table>
			</div>
			<!--分页-->
		[#if pagination!]
			[@mc.customPagination '/recharge/pagination?name=${command.name}&isSuccess=${command.isSuccess}&startDate=${command.startDate}&endDate=${command.endDate}' /]
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