$(function () {
    // today default setting in modal
    let todayDate = new Date().toISOString().substring(0, 10);
    $('#apvFromDate').val(todayDate);
    $('#apvToDate').val(todayDate);
})