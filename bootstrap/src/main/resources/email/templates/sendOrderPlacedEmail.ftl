<html>

<body>

<table border="0" width="100%" height="351" style="font-family: Tahoma">
    <tr>
        <td bgcolor="#224F90">
            <table border="0" width="100%" cellpadding="15" cellspacing="0">
                <tr>
                    <td width="100%" style="color: #FFFFFF" align="right">
                        <b><font size="2"><font color="#C0C0C0">Notification from</font><font color="#946EA9">
                        </font>VantaShala</font></b></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td bgcolor="#946EA9" align="left" valign="top">
            <table border="0" width="100%" height="100%" cellpadding="55" id="table1">
                <tr>
                    <td width="100%" height="100%" bgcolor="#946EA9">
                        <font color="#FFFFFF"><span style="font-weight: 700; ">
						<font size="2" color="#DBCEE1"># ${notificationBean.id} - Order Placed. </font></span>
                            <p><span style="font-weight: 700; ">
						<font size="2">Order Total: $${notificationBean.totalPrice}<hr></font></span></p>
                            <font size="2">
                                <ul type="circle">
                                <#list notificationBean.cookMenuItems as cookMenuItem>
                                    <li><b>${cookMenuItem.cookDetails.kitchenName}</b> <br>
                                        &nbsp;&nbsp;${cookMenuItem.itemDetails.name} - ${cookMenuItem.orderQuantity} -
                                        $${cookMenuItem.itemDetails.price} per item. <br><br>
                                    </li>
                                </#list>
                                </ul>
                            </font>
                            &nbsp;<p>&nbsp;</p>
                            <p><font size="2" color="#FFFFFF">See you again soon.<br>
                                VantaShala.com</font></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td bgcolor="#224F90">
            <table border="0" width="100%" cellpadding="15" cellspacing="0">
                <tr>
                    <td width="100%" style="color: #C0C0C0; font-size:8pt; font-family:Tahoma" align="center">
                        All Rights Reserved.
                        <p>This email was sent from a notification-only address
                            that cannot accept incoming email. Please do not reply
                            to this message.</td>
                </tr>
            </table>
        </td>
    </tr>
</table>

</body>

</html>
