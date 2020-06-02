// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// function displayList() {
//     fetch('/data').then(response => response.json()).then(json => {
//         const container = document.getElementById("text-container");
//         container.innerText = "" + json[0] + json[1] + json[2];
//     });
// }

function getServerText() {
    console.log("Fetching text...");

    const responsePromise = fetch("/data");

    responsePromise.then(promiseText);
}

function promiseText(promiseResponse) {
    console.log("Handling the response...");

    const textPromise = promiseResponse.text();

    textPromise.then(addToDOM);
}

function addToDOM(textPromise) {
    console.log("Adding to DOM...");

    const container = document.getElementById("text-container");
    container.innerHTML = textPromise;
}