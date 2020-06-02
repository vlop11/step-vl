var slideIndex = 1;

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
}

function displayList() {
    fetch('/data').then(response => response.json()).then(json => {
        const container = document.getElementById("comments-container");
        
        for (var i = 0; i < json.length; i++) {
            var p = document.createElement("P");
            p.classList.add("comment");
            p.innerText = json[i];
            container.appendChild(p);
        }
    });
}