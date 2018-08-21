<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>下级详情</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20  TongjiTab">
			<div class="phoneTop">

			</div>
			<div class="divW w100">
				<table class="layerTab">
					<thead>
						<tr>
                            <th>玩家昵称</th>
                            <th>推广等级</th>
                            <th>下注金额</th>
                            <th>贡献收益</th>
						</tr>
					</thead>
					<tbody>
					[#list detailed as command ]
                    <tr>
                        <td>${command.name!}</td>
                        <td>${command.level!}级</td>
                        <td>${command.bet!}</td>
                        <td>${command.profit!}</td>
                    </tr>
					[/#list]
					</tbody>
				</table>
			</div>
		</div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>

</html>