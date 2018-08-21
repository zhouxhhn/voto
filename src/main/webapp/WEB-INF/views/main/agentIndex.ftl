<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>JC娱乐后台管理系统(代理)</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
	</head>

	<body style="background: #1c1c1c;">
		<!--左边tab-->
		<div class="leftTab box ov fl">
			<div class="logo">
				<img src="/resources/main/img/logo.png" />
			</div>
			<ul class="tabList">

				<li class="">
					<a href="/agent/pagination${url}" target="contentBox"><i class="iconfont icon-guanli"></i>下级管理 </a>
				</li>
				<li class="">
					<a href="/game_detailed/paginationAgent" target="contentBox"><i class="iconfont icon-mingxi1"></i>输赢明细 </a>
				</li>
                <#--<li class="">-->
                    <#--<a href="/agent_profit/pagination" target="contentBox"><i class="iconfont icon-mingxi1"></i>收益报表 </a>-->
                <#--</li>-->
                <li class="">
                    <a href="/upload/code" target="contentBox"><i class="iconfont icon-mingxi1"></i>我的二维码 </a>
                </li>
                <li class="">
                    <a href="/agent_config/ratio" target="contentBox"><i class="iconfont icon-mingxi1"></i>我的占比 </a>
                </li>

			</ul>
		</div>
		<div class="rightWork box ov ">
			<div class="admin posi_re">
			<div class="divTit ">下级管理</div>
				<div><a style="color: #DDDDDD" href="/logout">注销</a></div>
			</div>
			<div class="iframeDiv">
				<iframe frameborder=0 src="/agent/pagination${url}" name="contentBox"></iframe>
			</div>
		</div>
	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script type="text/javascript">
        $(".tabList>li,.layerTab tbody tr,.pageWrap a").click(function() {
            $(this).addClass("on").siblings().removeClass("on");
        });
        $(".tabList>li a").click(function() {
            $(".divTit").html($(this).html())
        });
        $(".tabList>li p").click(function() {
            $(this).siblings("div").slideToggle(300);
        });
	</script>

</html>