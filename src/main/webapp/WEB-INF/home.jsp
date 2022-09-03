<%@taglib prefix="sec" uri="http://mhosain.com/functions" %>

<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>



<div class="container">

    <div class="p-3 mb-2 bg-light rounded">
        <div class="row align-items-end">
            <div class="col-6">
                <img src="<c:url value="/img/cart.png"/>" style="height: 200px" alt="cart">
                <h1 class="mt-2 fw-bold">
                    <c:if test="${sec:isAuthenticated(pageContext.request)}">Hello
                        <c:out value="${sec:getCurrentUser(pageContext.request).firstName}"/>,
                    </c:if>
                    Welcome to eShopper</h1>
            </div>
            <div class="col-6 text-end">
                <c:if test="${cart != null && cart.cartItems.size() > 0}">
                    <div class="card shadow-sm p-3 ml-6 bg-white">
                        <div class="card-header bg-white">
                            <h4>Your Cart</h4>
                        </div>
                        <div class="card-body">
                            <p>Total item:
                                <span class="badge bg-success rounded-pill">
                                    <c:out value="${cart.totalItem}"/>
                                </span>
                            </p>
                            <p>Total Price: <c:out value="${cart.totalPrice}"/></p>
                            <p>
                                <a href="<c:url value="/checkout"/>" class="btn btn-outline-info">Checkout</a>
                            </p>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <div class="row mt-4">
        <c:if test="${message != null}">
            <div class="alert alert-success">
                    ${message}
            </div>
        </c:if>
    </div>

    <div class="row mt-4">
        <c:forEach var="product" items="${products}">
            <div class="col-sm-4 d-flex align-items-stretch">
                <div class="card mb-4 flex-fill">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title"><c:out value="${product.name}"/></h5>
                        <hr>
                        <p class="card-text">
                            <c:out value="${product.description}"/>
                        </p>
                        <p class="card-text">
                            Price: $<c:out value="${product.price}"/>
                        </p>
                        <a href="#" class="card-link btn mt-auto btn-outline-success"
                           onclick="addToCart(${product.id})">
                            Add to Cart
                        </a>

                        <form action="<c:url value="/add-to-cart?productId=${product.id}"/>"
                              id="addToCart_${product.id}"
                              method="post"
                              style="visibility: hidden">
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    function addToCart(productId) {
        document.getElementById("addToCart_" + productId).submit();
    }
</script>

<%@include file="includes/footer.jsp" %>
