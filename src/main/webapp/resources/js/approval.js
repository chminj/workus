$(function () {
    // today default setting in modal
    let todayDate = new Date().toISOString().substring(0, 10);
    $('#apvFromDate').val(todayDate);
    $('#apvToDate').val(todayDate);

    $(document).ready(function () {
        $('#summernote').summernote({
            height: 300,                 // set editor height
            minHeight: null,             // set minimum height of editor
            maxHeight: null,             // set maximum height of editor
            focus: true                  // set focus to editable area after initializing summernote
        });
    });

    $(document).ready(function () {
        $('#summernote').summernote();
    });
})