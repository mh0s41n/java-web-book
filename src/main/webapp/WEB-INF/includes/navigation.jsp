<%@taglib prefix="sec" uri="http://mhosain.com/functions" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a href="<c:url value="/home"/>" class="navbar-brand">eShoppers</a>
        <button class="navbar-toggler" type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarResponsive"
                aria-controls="navbarResponsive"
                aria-expanded="false"
        >
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="navbarResponsive">
            <ul class="navbar-nav text-center">
                <li class="nav-item">
                    <a href="<c:url value="/home"/>" class="nav-link active" aria-current="page">Home</a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link">About</a>
                </li>
                <c:choose>
                    <c:when test="${sec:isAuthenticated(pageContext.request)}">
                        <a href="#" class="nav-link" onclick="logout()">
                            Log Out [${sec:getCurrentUser(pageContext.request).firstName}]
                        </a>
                        <script>
                            function logout() {
                                document.getElementById("logoutForm").submit();
                            }
                        </script>
                        <form action="<c:url value="/logout"/>" id="logoutForm" method="post"
                              style="visibility: hidden"></form>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="/login"/>" class="nav-link">
                            Log In
                        </a>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
