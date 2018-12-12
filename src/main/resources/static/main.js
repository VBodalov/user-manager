$(document).ready(function () {
    // $.ajax({
    //     url: "/user/find-all"
    // }).then(function (data) {
    //     $('.users').append(data)
    // })

    var table = $('#usersTable').DataTable({
        "sAjaxSource": "/user/find-all",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "id"},
            { "mData": "userName" },
            { "mData": "password" },
            { "mData": "blocked" } //TODO rename blocked to active
        ]
    })
});