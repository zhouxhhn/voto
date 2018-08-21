<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<!--电话投注-->
		<form action="/phone_bet/pagination" id="form" method="post">
		<div class="tableW PhoneBetting divbox pad20">
			<div class="phoneTop">

				<div inDiv>昵称&nbsp;<input type="text" name="name" value="${command.name}"/> </div>
				<div inDiv>
					<label>
						<input id="startDate" name="startDate" value="${command.startDate}"/>
					</label>
					<label class="mLR5">到</label>
					<label>
						<input id="endDate" name="endDate" value="${command.endDate}"/>
					</label>
				</div>
				<div inDiv>
					<select name="status">
                        <option value="0" >全部</option>
						<option value="1" [#if command.status == 1]selected="selected" [/#if]>等待中</option>
                        <option value="2" [#if command.status == 2]selected="selected" [/#if]>接待中</option>
                        <option value="3" [#if command.status == 3]selected="selected" [/#if]>已完成</option>
					</select>
				</div>
				<div class="chaxun mLR5" onclick="$('#form').submit()">查询</div>

				<div inDiv_n>剩余分汇总：<i>${total[0]}</i></div>
				<div inDiv_n>洗码量汇总：<i>${total[1]}</i></div>
			</div>
        </form>
			<div class="divW w100">
				<table class="layerTab">
					<thead>
						<tr>
							<th>玩家昵称</th>
							<th>玩家电话</th>
                            <th>一级占比</th>
                            <th>二级占比</th>
							<th>创建时间</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>游戏场地</th>
							<th>冻结分</th>
							<th>玩家剩余分</th>
							<th>洗码注量</th>
							<th>工号</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination??]
					[#list pagination.data as phoneBet]


						<tr>
							<td>${phoneBet.user}</td>
							<td>${phoneBet.phoneNo}</td>
                            <td>${phoneBet.firstRatio}</td>
                            <td>${phoneBet.secondRatio}</td>
							<td>${phoneBet.createDate}</td>
							<td>${phoneBet.startDate!0}</td>
							<td>${phoneBet.endDate!0}</td>
							<td>[#if phoneBet.hall == 1] 卡卡厅1楼
								[#else ]卡卡厅2楼
								[/#if]
							</td>
							<td>${phoneBet.frozenScore}</td>
							<td>${phoneBet.restScore}</td>
							<td>${phoneBet.loseScore}</td>
							<td>${phoneBet.jobNum!0}</td>
							[#if phoneBet.status == 1]
								<td class="xiugai" onclick="start(this)" data-id="${phoneBet.id}"><span class="kai" >开工</span></td>
							[#elseif phoneBet.status == 2]
                                <td class="xiugai" onclick="end(this)" data-id="${phoneBet.id}"><span class="ting">停工</span></td>
							[#else ]
                                <td class="xiugai">已完成</td>
							[/#if]
						</tr>
					[/#list]
					[/#if]
					</tbody>
				</table>
			</div>
            <!--分页-->
		[#if pagination??]
			[@mc.customPagination '/phone_bet/pagination?name=${command.name}&startDate=${command.startDate}&endDate=${command.endDate}' /]
		[/#if]

		<!--开工提醒-->
		<div class="tip" kaigong style="display:none ;">
            <form action="/phone_bet/start" method="post" id="job_start">
			<div class="tipBox kaiBox">
				<p class="pTit tcenter f18 color33 mT10">开工提醒</p>
				<div class="tipCon">
					<p><span>玩家昵称：</span><span id="n"></span></p>
					<p><span>冻结分数：</span><span id="f"></span></p>
					<p><span>房间选择：</span><span id="h"></span></p>
					<p><span>电话号码：</span><span id="p"></span></p>
					<p><span>输入工号开始接待：</span><input type="text" name="jobNum"/></p>
                    <input type="text" name="id" id="jobId" hidden="hidden"/>
				</div>
				<div class="BtnDiv mT20">
					<span class="quxiao mLR15">取消</span>
					<span class="tijiao mLR15" onclick="$('#job_start').submit()">接待</span>
				</div>
			</div>
            </form>
		</div>
		<!--停工提醒-->
		<div class="tip" tinggong style="display:none ;">
            <form action="/phone_bet/end" method="post" id="job_end">
			<div class="tipBox tingBox">
				<p class="pTit tcenter f18 color33 mT10" >停工提醒</p>
				<div class="tipCon">
					<p><span>玩家昵称：</span><span id="tn"></span></p>
					<p><span>冻结分数：</span><span id="tf"></span></p>
					<p><span>房间选择：</span><span id="th"></span></p>
					<p><span>电话号码：</span><span id="tp"></span></p>
					<p><span>接待工号：</span><span id="tj"></span></p>
					<p class="mB15"><span>玩家剩余分数：</span><input type="text" name="score"/></p>
					<p><span><span hid>站</span>玩家洗码量：</span><input type="text" name="lose"/></p>
                    <input type="text" name="id" id="jobId_" hidden="hidden"/>
				</div>
				<div class="BtnDiv mT20">
					<span class="quxiao mLR15">取消</span>
					<span class="tijiao mLR15" onclick="$('#job_end').submit()">停工</span>
				</div>
			</div>
            </form>
		</div>
	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
    <script src="/resources/datetime/jquery.jedate.js"></script>
	<script src="/resources/main/js/home.js"></script>
	<script type="text/javascript">
		$(".layerTab tbody tr,.pageWrap a").click(function() {
			$(this).addClass("on").siblings().removeClass("on");
		});
		$(".xiugai>.kai").click(function(){
			$("div[kaigong]").css("display","block")
		});
		$(".xiugai>.ting").click(function(){
			$("div[tinggong]").css("display","block")
		});
		$(".quxiao").click(function(){
			$(".tip").css("display","none");
		});
		
		function start(obj) {
		    var thisObj = $(obj);
		    $('#jobId').val(thisObj.attr("data-id"));
		    var tds = thisObj.prevAll();
            $('#n').text(tds[11].innerHTML);
            $('#f').text(tds[3].innerHTML);
            $('#h').text(tds[4].innerHTML);
            $('#p').text(tds[10].innerHTML);

        }

        function end(obj) {
            var thisObj = $(obj);
            $('#jobId_').val(thisObj.attr("data-id"));
            var tds = thisObj.prevAll();
            $('#tn').text(tds[11].innerHTML);
            $('#tf').text(tds[3].innerHTML);
            $('#th').text(tds[4].innerHTML);
            $('#tp').text(tds[10].innerHTML);
            $('#tj').text(tds[0].innerHTML);

        }
	</script>

</html>