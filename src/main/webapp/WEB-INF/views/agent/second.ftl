<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>下级管理</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20 ">
			<div class="phoneTop">
                <div inDiv>
                    <form action="/agent/pagination" id="form">
                        <label>
                            用户昵称：<input type="text" name="name" value="${command.name}"/>&nbsp;
                        </label>
                        <label>用户角色：
                            <select name="level">
                                <option value="0">全部</option>
                                <option value="3" [#if command.level == 3]  selected="selected" [/#if]>普通玩家</option>
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
							<th>用户昵称</th>
                            <th>用户ID</th>
                            <th>用户角色</th>
							<th>最高占比</th>
                            <th>本人占比</th>
                            [#if role == 1]
                                <th>下级占比</th>
                            [/#if]
                            <th>R值</th>
							<th>下级人数</th>
							<th>剩余分</th>
                            <th>操作</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination??]
						[#list pagination.data as agent]

                        <tr>
                            <td>${agent.name}</td>
                            <td>${agent.username}</td>
                            <td>
                                [#if agent.level == 1]一级代理
                                [#elseif agent.level == 2]二级代理
                                [#else ]普通玩家
                                [/#if]
                            </td>
                            <td>${agent.highRatio!'-'}</td>
                            [#if role == 1]
                                <td>${agent.firstRatio!'-'}</td>
                                <td>${agent.secondRatio!'-'}</td>
                            [#else ]
                                <td>${agent.secondRatio!'-'}</td>
                            [/#if]
                            <td>${agent.r!'-'}</td>
                            <td>${agent.subCount!'-'}</td>
                            <td>${agent.restScore!'-'}</td>
                            <td>
                                [#if role == 1]
                                    <a la="2" title="点击修改" href="javascript:void(0)" data-id="${agent.id}"
                                       data-f="${agent.firstRatio}" data-s="${agent.secondRatio}"
                                       data-r="${agent.r}" onclick="edit(this)">
                                        <span class="label label-primary">修改</span>
                                    </a>
                                [#else ]
                                    <a la="3" title="点击修改" href="javascript:void(0)" data-id="${agent.id}"
                                       data-f="${agent.secondRatio}" data-r="${agent.r}" onclick="editR(this)">
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
            <!--修改下级占比或R值-->
            <div class="tip" lay="2" id="reset" style="display: none;">
                <div class="tipBox addBox" style="height: 320px">
                    <p class="pTit tcenter f18 color33 mT10">对玩家占比修改</p>
                    <br/>
                    <form action="" method="post" id="resetPass">
                        <div class="tipCon">
                            <input type="text" name="id" id="id" value="" hidden="hidden">
                            <p><span ><i hid></i>本人占比：</span><input type="text" id="firstRatio" /></p>
                            <p><span ><i hid></i>下级占比：</span><input type="text" id="secondRatio" /></p>
                            <p><span ><i hid>啊AA</i>R值：</span><input type="text" id="R" /></p>
                        </div>
                        <div class="BtnDiv mT20">
                            <span class="quxiao mLR15" type="button">取消</span>
                            <span class="tijiao mLR15" type="submit" onclick="submit()">修改</span>
                        </div>
                    </form>
                </div>

            </div>

            <!--修改普通玩家-->
            <div class="tip" lay="3" id="editR" style="display: none;">
                <div class="tipBox addBox">
                    <p class="pTit tcenter f18 color33 mT10">对玩家占比修改</p>
                    <br/>
                    <form action="" method="post" id="resetPass">
                        <div class="tipCon">
                            <input type="text" id="nid" value="" hidden="hidden">
                            <p><span ><i hid></i>本人占比：</span><input type="text" id="nFact" /></p>
                            <p><span ><i hid>啊啊1</i>R值：</span><input type="text" id="nR" /></p>
                        </div>
                        <div class="BtnDiv mT20">
                            <span class="quxiao mLR15" type="button">取消</span>
                            <span class="tijiao mLR15" type="submit" onclick="submitN()">修改</span>
                        </div>
                    </form>
                </div>

            </div>

            <!--分页-->
		[#if pagination??]
			[@mc.customPagination '/agent/pagination?name=${command.name}&level=${command.level}' /]
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
            $('#id').val(thisObj.attr("data-id"));
			$('#firstRatio').val(thisObj.attr("data-f"));
            $('#secondRatio').val(thisObj.attr("data-s"));
            $('#R').val(thisObj.attr("data-r"));
        }

        function editR(obj) {
            var thisObj = $(obj);
            $('#nid').val(thisObj.attr("data-id"));
            $('#nR').val(thisObj.attr("data-r"));
            $('#nFact').val(thisObj.attr("data-f"));
        }
        //异步提交
        function submit() {
		    if($('#firstRatio').val().length < 1 || $('#secondRatio').val().length < 1 || $('#R').val().length < 1 || $('#firstRatio').val() < 0 || $('#secondRatio').val() < 0 || $('#R').val() < 0){
                alert('占比不能为空，最低为0');
                return;
            }

            $.post(
                    "/agent/edit/first_ratio",
                    {'firstRatio': $('#firstRatio').val(),'secondRatio': $('#secondRatio').val(),  'R': $('#R').val(), 'id': $('#id').val()},
                    function (result) {
                        alert(result);
                    }
            );
        }

        //异步提交
        function submitN() {
            if($('#nR').val().length < 1 || $('#nFact').val().length < 1 ||  $('#nR').val()<0 ||  $('#nFact').val()<0){
                alert('不能填写为空，且最低为0');
                return;
            }

            $.post(
                    "/agent/edit/second_ratio",
                    {'R': $('#nR').val(), 'nFact': $('#nFact').val(), 'id': $('#nid').val()},
                    function (result) {
                        alert(result);
                    }
            );
        }

	</script>

</html>