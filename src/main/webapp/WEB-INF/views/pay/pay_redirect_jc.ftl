<html>
<head><title>支付</title></head>
<body>
<form action="http://micro.173600.com:30020/api/recharge/pullJC" method="post" name="E_FORM">
<#--<form action="http://10.0.0.200:10012/api/recharge/pullJC" method="post" name="E_FORM">-->
    <table align="center">

        <tr>
            <td></td>
            <td><input type="hidden" name="money" value="${info.money}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="userId" value="${info.userId}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="payType" value="${info.payType}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="pass" value="${info.pass}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="clientIp" value="${info.clientIp}"></td>
        </tr>

    </table>
    <p align="center">
        <label>
            <input type="text" value="正在提交..."/>
        </label>
    </p>
</form>
<script type="text/javascript">
    document.forms["E_FORM"].submit();
</script>
</body>
</html>
