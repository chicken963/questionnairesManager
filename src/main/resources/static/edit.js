const http = new httpUtils;
http.get('http://localhost:8080/questionnaires')
    .then(data => insert(data))
    .catch(err => console.log(err));
let questionnaireList;

function insert(data) {
    questionnaireList = data;
    let allQuestionnairesContainer = document.querySelector("ul.questionnaires-container");
    data.forEach(questionnaire => {
        let questionnaireContainer = document.createElement("li");
        questionnaireContainer.className = "list-group-item list-group-item-light m-1";
        questionnaireContainer.innerHTML = `<div class="container notificationContainer" style="display: flex; flex-direction: row">
                <h2 class="mr-2" style="display: inline; width: 85%">${questionnaire.heading}</h2>
                <button style="display: inline; width: 15%" class="btn btn-primary d-none hideButton">Hide</button>
            </div>
            <div class="d-none">   
            </div>`;
        let editButton = document.createElement("button");
        editButton.className = "btn btn-primary edit-button";
        editButton.setAttribute("style", 'display: inline; width: 15%');
        editButton.innerText = "Edit";
        let deleteButton = document.createElement("button");
        deleteButton.className = "btn btn-danger ml-3 delete-button";
        deleteButton.setAttribute("style", 'display: inline; width: 15%');
        deleteButton.innerText = "Delete";
        let hideButton = questionnaireContainer.querySelector("button.hideButton");
        hideButton.parentElement.insertBefore(editButton, hideButton);
        hideButton.parentElement.insertBefore(deleteButton, hideButton);
        editButton.addEventListener("click", (event) => {
            let questionnaireFormConainer = event.target.parentElement.nextElementSibling;
            defineRenderForm(questionnaireFormConainer, questionnaire);
            switchToExpandedMode(questionnaireFormConainer, event.target, hideButton);
        });
        deleteButton.addEventListener("click", (event) => {
            http.delete(`http://localhost:8080/questionnaires/${questionnaire.id}`)
                .then(data => insert(data))
                .catch(err => console.log(err));
            allQuestionnairesContainer.removeChild(questionnaireContainer);
        });
        allQuestionnairesContainer.appendChild(questionnaireContainer);
    });
}

function switchToExpandedMode(questionnaireContainer, editButton, hideButton) {
    questionnaireContainer.classList.remove("d-none");
    editButton.classList.add("d-none");
    hideButton.classList.remove("d-none");
    hideButton.addEventListener("click", () =>{
        questionnaireContainer.classList.add("d-none");
        editButton.classList.remove("d-none");
        hideButton.classList.add("d-none");
    });
}

function defineRenderForm(questionnaireContainer, questionnaire) {
    questionnaireContainer.innerHTML = `
            <div class="input-group mb-2">
                <div class="input-group-prepend">
                    <span class="input-group-text bg-primary text-light">Questionnaire heading</span>
                </div>
                <input type="text" class="form-control" placeholder="enter heading..." value="${questionnaire.heading}">
            </div>
            <ul class="w-100% questions-container">
            </ul>
            <button class="btn btn-primary btn-lg btn-block" disabled>Update questionnaire</button>`;
    let addQuestionButton = document.createElement("button");
    addQuestionButton.className = "btn btn-info m-2";
    addQuestionButton.innerText = "Add question";
    addQuestionButton.setAttribute("style", "background-color: salmon; border: none")
    let questionsContainer = questionnaireContainer.querySelector("ul");
    questionsContainer.appendChild(addQuestionButton);
    let editForm = new questionEditForm(questionnaire);
    questionnaire.questions.forEach(question => {
        editForm.renderNewQuestion(questionsContainer, question);
    });
    editForm.activateInputsNotNullCheck(questionnaireContainer);
    editForm.activateAddQuestionButton(addQuestionButton, questionsContainer);
    editForm.activateUpdateQuestionnaireButton(questionnaireContainer, questionnaire);
}