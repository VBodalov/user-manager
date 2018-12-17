$(document).ready(function () {

    // $.ajax({
    //     url: "/user/find-all"
    // }).then(function (data) {
    //     $('.users').append(data)
    // })

    // var table = $('#usersTable').DataTable({
    //     "sAjaxSource": "/user/find-all",
    //     "sAjaxDataProp": "",
    //     "order": [[ 0, "asc" ]],
    //     "aoColumns": [
    //         { "mData": "id"},
    //         { "mData": "userName" },
    //         { "mData": "password" },
    //         { "mData": "active" }
    //     ]
    // })

    // const axios = require('axios');

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
            title: 'User password'
        }, {
            field: 'active',
            title: 'Active',
            events: operateEvents,
            formatter: operateFormatter
        }]
    });
});

window.operateEvents = {
    'click .activeToggle': function (e, value, row, index) {

        axios.post('/user/toggleActive?userId=' + row.userId, {})
            .then(function (response) {
                $('#userTable').bootstrapTable(
                    'updateCell', {
                        index: index,
                        field: "active",
                        value: response
                    });
                alert(response);
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