<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body style="background: #eff1f3;">
		<!--游戏下注-->
		<div class="tableW GameBetting divbox TongjiTab">
			<div class="bettingL fl">
				<div class="tableDiv">
					<table class="layerTab">
						<thead>
							<tr>
								<th id="boot">${gameStatus.xNum}靴${gameStatus.jNum}局</th>
								<th>闲</th>
								<th>庄</th>
								<th>闲对</th>
								<th>庄对</th>
								<th>和</th>
								<th id="boot1">${gameStatus.xNum}靴${gameStatus.jNum}局</th>
								<th>本局得分</th>
								<th>剩余分</th>
								<th>初始分</th>
								<th>操作</th>
							</tr>
						</thead>
                        <tbody id="tb">
						[#if bets??]
							[#list bets as bet]

                            <tr id="${bet.username}">
                                <td>${bet.name}</td>
                                <td>${bet.score[0]}</td>
                                <td>${bet.score[1]}</td>
                                <td>${bet.score[2]}</td>
                                <td>${bet.score[3]}</td>
                                <td>${bet.score[4]}</td>
                                <td>${bet.name}</td>
                                <td>${bet.score[5]}</td>
                                <td>${bet.score[6]}</td>
                                <td>${bet.score[7]}</td>
                                <td class="xiugai" la="1" onclick="editBet(${bet.username})">修改</td>
                            </tr>

							[/#list]

						[/#if]
                        <tr id="total">
                            <td>总计:${total[0]}</td>
                            <td>${total[1]}</td>
                            <td>${total[2]}</td>
                            <td>${total[3]}</td>
                            <td>${total[4]}</td>
                            <td>${total[5]}</td>
                            <td>总计:${total[0]}</td>
                            <td>${total[6]}</td>
                            <td>${total[7]}</td>
                            <td>${total[8]}</td>
                            <td></td>
                        </tr>
                        </tbody>
					</table>

				</div>

				<!--分页-->
				<div class="pageWrap mT15">



					<div>
						<a><i class="iconfont icon-icon1"></i></a>
						<a class="on">1</a>
						<a>2</a>
						<a></a>
						<a>4</a>
						<a>10</a>
						<a><i class="iconfont icon-arrow-sl"></i> </a>
					</div>

                    <div style="float: left">当前游戏状态：
                        [#if gameStatus.status == 0] 未开始
                        [#elseif gameStatus.status == 1] 投注中
                        [#elseif gameStatus.status == 2] 停止投注
                        [#elseif gameStatus.status == 3] 已开奖
                        [#else ]未知
                        [/#if]
                    </div>
				</div>

			</div>
			<div class="bettingR">
				<p class="operatingBtn btnP" onclick="startOrStop()">开始/停止统计</p>
                <p class="startBet btnP" onclick="startBet()">开始下注</p>
				<div class="paiInfo">
                    <p class="f016dff"><span>闲：</span><span id="x">${playerScore}</span></p>
					<p class="fa3033"><span>庄：</span><span id="z">${bankScore}</span></p>
					<p class="fa3033"><span>庄对：</span><span id="zd">0</span></p>
					<p class="f016dff"><span>闲对：</span><span id="xd">0</span></p>
					<p class="f00e288"><span>和：</span><span id="h">0</span></p>
				</div>
				[#--<p class="TableShotsBtn btnP">台面截图</p>--]
				<br/>
				<p class="changexj ov"><label class="talignR"><input id="boot3" type="text" value="${gameStatus.xNum}" />靴</label> <label class="talignL"><input id="boot4" type="text" value="${gameStatus.jNum}" />局	</label></p>
				<p class="ModifyBoots btnP" onclick="editXJ()">修改靴局</p>
				<p class="toNextBtn btnP" onclick="nextBoot()">进入下一靴</p>
                <p class="endBet btnP" onclick="stopBet()">停止下注</p>
                <br/>
				<p class="BettingShotsBtn btnP" onclick="betTable()">投注表截图</p>
				[#--<p class="xiuBettingShotsBtn btnP">投注表修改</p>--]
                <br/>
				<div class="changeCheck changexj ov">
					<label class="fa3033"><input type="checkbox" value="0" name="lottery"/>庄</label>
					<label class="f016dff"><input type="checkbox" value="1" name="lottery"/>闲</label>
					<label class="fa3033"><input type="checkbox" value="2" name="lottery"/>庄对</label>
					<label class="f016dff"><input type="checkbox" value="3" name="lottery"/>闲对</label>
					<label class="f00e288"><input type="checkbox" value="4" name="lottery"/>和</label>
				</div>
				<p class="LotterySettlementBtn btnP" onclick="lottery()">开奖结算</p>
				<p class="ScoreBtn btnP" onclick="scoreTable()">分数表截图</p>
				<p class="IntoNextBtn btnP" onclick="nextGame()">进入下一局</p>
			</div>
		</div>
        <!--修改下注-->
        <div class="tip" lay="1" style="display: none;">
            <div class="tipBox addBox" style="height: 400px;margin-top: -280px">
                <p class="pTit tcenter f18 color33 mT10">玩家下注修改</p>
                    <div class="tipCon">
                        <input type="text" id="username" hidden="hidden">
                        <p><span ><i hid>位啊</i>闲：</span><input type="text" id="bx" /></p>
                        <p><span ><i hid>位啊</i>庄：</span><input type="text" id="bz" /></p>
                        <p><span ><i hid>位</i>闲对：</span><input type="text" id="bxd" /></p>
                        <p><span ><i hid>位</i>庄对：</span><input type="text" id="bzd" /></p>
                        <p><span ><i hid>位啊</i>和：</span><input type="text" id="bh" /></p>
                    </div>
                    <div class="BtnDiv mT20">
                        <span class="quxiao mLR15" type="button">取消</span>
                        <span class="tijiao mLR15" type="submit" onclick="betEdit()">确认</span>
                    </div>
            </div>
        </div>
        <!--开奖结算-->
		<div class="tip" style="display:none;" id="lottery">
			<div class="tipBox kaijiangBox" style="height: 380px">
				<p class="pTit tcenter f18 color33 mT10">开奖</p>
				<div class="tipCon">
					<p class="ov">
						<span class="zhuangRed" id="tz" style="display:none;">庄</span>
                        <span class="xianLan" id="tx" style="display:none;">闲</span>
						<span class="zhuangRed" id="tzd" style="display:none;">庄对</span>
						<span class="xianLan" id="txd" style="display:none;">闲对</span>
                        <span class="he" id="th" style="display:none;">和</span>
					</p>
				</div>
                <div class="BtnDiv mT20">
                    <form id="upload" method="post" enctype="multipart/form-data" action="/game_bet/lottery">
                        <p>开奖截图:<span class="tijiao mLR15"><input type="file" name="lotteryImg" id="lotteryImg"></span></p><br/>
                        <p>路单截图:<span class="tijiao mLR15"><input type="file" name="roadImg" id="roadImg"></span></p><br/>
                        <p>现场截图:<span class="tijiao mLR15"><input type="file" name="sceneImg" id="sceneImg"></span></p>
                        <input type="text" id="lotteryVal" name="lotteryVal" hidden="hidden">
                    </form>

                </div>
				<div class="BtnDiv mT20">
					<span class="tijiao mLR15" onclick="submitLottery()">开奖</span>
                    <span class="quxiao mLR15" onclick="$('#lottery').hide()">关闭</span>
				</div>
			</div>
		</div>
	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script src="/resources/main/js/home.js"></script>
	<script type="text/javascript">

        $("[la]").click(function(){
            $("[lay]").css("display","none");
            console.log(12)
            $("[lay="+$(this).attr("la")+"]").css("display","block");
        })
        $(".quxiao").click(function(){
            $(".tip").css("display","none")
        })

		var ws;

		function startOrStop() {
		    //1菲律宾  0 启停
            if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
            }else {
                ws.send("1,0");
            }

        }

        function betTable() {
		    //1菲律宾  1投注表
			if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return
			}else {
                ws.send("1,1");
			}

        }

        function scoreTable() {
            if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
            }else {
                //1菲律宾  2分数表
                ws.send("1,2");
            }

        }

        //开始投注
        function startBet() {
            if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
            }else {
                ws.send("1,8");
            }
        }

        //停止投注
        function stopBet() {
            if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
            }else {
                ws.send("1,8");
            }
        }



        //开奖
        function lottery() {
		    if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
			}else {
                var l = '';
                var sum = 0;
                $("input:checkbox[name='lottery']:checked").each(function() {
                    l += ','+$(this).val();
                    sum++;
                });
                if (l === ''){
                    alert('请勾选开奖结果');
                    return;
				}
				var a=0;
				if(l.indexOf("0") != -1){
                    a++;
                }
                if(l.indexOf("1") != -1){
                    a++;
                }
                if(l.indexOf("4") != -1){
                    a++;
                }
                if(a>1 || a<1){
                    alert('庄闲和只能开奖一个');
                    return;
                }
				$('#lottery').show();
                //宽度
                var width = parseFloat($('.tipCon').width())/sum;

                $("input:checkbox[name='lottery']:checked").each(function() {
                    if($(this).val() == 0){
                        $('#tz').css("width",width);
                        $('#tz').show();
                    } else if($(this).val() == 1){
                        $('#tx').css("width",width);
                        $('#tx').show();
                    }
                    else if($(this).val() == 2){
                        $('#tzd').css("width",width);
                        $('#tzd').show();
                    }
                    else if($(this).val() == 3){
                        $('#txd').css("width",width);
                        $('#txd').show();
                    }
                    else if($(this).val() == 4){
                        $('#th').css("width",width);
                        $('#th').show();
                    }
                });
                $('#lotteryVal').val("1,3"+l);
			}
            //1菲律宾  3开奖
           // ws.send("1,3"+l);
        }
        //提交开奖
        function submitLottery() {
		    //图片不能为空
            if($('#lotteryImg').val() == '' || $('#roadImg').val() == ''){
                alert('开奖图片不能为空');
            }else {
                $('#upload').submit();
            }
        }
        //下一鞋
        function nextBoot() {
            if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
            }else {
                ws.send("1,4");
            }


        }
        //下一局
        function nextGame() {
            if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
            }else {
                ws.send("1,5");
            }

        }

        //修改鞋局
        function editXJ() {
            if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
            }else {
                var x = $('#boot3').val();
                var j = $('#boot4').val();
                ws.send("1,6,"+x+","+j);
            }

        }

        //修改下注
        function editBet(username) {
            $('#username').val(username);
            var obj = $(document.getElementById(username)).children();
            $('#bx').val(obj[1].innerText);
            $('#bz').val(obj[2].innerText);
            $('#bxd').val(obj[3].innerText);
            $('#bzd').val(obj[4].innerText);
            $('#bh').val(obj[5].innerText);
        }

        //确认修改
        function betEdit() {
            if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
            }else {
                var s = $('#username').val()+','+$('#bx').val()+','+$('#bz').val()+','+$('#bxd').val()+','+$('#bzd').val()+','+$('#bh').val();
                ws.send('1,7,'+s);
            }

        }

		$(".layerTab tbody tr,.pageWrap a").click(function() {
			$(this).addClass("on").siblings().removeClass("on");
		})

        document.onreadystatechange = function () {
            if (document.readyState == "complete") {

//                var webSocket = new WebSocket('ws://micro.win00853.com:30020/hall/philippines');
                var webSocket = new WebSocket('ws://10.0.0.200:10012/hall/philippines');

                ws = webSocket;

                var opened = false; //是否连接上服务器

                webSocket.onerror = function (event) {
                    opened = false;
                    onError(event);
                };

                webSocket.onopen = function (event) {
                    console.log("已连接webService");
                    opened = true;
                };

                webSocket.onclose = function (event) {

                    opened = false;
                };

                webSocket.onmessage = function (event) {

                    onMessage(event);
                };
                function onError(event) {
                    //连接错误
                    // layer.msg("服务器连接失败,尝试重新连接......", { time: 3000, icon: 5 });
                    opened = false;
                }
                function onMessage(event) {
                    //data为消息内容，是一个json字符串
                    var message = eval('('+event.data+')');
                    console.log(message);
                    if(message.username != null){
                        var parent = document.getElementById(message.username);
                        console.log(parent);
                        if(parent == null){

							//页面无数据，则添加数据
							var html = '<tr id="'+message.username+'">'
										    +'<td>'+message.name+'</td>'
                                    		+'<td>'+message.score[0]+'</td>'
                                    		+'<td>'+message.score[1]+'</td>'
                                    		+'<td>'+message.score[2]+'</td>'
                                    		+'<td>'+message.score[3]+'</td>'
                                    		+'<td>'+message.score[4]+'</td>'
                                    		+'<td>'+message.name+'</td>'
                                    		+'<td>'+message.score[5]+'</td>'
                                    		+'<td>'+message.score[6]+'</td>'
                                    		+'<td>'+message.score[7]+'</td>'
                                    		+'<td class="xiugai">修改</td>'
                                    	+'</tr>';
							//追加到合计之前
                            $('#total').before(html);
                            var total1 = document.getElementById("total");
                            total1.children[0].innerText = '总计:'+message.total[0];
                            for(var i=1; i<6; i++){
                                total1.children[i].innerText = message.total[i];
                            }
                            total1.children[6].innerText = '总计:'+message.total[0];
                            total1.children[7].innerText = message.total[6];
                            total1.children[8].innerText = message.total[7];
                            total1.children[9].innerText = message.total[8];

                            if(message.total[1] >= message.total[2]){
                                $('#x').text(message.total[1] - message.total[2]);
                                $('#z').text(0);
                            }else {
                                $('#z').text(message.total[2] - message.total[1]);
                                $('#x').text(0);
                            }

						}else {
                            //页面已存在数据，则更新页面数据

                            for(var i=1; i<6; i++){
                                parent.children[i].innerText = message.score[i-1];
                            }
                            parent.children[7].innerText=message.score[5];
                            parent.children[8].innerText=message.score[6];
                            parent.children[9].innerText=message.score[7];

                            var total = document.getElementById("total");
                            total.children[0].innerText = '总计:'+message.total[0];
                            for(var i=1; i<6; i++){
								total.children[i].innerText = message.total[i];
							}
                            total.children[6].innerText = '总计:'+message.total[0];
                            total.children[7].innerText = message.total[6];
                            total.children[8].innerText = message.total[7];
                            total.children[9].innerText = message.total[8];

                            if(message.total[1] >= message.total[2]){
                                $('#x').text(message.total[1] - message.total[2]);
                                $('#z').text(0);
                            }else {
                                $('#z').text(message.total[2] - message.total[1]);
                                $('#x').text(0);
                            }
						}
					}else {
                        console.log(message);
                        if(message.type == 2){
                            alert(message.text);
                            window.location.reload();
						} else if(message.type == 3 && message.suc == 0){
                            window.location.reload();
                        } else if(message.type == 7){
                            $(".tip").css("display","none");
                            alert(message.text);
                        }
						else {
                            alert(message.text);
                            if(message.text == '已停止游戏'){
                                window.location.reload();
                            }
						}
					}
                }
            }
        }
	</script>

</html>