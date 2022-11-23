<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Transaction History</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>Source Account Number</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Time</th>
                    <th>Recipient Account Number</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${transactions}" var="transaction">
                    <tr>
                        <td>${transaction.sourceAccountNumber}</td>
                        <td>${transaction.type}</td>
                        <td>${transaction.amount}</td>
                        <td>${transaction.time}</td>
                        <td>${transaction.recipientAccountNumber}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
    <div class="navigationbutton">
                  <form action="/dashboard/${accountNumber}" method="get">
                    <button type="submit" class="btn-default btn">Back</button>
                  </form>
    </div>
</html>