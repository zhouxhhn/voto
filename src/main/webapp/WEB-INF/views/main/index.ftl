<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>JC娱乐后台管理系统</title>
		<link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
        <style>
            .outLogin {
                font-size: 14px;
                color: #666;
                text-align: center;
                line-height: 32px;
                border: 1px solid #999;
                position: absolute;
                right: 40px;
                top: 42px;
                width: 100px;
                z-index: 99;
                background: white;
            }
            .outLogin:hover {
                cursor: pointer;
            }
        </style>
	</head>

	<body style="background: #1c1c1c;">
		<!--左边tab-->
		<div class="leftTab box ov fl">
			<div class="logo">
				<img src="/resources/main/img/logo.png" />
			</div>
			<ul class="tabList">
				<#--<li class="on">-->
					<#--<a href="/game_bet/list?roomType=1" target="contentBox"><i class="iconfont icon-chouma"></i>游戏下注 </a>-->
				<#--</li>-->

				<li class="">
					<a href="/user/list" target="contentBox"><i class="iconfont icon-guanli"></i>玩家管理 </a>
				</li>

                    <li class="">
                        <a href="/phone_bet/pagination" target="contentBox"><i class="iconfont icon-dianhua"></i>电话投注</a>
					</li>

				<li class="">
					<a href="/game_detailed/pagination" target="contentBox"><i class="iconfont icon-mingxi1"></i>输赢明细 </a>
				</li>
				<li class="">
					<a href="/player_lose/pagination" target="contentBox"><i class="iconfont icon-yingkuizichan"></i>选手洗码盈亏</a>
				</li>
				<li class="">
					<a href="/triratna/pagination" target="contentBox"><i class="iconfont icon-huizong"></i>三宝盈亏汇总 </a>
				</li>
				<li class="">
					<a href="/personal/pagination" target="contentBox"><i class="iconfont icon-ticheng"></i>个人占成 </a>
				</li>
				<li class="">
					<a href="/total/pagination" target="contentBox"><i class="iconfont icon-caiwu"></i>财务账面汇总 </a>
				</li>



				<#--<li class="">-->
					<#--<a href="/agent_profit/pagination" target="contentBox"><i class="iconfont icon-zhanghaoguanli"></i>代理收益</a>-->
				<#--</li>-->
                <#--<li class="">-->
                    <#--<a  href="/agent_config/pagination" target="contentBox"><i class="iconfont icon-3"></i>代理管理</a>-->
                <#--</li>-->

                    <li class="">
                        <p class="iconfont icon-xialajiantou">&nbsp;&nbsp;&nbsp;&nbsp;代理管理</p>
                        <div>
                            <a href="/agent_profit/pagination" target="contentBox"><i class="iconfont icon-zhanghaoguanli"></i>代理收益</a>
                            <a  href="/agent_config/pagination" target="contentBox"><i class="iconfont icon-3"></i>代理信息</a>
                            <a  href="/ratio_check/pagination" target="contentBox"><i class="iconfont icon-3"></i>占比审核</a>
                        </div>

                    </li>
                    <li class="">
                        <p class="iconfont icon-xialajiantou">&nbsp;&nbsp;&nbsp;&nbsp;充值提现管理</p>
                        <div>
                            <a href="/recharge/pagination" target="contentBox"><i class="iconfont icon-chongzhimingxi"></i>充值明细</a>
                            <a href="/rechargeCtl/edit" target="contentBox"><i class="iconfont icon-mingxi"></i>支付管理</a>
                            <a href="/withdraw/pagination" target="contentBox"><i class="iconfont icon-tixian"></i>提现管理 </a>
                        </div>

                    </li>

                    <#--<li class="">-->
                        <#--<a  href="/spread/pagination" target="contentBox"><i class="iconfont icon-huizong"></i>推广活动</a>-->
                    <#--</li>-->

				<li class="">
					<a  href="/account/pagination" target="contentBox"><i class="iconfont icon-3"></i>账号管理</a>
				</li>
                 <li class="">
                     <a  href="/robot/pagination" target="contentBox"><i class="iconfont icon-3"></i>机器人管理</a>
                </li>

                <li class="">
                    <a href="/notice/pagination" target="contentBox"><i class="iconfont icon-tixian"></i>系统公告</a>
                </li>
                <li class="">
                    <a href="/activity/pagination" target="contentBox"><i class="iconfont icon-tixian"></i>活动管理</a>
                    </li>
                    <li class="">
                        <a href="/publicRecharge/pagination" target="contentBox"><i class="iconfont icon-tixian"></i>前端充值管理</a>
                    </li>
                    <li class="">
                        <a href="/carousel/pagination" target="contentBox"><i class="iconfont icon-tixian"></i>轮播图管理</a>
                    </li>
                    <li class="">
                        <a href="/guide/pagination" target="contentBox"><i class="iconfont icon-tixian"></i>新手指南管理</a>
                    </li>
                <li class="">
                    <a href="/bank/pagination" target="contentBox"><i class="iconfont icon-tixian"></i>银行卡管理</a>
                </li>
                    <li class="">
                        <a href="/transfer/pagination" target="contentBox"><i class="iconfont icon-tixian"></i>转账管理 </a>
                    </li>
				<li class="">
					<a href="/upDownPoint/list" target="contentBox"><i class="iconfont icon-mingxi"></i>上下分明细</a>
				</li>
				<li class="">
					<a href="/logger/pagination" target="contentBox"><i class="iconfont icon-rizhi1"></i>操作日志</a>
				</li>
                <li class="">
                    <a href="/system_config/edit" target="contentBox"><i class="iconfont icon-mingxi"></i>系统配置</a>
                </li>
                    <li class="">
                        <a href="/profit/edit" target="contentBox"><i class="iconfont icon-mingxi"></i>收益统计</a>
                    </li>
                    <li class="">
                        <a href="/upload/code" target="contentBox"><i class="iconfont icon-mingxi1"></i>我的二维码 </a>
                    </li>
                <li class="">
                    <a href="/update_package/get" target="contentBox"><i class="iconfont icon-rizhi1"></i>安装包更新</a>
                </li>
			</ul>
		</div>
		<div class="rightWork box ov ">
			<div class="admin posi_re">
				<div class="divTit ">玩家管理</div>
					<div><a href="/logout" style="color: #DDDDDD">注销</a></div>
				<#--<div style="padding-right: 50px">-->
                    <#--<img style="height: 46px;border-radius: 50%;padding: 5px 5px" src="${Session["sessionUser"].head!}"/>-->
				<#--</div>-->
				<#--<div>-->
                    <#--<span >${Session["sessionUser"].name!}</span><i class="iconfont icon-xialajiantou"></i>-->
				<#--</div>-->
			</div>

            <#--<div class="outLogin">-->
                <#--<i class="iconfont "></i>注销-->
            <#--</div>-->

			<div class="iframeDiv">
				<iframe frameborder=0 src="/user/list" name="contentBox"></iframe>
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
        $("#user").click(function() {
            $(this).siblings("#logout").slideToggle(300);
        });
	</script>

</html>