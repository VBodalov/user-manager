$(document).ready(function () {

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
        }],
        onDblClickRow: function (row, $element, field) {
            userId = (row.userId);
            name.val(row.userName);
            dialog.dialog("open");
        }
    });

    let userId;

    var name = $("#editUserDialogName"),
        password = $("#editUserDialogPassword"),
        allFields = $([]).add(name).add(password);

    const dialog = $("#edit-user-dialog").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        buttons: {
            "Save": addUser,
            Cancel: function () {
                dialog.dialog("close");
            }
        },
        close: function () {
            form[0].reset();
            allFields.removeClass("ui-state-error");
        }
    });

    const form = dialog.find("form").on("submit", function (event) {
        event.preventDefault();
        addUser(name.val(), password.val(), userId);
    });

    $("#edit-user").button().on("click", function () {
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

function addUser(name, password, userId) {

    alert(name);
    alert(password);
    alert(userId);

    const userCredentials = {
        userName: name,
        password: password
    };

    $.ajax({
        url: "/user/updateCredentials/" + userId,
        method: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(userCredentials)
    })
        .done(function (response) {
            $('#userTable').bootstrapTable('refresh');
        })
        .catch(function (error) {
            alert("Error: " + JSON.stringify(error))
        });
}