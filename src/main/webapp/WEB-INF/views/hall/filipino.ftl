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


                    <div style="float: left" id="statusText"> 当前游戏状态：
                        [#if gameStatus.status == -1] 已停止游戏
                        [#elseif gameStatus.status == 0] 已启动游戏
                        [#elseif gameStatus.status == 1] 投注中
                        [#elseif gameStatus.status == 2] 停止投注
                        [#elseif gameStatus.status == 3] 已开奖
                        [#elseif gameStatus.status == 4] 已推送分数表
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
                    <p class="fa3033" id="mesa_1" style="visibility: hidden"><span>庄对：</span><span id="zd">${bankPirScore}</span></p>
                    <p class="f016dff" id="mesa_2" style="visibility: hidden"><span>闲对：</span><span id="xd">${playPirScore}</span></p>
                    <p class="f00e288" id="mesa_3" style="visibility: hidden"><span>和：</span><span id="h">${drawScore}</span></p>

				</div>
                <p class="toNextBtn btnP" onclick="triratna()">隐藏/显示三宝</p>
				<p class="changexj ov"><label class="talignR"><input id="boot3" type="text" value="${gameStatus.xNum}" />靴</label> <label class="talignL"><input id="boot4" type="text" value="${gameStatus.jNum}" />局	</label></p>
				<p class="ModifyBoots btnP" onclick="editXJ()">修改靴局</p>
				<p class="toNextBtn btnP" onclick="nextBoot()">进入下一靴</p>
                <p class="IntoNextBtn btnP" onclick="haveOr()">提示有无</p>
                <p class="endBet btnP" onclick="stopBet()">停止下注</p>
				<p class="BettingShotsBtn btnP" onclick="betTable()">投注表截图</p>
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
                <br/>
                <p class="endBet btnP" onclick="reOpen()">重开本局</p>
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
			<div class="tipBox kaijiangBox" style="height: 720px;margin-top: -380px">
				<p class="pTit tcenter f18 color33 mT10">开奖</p>
				<div class="tipCon" id="ld">
					<p class="ov">
						<span class="zhuangRed" id="tz" style="display:none;">庄</span>
                        <span class="xianLan" id="tx" style="display:none;">闲</span>
						<span class="zhuangRed" id="tzd" style="display:none;">庄对</span>
						<span class="xianLan" id="txd" style="display:none;">闲对</span>
                        <span class="he" id="th" style="display:none;">和</span>
					</p>
				</div>
                [#--<div class="BtnDiv mT20">--]
                    [#--<form id="upload" method="post" enctype="multipart/form-data" action="/game_bet/lottery">--]
                        [#--<p>开奖截图:<span class="tijiao mLR15"><input type="file" name="lotteryImg" id="lotteryImg"></span></p><br/>--]
                        [#--<p>路单截图:<span class="tijiao mLR15"><input type="file" name="roadImg" id="roadImg"></span></p><br/>--]
                        [#--<p>现场截图:<span class="tijiao mLR15"><input type="file" name="sceneImg" id="sceneImg"></span></p>--]
                        [#--<input type="text" id="lotteryVal" name="lotteryVal" hidden="hidden">--]
                    [#--</form>--]

                [#--</div>--]
                <input type="text" id="lotteryVal" name="lotteryVal" hidden="hidden">
                <p>开奖截图：<button id="clearLottery" style="float:right" onclick="clearLottery()">点此清除图片</button></p>
                <div class="imgDiv mT10 mB10" id="lotteryImg" contenteditable="true">

                </div>
                <p>路单截图：<button id="clearRoad" style="float:right" onclick="clearRoad()">点此清除图片</button></p>
                <div class="imgDiv mT10 mB10" id="roadImg" contenteditable="true">

                </div
                <p>现场截图：<button id="clearScene" style="float:right" onclick="clearScene()">点此清除图片</button></p>
                <div class="imgDiv mT10 mB10" id="sceneImg" contenteditable="true">

                </div>
				<div class="BtnDiv mT10">
                    <span class="tijiao mLR15" id="ctl" onclick="lotterySubmit($('#temp').val())">开奖</span>
                    <span class="quxiao mLR15" onclick="lotteryHide()">关闭</span>
				</div>
                <input type="hidden" id="temp">
			</div>
		</div>
        <!--重新开奖-->

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script type="text/javascript">

        var lotteryBlob;
        var roadBlob;
        var sceneBlob;

        //绑定开奖截图粘贴事件
        document.querySelector('#lotteryImg').addEventListener('paste',function(e){
            lotteryBlob = null;
            var cbd = e.clipboardData;
            var ua = window.navigator.userAgent;
            // 如果是 Safari 直接 return
            if ( !(e.clipboardData && e.clipboardData.items) ) {
                return ;
            }
            // Mac平台下Chrome49版本以下 复制Finder中的文件的Bug Hack掉
            if(cbd.items && cbd.items.length === 2 && cbd.items[0].kind === "string" && cbd.items[1].kind === "file" &&
                    cbd.types && cbd.types.length === 2 && cbd.types[0] === "text/plain" && cbd.types[1] === "Files" &&
                    ua.match(/Macintosh/i) && Number(ua.match(/Chrome\/(\d{2})/i)[1]) < 49){
                return;
            }
            for(var i = 0; i < cbd.items.length; i++) {
                var item = cbd.items[i];
                if(item.kind == "file"){
                    lotteryBlob = item.getAsFile();
                    if (lotteryBlob.size === 0) {
                        return;
                    }
                    var reader = new FileReader();
                    var imgs = new Image();
                    imgs.file = lotteryBlob;
                    reader.onload = (function(aImg) {
                        return function(e) {
                            aImg.src = e.target.result;
                        };
                    })(imgs);
                    reader.readAsDataURL(lotteryBlob);
                    document.querySelector('#lotteryImg').appendChild(imgs);
                }
            }
        }, false);

        //绑定路单粘贴事件
        document.querySelector('#roadImg').addEventListener('paste',function(e){
            roadBlob = null;
            var cbd = e.clipboardData;
            var ua = window.navigator.userAgent;
            // 如果是 Safari 直接 return
            if ( !(e.clipboardData && e.clipboardData.items) ) {
                return ;
            }
            // Mac平台下Chrome49版本以下 复制Finder中的文件的Bug Hack掉
            if(cbd.items && cbd.items.length === 2 && cbd.items[0].kind === "string" && cbd.items[1].kind === "file" &&
                    cbd.types && cbd.types.length === 2 && cbd.types[0] === "text/plain" && cbd.types[1] === "Files" &&
                    ua.match(/Macintosh/i) && Number(ua.match(/Chrome\/(\d{2})/i)[1]) < 49){
                return;
            }
            for(var i = 0; i < cbd.items.length; i++) {
                var item = cbd.items[i];
                if(item.kind == "file"){
                    roadBlob = item.getAsFile();
                    if (roadBlob.size === 0) {
                        return;
                    }
                    var reader = new FileReader();
                    var imgs = new Image();
                    imgs.file = roadBlob;
                    reader.onload = (function(aImg) {
                        return function(e) {
                            aImg.src = e.target.result;
                        };
                    })(imgs);
                    reader.readAsDataURL(roadBlob);
                    document.querySelector('#roadImg').appendChild(imgs);
                }
            }
        }, false);

        //绑定现场截图粘贴事件
        document.querySelector('#sceneImg').addEventListener('paste',function(e){
            sceneBlob = null;
            var cbd = e.clipboardData;
            var ua = window.navigator.userAgent;
            // 如果是 Safari 直接 return
            if ( !(e.clipboardData && e.clipboardData.items) ) {
                return ;
            }
            // Mac平台下Chrome49版本以下 复制Finder中的文件的Bug Hack掉
            if(cbd.items && cbd.items.length === 2 && cbd.items[0].kind === "string" && cbd.items[1].kind === "file" &&
                    cbd.types && cbd.types.length === 2 && cbd.types[0] === "text/plain" && cbd.types[1] === "Files" &&
                    ua.match(/Macintosh/i) && Number(ua.match(/Chrome\/(\d{2})/i)[1]) < 49){
                return;
            }
            for(var i = 0; i < cbd.items.length; i++) {
                var item = cbd.items[i];
                if(item.kind == "file"){
                    sceneBlob = item.getAsFile();
                    if (sceneBlob.size === 0) {
                        return;
                    }
                    var reader = new FileReader();
                    var imgs = new Image();
                    imgs.file = sceneBlob;
                    reader.onload = (function(aImg) {
                        return function(e) {
                            aImg.src = e.target.result;
                        };
                    })(imgs);
                    reader.readAsDataURL(sceneBlob);
                    document.querySelector('#sceneImg').appendChild(imgs);
                }
            }
        }, false);

        function lotterySubmit(type) {

                var data = new FormData();
                var strins = $('#lotteryVal').val();
                data.append('fileLotty', lotteryBlob);
                data.append('fileRoad', roadBlob);
                data.append('fileScene', sceneBlob);
                data.append('lotteryVal',strins);
                data.append('type',type);
                $.ajax({
                    url: '/game_bet/lotteryUpload',
                    type: 'POST',
                    cache: false,
                    data: data,
                    processData: false,
                    contentType: false,
                    success: function (res) {
                        var obj = JSON.parse(res);
                        if (obj.success == 0) {
                            alert("开奖成功");
                            window.location.reload();
                        } else if (obj.success == 1) {
                            alert("开奖信息不能为空");
                            $('#lottery').hide();
                        } else if (obj.success == 2) {
                            alert("开奖图片至少为2张");
                        } else if (obj.success == 3) {
                            alert("游戏未开始，不能开奖");
                            $('#lottery').hide();
                        } else if (obj.success == 4) {
                            alert("当前游戏状态不能开奖");
                            $('#lottery').hide();
                        } else if (obj.success == 5) {
                            alert("已开过奖");
                            $('#lottery').hide();
                        } else if (obj.success == 6) {
                            alert("当前游戏状态不能重开本局");
                            $('#lottery').hide();
                        } else if (obj.success == 7) {
                            alert("无可操作数据");
                            $('#lottery').hide();
                        }
                        else if (obj.success == 8) {
                            alert("重开本局成功");
                            window.location.reload();
                        } else {
                            alert("无可操作");
                        }

                    }, error: function () {
                        alert("请重试或刷新页面");
                    }
                })

        }
        //清除开奖截图
        function clearLottery() {
            lotteryBlob = null;
            clearChild("lotteryImg");
        }
        //清除路单截图
        function clearRoad() {
            roadBlob = null;
            clearChild("roadImg");
        }
        //清除现场截图
        function clearScene() {
            sceneBlob =  null;
            clearChild("sceneImg");
        }
        //删除子元素
        function clearChild(id) {
            var div = document.getElementById(id);
            while(div.hasChildNodes()) //当div下还存在子节点时 循环继续
            {
                div.removeChild(div.firstChild);
            }
        }

        $("[la]").click(function(){
            $("[lay]").css("display","none");
            console.log(12)
            $("[lay="+$(this).attr("la")+"]").css("display","block");
        })
        $(".quxiao").click(function(){
            $(".tip").css("display","none")
        });

        function triratna() {

            if($('#mesa_1').css("visibility") === 'hidden'){
                $('#mesa_1').css("visibility","visible");
                $('#mesa_2').css("visibility","visible");
                $('#mesa_3').css("visibility","visible");
            }else {
                $('#mesa_1').css("visibility","hidden");
                $('#mesa_2').css("visibility","hidden");
                $('#mesa_3').css("visibility","hidden");
            }
        }


		var ws;

        //还有无
        function haveOr() {
            if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
            }else {
                ws.send("1,10");
            }
        }

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
                ws.send("1,9");
            }
        }

        function lotteryHide() {
            $("input:checkbox[name='lottery']").each(function() {
                if($(this).val() == 1){
                    $('#tz').hide();
                } else if($(this).val() == 1){
                    $('#tx').hide();
                }
                else if($(this).val() == 2){
                    $('#tzd').hide();
                }
                else if($(this).val() == 3){
                    $('#txd').hide();
                }
                else if($(this).val() == 4){
                    $('#th').hide();
                }
            });
            $('#lottery').hide();
        }

        //开奖
        function lottery() {
		    if(ws == null){
                alert("连接已断开，请刷新页面或重新登录");
                return;
			}else {
		        $("#temp").val(1);
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
                var width = parseFloat($('#ld').width())/sum;

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

        //重开本局
        function reOpen() {
            lottery();
            $("#temp").val(2);
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
		});

        document.onreadystatechange = function () {
            if (document.readyState == "complete") {

//                var webSocket = new WebSocket('ws://120.78.199.184:20030/hall/philippines');
                var webSocket = new WebSocket('ws://192.168.1.81:10012/hall/philippines');

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
                                    		+'<td class="xiugai" la="1" t="'+message.username+'" >修改</td>'
                                    	+'</tr>';
							//追加到合计之前
                            $('#total').before(html);
                            //绑定点击事件
                            $("[t="+message.username+"]").click(function () {
                                $('#username').val(message.username);
                                var obj = $(document.getElementById(message.username)).children();
                                $('#bx').val(obj[1].innerText);
                                $('#bz').val(obj[2].innerText);
                                $('#bxd').val(obj[3].innerText);
                                $('#bzd').val(obj[4].innerText);
                                $('#bh').val(obj[5].innerText);
                                $("[lay=1]").css("display","block");
                            });

                            var total1 = document.getElementById("total");
                            total1.children[0].innerText = '总计:'+message.total[0];
                            for(var i=1; i<6; i++){
                                total1.children[i].innerText = message.total[i];
                            }
                            total1.children[6].innerText = '总计:'+message.total[0];
                            total1.children[7].innerText = message.total[6];
                            total1.children[8].innerText = message.total[7];
                            total1.children[9].innerText = message.total[8];

                            if(message.total[9] >= message.total[10]){
                                if((message.total[9] - message.total[10])<1000){
                                    $('#x').text(0);
                                }else {
                                    $('#x').text(message.total[9] - message.total[10]);
                                }
                                $('#z').text(0);
                            }else {
                                if((message.total[10] - message.total[9])<1000){
                                    $('#z').text(0);
                                }else {
                                    $('#z').text(message.total[10] - message.total[9]);
                                }
                                $('#x').text(0);
                            }
                            //更新台面分
                            $('#xd').text(message.total[12]);
                            $('#zd').text(message.total[11]);
                            $('#h').text(message.total[13]);

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

                            if(message.total[9] >= message.total[10]){
                                if((message.total[9] - message.total[10])<1000){
                                    $('#x').text(0);
                                }else {
                                    $('#x').text(message.total[9] - message.total[10]);
                                }
                                $('#z').text(0);
                            }else {
                                if((message.total[10] - message.total[9])<1000){
                                    $('#z').text(0);
                                }else {
                                    $('#z').text(message.total[10] - message.total[9]);
                                }
                                $('#x').text(0);
                            }
                            //更新台面分
                            $('#xd').text(message.total[12]);
                            $('#zd').text(message.total[11]);
                            $('#h').text(message.total[13]);
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
                            } else if (message.text == '已启动游戏'){
                                $('#statusText').text(" 当前游戏状态：已启动游戏");
                            }else if (message.text == '已开始下注'){
                                $('#statusText').text(" 当前游戏状态：投注中");
                            } else if (message.text == '已停止下注'){
                                $('#statusText').text(" 当前游戏状态：停止投注");
                            }
						}
					}
                }
            }
        }
	</script>

</html>