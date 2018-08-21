<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>我的占比</title>
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
        <div class="tipBox updateBox" style="height: 240px;width: 400px;margin-left: -250px;margin-top: -350px">
            <p class="pTit tcenter f18 color33 mT10 mB15">我的占比</p>
            <form action="/agent_config/ratio" method="post"  id="form">
                <div class="tipCon ov">
                    <div class="updatePiliang">
                        <div><span><i hid>a</i>我的R数：</span><input type="text" value="${config.valueR}"  readonly="readonly"/></div>
                        <div><span><i hid></i>最高占比：</span><input type="text" value="${config.highRatio}"  readonly="readonly"/></div>
                        <input type="text" value="${config.id}" id="id" hidden="hidden">

                    </div>

                </div>
                <div style="text-align: center">

                </div>
                <#--<div class="BtnDiv mT20">-->
                    <#--<span class="tijiao mLR15" onclick="submit()">保存</span>-->
                <#--</div>-->
            </form>
        </div>
    </div>

	</body>
	<script src="/resources/main/js/jquery2-1-4.min.js"></script>
	<script src="/resources/main/js/home.js"></script>
    <script type="text/javascript">
//        function submit() {
//            $.post(
//                    "/agent_config/ratio",
//                    {'factRatio': $('#factRatio').val(), 'id': $('#id').val()},
//                    function (result) {
//                        if (0 == result) {
//                            alert('等过过期或未登陆');
//                        } else if ( 1 == result){
//                            alert('代理配置不存在');
//                        } else if ( 2== result){
//                            alert('实际占比不能超过最高占比');
//                        } else if ( -1 == result){
//                            alert('修改配置出错');
//                        } else if ( -2 == result){
//                            alert('实际占比配置错误');
//                        } else if ( 4 == result){
//                            alert('实际占比超出限制');
//                        }else if (3 == result){
//                            alert('申请修改配置成功，等待管理员审核');
//                        }
//                    }
//            );
//        }
    </script>
</html>