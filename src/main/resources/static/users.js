const http = new httpUtils;
http.get('http://localhost:8080/user/all')
    .then(data => insert(data))
    .catch(err => console.log(err));

function insert(data) {
    let usersContainer = document.querySelector("ul.users-container");
    data.forEach(user => {
        let userContainer = document.createElement("li");
        userContainer.className = "list-group-item list-group-item-light m-1 d-flex flex-row align-items-center";
        let infoContainer = document.createElement("div");
        infoContainer.className = "d-flex flex-row flex-grow-1";

        let nameContainer = document.createElement("div");
        nameContainer.className = "col-3"
        nameContainer.innerHTML = `<label for="name_${user.id}">Username</label>`
        let nameInput = document.createElement("input");
        nameInput.setAttribute("type", "text");
        nameInput.setAttribute("id", `name_${user.id}`);
        nameInput.setAttribute("value", user.username);
        nameInput.className = "form-control";
        nameContainer.appendChild(nameInput);

        let passwordContainer = document.createElement("div");
        passwordContainer.className = "col-3"
        passwordContainer.innerHTML = `<label for="pass_${user.id}">Password</label>`
        let passwordInput = document.createElement("input");
        passwordInput.setAttribute("type", "password");
        nameInput.setAttribute("id", `pass_${user.id}`);
        passwordInput.innerText = user.password;
        passwordInput.className = "form-control";
        passwordContainer.appendChild(passwordInput);

        infoContainer.appendChild(nameContainer);
        infoContainer.appendChild(passwordContainer);

        let adminContainer = document.createElement("div");
        adminContainer.className = "col-2 d-flex align-items-center";
        adminContainer.innerHTML = `<label class="switch"><span>Admin</span>
                                        <input type="checkbox" class="admin-toggle">
                                            <span class="slider round"></span>
                                    </label>`;
        infoContainer.appendChild(adminContainer);
        userContainer.appendChild(infoContainer);

        let adminToggle = userContainer.querySelector("input.admin-toggle");
        if (user.admin) {
            adminToggle.setAttribute("checked", "checked");
        }
        let updateButtonContainer = document.createElement("div");
        let updateUserButton = document.createElement("button");
        updateUserButton.className = "btn btn-danger h-40 w-10";
        updateUserButton.innerText = "Update user";
        updateButtonContainer.appendChild(updateUserButton);
        userContainer.appendChild(updateButtonContainer);
        usersContainer.appendChild(userContainer);
        updateUserButton.addEventListener("click", (event) => {
            let updatedUser = {
                id: user.id,
                username: nameInput.value,
                password: passwordInput.value,
                admin: adminToggle.checked
            };
            http.put(`http://localhost:8080/user/update`, updatedUser)
                .then(data => notifySuccess(userContainer, data))
                .catch(err => notifyFailure(userContainer, err));
        })
    })
}

function notifySuccess(usercontainer, data) {
    usercontainer.innerHTML =
        `<div class="alert alert-success m-auto w-100" role="alert">
            <div class="d-flex align-items-center">
                <strong>User is successfully updated. The changes are being applied...</strong>
                <div class="spinner-border ml-auto" role="status" aria-hidden="true"></div>
            </div>
        </div>`;


    setTimeout(() => {
        window.location.href = "http://localhost:8080/users";
    }, 2000);
}

function notifyFailure(usercontainer, err) {
    usercontainer.innerHTML =
        `<div class="d-flex align-items-center">
                <strong>Oops! Something went wrong. Please try again. Details: ${err}</strong>
                <div class="spinner-border ml-auto" role="status" aria-hidden="true"></div>
            </div>`;

    setTimeout(() => {
        window.location.href = "http://localhost:8080/users";
    }, 2000);
}