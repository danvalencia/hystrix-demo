'use strict';

const fetchRandomImage = (tag) => {
    $("#loader").show();
    $.ajax({
        url: "/random",
        data: {tag: tag},
        cache: false
    }).done((html) => {
        $("#loader").hide();
        console.log("Received data: " + html);
        $("#content").append(html)
    });
};

$("#randomImageForm").submit((e) => {
    let tag = e.target["tag"].value;
    if (typeof tag !== 'undefined' && tag.trim() !== "") {
        e.target["tag"].value = "";
        fetchRandomImage(tag);
    }
    return false;
})