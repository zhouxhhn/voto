<html>
<head><title>支付</title></head>
<body>
<form action="http://www.i3159.com/Pay_Index.html" method="post" name="E_FORM">
    <table align="center">

        <tr>
            <td></td>
            <td><input type="hidden" name="pay_amount" value="${info.pay_amount}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="pay_applydate" value="${info.pay_applydate}"></td>
        <tr>
            <td></td>
            <td><input type="hidden" name="pay_bankcode" value="${info.pay_bankcode}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="pay_callbackurl" value="${info.pay_callbackurl}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="pay_md5sign" value="${info.pay_md5sign}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="pay_memberid" value="${info.pay_memberid}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="pay_notifyurl" value="${info.pay_notifyurl}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="pay_orderid" value="${info.pay_orderid}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="hidden" name="pay_reserved1" value="${info.pay_reserved1}"></td>
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
