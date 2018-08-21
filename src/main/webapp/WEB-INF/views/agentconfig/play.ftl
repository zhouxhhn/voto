<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>代理管理</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20 ">
			<div class="phoneTop">
                <div inDiv>
                    <form action="/agent_config/pagination" id="form">
                        <label>
                            代理名：<input type="text" name="agentName" value="${command.agentName}"/>&nbsp;
                        </label>
                        <label>代理等级：
                            <select name="level">
                                <option value="0">全部</option>
                                <option value="1" [#if command.level == 1]  selected="selected" [/#if]>一级代理</option>
                                <option value="2" [#if command.level == 2]  selected="selected" [/#if]>二级代理</option>
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
							<th>代理昵称</th>
							<th>代理别名</th>
                            <th>代理等级</th>
							<th>代理R值</th>
							<th>最高占比</th>
							<th>上级占比</th>
							<th>下级人数</th>
                            <th>剩余分数</th>
                            <th>修改</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination??]
						[#list pagination.data as agentConfig]

                        <tr>
                            <td>${agentConfig.name}</td>
                            <td>${agentConfig.alias}</td>
                            <td>普通玩家</td>
                            <td>${agentConfig.valueR}</td>
                            <td>${agentConfig.highRatio}</td>
                            <td>${agentConfig.factRatio}</td>
                            <td>${agentConfig.subCount}</td>
                            <td>${agentConfig.restScore}</td>
                            <td>
                                <a  la="2" title="点击修改" href="javascript:void(0)" data-id="${agent.id}" data-f="${agent.factRatio}"
                                    data-h="${agent.highRatio}" data-r="${agent.r}" onclick="edit(this)">
                                    <span class="label label-primary">修改</span>
                                </a>
                            </td>

                        </tr>

						[/#list]
					[/#if]
					</tbody>
				</table>
			</div>
            <!--批量修改玩家信息-->
            <div class="tip" lay="2" style="display:none;">
                <div class="tipBox xiuigaiBox">
                    <p class="pTit tcenter f18 color33 mT10 mB15">代理配置修改</p>
                    <form action="/agent_config/edit" method="post"  id="edit">
                        <div class="tipCon ov">
                            <div class="fl piliang">
                                <p class="tcenter f16 color33 mB15" id="as"></p>
                                <div><span >代理最高占比：</span><input type="text" id="highRatio" name="highRatio" /></div>
                                <div><span ><i hid>站AA</i>代理R值：</span><input type="text" id="valueR" name="valueR" /></div>
                            </div>
                            <div class="fr piliang">
                                <p class="tcenter f16 color33 mB15" id="le"></p>
                                <div><span><i hid>站</i>公司占比：</span><input type="text" id="factRatio" name="factRatio"   /></div>
                            </div>
                        </div>
						<input type="text" id="agentId" name="id" hidden="hidden">
                        <div class="BtnDiv mT20">
                            <span class="quxiao mLR15" >取消</span>
                            <span class="tijiao mLR15" onclick="submit()">确认修改</span>
                        </div>
                    </form>
                </div>
            </div>
            <!--分页-->
		[#if pagination??]
			[@mc.customPagination '/agent_config/pagination?agentName=${command.agentName}&level=${command.level}' /]
		[/#if]
		</div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script src="/resources/main/js/home.js"></script>
	<script type="text/javascript">




		$(".layerTab tbody tr,.pageWrap a").click(function() {
			$(this).addClass("on").siblings().removeClass("on");
		})

        $("[la]").click(function(){
            $("[lay]").css("display","none");
            $("[lay="+$(this).attr("la")+"]").css("display","block");
        });

        $(".quxiao").click(function(){
            $(".tip").css("display","none")
        });

		function edit(obj) {
            var thisObj = $(obj);
            $('#agentId').val(thisObj.attr("data-id"));
            var tds = thisObj.prevAll();
            $('#highRatio').val(tds[3].innerHTML);
            $('#valueR').val(tds[4].innerHTML);
            $('#factRatio').val(tds[2].innerHTML);

        }

        function submit() {
            $.post(
                    "/agent_config/edit",
                    {'valueR': $('#valueR').val(),'highRatio': $('#highRatio').val(),  'factRatio': $('#factRatio').val(), 'id': $('#agentId').val()},
                    function (result) {
                        if (1 == result) {
                            alert('配置超范围');
                        } else if ( -1 == result){
                            alert('配置错误');
                        } else {
                            alert("修改成功");
                            window.location.reload();
                        }
                    }
            );
        }

	</script>

</html>