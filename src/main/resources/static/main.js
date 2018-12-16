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
            title: 'Active'
            // events: operateEvents
            // formatter: operateFormatter
        }]
    });


    window.operateEvents = {
        'click .activeTogle': function (e, value, row, index) {
            alert("fsdfsdf");
        }
    };


});

function operateFormatter(value, row, index) {
    return [
        '<a class="activeTogle" href="javascript:void(0)" title="Active togle">',
        'active',
        '</a>  '
    ].join('');
}