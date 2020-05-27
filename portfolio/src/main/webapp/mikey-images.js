function displayMikeyImage() {
    // const mikeyImages = ['mikey-1.jpg', 'mikey-2.jpg', 'mikey-3.jpg', 'mikey-4.jpg', 'mikey-5.jpg',
    // 'mikey-6.jpg', 'mikey-7.jpg', 'mikey-8.jpg', 'mikey-9.jpg', 'mikey-10.jpg', 'mikey-11.jpg',
    // 'mikey-12.jpg', 'mikey-13.jpg', 'mikey-14.jpg', 'mikey-15.jpg', 'mikey-16.jpg', 'mikey-17.jpg']

    // const mikeyImage = mikeyImages[Math.floor(Math.random() * mikeyImages.length)];
    const randNum = Math.floor(Math.random() * 17 + 1);
    console.log("Rand num: " + randNum);

    const mikeyContainer = document.getElementById('mikey-container');
    var width, height;
    
    if (randNum != 4 && randNum != 6 && randNum != 17) {
        width = "650px";
        height = "650px";
    } else {
        width = "650px";
        height = "500px";
    }

    console.log("Width: " + width + " - Height: " + height);

    const picURL = "url(images/mikey-" + randNum + ".jpg)";
    mikeyContainer.style.width = width;
    mikeyContainer.style.height = height;

    mikeyContainer.style.backgroundImage = picURL;

    console.log("Actual width: " + mikeyContainer.width);
    console.log("Actual height: " + mikeyContainer.height);
}