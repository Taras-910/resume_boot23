<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>--%>
<html>
<head>
    <title>Resume list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="/">Home</a></h3>
    <hr/>
    <h2>Resumes</h2>
    <hr>

    <hr>
    <a href="resumes?action=create">Add Resume</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th hidden>id</th>
            <th hidden>link</th>
            <th class="col-auto" style="text-align: center;">Позиция</th>
            <th class="col-auto"><h7>Имя</h7></th>
            <th class="col-auto"><h7>Возраст</h7></th>
            <th class="col-auto"><h7>Место</h7></th>
            <th class="col-auto"><h7>$USD</h7></th>
            <th class="col-auto">Навыки</th>
            <th class="col-auto"><h7>Предыдущий опыт</h7></th>
            <th class="col-auto text-nowrap"></th>
            <th class="toVote"></th>
            <th hidden>work place</th>
            <th hidden>language</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
<%--
        <c:forEach items="${requestScope.resumes}" var="resume">
            <jsp:useBean id="resume" type="ua.top.bootjava.to.ResumeTo"/>
            <tr class="${resume.toVote ? 'excess' : 'normal'}">
                <td hidden>${resume.id}</td>
                <td hidden>${resume.url}</td>
                <td class="col-auto" style="text-align: center;">${resume.title}</td>
                <td class="col-auto">
                    <h7>${resume.name}</h7>
                </td>
                <td class="col-auto">
                    <h7>${resume.age}</h7>
                </td>
                <td class="col-auto">
                    <h7>${resume.address}</h7>
                </td>
                <td class="col-auto">
                    <h7>${resume.salary}</h7>
                </td>
                <td class="col-auto">${resume.workBefore}</td>
                <td class="col-auto">
                    <h7>${resume.skills}</h7>
                </td>
                &lt;%&ndash;<td class="col-auto text-nowrap">${resume.releaseDate}</td>&ndash;%&gt;
                <td class="col-auto text-nowrap"></td>
                <td class="toVote">${resume.toVote}</td>
                <td hidden>${resume.workplace}</td>
                <td hidden>${resume.language}</td>
                <td><a href="resumes?action=update&id=${resume.id}">Update</a></td>
                <td><a href="resumes?action=delete&id=${resume.id}">Delete</a></td>
            </tr>
        </c:forEach>
--%>
    </table>
</section>
</body>
</html>



<%--
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/top.common.js" defer></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/top.resumes.js" defer></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/top.freshen.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-md-">
    <div class="container-fluid row-cols-md-10">
        <div class="card border-dark">
            <div class="card-body pb-1" style="width:100%; background-color: #e5e7e7">
                <div class="container rounded-lg" style="width:90%;">
                    <div class="row justify-content-md-between align-items-center align-items-baseline">
                        <form class="col-sm-8 form-row needs-validation" id="filter">
                            <div class="col-md-4 mb-3 col-form-label">
                                <input class="form-control" type="text" name="language" id="language"
                                       list="language_name" style="width:98%;border:2px solid #0397ba" placeholder="please enter language...">
                                <datalist id="language_name">
                                    <option value='all' selected>all</option>
                                    <option value='Java'>Java</option>
                                    <option value='Ruby'>Ruby</option>
                                    <option value='Kotlin'>Kotlin</option>
                                    <option value='Python'>Python</option>
                                    <option value='JavaScript'>JavaScript</option>
                                    <option value='TypeScript'>TypeScript</option>
                                    <option value='Php'>Php</option>
                                    <option value='React'>React</option>
                                    <option value='Angular'>Angular</option>
                                    <option value='Android'>Android</option>
                                    <option value='C++'>C++</option>
                                    <option value='C#'>C#</option>
                                    <option value='.Net'>.Net</option>
                                    <option value='Ios'>Ios</option>
                                    <option value='Node.js'>Node.js</option>
                                    <option value='Sql'>Sql</option>
                                    <option value='Goland'>Goland</option>
                                    <option value='Ruby on rails'>Ruby on rails</option>
                                    <option value='Scala'>Scala</option>
                                    <option value='Asw'>Asw</option>
                                    <option value='Azure'>Azure</option>
                                    <option value='Html&css'>Html&css</option>
                                    <option value='Html'>Html</option>
                                    <option value='css'>css</option>
                                    <option value='Spark'>Spark</option>
                                    <option value='React native'>React native</option>
                                    <option value='Vue.js'>Vue.js</option>
                                    <option value='Flutter'>Flutter</option>
                                    <option value='Elixir'>Elixir</option>
                                    <option value='Hadoop'>Hadoop</option>
                                </datalist>
                            </div>
                            <div class="col-md-4 mb-3 col-form-label">
                                <input class="form-control" type="text" name="level" id="level" list="level_name"
                                       style="width:101%;border:2px solid #0397ba"
                                       placeholder="level...">
                                <datalist id="level_name">
                                    <option value='all' selected>all</option>
                                    <option value='Trainee'>(без опыта разработки)</option>
                                    <option value='Junior'>(опыт до 1 года)</option>
                                    <option value='Middle'>(опыт 2+ года)</option>
                                    <option value='Senior'>(опыт 3+ года)</option>
                                    <option value='Expert'>(опыт 5+ лет)</option>
                                </datalist>
                            </div>
                            <div class="col-md-4 mb-3 col-form-label">
                                <input class="form-control" type="text" name="workplace" id="workplace" list="city_name"
                                       style="width:101%;border:2px solid #0397ba"
                                       placeholder="workplace...">
                                <datalist id="city_name">
                                    <option value='all' selected>all</option>
                                    <option value='Киев'>Киев</option>
                                    <option value='Минск'>Минск</option>
                                    <option value='Львов'>Львов</option>
                                    <option value='Харьков'>Харьков</option>
                                    <option value='Днепр'>Днепр</option>
                                    <option value='Украина'>Украина</option>
                                    <option value='remote'>удаленно</option>
                                    <option value='foreign'>другие страны</option>
                                </datalist>
                            </div>
                        </form>
                        <div class="col-sm-2">
                            <button class="btn-sm btn-outline-info" onclick="updateFilteredTable()">
                                <span class="fa fa-filter"></span>
                                Фильтровать
                            </button>
                        </div>
                        <div class="col-md-2 ml-md-auto">
                            <button class="btn-sm btn-outline-danger" onclick="clearFilter()">
                                <span class="fa fa-remove"></span>
                                Сбросить
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-body pb-0">
            <div class="row card-footer justify-content-between" style="width: 103%">
                <div class="col">
                    &lt;%&ndash;<sec:authorize access="hasRole('ADMIN')">&ndash;%&gt;
                    <button class="col-md-2 btn btn-primary mt-2" onclick="add()">
                        <span class="fa fa-plus text-left"></span>
                        Добавить
                    </button>
                    &lt;%&ndash;</sec:authorize>&ndash;%&gt;
                </div>
                <button class=" col-md-2 btn btn-info bs-popover-right mt-2" onclick="refreshDB()">
                    <span class="fa fa-refresh text-left pull-right"></span>
                    Обновить БД
                </button>
            </div>
        </div>
        <table class="table table-striped table-bordered" id="datatable" style="width: 100%">
            <div class="row">
                <thead>
                <tr style="text-align: center;">
                    <th hidden>id</th>
                    <th hidden>link</th>
                    <th class="col-auto" style="text-align: center;">Позиция</th>
                    <th class="col-auto"><h7>Имя</h7></th>
                    <th class="col-auto"><h7>Возраст</h7></th>
                    <th class="col-auto"><h7>Место</h7></th>
                    <th class="col-auto"><h7>$USD</h7></th>
                    <th class="col-auto">Навыки</th>
                    <th class="col-auto"><h7>Предыдущий опыт</h7></th>
                    <th class="col-auto text-nowrap"></th>
                    <th class="toVote"></th>
                    <th hidden>work place</th>
                    <th hidden>language</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
            </div>
        </table>
        <br>
    </div>
</div>
&lt;%&ndash;<sec:authorize access="hasRole('ADMIN')">&ndash;%&gt;
&lt;%&ndash;add ResumeTo&ndash;%&gt;
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
                    <div class="form-group not_update_visible">
                        <label for="title" class="col-form-label">Позиция</label>
                        <input type="text" class="form-control" id="title" name="title">
                    </div>
                    <div class="form-group not_update_visible">
                        <label for="name" class="col-form-label">Имя</label>
                        <input type="text" class="form-control" id="name" name="name">
                    </div>
                    <div class="form-group not_update_visible">
                        <label for="age" class="col-form-label">Возраст</label>
                        <input type="text" class="form-control" id="age" name="age">
                    </div>
                    <div class="form-group not_update_visible">
                        <label for="address" class="col-form-label">Адрес</label>
                        <input type="text" class="form-control" id="address" name="address">
                    </div>
                    <div class="form-group">
                        <label for="salary" class="col-form-label">з/п от (usd cent)</label>
                        <input type="number" class="form-control" id="salary" name="salary">
                    </div>
                    <div class="form-group">
                        <label for="skills" class="col-form-label">Навыки...</label>
                        <input type="text" class="form-control" id="skills" name="skills">
                    </div>
                    <div class="form-group">
                        <label for="workBefore" class="col-form-label">Предыдущая работа</label>
                        <input type="text" class="form-control" id="workBefore" name="workBefore">
                    </div>
                    <div class="form-group">
                        <label for="url" class="col-form-label">Ссылка</label>
                        <input type="text" class="form-control" id="url" name="url"
                               placeholder="https://www.example.com">
                    </div>
                    <div class="form-group">
                        <input type="hidden" class="form-control" id="releaseDate" name="releaseDate">
                    </div>
                    <div class="form-group">
                        <label for="languageCode" class="col-form-label">Язык программирования</label>
                        <input type="text" class="form-control" id="languageCode" name="language">
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
&lt;%&ndash;update RowResume&ndash;%&gt;
<div class="modal fade" tabindex="-1" id="updateRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header box">
                <h4 class="modal-title">Редактировать</h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsUpdateForm">
                    <input type="hidden" id="idUpdate" name="id">
                    <div class="form-group not_update_visible">
                        <label for="title" class="col-form-label">Позиция</label>
                        <input type="text" class="form-control" id="titleUpdate" name="title">
                    </div>
                    <div class="form-group not_update_visible">
                        <input type="hidden" class="form-control" id="nameUpdate" name="name">
                    </div>
                    <div class="form-group">
                        <label for="age" class="col-form-label">Возраст</label>
                        <input type="text" class="form-control" id="ageUpdate" name="age">
                    </div>
                    <div class="form-group not_update_visible">
                        <input type="text" class="form-control" id="addressUpdate" name="address">
                    </div>
                    <div class="form-group">
                        <label for="salary" class="col-form-label">з/п от (usd cent)</label>
                        <input type="number" class="form-control" id="salaryUpdate" name="salary">
                    </div>
                    <div class="form-group">
                        <label for="skills" class="col-form-label">Навыки...</label>
                        <input type="text" class="form-control" id="skillsUpdate" name="skills">
                    </div>
                    <div class="form-group not_update_visible">
                        <label for="workBefore" class="col-form-label">Предыдущая работа</label>
                        <input type="text" class="form-control" id="lastWorkUpdate" name="workBefore">
                    </div>
                    <div class="form-group">
                        <label for="url" class="col-form-label"></label>
                        <input type="text" class="form-control" id="urlUpdate" name="url"
                               placeholder="https://www.example.com">
                    </div>
                    <div class="form-group">
                        <input type="hidden" class="form-control" id="releaseDateUpdate" name="releaseDate">
                    </div>
                    <div class="form-group">
                        <input type="hidden" class="form-control" id="languageCodeUpdate" name="language">
                    </div>
                    <div class="form-group">
                        <input type="hidden" class="form-control" id="workplaceUpdate" name="workplace">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    Отменить
                </button>
                <button type="button" class="btn btn-primary" onclick="updateResumeTo()">
                    <span class="fa fa-check"></span>
                    Сохранить
                </button>
            </div>
        </div>
    </div>
</div>
&lt;%&ndash;delete RowResume&ndash;%&gt;
<div class="modal fade" tabindex="-1" id="deleteRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header box" style="text-align: center;">
                <h6><em>Подтвердите ваше действие !</em></h6>
            </div>
            <div class="modal-body">
                <form id="detailsDeleteForm">
                    <label for="idDelete"><em>'Are you sure?'</em></label>
                    <input type="hidden" id="idDelete" name="id">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    Отменить
                </button>
                <button type="button" class="btn btn-primary" onclick="deleteResumeTo()">
                    <span class="fa fa-check"></span>
                    Удалить
                </button>
            </div>
        </div>
    </div>
</div>
&lt;%&ndash;</sec:authorize>&ndash;%&gt;
&lt;%&ndash;refresh&ndash;%&gt;
<div class="modal fade" tabindex="-1" id="refreshRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header box">
                <h5 class="modal-title">Обновить БД по параметрам:</h5>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsRefreshForm">
                    <div class="form-group">
                        <label type="hidden" for="recordedDate" class="col-form-label"></label>
                        <input type="hidden" class="form-control" id="recordedDate" name="recordedDate">
                    </div>
                    <div class="form-group">
                        <label for="languageTask">
                            <h7 class="btn-outline-info"><em>please enter language...</em></h7>
                        </label>
                        <input class="form-control" type="text" name="language" id="languageTask"
                               list="language_name_2" placeholder="please enter any language">
                        <datalist id="language_name_2">
                            <option value='all' selected>all</option>
                            <option value='Java'>Java</option>
                            <option value='Ruby'>Ruby</option>
                            <option value='Kotlin'>Kotlin</option>
                            <option value='Python'>Python</option>
                            <option value='JavaScript'>JavaScript</option>
                            <option value='TypeScript'>TypeScript</option>
                            <option value='Php'>Php</option>
                            <option value='React'>React</option>
                            <option value='Angular'>Angular</option>
                            <option value='Android'>Android</option>
                            <option value='C++'>C++</option>
                            <option value='C#'>C#</option>
                            <option value='.Net'>.Net</option>
                            <option value='Ios'>Ios</option>
                            <option value='Node.js'>Node.js</option>
                            <option value='Sql'>Sql</option>
                            <option value='Goland'>Goland</option>
                            <option value='Ruby on rails'>Ruby on rails</option>
                            <option value='Scala'>Scala</option>
                            <option value='Asw'>Asw</option>
                            <option value='Azure'>Azure</option>
                            <option value='Html&css'>Html&css</option>
                            <option value='css'>css</option>
                            <option value='Html'>Html</option>
                            <option value='Spark'>Spark</option>
                            <option value='React native'>React native</option>
                            <option value='Vue.js'>Vue.js</option>
                            <option value='Flutter'>Flutter</option>
                            <option value='Elixir'>Elixir</option>
                            <option value='Hadoop'>Hadoop</option>
                        </datalist>
                    </div>
                    <div class="form-group">
                        <label for="levelTask">
                            <h7 class="btn-outline-info"><em>level...</em></h7>
                        </label>
                        <input class="form-control" type="text" name="level" id="levelTask" list="level_2"
                               placeholder="please enter any level">
                        <datalist id="level_2">
                            <option value='Trainee'>(без опыта разработки)</option>
                            <option value='Junior'>(опыт до 1 года)</option>
                            <option value='Middle'>(опыт 2+ года)</option>
                            <option value='Senior'>(опыт 3+ года)</option>
                            <option value='Expert'>(опыт 5+ лет)</option>
                            <option value='all'>all</option>
                        </datalist>
                    </div>
                    <div class="form-group">
                        <label for="workplaceTask">
                            <h7 class="btn-outline-info"><em>location...</em></h7>
                        </label>
                        <input class="form-control" type="text" name="workplace" id="workplaceTask" list="city_name_2"
                        placeholder="please enter the place of work you need">
                        <datalist id="city_name_2">
                            <option value='Киев'>Киев</option>
                            <option value='Минск'>Минск</option>
                            <option value='Львов'>Львов</option>
                            <option value='Харьков'>Харьков</option>
                            <option value='Днепр'>Днепр</option>
                            <option value='Украина'>Украина</option>
                            <option value='remote'>удаленно</option>
                            <option value='foreign'>другие страны</option>
                            <option value='all'>all</option>
                        </datalist>
                    </div>
                    <div class="form-group">
                        <label type="hidden" for="userId" class="col-form-label"></label>
                        <input type="hidden" class="form-control" id="userId" name="userId">
                    </div>
                </form>
            </div>
            <span class="d-flex justify-content-center" id="spinner2" style="visibility: hidden">
                <h7><em>идет загрузка... подождите минуту или две...<br>
                    через 2 мин жмите  </em><h6 class="fa fa-filter text-info">  Фильтровать</h6>
                </h7>
            </span>
            <h7 class="modal-title"></h7>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    Отменить
                </button>
                <button type="button" class="btn btn-info" onclick="sendRefresh()">
                    Обновить  
                    <span class="spinner-border spinner-border-sm" id="spinner1" style="visibility: hidden"></span>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
--%>
