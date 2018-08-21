<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>推广报表</title>
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
                            <th>统计类型</th>
                            <th>一级</th>
                            <th>二级</th>
                            <th>三级</th>
                            <th>四级</th>
                            <th>五级</th>
						</tr>
					</thead>
					<tbody>
					[#list list as report ]
                    <tr>
                        <td>
							[#if report_index+1 == 1]今日推广人数
							[#elseif report_index+1 == 3]历史总推广人数
							[#elseif report_index+1 == 4]今日推广收益(元)
							[#elseif report_index+1 == 5]昨日推广收益(元)
							[#elseif report_index+1 == 6]本周推广收益(元)
							[#elseif report_index+1 == 7]上周推广收益(元)
							[#elseif report_index+1 == 2]本月推广收益(元)
							[/#if]
                        </td>
                        <td>${report[0]!}</td>
                        <td>${report[1]!}</td>
                        <td>${report[2]!}</td>
                        <td>${report[3]!}</td>
                        <td>${report[4]!}</td>
                    </tr>
					[/#list]
					</tbody>
				</table>
			</div>
		</div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>

</html>