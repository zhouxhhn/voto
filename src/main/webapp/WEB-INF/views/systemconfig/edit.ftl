<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>系统配置</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>
<style>
    .fileInput{
        display: inline-block;
        height:35px;
        line-height: 35px;
        width: 200px;
        border:1px solid #DDDDDD;
        -webkit-border-radius:5px;
        -moz-border-radius:5px;
        border-radius:5px;
        text-align: center;
        color: #170f15;
    }
    .fileInput>input{
        width:100%;
        height:100%;
        position: absolute;
        top:0px;
        left:0px;
        z-index: 2;
        opacity:0;
    }

</style>
	<body>

    <div class="tip" lay="2">
        <div class="tipBox updateBox" style="height: 890px;width: 400px;margin-left: -250px;margin-top: -450px">
            <p class="pTit tcenter f18 color33 mT10 mB15">系统配置</p>
            <form action="/system_config/edit" method="post"  id="form">
                <div class="tipCon ov" style="margin-bottom: 8px;">
                    <div class="updatePiliang">
                        <div><span><i hid>站</i>玩家默认R数：</span><input type="text" value="${systemConfig.playerR}" name="playerR" /></div>
                        <div><span><i hid>a</i>庄闲最小限红：</span><input type="text"  value="${systemConfig.bankerPlayerMix}" name="bankerPlayerMix" /></div>
                        <div><span><i hid>A</i>庄闲最大限红：</span><input type="text" value="${systemConfig.bankerPlayerMax}" name="bankerPlayerMax" /></div>
                        <div><span><i hid>A</i>三宝最小限红：</span><input type="text" value="${systemConfig.triratnaMix}" name="triratnaMix" /></div>
                        <div><span><i hid>a</i>三宝最大限红：</span><input type="text"  value="${systemConfig.triratnaMax}" name="triratnaMax" /></div>
                        <div><span><i hid>a</i>游戏充值比例：</span><input type="text"  value="${systemConfig.rechargeRatio}" name="rechargeRatio" /></div>
                        <div><span><i hid>a</i>银行上分费率：</span><input type="text"  value="${systemConfig.rechargeFee}" name="rechargeFee" /></div>
                        <div><span><i hid>a</i>三方上分费率：</span><input type="text"  value="${systemConfig.upScoreFee}" name="upScoreFee" /></div>

                        <div><span><i hid></i>一级抽水比(%)：</span><input type="text"  value="${systemConfig.pump_1}" name="pump_1" /></div>
                        <div><span><i hid></i>二级抽水比(%)：</span><input type="text"  value="${systemConfig.pump_2}" name="pump_2" /></div>
                        <div><span><i hid></i>三级抽水比(%)：</span><input type="text"  value="${systemConfig.pump_3}" name="pump_3" /></div>
                        <div><span><i hid></i>四级抽水比(%)：</span><input type="text"  value="${systemConfig.pump_4}" name="pump_4" /></div>
                        <div><span><i hid></i>五级抽水比(%)：</span><input type="text"  value="${systemConfig.pump_5}" name="pump_5" /></div>
                        <div><span><i hid>a</i>是否开启活动：</span>
                            <select name="openPump" style="width: 198px;height: 32px">
                                <option value="1" [#if systemConfig.openPump == 1]selected[/#if]>开启</option>
                                <option value="0" [#if systemConfig.openPump == 0]selected[/#if]>关闭</option>
                            </select>
                        </div>

                    </div>

                </div>
                <div style="text-align: center">

                </div>
                <div class="BtnDiv mT20">
                    <span class="tijiao mLR15" onclick="$('#form').submit()">保存</span>
                </div>
            </form>
        </div>
    </div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script src="/resources/main/js/home.js"></script>
</html>