const API_TODAY = "http://localhost:8080/api/apod/today";
const API_GALLERY = "http://localhost:8080/api/apod/recent?count=10";
const API_BY_DATE = (date) => `http://localhost:8080/api/apod?date=${date}`;


document.addEventListener("DOMContentLoaded", () => {
    fetchToday();
    fetchGallery();
});

function fetchToday() {
    fetch(API_TODAY)
        .then(res => res.json())
        .then(data => renderToday(data.data))
        .catch(() => alert("Unable to load today's APOD"));
}

function fetchGallery() {
    fetch(API_GALLERY)
        .then(res => res.json())
        .then(data => renderGallery(data.data))
        .catch(() => alert("Unable to load gallery"));
}

function fetchByDate() {
    const date = document.getElementById("datePicker").value;
    if (!date) return alert("Select a date first!");
    fetch(API_BY_DATE(date))
        .then(res => res.json())
        .then(data => renderToday(data.data));
}

function renderToday(data) {
    document.getElementById("todayApod").innerHTML = `
        <div class="card-custom p-3">
            <img src="${data.hdurl || data.url}" class="apod-img">
            <h3 class="mt-3 fw-bold">${data.title}</h3>
            <p>${data.explanation.substring(0, 350)}...</p>
            <button class="btn btn-outline-light" onclick='openModal(${JSON.stringify(data)})'>View Details</button>
        </div>
    `;
}

function renderGallery(list) {
    document.getElementById("gallery").innerHTML =
        list.map(item => `
            <div class="col-md-4">
                <div class="card-custom p-2">
                    <img src="${item.url}" class="apod-img" onclick='openModal(${JSON.stringify(item)})'>
                    <h5 class="mt-2 text-center">${item.title}</h5>
                </div>
            </div>
        `).join("");
}

function openModal(data) {
    document.getElementById("modalBody").innerHTML = `
        <img src="${data.hdurl || data.url}" class="w-100 rounded mb-3">
        <h2>${data.title}</h2>
        <p class="small">📅 ${data.date} | © ${data.copyright || "Unknown"}</p>
        <p>${data.explanation}</p>
    `;
    new bootstrap.Modal(document.getElementById("detailModal")).show();
}
