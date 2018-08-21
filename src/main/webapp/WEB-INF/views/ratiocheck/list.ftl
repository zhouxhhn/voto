<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>占比审核</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
    <div class="tableW PhoneBetting divbox pad20 ">

        <div class="phoneTop">
            <div inDiv>
                <form action="/ratio_check/pagination" id="form">

                    <label>状态：
                        <select name="status">
                            <option value="0" >全部</option>
                            <option value="1" [#if command.status == '1']selected="selected" [/#if]>待审核</option>
                            <option value="2" [#if command.status == '2']selected="selected" [/#if]>通过审核</option>
                            <option value="3" [#if command.status == '3']selected="selected" [/#if]>未通过审核</option>
                        </select>
                    </label>

                </form>
            </div>
            <div class="chaxun mLR5" onclick="$('#form').submit()">查询</div>
		</div>

			<div class="divW w100">
				<table class="layerTab">
					<thead>
						<tr>
                            <th>申请时间</th>
                            <th>一级代理</th>
                            <th>二级代理</th>
                            <th>普通玩家</th>
                            <th>原R值</th>
                            <th>原最高占比</th>
                            <th>原一级占比</th>
                            <th>原二级占比</th>
                            <th>现R值</th>
                            <th>现在最高占比</th>
                            <th>现一级占比</th>
                            <th>现二级占比</th>
                            <th>状态</th>
                            <th>备注</th>
                            <th>操作</th>
						</tr>
					</thead>

					<tbody>
				[#if pagination.data ??]
					[#list pagination.data as check ]
    					<tr>
                            <td>${check.createDate}</td>
                            <td>${check.parent.name!'-'}</td>
							<td>${check.user.name!'-'}</td>
                            <td>${check.play.name!'-'}</td>
							<td>${check.oldR!'-'}</td>
                            <td>${check.oldHigh!'-'}</td>
                            <td>${check.oldFirstRatio!'-'}</td>
                            <td>${check.oldSecondRatio!'-'}</td>
                            <td>${check.newR!'-'}</td>
                            <td>${check.newHigh!'-'}</td>
                            <td>${check.newFirstRatio!'-'}</td>
                            <td>${check.newSecondRatio!'-'}</td>

                            [#if check.status == 1]
                                <td>待审核</td>
                            [#elseif check.status == 2]
                                <td style="color: #00dd1c">允许修改</td>
                            [#elseif check.status == 3]
                                <td style="color: #aa1111">拒绝修改</td>
                            [#elseif check.status == 4]
                                <td style="color: #aa1111">配置不存在</td>
                            [#elseif check.status == 5]
                                <td style="color: #aa1111">配置超范围</td>
                            [#elseif check.status == 6]
                                <td style="color: #aa1111">配置错误</td>
                            [#else ]<td>-</td>
                            [/#if]
                            <td>
                                [#if check.type == 1]一级修改对二级占比
                                [#elseif  check.type == 2]一级修改对直接玩家占比
                                [#elseif  check.type == 3]二级修改对玩家占比
                                [#elseif  check.type == 4]一级修改对玩家占比
                                [#else]--
                                [/#if]
                            </td>
                            <td>
                                [#if check.status == 1]
                                    <span class="xiugai" style="color: #108af1" onclick="pass('${check.id}')">允许</span>&nbsp;&nbsp;
                                    <span class="xiugai" style="color: #108af1" onclick="refuse('${check.id}')">拒绝</span>
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
                <p class="pTit tcenter f18 color33 mT10">是否确定允许本次修改</p>
                <form action="/ratio_check/pass" method="post" id="pass">
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
                <p class="pTit tcenter f18 color33 mT10">是否确定拒绝本次修改</p>
                <form action="/ratio_check/refuse" method="post" id="refuse">
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
			[@mc.customPagination '/ratio_check/pagination?status=${command.status}' /]
		[/#if]
		</div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
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