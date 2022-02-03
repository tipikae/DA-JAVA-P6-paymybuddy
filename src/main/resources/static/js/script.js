jQuery(document).ready(function(){
    var url = window.location;
    var element = $('ul.sidebar-menu a').filter(function () {
        return this.href == url || url.href.indexOf(this.href) == 0;
    });
    $(element).parentsUntil('ul.sidebar-menu', 'li').addClass('active');
});