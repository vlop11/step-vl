let slideIndex;

if (sessionStorage.getItem("currSlide") != null) {
    slideIndex = parseInt(sessionStorage.getItem("currSlide"));
} else {
    slideIndex = 1;
}

function initial() {
    getLoginStatus();
    showImage(slideIndex);
}

function moveImage(n) {
    slideIndex += n;
    showImage(slideIndex);
}

function showImage(n) {    
    const images = document.getElementsByClassName("movie-image");
    const captions = document.getElementsByClassName("movie-caption");

    if (n > images.length) {
        slideIndex = 1;
    }
    if (n < 1) {
        slideIndex = images.length;
    }

    for (let i = 0; i < images.length; i++) {
        images[i].style.display = "none";
        captions[i].style.display = "none";
    }

    images[slideIndex - 1].style.display = "block";
    captions[slideIndex - 1].style.display = "block";

    sessionStorage.setItem("currSlide", slideIndex);
    document.getElementById("curr-movie").value = slideIndex;
    
    displayList();
}

function displayList() {
    const selectMaxContainer = document.getElementById("selectMax");
    const maxComment = selectMaxContainer.options[selectMaxContainer.selectedIndex].value;
    const urlString = "/data?max-comments=" + maxComment + "&movie=" + slideIndex;
    
    const container = document.getElementById("comments-section");
    container.innerHTML = "";

    fetch(urlString).then(response => response.json()).then(json => {
        for (let i = 0; i < json.length; i++) {
            const p = document.createElement("P");
            container.appendChild(p);
            p.innerHTML = "<strong>" + json[i]["displayName"] + ": </strong>" + json[i]["text"];
            p.classList.add("comment");
        }
    });

}

function getLoginStatus() {
    const postButton = document.getElementById("post-btn");
    const logoutButton = document.getElementById("logout-btn");
    const loginButton = document.getElementById("login-btn");
    
    fetch("/login").then(reponse => reponse.json()).then(json => {
        if (json["Status"] == "true") {
            postButton.style.display = "block";
            logoutButton.style.display = "block";
            logoutButton.href = json["Link"];
        } else {
            loginButton.style.display = "block";
            loginButton.href = json["Link"];
        }
    });
}

function deleteComments() {
    fetch("/delete-data?movie=" + slideIndex, {
        method: "POST"
    }).then(response => displayList());
}
