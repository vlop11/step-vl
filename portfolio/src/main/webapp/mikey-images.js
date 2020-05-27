function displayMikeyImage() {
    const mikeyImages = ['mikey-1.jpg', 'mikey-2.mov', 'mikey-3.jpg', 'mikey-4.jpg', 'mikey-5.jpg',
    'mikey-6.jpg', 'mikey-7.jpg', 'mikey-8.jpg', 'mikey-9.jpg', 'mikey-10.jpg', 'mikey-11.jpg',
    'mikey-12.jpg', 'mikey-13.jpg', 'mikey-14.jpg', 'mikey-15.jpg', 'mikey-16.jpg', 'mikey-17.mov',
    'mikey-18.jpg', 'mikey-19.jpg']

    const mikeyImage = mikeyImages[Math.floor(Math.random() * mikeyImages.length)];
    console.log("Random mikey pic: " + mikeyImage);

    const mikeyContainer = document.getElementById('mikey-container');
    const picURL = "url(images/" + mikeyImage + ")";
    mikeyContainer.style.backgroundImage = picURL;
}