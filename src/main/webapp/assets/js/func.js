$(document).ready(function () {
    $('#itemsPerPage').on('change', function () {
        $.get(window.location.pathname, {itemsPerPage: $('#itemsPerPage').val()})
            .done(function () {
                window.location = window.location.pathname;
            });
    });
    $('#btn-filter-date').on('click', function () {
        $.get("/exhibitions/main",
            {
                'from': $('#from-date').val(),
                'to': $('#to-date').val()
            });

    });
});