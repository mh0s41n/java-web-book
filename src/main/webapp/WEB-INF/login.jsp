<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container col-sm-8 col-md-4">

    <div class="row">
        <c:if test="${message != null}">
            <div class="alert alert-success text-center">
                    ${message}
            </div>
        </c:if>
    </div>

    <h2 class="h2 text-center mb-4">Log In</h2>

    <form action="<c:url value="/login"/>" class="form" role="form" method="post">
        <div class="form-group mb-4">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username" name="username" value="${userDTO.username}"/>
            <c:if test="${errors.username != null}">
                <small class="text-danger">${errors.username}</small>
            </c:if>
        </div>

        <div class="form-group mb-4">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" name="password" value="${userDTO.password}"/>
            <c:if test="${errors.password != null}">
                <small class="text-danger">${errors.password}</small>
            </c:if>
        </div>

        <div class="form-group text-end">
            <button class="btn btn-primary" type="submit">Log In</button>
        </div>
    </form>

    <div class="mb-2">
        Don't have an account? <a href="<c:url value="/signup"/>" class="btn-link text-decoration-none"> Sign Up</a>.
    </div>
</div>

<%@include file="includes/footer.jsp" %>
