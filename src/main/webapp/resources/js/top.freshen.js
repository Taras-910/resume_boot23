var freshenUrl = "profile/freshen";
let spinner1 = document.getElementById("spinner1");
let spinner2 = document.getElementById("spinner2");

function refreshDB() {
    spinner1.style.visibility = 'hidden';
    spinner2.style.visibility = 'hidden';
    $('#detailsRefreshForm').find(":input").val("");
    $("#refreshRow").modal();
}

function sendRefresh() {
    spinner1.style.visibility = 'visible';
    spinner2.style.visibility = 'visible';
    let languageFilter = document.getElementById('language');
    let levelFilter = document.getElementById('level');
    // let workplaceFilter = document.getElementById('workplace');
    languageFilter.value = document.getElementById('languageTask').value;
    levelFilter.value = document.getElementById('levelTask').value;
    // workplaceFilter.value = document.getElementById('workplaceTask').value;
    $.ajax({
        type: "POST",
        url: freshenUrl,
        data: $("#detailsRefreshForm").serialize()
    }).done(function () {
        $("#refreshRow").modal("hide");
        ctx.updateTable();
        successNoty("Update has finished ");
    });
}
