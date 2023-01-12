<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container">
        <div class="navbar-brand"><h3><img src="resources/images/icon-vacancy.png">   3000+ programmer resumes 🔥</h3></div>
        <form class="form-inline my-2" id="login_form" action="spring_security_check" method="post">
            <input class="form-control mr-1" type="text" placeholder="Email" name="username">
            <input class="form-control mr-1" type="password" placeholder="Password" name="password">
            <button class="btn btn-success" type="submit">
                <span class="fa fa-sign-in"></span>
            </button>
        </form>
    </div>
</nav>
<div class="container jumbotron py-0">
    <c:if test="${param.error}">
        <div class="error">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
    </c:if>
    <c:if test="${not empty param.message}">
        <div class="message">
            "${param.message}"
        </div>
    </c:if>
    <br/>
    <sec:authorize access="isAnonymous()">
        <p>
            <a class="btn btn-lg btn-info" href="profile/register">Регистрация &raquo;</a>
            <button type="submit" class="btn btn-lg btn-outline-primary" onclick="login('user@yandex.ru', 'password')">
                Зайти как User
            </button>
            <button type="submit" class="btn btn-lg btn-outline-primary" onclick="login('admin@gmail.com', 'admin')">
                Зайти как Admin
            </button>
        </p>
    </sec:authorize>
    <div class="lead py-3">Стек технологий: 
        <a href="http://projects.spring.io/spring-security/">Spring Security</a>,
        <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>,
        <a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,
        <a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security Test</a>,
        <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling-task-scheduler">Spring
            TaskScheduler</a>,<br>
        <a href="https://jsoup.org">Jsoup HTML Parser</a>,
        <a href="http://hibernate.org/orm/">Hibernate ORM</a>,
        <a href="http://hibernate.org/validator/">Hibernate Validator</a>,
        <a href="http://www.slf4j.org/">SLF4J</a>,
        <a href="https://github.com/FasterXML/jackson">Json Jackson</a>,
        <a href="http://ru.wikipedia.org/wiki/JSP">JSP</a>,
        <a href="http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library">JSTL</a>,
        <a href="http://tomcat.apache.org/">Apache Tomcat</a>,<br>
        <a href="http://www.webjars.org/">WebJars</a>,
        <a href="http://datatables.net/">DataTables plugin</a>,
        <a href="http://ehcache.org">EHCACHE</a>,
        <a href="http://www.postgresql.org/">PostgreSQL</a>,
        <a href="http://junit.org/">JUnit</a>,
        <a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>
        <a href="http://jquery.com/">jQuery</a>,
        <a href="http://ned.im/noty/">jQuery notification</a>,
        <a href="http://getbootstrap.com/">Bootstrap</a>
    </div>
</div>
<div class="container lead py-3">
    <a href="https://github.com/JavaOPs/topjava">Java Enterprise проект</a> с регистрацией / авторизацией и правами
    доступа на основе ролей (USER, ADMIN).<br/>Администратор может создавать / редактировать / удалять пользователей и
    резюме, пользователи могут управлять своим профилем через UI (по AJAX) и по REST интерфейсу с базовой авторизацией.
    Резюме можно фильтровать / обновлять / отмечать (меняется цвет строки, индивидуальный выбор сохраняется в базе
    данных).<br/>Весь REST интерфейс покрывается JUnit тестами, при этом используется Spring MVC Test и Spring Security
    Test.<br/>БД хранит резюме за последние 4-е месяца, само-обновляется из 5-ти ресурсов:<br/>
    <a href="https://djinni.co">Djinni</a>,
    <a href="http://grc.ua">HH(GrcUA)</a>,
    <a href="https://career.habr.com">Habr</a>,
    <%--<a href="https://www.linkedin.com">LinkedIn</a>,--%>
    <a href="https://rabota.ua">Rabota</a>,
    <a href="https://www.work.ua">Work</a>
</div>
<br>
<div class="container jumbotron py-0">
    <div class="col">
        <a class="btn btn-lg btn-success my-4" href="swagger-ui.html" target="_blank">Swagger REST Api Documentation</a>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript">
    <c:if test="${not empty param.username}">
    setCredentials("${param.username}", "");
    </c:if>

    function login(username, password) {
        setCredentials(username, password);
        $("#login_form").submit();
    }

    function setCredentials(username, password) {
        $('input[name="username"]').val(username);
        $('input[name="password"]').val(password);
    }
</script>
</body>
</html>
