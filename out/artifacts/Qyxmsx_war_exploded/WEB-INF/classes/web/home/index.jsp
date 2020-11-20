<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:forward page="/ProductServlet">
	<jsp:param name="action" value="findIndex"/>
</jsp:forward>