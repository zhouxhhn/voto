<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>机器人管理</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body>
		<div class="tableW PhoneBetting divbox pad20 ">
			<div class="phoneTop">
                <div class="chaxun mLR5" style="background: #29c36d;" la="1">新增机器人</div>
                <div inDiv>
                    <form action="/robot/pagination" id="form">
                        <label class="mR10">
                            昵称：<input type="text" name="name" value="${command.name!}"/>&nbsp;&nbsp;
                        </label>
                        <label>大厅：
                            <select name="hallType">
							[#assign hallType = (command.hallType!)?default(0) /]
                                <option value="0" [@mc.selected hallType "0" /]>全部</option>
                                <option value="1" [@mc.selected hallType "1" /]>菲律宾厅</option>
                                <option value="2" [@mc.selected hallType "2" /]>越南厅</option>
                                <option value="3" [@mc.selected hallType "3" /]>澳门厅</option>
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
							<th>昵称</th>
							<th>头像编号</th>
							<th>创建时间</th>
							<th>剩余分</th>
							<th>初始分</th>
                            <th>所属大厅</th>
							<th>庄闲下注范围</th>
                            <th>庄闲下注概率</th>
                            <th>三宝下注范围</th>
                            <th>三宝下注概率</th>
                            <th>下注次数</th>
                            <th>下限分数</th>
                            <th>补分分数</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					[#if pagination.data??]
						[#list pagination.data as robot ]

						<tr>
							<td>${robot.name!}</td>
							<td>${robot.head}</td>
							<td>${robot.createDate}</td>
							<td>${robot.score}</td>
                            <td>${robot.primeScore}</td>
                            <td>
                                [#if robot.hallType == 1]菲律宾厅
                                    [#elseif robot.hallType == 2]越南厅
                                [#else ]澳门厅
                                [/#if ]
                            </td>
                            <td>${robot.bankPlayMin}~${robot.bankPlayMax}</td>
                            <td>${robot.bankPlayRatio}%</td>
                            <td>${robot.triratnaMin}~${robot.triratnaMax}</td>
                            <td>${robot.triratnaRatio}%</td>
                            <td>${robot.frequency}</td>
                            <td>${robot.scoreMin}</td>
                            <td>${robot.addScore}</td>
                            <td>
                                <a href="[@spring.url '/robot/delete?id=${robot.id!}'/]"
                                   data-toggle="tooltip" data-placement="top" title="点击删除机器人">
                                    <span class="label label-danger">删除</span>
                                </a>
                            </td>
						</tr>
						[/#list ]
					[/#if ]

					</tbody>
				</table>
			</div>

			<!--分页-->
            <div>
				[#if pagination??]
						[@mc.customPagination '/robot/pagination?name=${command.name}&hallType=${command.hallType}'/]
				[/#if]
            </div>


		</div>

        <!--新增玩家-->
        <div class="tip" lay="1" style="display:none ;">
            <div class="tipBox xiuigaiBox xiu_box" style="height: 650px !important;">
                <p class="pTit tcenter f18 color33 mT10 mB15">添加机器人</p>
                <form id="add" action="/robot/create" method="post" >
                    <div class="tipCon ov b_bor">
                        <div class="fl piliang">
                            <div><span><i hid>位啊啊</i>机器人昵称：</span><input type="text" name="name" class="form-control" id="name"></div>
                            <div><span><i hid>位位位啊</i>初始分数：</span><input type="number"  name="score" class="form-control" id="score"/></div>
                        </div>
                        <div class="fr piliang">
                            <div><span >机器人头像：</span>
                                <select name="head" style="width: 142px;height: 32px">
                                    <option value="1" [#if systemConfig.head == 1]selected[/#if]>1号头像</option>
                                    <option value="2" [#if systemConfig.head == 2]selected[/#if]>2号头像</option>
                                    <option value="3" [#if systemConfig.head == 3]selected[/#if]>3号头像</option>
                                    <option value="4" [#if systemConfig.head == 4]selected[/#if]>4号头像</option>
                                    <option value="5" [#if systemConfig.head == 5]selected[/#if]>5号头像</option>
                                    <option value="6" [#if systemConfig.head == 6]selected[/#if]>6号头像</option>
                                </select>
                            </div>
                            <div><span ><i hid>位</i>所属大厅：</span>
                                <select name="hallType" style="width: 142px;height: 32px">
                                    <option value="1" [#if systemConfig.hallType == 1]selected[/#if]>菲律宾厅</option>
                                    <option value="2" [#if systemConfig.hallType == 2]selected[/#if]>越南厅</option>
                                    <option value="3" [#if systemConfig.hallType == 3]selected[/#if]>澳门厅</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="tipCon ov b_bor">
                        <div class="fl piliang">
                            <div><span >庄闲最小下注金额：</span><input type="number" name="bankPlayMin" class="form-control" id="bankPlayMin"></div>
                            <div><span><i hid>a</i>庄闲下注概率(%)：</span><input type="number"  name="bankPlayRatio" class="form-control" id="bankPlayRatio"/></div>
                        </div>
                        <div class="fr piliang">
                            <div><span >庄闲最大下注金额：</span><input type="number" name="bankPlayMax" class="form-control" id="bankPlayMax"></div>
                        </div>
                    </div>

                    <div class="tipCon ov b_bor">
                        <div class="fl piliang">
                            <div><span >三宝最小下注金额：</span><input type="number" name="triratnaMin" class="form-control" id="triratnaMin"></div>
                            <div><span><i hid>a</i>三宝下注概率(%)：</span><input type="number"  name="triratnaRatio" class="form-control" id="triratnaRatio"/></div>
                        </div>
                        <div class="fr piliang">
                            <div><span >三宝最大下注金额：</span><input type="number" name="triratnaMax" class="form-control" id="triratnaMax"></div>
                            <div><span ><i hid>啊</i>机器人下注次数：</span><input type="number" name="frequency" class="form-control" id="frequency"></div>

                        </div>
                    </div>

                    <div class="tipCon mT20 ov">
                        <div class="fl piliang">
                            <div><span><i hid>啊</i>机器人下限分数：</span><input type="number" name="scoreMin"  id="scoreMin"/></div>

                        </div>
                        <div class="fr piliang">
                            <div><span>机器人补分分数：</span><input type="number" name="addScore"  id="addScore"/></div>
                        </div>
                    </div>

                    <div class="BtnDiv mT20">
                        <span class="yanQing mLR15_"   onclick="addRobot()">添加</span>
                        <span class="quxiao mLR15_">取消</span>

                    </div>
                </form>
            </div>

        </div>


	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script type="text/javascript">
		$(".layerTab tbody tr,.pageWrap a").click(function() {
			$(this).addClass("on").siblings().removeClass("on");
		});

        $("[la]").click(function(){
            $("[lay]").css("display","none");
            $("[lay="+$(this).attr("la")+"]").css("display","block");
        });

        $(".quxiao").click(function(){
            $(".tip").css("display","none")
        });

        function addRobot() {
            if($('#name').val().length<1){
                alert("昵称不能为空");
                return;
            }
            //检查昵称是否存在
            $.post(
                    "/robot/checkName",
                    {'name': $('#name').val()},
                    function (result) {
                        if(result == 1){
                            alert("和玩家昵称重复");
                            return;
                        }else if(result == 2){
                            alert("和其他机器人昵称重复");
                            return;
                        }else {
                            if($('#score').val().length<1){
                                alert("初始分不能为空");
                                return;
                            }
                            var bankPlayMin = Number($('#bankPlayMin').val());
                            if(bankPlayMin.length<1){
                                alert("庄闲最小下注不能为空");
                                return;
                            }
                            var bankPlayMax = Number($('#bankPlayMax').val());
                            if(bankPlayMax.length<1){
                                alert("庄闲最大下注不能为空");
                                return;
                            }
                            if(bankPlayMin > bankPlayMax){
                                alert("庄闲最小下注不能比最大下注高");
                                return;
                            }
                            var bankPlayRatio = Number($('#bankPlayRatio').val());
                            if(bankPlayRatio.length<1){
                                alert("庄闲下注概率不能为空");
                                return;
                            }

                            var triratnaMin = Number($('#triratnaMin').val());
                            if(triratnaMin.length<1){
                                alert("三宝最小下注不能为空");
                                return;
                            }
                            var triratnaMax = Number($('#triratnaMax').val());
                            if(triratnaMax.length<1){
                                alert("三宝最大下注不能为空");
                                return;
                            }
                            if(triratnaMin > triratnaMax){
                                alert("三宝最小下注不能比最大下注高");
                                return;
                            }
                            var triratnaRatio = Number($('#triratnaRatio').val());
                            if(triratnaRatio.length<1){
                                alert("三宝下注概率不能为空");
                                return;
                            }
                            var frequency = Number($('#frequency').val());
                            if(frequency.length<1){
                                alert("下注次数不能为空");
                                return;
                            }
                            if(frequency < 1){
                                alert("下注次数必须大于0");
                                return;
                            }

                            if($('#scoreMin').val().length<1){
                                alert("分数下限不能为空");
                                return;
                            }
                            if($('#addScore').val().length<1){
                                alert("补分分数不能为空");
                                return;
                            }
                            if((bankPlayRatio+triratnaRatio)>100){
                                alert("概率之和不能大于100");
                                return;
                            }
                            //提交
                            $('#add').submit();
                        }
                    }
            );


        }

	</script>

</html>