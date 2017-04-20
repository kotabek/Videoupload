<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kotabek
  Date: 4/20/17
  Time: 1:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:import url="base/head.jsp"/>
    <title>Video Hosting</title>
</head>
<body>
<div class="container">
    <form class="form-signin col-md-4 col-md-offset-4"
          method="post"
          action="/j_spring_security_check">
        <h2 class="form-signin-heading">Please sign in</h2>
        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
            </div>
        </c:if>
        <label for="j_username" class="sr-only">Username</label>
        <input type="text" id="j_username" name="j_username"
               class="form-control"
               placeholder="Username"/>
        <label for="j_password" class="sr-only">Password</label>
        <input type="password" id="j_password" name="j_password"
               class="form-control"
               placeholder="Password"
        />

        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
    </form>

</div>

<c:import url="base/footer.jsp"/>

</body>
</html>
