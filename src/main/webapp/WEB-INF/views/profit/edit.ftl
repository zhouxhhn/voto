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
        <div class="tipBox updateBox" style="height: 280px;width: 400px;margin-left: -250px;margin-top: -350px">
            <p class="pTit tcenter f18 color33 mT10 mB15">收益统计</p>
            <form action="/profit/edit" method="post"  id="form">
                <div class="tipCon ov">
                    <div class="updatePiliang">
                        <div><span ><i hid></i>上次统计时间：${date}</span></div>
                    [#if success != 2]
                        <div><span id="t" style="color: #00dd1c">统计成功！</span></div>
                    [#else ]
                        <div><span id="t"></span></div>
                    [/#if]
                    </div>
                </div>
                <div style="text-align: center">

                </div>
                <div class="BtnDiv mT20">
                    <span class="tijiao mLR15" onclick="submit()">统计</span>
                </div>
            </form>
        </div>
    </div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script src="/resources/main/js/home.js"></script>
    <script type="text/javascript">
        function submit() {
            $('#t').text("正在统计中,统计完成前请勿重复点击");
            $('#form').submit();
        }
    </script>
</html>