<%--
<%@ page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container text-center">
        <br>
        &lt;%&ndash;<h4 class="my-3">${status}</h4>&ndash;%&gt;
        <h4 class="my-5">${message}</h4>
        <h3>Ошибка проверки данных</h3>
        <h4 class="my-5">${exception}</h4>
    </div>
</div>
&lt;%&ndash;
<c:forEach items="${exception.stackTrace}" var="stackTrace">
    ${stackTrace}
</c:forEach>
&ndash;%&gt;
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
--%>
