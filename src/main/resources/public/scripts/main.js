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
        $("#content").prepend(html)
    });
};

const searchImages = (searchTerm) => {
    $("#loader").show();
    $.ajax({
        url: "/search",
        data: {q: searchTerm},
        cache: false
    }).done((html) => {
        $("#loader").hide();
        console.log("Received data: " + html);
        $("#content").prepend(html)
    });
};

$("#randomImageForm").submit((e) => {
    let tag = e.target["tag"].value;
    if (typeof tag !== 'undefined' && tag.trim() !== "") {
        e.target["tag"].value = "";
        fetchRandomImage(tag);
    }
    return false;
});

$("#searchImagesForm").submit((e) => {
    let searchTerm = e.target["searchTerm"].value;
    if (typeof searchTerm !== 'undefined' && searchTerm.trim() !== "") {
        e.target["searchTerm"].value = "";
        searchImages(searchTerm);
    }
    return false;
});