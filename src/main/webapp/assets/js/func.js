$(document).ready(function () {
    $('#itemsPerPage').on('change', function () {
        $.get(window.location.pathname, {itemsPerPage: $('#itemsPerPage').val()})
            .done(function () {
                window.location = window.location.pathname;
            });
    });
});