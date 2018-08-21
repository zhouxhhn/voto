<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>提现管理</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
    <div class="tableW PhoneBetting divbox pad20 ">

        <div class="phoneTop">
            <div inDiv>
                <form action="/withdraw/pagination" id="form">
                    <label class="mR10">
                        <input type="text" name="name" value="${command.name}" placeholder="玩家昵称"/>
                    </label>
                    <label>状态：
                        <select name="status">
                            <option value="0" >全部</option>
                            <option value="1" [#if command.status == '1']selected="selected" [/#if]>待审核</option>
                            <option value="2" [#if command.status == '2']selected="selected" [/#if]>允许提现</option>
                            <option value="3" [#if command.status == '3']selected="selected" [/#if]>拒绝提现</option>
                        </select>
                    </label>
                    <label>类型：
                        <select name="success">
                            <option value="0" >全部</option>
                            <option value="1" [#if command.success == '1']selected="selected" [/#if]>未处理</option>
                            <option value="2" [#if command.success == '2']selected="selected" [/#if]>提现成功</option>
                            <option value="3" [#if command.success == '3']selected="selected" [/#if]>提现失败</option>
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
                            <th>用户昵称</th>
                            <th>申请时间</th>
                            <th>申请金额</th>
                            <th>手续费</th>
                            <th>实提金额</th>
                            <th>银行名称</th>
                            <th>到账账号</th>
                            <th>提现状态</th>
                            <th>是否成功</th>
                            <th>说明</th>
                            <th>操作</th>
						</tr>
					</thead>

					<tbody>
				[#if pagination.data ??]
					[#list pagination.data as withdraw ]
    					<tr>
                            <td>${withdraw.account.name}</td>
							<td>${withdraw.createDate}</td>
							<td>${withdraw.money}</td>
                            <td>${withdraw.charge}</td>
                            <td>${withdraw.actualMoney}</td>
                            <td>${withdraw.bankName}</td>
                            <td>${withdraw.acc}</td>
                            <td>
                            [#if withdraw.status == 1]
                                待审核
                            [#elseif withdraw.status == 2]
                                允许提现
                            [#elseif withdraw.status == 3]
                                拒绝提现
                            [/#if]
                            </td>
                            <td>
                                [#if withdraw.success == 1]
                                    未处理
                                [#elseif withdraw.success == 2]
                                    提现成功
                                [#elseif withdraw.success == 3]
                                    提现失败
                                [/#if]
                            </td>
                            <td>${withdraw.comment}</td>
                            <td>
                                [#if withdraw.status == 1]
                                    <span class="xiugai" style="color: #108af1" onclick="pass('${withdraw.id}')">允许</span>&nbsp;&nbsp;
                                    <span class="xiugai" style="color: #108af1" onclick="refuse('${withdraw.id}')">拒绝</span>
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
                <p class="pTit tcenter f18 color33 mT10">是否确定允许该玩家提现</p>
                <form action="/withdraw/pass" method="post" id="pass">
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
                <p class="pTit tcenter f18 color33 mT10">是否确定拒绝改玩家提现</p>
                <form action="/withdraw/refuse" method="post" id="refuse">
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
			[@mc.customPagination '/withdraw/pagination?name=${command.name}&status=${command.status}&success=${command.success}&startDate=${command.startDate}&endDate=${command.endDate}' /]
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