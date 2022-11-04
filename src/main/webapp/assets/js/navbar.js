$(document).ready(function () {
    $('#exhibitions-link').on('click', function (e) {
        let link = e.currentTarget;
        console.log($(link).text());

    });
});
