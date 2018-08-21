<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>转账管理</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
    <div class="tableW PhoneBetting divbox pad20 ">

        <div class="phoneTop">
            <div inDiv>
                <form action="/transfer/pagination" id="form">
                    <label class="mR10">
                        <input type="text" name="transferName" value="${command.transferName}" placeholder="转账人"/>
                    </label>
                    <label class="mR10">
                        <input type="text" name="receiveName" value="${command.receiveName}" placeholder="收款人"/>
                    </label>
                    <label>状态：
                        <select name="status">
                            <option value="0" >全部</option>
                            <option value="1" [#if command.status == '1']selected="selected" [/#if]>待审核</option>
                            <option value="2" [#if command.status == '2']selected="selected" [/#if]>允许转账</option>
                            <option value="3" [#if command.status == '3']selected="selected" [/#if]>拒绝转账</option>
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
                            <th>转账人</th>
                            <th>收款人</th>
                            <th>申请时间</th>
                            <th>转账分数</th>
                            <th>转账人剩余分数</th>
                            <th>收款人剩余分数</th>
                            <th>状态</th>
                            <th>操作</th>
						</tr>
					</thead>

					<tbody>
				[#if pagination.data ??]
					[#list pagination.data as transfer ]
    					<tr>
                            <td>${transfer.transfer.name}</td>
							<td>${transfer.receive.name}</td>
							<td>${transfer.createDate}</td>
                            <td>${transfer.score}</td>
                            <td>${transfer.transferScore}</td>
                            <td>${transfer.receiveScore}</td>

                            [#if transfer.status == 1]
                                <td>待审核</td>
                            [#elseif transfer.status == 2]
                                <td style="color: #00dd1c">允许转账</td>
                            [#elseif transfer.status == 3]
                                <td style="color: #aa1111">拒绝转账</td>
                            [/#if]

                            <td>
                                [#if transfer.status == 1]
                                    <span class="xiugai" style="color: #108af1" onclick="pass('${transfer.id}')">允许</span>&nbsp;&nbsp;
                                    <span class="xiugai" style="color: #108af1" onclick="refuse('${transfer.id}')">拒绝</span>
                                [#else ]无可操作
                                [/#if]
                            </td>
						</tr>
					[/#list ]
				[/#if]
					</tbody>
				</table>
			</div>

        <div class="tip" lay="1" style="display: none;">
            <div class="tipBox addBox" style="height: 150px">
                <p class="pTit tcenter f18 color33 mT10">是否确定允许本次转账</p>
                <form action="/transfer/pass" method="post" id="pass">
                    <div class="tipCon">
                        <input type="text" id="passId" name="id" value="" hidden="hidden"/>
                    </div>
                    <div class="BtnDiv mT20">
                        <span class="quxiao mLR15" type="button" onclick="$('[lay=1]').css('display','none')">取消</span>
                        <span class="tijiao mLR15" type="submit" onclick="$('#pass').submit()">确定</span>
                    </div>
            </div>
            </form>
        </div>

        <div class="tip" lay="2" style="display: none;">
            <div class="tipBox addBox" style="height: 150px">
                <p class="pTit tcenter f18 color33 mT10">是否确定拒绝本次转账</p>
                <form action="/transfer/refuse" method="post" id="refuse">
                    <div class="tipCon">
                        <input type="text" id="refuseId" name="id" value="" hidden="hidden"/>
                    </div>
                    <div class="BtnDiv mT20">
                        <span class="quxiao mLR15" type="button" onclick="$('[lay=2]').css('display','none')">取消</span>
                        <span class="tijiao mLR15" type="submit" onclick="$('#refuse').submit()">确定</span>
                    </div>
            </div>
            </form>
        </div>
			<!--分页-->
		[#if pagination!]
			[@mc.customPagination '/transfer/pagination?transferName=${command.transferName}&receiveName=${command.receiveName}&status=${command.status}&startDate=${command.startDate}&endDate=${command.endDate}' /]
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

        function pass(id) {
		    $("#passId").val(id);
            $('[lay="1"]').css('display','block')
        }

        function refuse(id) {
            $("#refuseId").val(id);
            $('[lay="2"]').css('display','block')
        }

	</script>

</html>