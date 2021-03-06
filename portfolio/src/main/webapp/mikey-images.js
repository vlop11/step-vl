function displayMikeyImage() {

    const imageCaptions = {
        1: "He just... sat on our game board. He has a bed - I don't get it.", 
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
        17: "I seriously don't understand why this dog is so bad at pictures."
    }
    
    const randNum = Math.floor(Math.random()* 17 + 1);

    const mikeyContainer = document.getElementById('mikey-container');
    const mikeyCaption = document.getElementById('mikey-caption');
    mikeyContainer.style.width = "750px";
    mikeyContainer.style.height = "750px";

    const picURL = "url(images/mikey-" + randNum + ".jpg)";

    mikeyContainer.style.backgroundImage = picURL;
    mikeyCaption.innerText = imageCaptions[randNum];
}

google.charts.load('current', {'packages': ['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    fetch('/chart-data').then(response => response.json())
    .then((chartVotes) => {
        const data = new google.visualization.DataTable();
        data.addColumn('string', 'Response');
        data.addColumn('number', 'Count');
        Object.keys(chartVotes).forEach((choice) => {
            data.addRow([choice, chartVotes[choice]]);
        });

        const options = {
        'title': 'Is Mikey ugly?',
        'titleTextStyle': {'fontSize': 20},
        'width': 500,
        'height': 400
        };

        const chart = new google.visualization.PieChart(
            document.getElementById('chart-container')
        );

        chart.draw(data, options);
    
    });
}
