<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>资源包更新</title>
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
        <div class="tipBox updateBox">
            <p class="pTit tcenter f18 color33 mT10 mB15">资源包更新</p>
            <form action="/update_package/update" method="post" enctype="multipart/form-data" id="form">
                <div class="tipCon ov">
                    <div class="fl updatePiliang">
                        <div><span ><i hid></i>上次更新时间：[@mc.dateShow update.lastUpdateDate/]</span></div>
                        <div><span><i hid>站</i>安卓版本号：</span><input type="text" value="${update.androidVersion}" name="androidVersion" /></div>
                        <div><span><i hid>站 </i>IOS版本号：</span><input type="text" value="${update.iosVersion}" name="iosVersion" /></div>
                        <div><span><i hid>a</i>Html版本号：</span><input type="text"  value="${update.htmlVersion}" name="htmlVersion" /></div>

                    </div>
                    <div class="fr updatePiliang">
                        <div><span><i hid>位</i>上次更新人：${update.modifier}</span></div>
                        <div><span><i hid>站</i>安卓包地址：</span><input type="text" value="${update.androidUrl}" name="androidUrl"/></div>
                        <div><span><i hid>位 </i>IOS包地址：</span><input type="text"  value="${update.iosUrl}" name="iosUrl"/></div>
                        <div><span ><i hid>位</i>资源包上传：</span><label class="fileInput"><input id="file" type="file" name="file" onchange="change()"/>点击选择文件</label></div>
                    </div>



                </div>
                <div style="text-align: center">
                <div style="margin: 0 auto">
                [#if flag??]
                    [#if flag == true]
                        <span id="changeFile" style="color: #00dd1c"><i hid>位</i>
                                资源包更新成功！
                            </span>
                    [#else ]
                        <span id="changeFile" style="color: #c0070f"><i hid>位</i>
                                资源包更新失败！
                            </span>
                    [/#if]
                [#else ]
                    <span id="changeFile"><i hid>位</i>
                            资源包名称：未选择文件
                            </span>
                [/#if]
                </div>
                </div>
                <div class="BtnDiv mT20">
                    <span class="tijiao mLR15" onclick="submit()">保存</span>
                </div>
            </form>
        </div>
    </div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script src="/resources/main/js/home.js"></script>
	<script type="text/javascript">

        function change() {
            var f = $("#file").val().replace(/^.+?\\([^\\]+?)?$/gi,"$1");
            $("#changeFile").css({color:""});
            $("#changeFile").text("资源包名称："+f);
        }

        function submit() {
            $("#form").submit();
        }
	</script>

</html>