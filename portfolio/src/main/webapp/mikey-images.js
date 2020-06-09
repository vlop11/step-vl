function displayMikeyImage() {
    // const mikeyImages = ['mikey-1.jpg', 'mikey-2.jpg', 'mikey-3.jpg', 'mikey-4.jpg', 'mikey-5.jpg',
    // 'mikey-6.jpg', 'mikey-7.jpg', 'mikey-8.jpg', 'mikey-9.jpg', 'mikey-10.jpg', 'mikey-11.jpg',
    // 'mikey-12.jpg', 'mikey-13.jpg', 'mikey-14.jpg', 'mikey-15.jpg', 'mikey-16.jpg', 'mikey-17.jpg']

    // const mikeyImage = mikeyImages[Math.floor(Math.random() * mikeyImages.length)];

    const imageCaptions = {1: "He just... sat on our game board. He has a bed - I don't get it.", 
    2: "He's just so tiny, everything else looks so big. Like a giant kingdom.",
    3: "Sometimes we literally put him in a purse in order to bring him inside stores. Peep \
    his little paw 🥺",
    4: "I don't know why this is so funny. He just looks like Scrappy Doo.",
    5: "Self-explanatory",
    6: "He's so tiny, but he eats SO MUCH. He literally consumes things larger than his head \
    - I'm just impressed... and confused.",
    7: "I don't capture Mikey at his most photogenic moments.",
    8: "Okay, he actually looks pretty cute in this one 💓",
    9: "I feel like if you were to get a glimpse of Mikey's thoughts, it'd just be circus music... \
    looping 24/7.",
    10: "He looks judgemental in this picture, and he has no business judging other people.",
    11: "This was made because he looked like that chicken meme where the chicken just ZOOMS.",
    12: "This took so much effort and this was the best we could do.",
    13: "🥺🥺",
    14: "Not pictured. I am also wearing a matching hoodie.",
    15: "We call this one Moot after that Poot Lovato meme. If you don't know what that is, don't worry \
    about it. It won't help make sense either way.",
    16: "My brother captured this absolutely DEMONIC picture of Mikey. It literally looks like \
    a cursed image.",
    17: "I seriously don't understand why this dog is so bad at pictures."}
    
    const randNum = Math.floor(Math.random()* 17 + 1);

    const mikeyContainer = document.getElementById('mikey-container');
    const mikeyCaption = document.getElementById('mikey-caption');

    const picURL = "url(images/mikey-" + randNum + ".jpg)";

    mikeyContainer.style.backgroundImage = picURL;
    mikeyCaption.innerText = imageCaptions[randNum];
}

google.charts.load('current', {'packages': ['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'Response');
    data.addColumn('number', 'Count');
    data.addRows([
        ['Yes, absolutely', 10],
        ['No, he\'s so cute', 5],
        ['So ugly, it\'s cute', 15]
    ]);

    const options = {
        'title': 'Is Mikey ugly?',
        'width': 500,
        'height': 400
    };

    const chart = new google.visualization.PieChart(
        document.getElementById('chart-container')
    );

    chart.draw(data, options);
}