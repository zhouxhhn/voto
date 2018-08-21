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
                    <form action="${url}" id="form">
                        <label>
                            昵称：<input type="text" name="agentName" value="${command.agentName}"/>&nbsp;
                        </label>
                        <label>代理等级：
                            <select name="level">
                                <option value="0">全部</option>
                                <option value="1" [#if command.level == 1]  selected="selected" [/#if]>一级代理</option>
                                <option value="2" [#if command.level == 2]  selected="selected" [/#if]>二级代理</option>
                            </select>
                        </label>
                        <input type="text" name="parentId" value="${command.parentId}" hidden="hidden"/>
                    </form>
                </div>
                <div class="chaxun mLR5" onclick="$('#form').submit()">查询</div>

			</div>
			<div class="divW w100">
				<table class="layerTab">
					<thead>
						<tr>
							<th>用户昵称</th>
							<th>代理别名</th>
                            <th>用户角色</th>
							<th>R值</th>
							<th>最高占比</th>
                            <th>一级占比</th>
                            <th>二级占比</th>
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
                            <td>
                                [#if agentConfig.level == 1]一级代理
                                [#elseif agentConfig.level == 2]二级代理
                                [#else ]普通玩家
                                [/#if]
                            </td>
                            <td>${agentConfig.valueR!'-'}</td>
                            <td>${agentConfig.highRatio!'-'}</td>
                            <td>${agentConfig.firstFact!'-'}</td>
                            <td>${agentConfig.secondFact!'-'}</td>
                            <td>${agentConfig.subCount!'-'}</td>
                            <td>${agentConfig.restScore!'-'}</td>
                            <td>
                                [#if agentConfig.level == 1 || agentConfig.level == 2]
                                <a la="2" title="点击修改" href="javascript:void(0)" data-id="${agentConfig.id}"
                                   data-f="${agentConfig.firstFact}" data-h="${agentConfig.highRatio}"
                                   data-r="${agentConfig.valueR}" data-t="${agentConfig.level}" onclick="edit(this)">
                                    <span class="label label-primary">修改</span>
                                </a>
                                <a href="[@spring.url '/agent_config/pagination/second?parentId=${agentConfig.id!}'/]"
                                   data-toggle="tooltip" data-placement="top" title="点击查看下级">
                                    <span class="label label-primary">查看下级</span>
                                </a>
                                [#else ]
                                    <a la="3" title="点击修改" href="javascript:void(0)" onclick="editPlay('${agentConfig.id}')">
                                        <span class="label label-primary">修改</span>
                                    </a>
                                [/#if]
                            </td>


                        </tr>

						[/#list]
					[/#if]
					</tbody>
				</table>
			</div>
            <!--修改代理信息-->
            <div class="tip" lay="2" style="display:none;">
                <div class="tipBox xiuigaiBox" style="height: 300px">
                    <p class="pTit tcenter f18 color33 mT10 mB15">代理配置修改</p>
                    <form action="/agent_config/edit" method="post"  id="edit">
                        <div class="tipCon ov">
                            <div class="fl piliang">
                                <p class="tcenter f16 color33 mB15" id="as"></p>
                                <div ><span >最高占比：</span><input type="number" id="highRatio" name="highRatio" /></div>
                                <div id="temp"><span >一级占比：</span><input type="number" id="factRatio" name="factRatio" /></div>
                            </div>
                            <div class="fr piliang">
                                <p class="tcenter f16 color33 mB15" id="le"></p>
                                <div><span ><i hid>站AA</i>R值：</span><input type="number" id="valueR" name="valueR" /></div>
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
            <!--修改普通玩家信息-->
            <div class="tip" lay="3" style="display:none;">
                <div class="tipBox xiuigaiBox" style="height: 300px">
                    <p class="pTit tcenter f18 color33 mT10 mB15">普通玩家配置修改</p>
                    <form action="/agent_config/edit" method="post"  id="playEdit">
                        <div class="tipCon ov">
                            <div class="fl piliang">
                                <p class="tcenter f16 color33 mB15" id="as"></p>
                                <div><span >一级实际占比：</span><input type="number" id="firstFact" name="firstFact" /></div>
                                <div><span ><i hid>&nbsp;</i>普通玩家R值：</span><input type="number" id="userR" name="userR" /></div>
                            </div>
                            <div class="fr piliang">
                                <p class="tcenter f16 color33 mB15" id="le"></p>
                                <div><span >二级实际占比：</span><input type="number" id="secondFact" name="secondFact" /></div>
                                <div><span ><i hid>啊啊</i>公司占比：</span><input type="number" id="companyFact" name="companyFact" /></div>
                            </div>
                        </div>
                        <input type="text" id="ratioId" name="ratioId" hidden="hidden">
                        <div class="BtnDiv mT20">
                            <span class="quxiao mLR15" >取消</span>
                            <span class="tijiao mLR15" onclick="submitPlay()">确认修改</span>
                        </div>
                    </form>
                </div>
            </div>
            <!--分页-->
		[#if pagination??]
			[@mc.customPagination '${url}?agentName=${command.agentName}&level=${command.level}&parentId=${command.parentId}' /]
		[/#if]
		</div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
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
            if( thisObj.attr("data-t") == '2'){
                $('#temp').show();
            } else {
                $('#temp').hide();
            }
            $('#agentId').val(thisObj.attr("data-id"));
            $('#highRatio').val(thisObj.attr("data-h"));
            $('#valueR').val(thisObj.attr("data-r"));
            $('#factRatio').val(thisObj.attr("data-f"));
        }

        function editPlay(id) {
            $.post(
                    "/ratio/info",
                    {'id': id},
                    function (result){
                        var json = JSON.parse(result);
                        if (json.status == 0) {
                            $('#firstFact').val(json.data.firstFact);
                            $('#userR').val(json.data.userR);
                            $('#secondFact').val(json.data.secondFact);
                            $('#ratioId').val(json.data.id);
                            $('#companyFact').val(json.data.companyFact);
                        } else if ( json.status == 1){
                            alert('配置超范围或错误');
                        } else {
                            alert("配置出错");
                        }
                    }
            );
        }

        function submitPlay() {
            $.post(
                    "/ratio/edit",
                    {'userR': $('#userR').val(),'firstFact': $('#firstFact').val(), 'secondFact': $('#secondFact').val(),'companyFact': $('#companyFact').val(),'id': $('#ratioId').val()},
                    function (result) {
                        console.log(result);
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

        function submit() {
            $.post(
                    "/agent_config/edit",
                    {'valueR': $('#valueR').val(),'highRatio': $('#highRatio').val(),'factRatio': $('#factRatio').val(), 'id': $('#agentId').val()},
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