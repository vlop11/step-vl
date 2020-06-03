var slideIndex;

if (sessionStorage.getItem("currMovie") != null) {
    slideIndex = parseInt(sessionStorage.getItem("currMovie"));
} else {
    slideIndex = 1;
}

function initial() {
    showImage(slideIndex);
    displayList();
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

    sessionStorage.setItem("currMovie", slideIndex);
}

function displayList() {
    var maxCont = document.getElementById("selectMax");
    var maxComment = maxCont.options[maxCont.selectedIndex].value;
    var urlString = "/data?max-comments=" + maxComment;
    
    const container = document.getElementById("comments-section");
    container.innerHTML = "";

    fetch(urlString).then(response => response.json()).then(json => {
        for (var i = 0; i < json.length; i++) {
            var p = document.createElement("P");
            p.classList.add("comment");
            p.innerText = json[i];
            container.appendChild(p);
        }
    });
}