<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>支付管理</title>
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
        <div class="tipBox updateBox" style="height: 530px;width: 400px;margin-left: -250px;margin-top: -350px">
            <p class="pTit tcenter f18 color33 mT10 mB15">系统配置</p>
            <form action="/rechargeCtl/edit" method="post"  id="form">
                <div class="tipCon ov">
                    <div class="updatePiliang">

                        <div><span ><i hid>啊啊a</i>支付宝：</span>
                            <label >
                                <select name="aliPay" class="op">
                                    <option value="1" [#if rechargeCtl.aliPay == 1 ]selected="selected" [/#if] >开启</option>
                                    <option value="2" [#if rechargeCtl.aliPay == 2 ]selected="selected" [/#if] >关闭</option>
                                 </select>

                            </label>
                        </div>
                        <div><span><i hid>站啊啊a</i>微信：</span>
                            <label >
                                <select name="weChat" class="op">
                                    <option value="1" [#if rechargeCtl.weChat == 1 ]selected="selected" [/#if] >开启</option>
                                    <option value="2" [#if rechargeCtl.weChat == 2 ]selected="selected" [/#if] >关闭</option>
                                </select>

                            </label>
                        </div>

                        <div><span><i hid>嗷啊</i>QQ钱包：</span>
                            <label >
                                <select name="qqPay" class="op">
                                    <option value="1" [#if rechargeCtl.qqPay == 1 ]selected="selected" [/#if] >开启</option>
                                    <option value="2" [#if rechargeCtl.qqPay == 2 ]selected="selected" [/#if] >关闭</option>
                                </select>

                            </label>
                        </div>
                        <div><span><i hid>啊啊啊a</i>网银：</span>
                            <label >
                                <select name="bank" class="op">
                                    <option value="1" [#if rechargeCtl.bank == 1 ]selected="selected" [/#if] >开启</option>
                                    <option value="2" [#if rechargeCtl.bank == 2 ]selected="selected" [/#if] >关闭</option>
                                </select>

                            </label>
                        </div>
                        <div><span><i hid>a</i>支付宝扫码：</span>
                            <label >
                                <select name="aliCode" class="op">
                                    <option value="1" [#if rechargeCtl.aliCode == 1 ]selected="selected" [/#if] >开启</option>
                                    <option value="2" [#if rechargeCtl.aliCode == 2 ]selected="selected" [/#if] >关闭</option>
                                </select>

                            </label>
                        </div>
                        <div><span><i hid>啊a</i>微信扫码：</span>
                            <label >
                                <select name="weCode" class="op">
                                    <option value="1" [#if rechargeCtl.weCode == 1 ]selected="selected" [/#if] >开启</option>
                                    <option value="2" [#if rechargeCtl.weCode == 2 ]selected="selected" [/#if] >关闭</option>
                                </select>

                            </label>
                        </div>

                        <div><span><i hid></i>QQ钱包扫码：</span>
                            <label >
                                <select name="qqCode" class="op">
                                    <option value="1" [#if rechargeCtl.qqCode == 1 ]selected="selected" [/#if] >开启</option>
                                    <option value="2" [#if rechargeCtl.qqCode == 2 ]selected="selected" [/#if] >关闭</option>
                                </select>

                            </label>
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