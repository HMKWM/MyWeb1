<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<c:set var="LoginOutLink" value="${pageContext.request.getSession(false).getAttribute('id')==null || pageContext.request.getSession(false).getAttribute('id')=='' ? '/login' : '/logout'}"/>
<c:set var="LoginOut" value="${pageContext.request.getSession(false).getAttribute('id')==null || pageContext.request.getSession(false).getAttribute('id')=='' ? 'Login' : 'Logout'}"/>
<!DOCTYPE html>
<div id="menu">
    <ul>
        <li id="logo"><a href="<c:url value='/'/>">Index</a></li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board'/>">Board</a></li>
        <li><a href="<c:url value='${LoginOutLink}'/>">${LoginOut}</a></li>
        <li><a href="<c:url value='/register'/>">Sign in</a></li>
        <li><a href=""><i class="fas fa-search small"></i></a></li>
    </ul>
</div>