$(function () {
   // $('.portlet-content').css({'display':'block'});
   // $('#feed .portlet-content').css({'display':'block'});
    
    $(".column").sortable({
        connectWith: ".column"
    });
    $(".portlet").addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
        .find(".portlet-header")
        .addClass("ui-widget-header ui-corner-all")
        .prepend("<span class='ui-icon ui-icon-minusthick'></span>")
        .end()
        .find(".portlet-content");
    
    $("#feed .portlet-header span").removeClass("ui-icon-minusthick").addClass("ui-icon-plusthick");
    
    $(".portlet-header .ui-icon").click(function () {
        $(this).toggleClass("ui-icon-minusthick").toggleClass("ui-icon-plusthick");
        $(this).parents(".portlet:first").find(".portlet-content").toggle();
    });
  //  $(".column").disableSelection();
});