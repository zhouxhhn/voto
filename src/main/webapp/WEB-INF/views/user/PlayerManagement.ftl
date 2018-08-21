<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/resources/main/css/style.css" />
    <link rel="stylesheet" type="text/css" href="/resources/main/css/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="/resources/datetime/skin/jedate.css" />
</head>

<body>
<!--玩家管理-->
<div class="tableW PhoneBetting divbox pad20 TongjiTab">
    <div class="phoneTop">
        <div class="addPlayer Btn1 yanHuang"la="1" inDiv>新增玩家</div>
        <div class="batchEdit Btn1 yanQing" la="2" inDiv>批量修改玩家信息</div>
        <div inDiv>
            <form action="/user/list" id="form">
                <label class="mR10">
                    <input type="text" name="userName" value="${command.userName}"/>&nbsp;&nbsp;玩家昵称
                </label>
                <label>
                    <input id="startDate" name="startDate" value="${command.startDate}"/>
                </label>
                <label class="mLR5">到</label>
                <label>
                    <input id="endDate" name="endDate" value="${command.endDate}"/>
                </label>
            </form>
        </div>
        <div class="chaxun mLR5" onclick="$('#form').submit()">查询</div>
    </div>
    <div class="divW w100">
        <table class="layerTab">
            <thead>
            <tr>
                <th>序号</th>
                <th>玩家昵称</th>
                <th>玩家ID</th>
                <th>注册时间</th>
                <th>玩家账号</th>
                <th>代理别名</th>
                <th>一级代理</th>
                <th>二级代理</th>
                <th>推荐人</th>
                <th>隐藏显示</th>
                <th>是否置顶</th>
                <th>是否虚拟</th>
                <th>剩余分</th>
                <th>现初始分</th>
                <th>原初始分</th>
                <th>银行分</th>
                [#--<th>庄闲占成</th>--]
                [#--<th>庄闲占成额度</th>--]
                [#--<th>三宝占成</th>--]
                [#--<th>三宝占成额度</th>--]
                <th>庄闲最小</th>
                <th>庄闲最大</th>
                <th>三宝最小</th>
                <th>三宝最大</th>
                <th>修改</th>
                <th>上下分</th>
            </tr>

            </thead>
            <tbody>
            [#if pagination ??]
                [#list pagination.data as user]
                <tr>
                    <td>${user_index+1}</td>
                    <td >${user.name}</td>
                    <td >${user.username}</td>
                    <td >${user.createDate}</td>
                    <td >${user.token}</td>
                    <td >${user.agentAlias}</td>
                    <td >${user.firstAgent}</td>
                    <td >${user.secondAgent}</td>
                    <td >${user.referee}</td>
                    <td>[#if user.printScreen == 1]显示[#else ]隐藏[/#if]</td>
                    <td>[#if user.setTop == 1]是[#else ]否[/#if]</td>
                    <td>[#if user.virtual == 1]是[#else ]否[/#if]</td>
                    <td>${user.score}</td>
                    <td>${user.primeScore}</td>
                    <td>${user.dateScore}</td>
                    <td>${user.bankScore}</td>
                    [#--<td >${user.bankerPlayerProportion}</td>--]
                    [#--<td >${user.bankerPlayerCredit}</td>--]
                    [#--<td >${user.triratnaProportion}</td>--]
                    [#--<td >${user.triratnaCredit}</td>--]
                    <td >${user.bankerPlayerMix}</td>
                    <td >${user.bankerPlayerMax}</td>
                    <td >${user.triratnaMix}</td>
                    <td >${user.triratnaMax}</td>
                    <td class="xiugai" la="3" onclick="xiugai(this)" data-id="${user.id!}">修改</td>
                    <td>
                        <a href="javascript:void(0)" style="color:#27f13a" la="4" data-score="${user.score!}" data-id="${user.id!}">上分</a>&nbsp;|&nbsp;
                        <a href="javascript:void(0)" style="color:#f1732a" la="5" data-score="${user.score!}" data-id="${user.id!}">下分</a>
                    </td>
                </tr>
                [/#list]
            [/#if]
            <tr>
                <td>总计</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>${sum[1]!0}</td>
                <td>${sum[0]!0}</td>
                <td>${sum[3]!0}</td>
                <td>${sum[2]!0}</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>
    <!--分页-->
[#if pagination??]
    [@mc.customPagination '/user/list?userName=${command.userName}&startDate=${command.startDate}&endDate=${command.endDate}'/]
[/#if]
</div>
<!--批量修改玩家信息-->
<div class="tip" lay="2" style="display:none;">
    <div class="tipBox xiuigaiBox">
        <p class="pTit tcenter f18 color33 mT10 mB15">批量修改玩家信息</p>
        <form action="/user/listmodify" method="post"  id="listModify">
            <div class="tipCon ov">
                <div class="fl piliang">
                    <p class="tcenter f16 color33 mB15">玩家限红</p>
                    <div><span >个人庄闲最小：</span><input type="text" name="bankerPlayerMix" /></div>
                    <div><span>个人庄闲最大：</span><input type="text"  name="bankerPlayerMax" /></div>

                </div>
                <div class="fr piliang">
                    <p class="tcenter f16 color33 mB15">玩家占成</p>
                    <div><span >个人三宝最小：</span><input type="text"  name="triratnaMix"  /></div>
                    <div><span >个人三宝最大：</span><input type="text" name="triratnaMax" /></div>
                    [#--<div><span><i hid>站位了</i>个人庄闲占成比：</span><input type="text" name="bankerPlayerProportion"   /></div>--]
                    [#--<div><span>个人庄闲最高占成金额：</span><input type="text"  name="bankerPlayerCredit" /></div>--]
                    [#--<div><span><i hid>站位了</i>个人三宝占成比：</span><input type="text" name="triratnaProportion"    /></div>--]
                    [#--<div><span>个人三宝最高占成金额：</span><input type="text"  name="triratnaCredit" /></div>--]
                </div>
            </div>
            <div class="BtnDiv mT20">
                <span class="quxiao mLR15" >取消</span>
                <span class="tijiao mLR15" onclick="listModify()">确认修改</span>
            </div>
        </form>
    </div>
</div>
<!--新增玩家-->
<div class="tip" lay="1" style="display: none;">
    <div class="tipBox addBox">
        <p class="pTit tcenter f18 color33 mT10">新增玩家</p>
        <form action="/user/create" method="post" id="addUser">
            <div class="tipCon">
                <p><span ><i hid>位</i>玩家昵称：</span><input type="text" name="playerAlias" /></p>
                <p><span ><i hid>位</i>代理别名：</span><input type="text" name="agentAlias" /></p>
            </div>
            <div class="BtnDiv mT20">
                <span class="quxiao mLR15" type="button">取消</span>
                <span class="tijiao mLR15" type="submit" onclick="submit()">保存</span>
            </div>
    </div>
    </form>
</div>
[#--上分--]
<div class="tip" lay="4" style="display: none;">
    <div class="tipBox addBox">
        <p class="pTit tcenter f18 color33 mT10">玩家上分</p>
        <form action="/user/changeScore" method="post" id="addScore">
            <div class="tipCon">
                <p><span ><i hid>位</i>玩家余额：</span><span id="u"></span></p>
                <p><span ><i hid>位</i>上分分数：</span><input type="number" id="scoreU" name="score"/></p>
            </div>
            <input id="add" name="id" hidden="hidden">
            <input  name="type" value="1" hidden="hidden">
            <div class="BtnDiv mT20">
                <span class="quxiao mLR15" type="button">取消</span>
                <span class="tijiao mLR15" type="submit" onclick="addScore()">确定</span>
            </div>
    </div>
    </form>
</div>
[#--下分--]
<div class="tip" lay="5" style="display: none;">
    <div class="tipBox addBox">
        <p class="pTit tcenter f18 color33 mT10">玩家下分</p>
        <form action="/user/changeScore" method="post" id="subScore">
            <div class="tipCon">
                <p><span ><i hid>位</i>玩家余额：</span><span id="d"></span></p>

                <p><span ><i hid>位</i>下分分数：</span><input type="number" id="scoreD" name="score" /></p>
            </div>
            <input id="sub" name="id" hidden="hidden">
            <input  name="type" value="2" hidden="hidden">
            <div class="BtnDiv mT20">
                <span class="quxiao mLR15" type="button">取消</span>
                <span class="tijiao mLR15" type="submit" onclick="subScore()">确定</span>
            </div>
    </div>
    </form>
</div>

<div class="tip" lay="3" style="display:none ;">
    <div class="tipBox xiuigaiBox xiu_box">
        <p class="pTit tcenter f18 color33 mT10 mB15">修改玩家信息</p>
        <form id="edit" action="/user/modify" method="post" >
            <div class="tipCon ov b_bor">
                <div class="fl piliang">
                    <input id="editUser" name="id" value="" hidden="hidden">
                    <div><span >玩家别名：</span><input type="text" name="playerAlias" class="form-control" id="playerAlias"></div>
                    [#--<div><span>上下分数：</span><input type="text"  name="upDownPoint" class="form-control" id="upDownPoint"/></div>--]
                </div>
                <div class="fr piliang">
                    <div><span >代理别名：</span><input type="text"  name="agentAlias" class="form-control" id="agentAlias"/></div>
                    [#--<div><span >玩家余额：</span><input type="text"  class="form-control" id="restScore"/></div>--]
                </div>
            </div>
            <div class="tipCon mT20 ov">
                <div class="fl piliang">
                    <p class="tcenter f16 color33 mB15">庄闲限红</p>
                    <div><span>个人庄闲最小：</span><input type="text" name="bankerPlayerMix"  id="bankerPlayerMix"/></div>
                    <div><span >个人庄闲最大：</span><input type="text" name="bankerPlayerMax"  id="bankerPlayerMax"></div>

                </div>
                <div class="fr piliang">
                    <p class="tcenter f16 color33 mB15">三宝限红</p>
                    <div><span >个人三宝最小：</span><input type="text" name="triratnaMix"  id="triratnaMix"/></div>
                    <div><span>个人三宝最大：</span><input type="text"  name="triratnaMax" id="triratnaMax"></div>

                </div>
            </div>


            <div class="BtnDiv mT20">

                    <span class="shanchu mLR15_"   onclick="deletePlayer()">删除玩家</span>
                    <span class="zhiding mLR15_"   onclick="setTop()"  name="setTop" value="1">置顶玩家</span>
                    <span class="tijiao mLR15_"  onclick="update()">确认修改</span>
                    <span class="zhiding mLR15_"   onclick="setVirtual()" >修改虚拟</span>
                    <span class="tijiao mLR15_"  onclick="setPrintScreen()">修改显示</span>
                    <span class="quxiao mLR15_">取消</span>

            </div>
        </form>
    </div>

</div>

</body>
<script src="/resources/main/js/jquery2-1-4.min.js"></script>
<script src="/resources/datetime/jquery.jedate.js"></script>
<script src="/resources/main/js/home.js"></script>
<script type="text/javascript">

    $(".layerTab tbody tr,.pageWrap a").click(function() {
        $(this).addClass("on").siblings().removeClass("on");
    })
    $("[la]").click(function(){
        if($(this).attr("la") == 4){
            $('#u').text($(this).attr("data-score"));
            $('#add').val($(this).attr("data-id"));
        } else if($(this).attr("la") == 5){
            $('#d').text($(this).attr("data-score"));
            $('#sub').val($(this).attr("data-id"));
        }
        $("[lay]").css("display","none");
        $("[lay="+$(this).attr("la")+"]").css("display","block");
    })
    $(".quxiao").click(function(){
        $(".tip").css("display","none")
    })

    $(".xiugai").on("click",function () {
        var _modal = $("#edit");
        _modal.find("#editUser").val($(this).attr("data-id"));

    });
    function xiugai(obj) {
        var thisObj = $(obj);
        var tds = thisObj.prevAll();
        $('#playerAlias').val(tds[16].innerHTML);
        $('#agentAlias').val(tds[12].innerHTML);
        $('#restScore').val(tds[6].innerHTML);
        $('#bankerPlayerMix').val(tds[3].innerHTML);
        $('#bankerPlayerMax').val(tds[2].innerHTML);
        $('#triratnaMix').val(tds[1].innerHTML);
        $('#triratnaMax').val(tds[0].innerHTML);


    }



    /*上分*/
    function addScore() {
        var score = $('#scoreU').val();
        if(score.length<1){
            alert('不能为空');
            return;
        }
        if(score <= 0){
            alert('必须大于0');
            return;
        }
        $('#addScore').submit();
    }

    /*下分*/
    function subScore() {
        var score = $('#scoreD').val();
        if(score.length<1){
            alert('不能为空');
            return;
        }
        if(score <= 0){
            alert('必须大于0');
            return;
        }

        if(score - Number($('#d').text()) >0){
            alert('玩家余额不足');
            return;
        }
        $('#subScore').submit();
    }

    /*添加*/
    function submit() {
        $('#addUser').submit();
    }

    /*批量修改*/
    function listModify() {

        $('#listModify').submit()

    }

    function update() {

        $('#edit').submit();


    }

    function setVirtual() {
        window.location.href = "/user/setVirtual?id="+$('#editUser').val();
    }

    function setPrintScreen() {
        window.location.href = "/user/setPrintScreen?id="+$('#editUser').val();
    }


    function deletePlayer(){
        window.location.href = "/user/delete?id="+$('#editUser').val();

    }

    function setTop() {
        window.location.href = "/user/setTop?id="+$('#editUser').val();
    }

</script>

</html>