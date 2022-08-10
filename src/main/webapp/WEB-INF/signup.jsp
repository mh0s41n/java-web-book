<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container col-sm-8 col-md-4">
    <h2 class="h2 text-center mb-4">Sign Up</h2>

    <form action="<c:url value="/signup"/>" class="form" role="form" method="post">
        <div class="form-group mb-4">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username" name="username" value="${userDTO.username}"/>
            <c:if test="${errors.username != null}">
                <small class="text-danger">${errors.username}</small>
            </c:if>
        </div>
        <div class="form-group mb-4">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="email@domain.tld"
                   value="${userDTO.email}"/>
            <c:if test="${errors.email != null}">
                <small class="text-danger">${errors.email}</small>
            </c:if>
        </div>
        <div class="form-group mb-4">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" name="password" value="${userDTO.password}"/>
            <c:if test="${errors.password != null}">
                <small class="text-danger">${errors.password}</small>
            </c:if>
        </div>
        <div class="form-group mb-4">
            <label for="passwordConfirmed">Confirm Password</label>
            <input type="password" class="form-control" id="passwordConfirmed" name="passwordConfirmed"
                   value="${userDTO.passwordConfirmed}"/>
            <c:if test="${errors.passwordConfirmed != null}">
                <small class="text-danger">${errors.passwordConfirmed}</small>
            </c:if>
        </div>
        <div class="form-group mb-4">
            <label for="firstName">First Name</label>
            <input type="text" class="form-control" id="firstName" name="firstName" value="${userDTO.firstName}"/>
            <c:if test="${errors.firstName != null}">
                <small class="text-danger">${errors.firstName}</small>
            </c:if>
        </div>
        <div class="form-group mb-4">
            <label for="lastName">Last Name</label>
            <input type="text" class="form-control" id="lastName" name="lastName" value="${userDTO.lastName}"/>
            <c:if test="${errors.lastName != null}">
                <small class="text-danger">${errors.lastName}</small>
            </c:if>
        </div>

        <div class="form-group text-end">
            <button class="btn btn-primary" type="submit">Sign Up</button>
        </div>

    </form>

    <div class="mb-2">
        Already have an account? <a href="<c:url value="/login"/>" class="btn-link text-decoration-none"> Log In</a>.
    </div>
</div>

<script type="text/javascript">
    function validatePassword() {
        let password = document.getElementById('password').value;
        let passwordConfirmed = document.getElementById('passwordConfirmed').value;

        if (password !== passwordConfirmed) {
            alert("Passwords do not match.")

            return false;
        }

        return true;
    }
</script>

<%@include file="includes/footer.jsp" %>
