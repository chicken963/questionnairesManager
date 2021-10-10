let user;
const http = new httpUtils;
const properties = new props;
document.getElementById("getById").addEventListener("click", (event) => {
    let id = event.target.previousElementSibling.value;
    let resultsList = document.getElementById("userList");
    resultsList.innerHTML = "";
    http.get(`${properties.serviceUrl}/result/${id}`)
        .then(userResults => insertSingleUser(userResults, document.getElementById("userList"), id))
        .catch(err => console.log(err));
})

document.getElementById("getAll").addEventListener("click", (event) => {
    let resultsList = document.getElementById("userList");
    resultsList.innerHTML = "";
    http.get(`${properties.serviceUrl}/result/all`)
        .then(data => insertAllUsersResults(data))
        .catch(err => console.log(err));
})

function insertSingleUser(userResults, sourceList, userId) {
    let userResult = document.createElement("li");
    userResult.className = "list-group-item";
    userResult.innerHTML += `<h2>Results for user ${userId}</h2>`
    if (userResults.length === 0) {
        userResult.innerHTML += `
            <div class="alert alert-danger m-2" role="alert">
                User with id ${userId} doesn't have passed questionnaires yet.
            </div>`
    } else {
       let questionnairesList =  renderUserQuestionnaires(userResults);
        userResult.appendChild(questionnairesList);
    }
    sourceList.appendChild(userResult);
}

function insertAllUsersResults(data) {
    let userResult = document.createElement("li");
    userResult.className = "list-group-item list-group-item-secondary m-1";
    let userQuestionnairesContainer = document.createElement("ul");
    userQuestionnairesContainer.className = "list-group";
    for (let [userId, userQuestionnaires] of Object.entries(data)) {
        insertSingleUser(userQuestionnaires, userQuestionnairesContainer, userId);
    }
    userResult.appendChild(userQuestionnairesContainer);
    document.getElementById("userList").appendChild(userResult);
}

function renderUserQuestionnaires(userResults) {
    let questionnairesList = document.createElement("ul");
    questionnairesList.className = "list-group";
    userResults.forEach(questionnaireResult => {
        let questionnaireContainer = renderSingleQuestionnaire(questionnaireResult);
        questionnairesList.appendChild(questionnaireContainer);
    });
    return questionnairesList;
}

function renderSingleQuestionnaire(questionnaireResult) {
    let questionnaireContainer = document.createElement("li");
    questionnaireContainer.className = "list-group-item list-group-item-light m-1";

    let questionnaireHeading = document.createElement("h3");
    questionnaireHeading.className = "mr-2";
    questionnaireHeading.innerText = questionnaireResult.heading;
    questionnaireContainer.appendChild(questionnaireHeading);

    let questionsList = document.createElement("ul");
    questionsList.className = "list-group";
    questionnaireResult.questions.forEach(question => {
        let questionContainer = renderSingleQuestion(question);
        questionsList.appendChild(questionContainer);
    });
    questionnaireContainer.appendChild(questionsList);
    return questionnaireContainer;
}

function renderSingleQuestion(question) {
    let questionContainer = document.createElement("li");
    questionContainer.className = "list-group-item list-group-item-secondary m-1";
    let questionHeader = document.createElement("h5");
    questionHeader.innerText = question.question;
    let answersContainer = document.createElement("ul");
    answersContainer.className = "list-group list-group-flush questions-container";
    let answers = question.answers.split(properties.answersSeparator);
    let rejectedAnswers = question.rejectedAnswers.split(properties.answersSeparator);
    answers.forEach(answer => {
        answersContainer.innerHTML += `
                    <li class="list-group-item pl-5 list-group-item-action">
                        <input class="form-check-input" type="checkbox" id="${question.id}${answer}" checked disabled>
                        <label class="form-check-label" for="${question.id}${answer}">${answer}</label>
                    </li>`
    })
    rejectedAnswers.forEach(answer => {
        answersContainer.innerHTML += `
                    <li class="list-group-item pl-5 list-group-item-action">
                        <input class="form-check-input" type="checkbox" id="${question.id}${answer}" disabled>
                        <label class="form-check-label" for="${question.id}${answer}">${answer}</label>
                    </li>`
    });
    questionContainer.appendChild(questionHeader);
    questionContainer.appendChild(answersContainer);
    return questionContainer;
}

