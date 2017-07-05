<%-- 
    Document   : vendingMachine
    Created on : Jun 25, 2017, 5:12:10 PM
    Author     : camber
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"> 
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js" ></script>
        <script src="${pageContext.request.contextPath}/js/vending.js" ></script>
        <title>JSP Page</title>
    </head>
    <body>
        <div class ="container">
            <div class="row">
                <div class="col-md-12" style="text-align: Center">
                    <h1>Spring MVC Vending Machine</h1>
                </div>
            </div>
            <div class="row" id="main-panels">
                <div class="col-md-8" id="snack-buttons">
                    <div class="row">

                        <c:forEach var="product" items="${snacks}">

                            <div class="col-md-4" border="2px">
                                <button class="btn btn-default"style="width:100%" onclick="setSelect(${product.snackId})">
                                    <p>
                                        <c:out value="${product.snackId}"/>
                                    </p>
                                    <p style="text-align: center">
                                        <c:out value="${product.name}"/>
                                    </p>
                                    <p style="text-align: center">
                                        $<c:out value="${product.price}"/>
                                    </p>
                                    <p style="text-align: center">
                                        <c:out value="${product.quantity}"/>
                                    </p>
                                </button>
                            </div>

                        </c:forEach>


                    </div>
                </div>
                <div class="col-md-4" id="interface-panel">
                    <div class="row">
                        <div class="col-md-12" style="text-align: center">
                            <h2>Total $ In</h2><br/>
                            <h3 id="moneyIn"><c:out value="${money}"/></h3><br/>

                        </div>
                        <div class="col-md-12" style="text-align: center">
                            <a href="http://localhost:8080/VendingMachineSpringMvc/insertMoney?amount=1.00"> 
                                <button class="btn btn-default">Dollar</button>
                            </a>
                            <a href="http://localhost:8080/VendingMachineSpringMvc/insertMoney?amount=0.25"> 
                                <button class="btn btn-default">Quarter</button>
                            </a>
                        </div>  
                        <div class="col-md-12" style="text-align: center">
                            <a href="http://localhost:8080/VendingMachineSpringMvc/insertMoney?amount=0.10"> 
                                <button class="btn btn-default">Dime</button>
                            </a>
                            <a href="http://localhost:8080/VendingMachineSpringMvc/insertMoney?amount=0.05"> 
                                <button class="btn btn-default">Nickel</button>
                            </a>
                            <hr/>
                        </div>    
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <h2>Messages</h2><br/>
                            <h3><c:out value="${message}"/></h3><br/>

                        </div>
                        <form class="form-horizontal" method="POST" action="vend">
                            <label class="col-md-4 form-control">Item:</label>
                            <div class="col-md-8">
                                <input readonly  type="text" name="itemSelect" id="itemSelect" placeholder="Select an Item"/>
                                <input type="submit" class="btn btn-default" value="Make Purchase"/>
                                <hr/>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <h2>Change</h2><br/>

                            <h3>Dollars| <c:out value="${change.dollars}"/> Quarters| <c:out value="${change.quarters}"/>  Dimes| <c:out value="${change.dimes}"/> Nickels| <c:out value="${change.nickels}"/></h3><br/>
                            <a href="http://localhost:8080/VendingMachineSpringMvc/giveChange">
                                <button class="btn btn-default">Change Return</button>
                            </a>
                        </div>
                    </div>


                </div>
            </div>

        </div>

    </body>
</html>

<!-- june 28 -->
