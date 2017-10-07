function toggleOverlay(){
    console.log("toggleOverlay");
    var overlay = document.getElementById('overlay-background');
    var specialBox = document.getElementById('overlay-box');
    // overlay.style.opacity = .8;
    if(specialBox.style.display == "block"){
        overlay.style.display = "none";
        specialBox.style.display = "none";
    } else {
        overlay.style.display = "block";
        specialBox.style.display = "block";

    }
}
function toggleLifeOverlay(overlayId, toggleBackground){
    var overlay = document.getElementById('overlay-background');
    var specialBox = document.getElementById(overlayId);

    $("#" + overlayId).prependTo($("#overlay-background"));

    // overlay.style.opacity = .8;
    if(specialBox.style.display == "block"){
        if (toggleBackground)
        {
            overlay.style.display = "none";
        }
        specialBox.style.display = "none";
    } else {
        if (toggleBackground)
        {
            overlay.style.display = "block";
        }
        specialBox.style.display = "block";

    }
}

function toggleAddPanel(){
    var addPanel = $("#add-movement-overlay-box");
    addPanel.insertAfter($("#create-new-movement-button"));
    if(addPanel.css('display') == "block"){
        addPanel.css('display', 'none');
    } else {
        addPanel.css('display', 'block');
    }
}

function toggleEditPanel($event, panelName, editMode) {
    var editPanel = $('#' + panelName);
    if (editMode) {
        $('#add-movement-overlay-box').css('display', 'none');
        $('#edit-movement-overlay-box').css('display', 'none');
        $('#edit-transfer-overlay-box').css('display', 'none');
        editPanel.css('display', 'block');
        var closestRow = $event.currentTarget.closest('.row');
        editPanel.insertAfter(closestRow);
        closestRow.scrollIntoView();
    } else {
        editPanel.css('display', 'none');
        editPanel.insertAfter($('#movement-overlays'));
    }
}

function toggleExtendPanel($event, panelName, editMode) {
    var editPanel = $('#' + panelName);
    if (editMode) {
        editPanel.css('display', 'block');
        // var closestRow = $event.currentTarget.closest('.panel-heading');
        var closestRow = $event.currentTarget.closest('.cockpit-item');

        editPanel.insertAfter(closestRow);
        closestRow.scrollIntoView();
    } else {
        editPanel.css('display', 'none');
        editPanel.insertAfter($('#category-overlays'));
    }
}

$('.input-alphanum').bind('keypress', function (event) {
    var regex = new RegExp("^[a-zA-Z0-9]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
        event.preventDefault();
        return false;
    }
});

function activaPill(pill){
    $('.nav-pills a[href="#' + pill + '"]').tab('show');
};

// $('div.taphover').on('touchstart', function(e){
//    'use strict';
//     console.log('div.taphover');
//     var link = $(this);
//     if (link.hasClass('hover')) {
//         return true;
//     }
//     else {
//         link.addClass('hover');
//         $('div.taphover').not(this).removeClass('hover');
//         e.preventDefault();
//         return false;
//     }
// });
