var slideIndex;

if (sessionStorage.getItem("currSlide") != null) {
    slideIndex = parseInt(sessionStorage.getItem("currSlide"));
} else {
    slideIndex = 1;
}

function initial() {
    var currMovie = document.getElementById("curr-movie");
    currMovie.value = slideIndex;
    showImage(slideIndex);
}

function moveImage(n) {
    slideIndex += n;
    showImage(slideIndex);
}

function showImage(n) {    
    var images = document.getElementsByClassName("movie-image");
    var captions = document.getElementsByClassName("movie-caption");

    if (n > images.length) {
        slideIndex = 1;
    }
    if (n < 1) {
        slideIndex = images.length;
    }

    for (var i = 0; i < images.length; i++) {
        images[i].style.display = "none";
        captions[i].style.display = "none";
    }

    images[slideIndex - 1].style.display = "block";
    captions[slideIndex - 1].style.display = "block";

    sessionStorage.setItem("currSlide", slideIndex);
    
    displayList();
}

function displayList() {
    var maxCont = document.getElementById("selectMax");
    var maxComment = maxCont.options[maxCont.selectedIndex].value;
    var urlString = "/data?max-comments=" + maxComment + "&movie=" + slideIndex;
    
    const container = document.getElementById("comments-section");
    container.innerHTML = "";

    fetch(urlString).then(response => response.json()).then(json => {
        for (var i = 0; i < json.length; i++) {
            var p = document.createElement("P");
            container.appendChild(p);
            p.innerText = json[i];
            p.classList.add("comment");
            
        }
    });

}

function deleteComments() {
    fetch("/delete-data", {
        method: "POST"
    }).then(response => displayList(slideIndex));
}