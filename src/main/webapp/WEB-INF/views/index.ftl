[@override name="title"]首页控制台[/@override]

[@override name="breadcrumb"]
<div class="row">
    <div class="col-sm-12">
        <div class="page-sub-header">
            <h4>欢迎回来, ${user.userName!}</h4>
        </div>
    </div>
</div>
<div class="custom-popup delete-widget-popup delete-confirmation-popup" id="deleteWidgetConfirm">
    <div class="popup-header text-center">
				<span class="fa-stack fa-4x">
				  <i class="fa fa-circle fa-stack-2x text-danger"></i>
				  <i class="fa fa-exclamation-circle fa-stack-1x fa-inverse"></i>
				</span>
    </div>
    <div class="popup-body text-center">
        <h5>[#if maintenance]你确定维护完成了吗?[#else]你确定要进行维护吗?[/#if]</h5>
        <strong class="block m-top-xs"><i class="fa fa-exclamation-circle m-right-xs text-danger"></i>
            [#if maintenance]维护完成玩家将可以进入开始游戏![#else]维护过程中玩家将不能进入游戏,已经在游戏中的玩家可以玩完当前这把游戏[/#if]
        </strong>

        <div class="text-center m-top-lg">
            <a class="btn btn-success m-right-sm remove-widget-btn">确定</a>
            <a class="btn btn-default deleteWidgetConfirm_close">取消</a>
        </div>
    </div>
</div>
[/@override]

<a class="widget-remove-option" href="javascript:void(0)"><h4>[#if maintenance]
    系统正在维护中,如已完成维护,请点击此处[#else]
    系统正常运行中,需要维护请先点击此处[/#if]</h4></a>

[@override name="subContent"]
    <h1>万豪百家乐后台管理系统</h1>
[/@override]

[@override name="bottomResources"]
    [@super /]
<script type="text/javascript">
    $(".remove-widget-btn").click(function () {
        window.location.href = '/maintenance';
    });
</script>
[/@override]
[@extends name="/decorator.ftl"/]
