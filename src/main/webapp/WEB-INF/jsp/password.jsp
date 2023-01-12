<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="vacancy" tagdir="/WEB-INF/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/top.password.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <div class="row">
            <div class="col-5 offset-3">
                <form>
                    <label>Password: </label>
                    <input name="password" type="password" onChange="onChange()"/> <br/>
                    <label>Confirm : </label><br/>
                    <input name="confirm" type="password" onChange="onChange()"/>
                    <input type="submit"/>
                </form>
                <div class="modal-body">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" onChange="onChange()">
                        <span class="fa fa-close"></span>
                        Отменить
                    </button>
                    <button type="button" class="btn btn-info" onclick="sendRefresh()">
                        Обновить
                        <span class="spinner-border spinner-border-sm" id="spinner" style="visibility: hidden"></span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
