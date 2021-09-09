<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="application" />
<!DOCTYPE html>
<html lang="ko">
    <head>
        <title></title>
        <style>
            .vCenter { display: table; width: 400px; height: 400px; margin: auto; } 
            .vCenter span { display: table-cell; text-align: center; font-size:large; vertical-align: middle; }
        </style>
    </head>
    <body>
    	<div class="vCenter"> <span> Multilang page.. </span> </div>
    	name : ${name} <br>
    	product : ${product}
    </body>
</html>