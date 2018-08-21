/**
 * Author pengyi
 * Date 17-2-16.
 */
$(".top-operation-back").click(function () {
    history.go(-1);
});
$(".top-operation-refresh").click(function () {
    document.getElementsByName('ifr')[0].src = document.getElementsByName('ifr')[0].src;
});
$(".top-operation-go").click(function () {
    history.go(1);
});