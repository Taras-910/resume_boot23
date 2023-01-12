<%@ page contentType="text/html;charset=UTF-8" %>
<%--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
--%>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="/">Home</a></h3>
<hr>
<h2>Users</h2>
</body>
</html>





<%--<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h3>Users</h3>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Active</th>
            <th>Registered</th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.users}" var="user">
            <jsp:useBean id="user" type="ua.top.bootjava.model.User"/>
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><a href="mailto:${user.email}">${user.email}</a></td>
                <td>${user.roles}</td>
                <td>${user.enabled}</td>
                <td><fmt:formatDate value="${user.registered}" pattern="dd-MM-yyyy"/></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>--%>





<%--
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/top.common.js" defer></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/top.users.js" defer></script>
<script type="text/javascript" src="/resources/js/top.users.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-md-4">
    <div class="container">
        <button class="btn btn-outline-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            Добавить
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th>Имя</th>
                <th>Email</th>
                <th>Role</th>
                <th></th>
                <th>Регистрирован</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Добавить</h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="col-form-label">Имя</label>
                        <input type="text" class="form-control" id="name" name="name"
                               placeholder="Имя">
                    </div>

                    <div class="form-group">
                        <label for="email" class="col-form-label">Еmail</label>
                        <input type="email" class="form-control" id="email" name="email"
                               placeholder="Еmail">
                    </div>

                    <div class="form-group">
                        <label for="password" class="col-form-label">Пароль</label>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="Пароль">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    Отменить
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    Сохранить
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
--%>
