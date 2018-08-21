<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>银行卡管理</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20 ">
			<div class="phoneTop">
                <div inDiv>
                    <form action="/bank/pagination" id="form">
                        <label class="mR10">
                            昵称：<input type="text" name="username" value="${command.username}"/>
                        </label>
                    </form>
                </div>
                <div class="chaxun mLR5" onclick="$('#form').submit()">查询</div>
			</div>

			<div class="divW w100">
				<table class="layerTab">
					<thead>
						<tr>
                            <th>玩家账号</th>
							<th>玩家昵称</th>
							<th>银行名称</th>
							<th>银行卡号</th>
                            <th>持卡人姓名</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination??]
					[#list pagination.data as bank]
						<tr>
                            <td>${bank.token}</td>
							<td>${bank.name}</td>
							<td>${bank.bankName}</td>
							<td>${bank.bankAccountNo}</td>
                            <td>${bank.bankAccountName}</td>
							<td class="xiugai" onclick="unbind('${bank.id}')">解除绑定</td>
						</tr>
					[/#list]
					[/#if]
					</tbody>
				</table>
			</div>
			<!--分页-->
		[#if pagination??]
			[@mc.customPagination '/bank/pagination?username=${command.username!}' /]
		[/#if]

		</div>

        <div class="tip" lay="1" style="display: none;">
            <div class="tipBox addBox" style="height: 150px">
                <p class="pTit tcenter f18 color33 mT10">是否确定解绑此玩家银行卡</p>
                <form action="/bank/unbind" method="post" id="unbind">
                    <div class="tipCon">
                        <input type="text" id="bankId" name="id" value="" hidden="hidden"/>
                    </div>
                    <div class="BtnDiv mT20">
                        <span class="quxiao mLR15" type="button" onclick="$('[lay]').css('display','none')">取消</span>
                        <span class="tijiao mLR15" type="submit" onclick="$('#unbind').submit()">确定</span>
                    </div>
            </div>
            </form>
        </div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script src="/resources/main/js/home.js"></script>
	<script type="text/javascript">
		$(".layerTab tbody tr,.pageWrap a").click(function() {
			$(this).addClass("on").siblings().removeClass("on");
		})

		function unbind(id) {
			$('#bankId').val(id);
			$('.tip').show();
        }
	</script>

</html>