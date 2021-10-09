class props {
    serviceUrl = "http://localhost:8080";
}

let navbar = document.createElement("nav");
let serviceUrl = new props().serviceUrl;
navbar.className = "navbar navbar-expand-lg navbar-light bg-light";
navbar.innerHTML =
    `<a class="navbar-brand" href="/">Q'ester</a>
    <div class="expand navbar-expand w-100" style="display: flex; flex-direction: row; justify-content: space-between; align-items: center" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${serviceUrl}/create">Create Questionnaire</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${serviceUrl}/view">View Questionnaires</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${serviceUrl}/check">Check Answers</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${serviceUrl}/edit">Edit Questionnaires</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${serviceUrl}/users">Manage users</a>
            </li>
        </ul>
        <form action="/logout" method="post">
            <input type="submit" value="Logout">
        </form>
    </div>`;
let globalContainer = document.querySelector("div.global-container");
globalContainer.insertBefore(navbar, globalContainer.firstElementChild);