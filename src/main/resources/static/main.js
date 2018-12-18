$(document).ready(function () {

    // $.ajax({
    //     url: "/user/find-all"
    // }).then(function (data) {
    //     $('.users').append(data)
    // })

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
        axios.post('/user/create', {
            id: null,
            userName: $("#userName").val(),
            password: $("#password").val(),
            active: true
        })
            .then(function (response) {

            })
            .catch(function (error) {
                alert(error);
            });
    });
});

window.operateEvents = {
    'click .activeToggle': function (e, value, row, index) {

        axios.post('/user/toggleActive/' + row.id, {})
            .then(function (response) {
                $('#userTable').bootstrapTable(
                    'updateCell', {
                        index: index,
                        field: "active",
                        value: response.data
                    });
            })
            .catch(function (error) {
                alert(error);
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