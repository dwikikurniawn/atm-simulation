<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>View Account</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>Account Number</th>
                    <th>Name</th>
                    <th>Balance</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${accounts}" var="account">
                    <tr>
                        <td>${account.accountNumber}</td>
                        <td>${account.name}</td>
                        <td>${account.balance}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>