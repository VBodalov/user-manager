$(document).ready(function () {

    $('#userTable').bootstrapTable({
        url: '/user/find-all',
        columns: [{
            field: 'id',
            title: 'User ID'
        }, {
            field: 'userName',
            title: 'User name'
        }, {
            field: 'password',
            title: 'Password'
        }, {
            field: 'active',
            title: 'Active',
            events: operateEvents,
            formatter: operateFormatter
        }]
    });

    $("#addUserForm").submit(function (event) {

        event.preventDefault();

        const newUser = {
            userName: $("#userName").val(),
            password: $("#password").val(),
            active: true
        };

        $.ajax({
            url: "/user/create",
            method: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(newUser)
        })
            .done(function (response) {
                $('#userTable').bootstrapTable('refresh');
            })
            .catch(function (error) {
                alert("Error: " + JSON.stringify(error));
            });
    });

    var name = $("#userNameDialog"),
        password = $("#passwordDialog"),
        allFields = $([]).add(userNameDialog).add(passwordDialog);

    dialog = $("#edit-user-dialog").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        buttons: {
            "Create an account": addUser,
            Cancel: function () {
                dialog.dialog("close");
            }
        },
        close: function () {
            form[0].reset();
            allFields.removeClass("ui-state-error");
        }
    });

    form = dialog.find("form").on("submit", function (event) {
        event.preventDefault();
        addUser();
    });

    $("#create-user").button().on("click", function () {
        dialog.dialog("open");
    });
});

window.operateEvents = {
    'click .activeToggle': function (e, value, row, index) {

        $.ajax({
            url: "/user/toggleActive/" + row.id,
            method: "POST",
            contentType: "application/json; charset=utf-8"
        })
            .done(function (response) {
                $('#userTable').bootstrapTable(
                    'updateCell',
                    {
                        index: index,
                        field: "active",
                        value: response
                    });
            })
            .catch(function (error) {
                alert("Error: " + JSON.stringify(error));
            });
    }
};

function operateFormatter(value, row, index) {
    return [
        '<a class="activeToggle" href="javascript:void(0)" title="Active toggle">',
        value,
        '</a>  '
    ].join('');
}