<!DOCTYPE>
<html lang="zh_cn">
<head>
[@block name="Meta"]
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <link rel="shortcut icon" href="[@spring.url '/resources/images/favicon.ico' /]" type="image/x-icon"/>
[/@block]

    <title>后台管理[@block name="title"][/@block]</title>

[@block name="topResources"]
    <!-- Bootstrap core CSS -->
    <link href="[@spring.url '/resources/bootstrap/css/bootstrap.min.css'/]" rel="stylesheet">

    <!-- 字体 -->
    <link href="[@spring.url '/resources/css/font-awesome.min.css'/]" rel="stylesheet">

    <!-- 离子图标 -->
    <link href="[@spring.url '/resources/css/ionicons.min.css'/]" rel="stylesheet">

    <!-- Morris 时间系列绘图 -->
    <link href="[@spring.url '/resources/css/morris.css'/]" rel="stylesheet"/>

    <!-- 时间选择器 -->
    <link href="[@spring.url '/resources/css/datepicker.css'/]" rel="stylesheet"/>
    <link href="[@spring.url '/resources/js/datetimepicker/jquery.datetimepicker.css'/]" rel="stylesheet"/>

    <!-- Animate css动画库 -->
    <link href="[@spring.url '/resources/css/animate.min.css'/]" rel="stylesheet">

    <!-- Owl Carousel 旋转木马 -->
    <link href="[@spring.url '/resources/css/owl.carousel.min.css'/]" rel="stylesheet">
    <link href="[@spring.url '/resources/css/owl.theme.default.min.css'/]" rel="stylesheet">

    <!-- parsley验证css -->
    <link href="[@spring.url '/resources//js/parsley/parsley.css'/]" rel="stylesheet">

    <!-- 模板自定义css -->
    <link href="[@spring.url '/resources/css/simplify.min.css'/]" rel="stylesheet">
    <link href="[@spring.url '/resources/css/reset.css'/]" rel="stylesheet">
[/@block]
</head>

<body class="overflow-hidden">
<div>
    <div class="padding-md">
        <!-- 面包屑导航 -->
    [@block name="breadcrumb"]
        <ul class="breadcrumb">
            <li><span class="primary-font"><i class="icon-home"></i></span><a href="/"> Home</a></li>
            <li>UI Elements</li>
            <li>Tab</li>
        </ul>
    [/@block]
        <div class="has_new_withdraw" style="position: fixed; right: 10px; top: 10px;"></div>
        <div class="has_new_customer" style="position: fixed; right: 10px; top: 10px;"></div>

    [@block name="subContent"]

    [/@block]
    </div>
</div>

<!-- top 返回顶部按钮 -->
<a href="#" class="scroll-to-top hidden-print active"><i class="fa fa-chevron-up fa-lg"></i></a>

[@block name="bottomResources"]
<!-- ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<!-- Jquery -->
<script src="[@spring.url '/resources/js/jquery.min.js'/]"></script>
<script src="[@spring.url '/resources/js/jquery.ui.min.js'/]"></script>

<!-- Bootstrap -->
<script src="[@spring.url '/resources/bootstrap/js/bootstrap.min.js'/]"></script>

<!--验证表单-->
<script src="[@spring.url '/resources/js/parsley/parsley.js' /]" type="text/javascript"></script>

<!-- Flot 绘图js -->
[#--<script src="[@spring.url '/resources/js/jquery.flot.min.js'/]"></script>--]

<!-- Slimscroll 滚动条js -->
<script src="[@spring.url '/resources/js/jquery.slimscroll.min.js'/]"></script>

<!-- Nicescroll 滚动条 -->
<script src="[@spring.url '/resources/js/nicescroll/jquery.nicescroll.js'/]"></script>

<!-- Morris 时间系列绘图 -->
[#--<script src="[@spring.url '/resources/js/rapheal.min.js'/]"></script>--]
[#--<script src="[@spring.url '/resources/js/morris.min.js'/]"></script>--]

<!-- Datepicker 时间选择js -->
[#--<script src="[@spring.url '/resources/js/uncompressed/datepicker.js'/]"></script>--]

<!-- Sparkline 线状图插件 -->
[#--<script src="[@spring.url '/resources/js/sparkline.min.js'/]"></script>--]

<!-- Skycons 天气插件 -->
[#--<script src='[@spring.url '/resources/js/uncompressed/skycons.js'/]'></script>--]

<!-- Popup Overlay 弹出层 -->
<script src="[@spring.url '/resources/js/jquery.popupoverlay.min.js'/]"></script>

<!-- Easy Pie Chart 饼状图 -->
[#--<script src="[@spring.url '/resources/js/jquery.easypiechart.min.js'/]"></script>--]

<!-- Sortable 拖拽 -->
[#--<script src="[@spring.url '/resources/js/uncompressed/jquery.sortable.js'/]"></script>--]

<!-- Owl Carousel 旋转木马的 -->
[#--<script src="[@spring.url '/resources/js/owl.carousel.min.js'/]"></script>--]

<!-- Modernizr 浏览器检测 -->
<script src="[@spring.url '/resources/js/modernizr.min.js'/]"></script>

<!-- 模板自定义js -->
<script src="[@spring.url '/resources/js/simplify/simplify.js'/]"></script>
<script src="[@spring.url '/resources/js/simplify/simplify_dashboard.js'/]"></script>

<script src="[@spring.url '/resources/js/fastjson.js'/]"></script>

<!-- 公共js库 -->
<script src="[@spring.url '/resources/js/common.js'/]"></script>
<script src="[@spring.url '/resources/js/ArrayPrototype.js'/]"></script>
<script src="[@spring.url '/resources/js/StringUtil.js'/]"></script>
<script src="[@spring.url '/resources/js/layer/layer.js'/]"></script>
<script src="[@spring.url '/resources/js/datetimepicker/jquery.datetimepicker.full.js'/]"></script>
<script src="[@spring.url '/resources/js/upload/webuploader.js'/]"></script>
<script type="text/javascript">


    $.datetimepicker.setLocale('en');
    $('#startDate').datetimepicker({
        dayOfWeekStart: 1,
        lang: 'en',
    });
    $('#endDate').datetimepicker({
        dayOfWeekStart: 1,
        lang: 'en',
    });


</script>

<!--[if lt IE 9]>
<script type="text/javascript">
    window.location.href = "ie_update";
</script>
<![endif]-->
[/@block]
</body>
</html>
