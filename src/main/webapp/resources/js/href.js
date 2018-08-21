/**
 * Created by pengyi
 * date on 2017/2/7.
 */

function load(url) {
    // document.getElementsByName("div-container")[0].style.display = 'none';
    var ifr = document.getElementsByName('ifr')[0].src = url;
    // ifr.style.display = 'block';
}

function iframeResize() {
    document.getElementsByName('ifr')[0].height = document.documentElement.clientHeight - 98;
}

function iframeLoad(obj) {
    var url = obj.contentWindow.location.href;
    if (url.indexOf("login_hf_889") !== -1) {
        window.location.href = "/login_hf_889";
    }
}