var ctx, ajaxUrl = "profile/resumes/";

function vote(chkbox, id) {
    var toVote = chkbox.is(":checked");
    $.ajax({
        url: ajaxUrl + id,
        type: "POST",
        data: "enabled=" + toVote
    }).done(function () {
        chkbox.closest("tr").attr("resume-vote", toVote);
        successNoty(toVote ? "Select" : "Deselect");
    }).fail(function () {
        $(chkbox).prop("checked", !toVote);
    });
}

function updateFilteredTable() {
//https://stackoverflow.com/questions/56977572/how-to-set-default-value-in-input-datalist-and-still-have-the-drop-down
    let inputLanguage = document.getElementById('language');
    let inputLevel = document.getElementById('level');
    let inputWorkplace = document.getElementById('workplace');
    if (!inputLanguage.value) {
        inputLanguage.value = 'all';
    }
    if (!inputLevel.value) {
        inputLevel.value = 'all';
    }
    if (!inputWorkplace.value) {
        inputWorkplace.value = 'all';
    }
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

function updateRowResume(id) {
    $.ajaxSetup({cacheURL: false});
    $('#detailsUpdateForm').find(":input").val("");
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            $('#detailsUpdateForm').find("input[name='" + key + "']").val(value);
        });
        $('#updateRow').modal();
    });
}

function updateResumeTo() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: $('#detailsUpdateForm').serialize()
    }).done(function () {
        $("#updateRow").modal("hide");
        ctx.updateTable();
        successNoty("Saved");
    });
}

function deleteRowResume(id) {
    $.ajaxSetup({cacheURL: false});
    $('#detailsDeleteForm').find(":input[name='id']").val(id);
    $('#deleteRow').modal();
}

function deleteResumeTo() {
    var id = document.getElementById("idDelete").value;
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE",
    }).done(function () {
        $("#deleteRow").modal("hide");
        ctx.updateTable();
        successNoty("Deleted");
    });
}

// $(document).ready(function () {
$(function () {
    ctx = {
        ajaxUrl: ajaxUrl,
        datatableApi: $("#datatable").DataTable({
            "iDisplayLength": 10,
            "bPaginate": true,
            "ajax": {
                "url": ajaxUrl,
                "dataSrc": ""
            },
            columnDefs: [{
                "defaultContent": "-",
                "targets": "_all"
            }],
            "info": true,
            "columns": [
                {
                    "data": "id",
                    "visible": false
                },
                {
                    "data": "url",
                    "visible": false
                },
                {
                    "data": function (data, type, row) {
                        return '<a href="' + data.url + '">' + data.title + '</a>'
                    }
                },
                {
                    "data": function (data, type, row) {
                        if (data.name === "see the card" && type === "display") {
                            return 'see the <a href="' + data.url + '">card</a>'
                        }
                        return data.name;
                    }
                },
                {
                    "data": function (data, type, row) {
                        if (data.age === "see the card" && type === "display") {
                            return '<a href="' + data.url + '">-</a>'
                        }
                        return data.age;
                    },
                    className: "uniqueClassName"
                },
                {
                    "data": function (data, type, row) {
                        if (data.address === "see the card" && type === "display") {
                            return '<a href="' + data.url + '">-</a>'
                        }
                        return data.address;
                    },
                    className: "uniqueClassName"
                },
                {
                    "data": function (data, type, row) {
                        if (data.salary === 1 && type === "display") {
                            return '<a href="' + data.url + '">-</a>'
                        }
                        if (data.salary > 1 && type === "display") {
                            return data.salary / 100;
                        }
                        return data.salary;
                    },
                    className: "uniqueClassName"
                },
                {
                    "data": function (data, type, row) {
                        if (data.skills === "see the card" && type === "display") {
                            return 'see the <a href="' + data.url + '">card</a>'
                        }
                        return data.skills;
                    }
                },
                {
                    "data": "workBefore"
                },
                {
                    "data": "releaseDate",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return date.substring(0, 10);
                        }
                        return date;
                    }
                },
                {
                    "data": "toVote",
                    "render": function (data, type, row) {
                        if (type === "display") {
                            return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='vote($(this)," + row.id + ");'/>";
                        }
                        return data;
                    }
                },
                {
                    "data": "workplace",
                    "visible": false
                },
                {
                    "data": "language",
                    "visible": false
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": function (data, type, row) {  // update
                        if (type === "display") {
                            return "<a onclick='updateRowResume(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
                        }
                    }
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": function (data, type, row) {  // delete
                        if (type === "display") {
                            return "<a onclick='deleteRowResume(" + row.id + ");'><span class='fa fa-remove'></span></a>";
                        }
                    }
                }
            ],
            "order": [
                [
                    9,
                    "desc"
                ],
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (!data.toVote) {
                    $(row).attr("resume-vote", false);
                } else {
                    $(row).attr("resume-vote", true);
                }
            }
        }),
        updateTable: updateFilteredTable
    };
    makeEditable(ctx);
});
