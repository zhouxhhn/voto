<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>账号管理</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20 ">
			<div class="phoneTop">
                <div inDiv>
                    <form action="/account/pagination" id="form">
                        <label class="mR10">
                            <input type="text" name="userName" value="${command.userName!}"/>&nbsp;&nbsp;账号ID
                        </label>
                        <label>
                            <select name="status">
							[#assign status = (command.status!)?default("") /]
                                <option value="ALL" [@mc.selected status "ALL" /]>全部</option>
                                <option value="ENABLE" [@mc.selected status "ENABLE" /]>启用</option>
                                <option value="DISABLE" [@mc.selected status "DISABLE" /]>禁用</option>
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
							<th>账户ID</th>
							<th>昵称</th>
							<th>创建时间</th>
							<th>登陆时间</th>
							<th>角色</th>
                            <th>禁言</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination.data??]
						[#list pagination.data as account ]

						<tr>
							<td>${account.userName!}</td>
							<td>${account.name}</td>
							<td> ${account.createDate}</td>
							<td>${account.lastLoginDate}</td>
                            <td>${account.roles[0].description}</td>
                            <td>
                                [#if account.gag == 1]已禁言
                                [#else ]未禁言
                                [/#if ]
                            </td>
                            <td>${(account.status.getName())!}</td>
                            <td>
                                <a href="javascript:void(0)" onclick="reset('${account.id}')" title="点击重置密码">
                                    <span class="label label-warning">重置密码</span>
                                </a>
                                <a  title="点击给用户授予角色" href="javascript:void(0)" onclick="authorize('${account.id}')">
									<span class="label label-primary">授权</span>
								</a>
                               [#if account.isFrontAdmin == 0]
                                    <a href="[@spring.url '/account/frontAdmin?id=${account.id!}&version=${account.version!}'/]"
                                       data-toggle="tooltip" data-placement="top" title="点击给用户授予前台管理员">
                                        <span class="label label-danger">授权前台管理员</span>
                                    </a>
                               [#else]
                                    <a href="[@spring.url '/account/frontAdmin?id=${account.id!}&version=${account.version!}'/]"
                                       data-toggle="tooltip" data-placement="top" title="点击给用户取消前台管理员">
                                        <span class="label label-danger">取消前台管理员</span>
                                    </a>
                               [/#if]
								[#if account.status == "ENABLE"]
                                    <a href="[@spring.url '/account/update_status?id=${account.id!}&version=${account.version!}'/]"
                                       data-toggle="tooltip" data-placement="top" title="点击禁用此数据">
                                        <span class="label label-danger">禁用</span>
                                    </a>
								[#else]
                                    <a href="[@spring.url '/account/update_status?id=${account.id!}&version=${account.version!}'/]"
                                       data-toggle="tooltip" data-placement="top" title="点击启用此数据">
                                        <span class="label label-danger">启用</span>
                                    </a>
								[/#if]
                                [#if account.gag == 1]
                                    <a href="[@spring.url '/account/gag?id=${account.id!}'/]"
                                       data-toggle="tooltip" data-placement="top" title="点击解除禁言">
                                        <span class="label label-danger">解除禁言</span>
                                    </a>
                                [#else]
                                    <a href="[@spring.url '/account/gag?id=${account.id!}'/]"
                                       data-toggle="tooltip" data-placement="top" title="点击禁言">
                                        <span class="label label-danger">禁言</span>
                                    </a>
                                [/#if]
                            </td>
						</tr>
						[/#list ]
					[/#if ]

					</tbody>
				</table>
			</div>
            <!--授权-->
            <div class="tip" lay="1" style="display: none;">
                <div class="tipBox addBox" style="height: 180px">
                    <p class="pTit tcenter f18 color33 mT10">角色权限</p>
                    <form action="/account/authorize" method="post" id="changeAuthorize">
                        <div class="tipCon">
							<input type="text" name="accountId" id="roleId" value="" hidden="hidden">
                            <label>角色选择：
                                <select name="role" style="width: 200px">
                                    <option value="">——————选择——————</option>
									[#list roles as role]
                                    <option value="${role.id}">${role.description}</option>
									[/#list]
                                </select>
                            </label>
                        </div>
                        <div class="BtnDiv mT20">
                            <span class="quxiao mLR15" type="button" onclick="$('[lay]').css('display','none')">取消</span>
                            <span class="tijiao mLR15" type="submit" onclick="$('#changeAuthorize').submit()">授权</span>
                        </div>
                </div>
                </form>
            </div>
            <!--重置密码-->
            <div class="tip" id="reset" style="display: none;">
                <div class="tipBox addBox">
                    <p class="pTit tcenter f18 color33 mT10">重置密码</p>
                    <form action="/account/reset_password" method="post" id="resetPass">
                        <div class="tipCon">
                            <input type="text" name="id" id="userName" value="" hidden="hidden">
                            <p><span ><i hid>啊啊位</i>密码：</span><input type="text" name="password" /></p>
                            <p><span ><i hid>位</i>确认密码：</span><input type="text" name="confirmPassword" /></p>
                        </div>
                        <div class="BtnDiv mT20">
                            <span class="quxiao mLR15" type="button" onclick="$('#reset').css('display','none')">取消</span>
                            <span class="tijiao mLR15" type="submit" onclick="$('#resetPass').submit()">修改</span>
                        </div>
                </div>
                </form>
            </div>
			<!--分页-->
            <div>
				[#if pagination??]
						[@mc.customPagination '/account/pagination?userName=${command.userName}&status=${command.status}'/]
				[/#if]
            </div>


		</div>



	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script src="/resources/main/js/home.js"></script>
	<script type="text/javascript">
		$(".layerTab tbody tr,.pageWrap a").click(function() {
			$(this).addClass("on").siblings().removeClass("on");
		})

		function authorize(id) {
			$('#roleId').val(id);
            $("[lay]").css("display","block");
        }

        function reset(id) {

            $('#userName').val(id);
            $("#reset").css("display","block");
        }

	</script>

</html>