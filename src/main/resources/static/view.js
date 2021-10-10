const http = new httpUtils;
const properties = new props;
http.get(`${properties.serviceUrl}/questionnaires`)
    .then(data => insert(data))
    .catch(err => console.log(err));

let currentQuestionnaire;

function insert(data) {
    let questionnairesRendered = 0;
    let questionnaireStatuses = new Map();
    data.forEach(questionnaire => {
        questionnairesRendered++;
        let questionStatuses = new Map();
        let questionsRendered = 0;

        let itemHTML =
            `<li class="list-group-item list-group-item-light m-1">
                <div class="notificationContainer_${questionnaire.heading}" style="display: flex; flex-direction: row">
                    <h2 class="mr-2" style="display: inline; width: 85%">${questionnaire.heading}</h2>
                    <button style="display: inline; width: 15%" class="btn btn-primary" id="pass-${questionnaire.id}">Pass</button>
                    <button style="display: inline; width: 15%" class="btn btn-primary d-none" id="hide-${questionnaire.id}">Hide</button>
                </div>
                <div class="questions-container ${questionnaire.id} d-none">
                <ul class="list-group">`;
        questionnaire.questions.forEach(question => {
            questionsRendered++;
            let label = "questionnaire" + questionnairesRendered + "question" + questionsRendered
            questionStatuses.set(label, false);
            let answers = question.answers.split(properties.answersSeparator);
            itemHTML += `<li class="list-group-item list-group-item-secondary m-1">
                <h5>${question.question}</h5>
                <ul class="list-group list-group-flush questions-container">`
            if (question.questionType === 'MULTIPLE') {
                answers.forEach(answer => {
                    itemHTML += `<li class="list-group-item pl-5 list-group-item-action">
                                <input class="form-check-input ${label} ${question.id}" 
                                type="checkbox" 
                                id="${question.id}${answer}">
                                 <label class="form-check-label" for="${question.id}${answer}">
                                      ${answer}
                                 </label>
                                </li>`
                });
            } else {
                answers.forEach(answer => {
                    itemHTML += `<li class="list-group-item pl-5 list-group-item-action">
                                    <input class="form-check-input ${label} ${question.id}" 
                                    type="radio" 
                                    name="${question.id}" 
                                    id="${question.id}${answer}">
                                    <label class="form-check-label" for="${question.id}${answer}">
                                        ${answer}
                                    </label>
                                    </li>`
                });
            }
            itemHTML += `</ul>
                        </li>`;
        });
        itemHTML += `</ul>
                    <button type="button" class="btn btn-success" 
                    disabled id="send-${questionnaire.id}">Send the results</button>
                    </div>
                    </li>`;
        questionnaireStatuses.set(questionnaire.id, questionStatuses);
        document.getElementsByClassName("questionnaires-container")[0].innerHTML += itemHTML;
    })
    addCheckingQuestionnaireIsFilled(data, questionnaireStatuses);
}

function defineExpandCollapleLogic(questionnaireId) {
    document.getElementById("pass-" + questionnaireId).addEventListener("click", () => {
        document.getElementsByClassName("questions-container " + questionnaireId)[0]
            .classList
            .remove("d-none");
        document.getElementById("pass-" + questionnaireId).classList.add("d-none");
        document.getElementById("hide-" + questionnaireId).classList.remove("d-none");
        document.getElementById("hide-" + questionnaireId).addEventListener("click", () =>{
            document.getElementsByClassName("questions-container " + questionnaireId)[0]
                .classList
                .add("d-none");
            document.getElementById("pass-" + questionnaireId).classList.remove("d-none");
            document.getElementById("hide-" + questionnaireId).classList.add("d-none");
        });
    })
}

function addCheckingQuestionnaireIsFilled(data, questionnaireStatuses) {
    questionnaireStatuses.forEach((questionStatuses, questionnaireId) => {
        questionStatuses.forEach((v, questionId) => {
            Array.from(document.getElementsByClassName(questionId)).forEach(elem =>
                elem.addEventListener("click",
                    () => {
                        let questionIsAnswered = false;
                        Array.from(document.getElementsByClassName(questionId)).forEach(elem =>
                            questionIsAnswered = questionIsAnswered || elem.checked);
                        questionStatuses.set(questionId, questionIsAnswered);
                        let questionnaireIsReady = true;
                        questionStatuses.forEach(value =>
                            questionnaireIsReady = questionnaireIsReady && value);
                        if (questionnaireIsReady) {
                            document.getElementById("send-" + questionnaireId).removeAttribute("disabled");
                        } else {
                            document.getElementById("send-" + questionnaireId).setAttribute("disabled", "true");
                        }
                    }));
        });
        document.getElementById("send-" + questionnaireId).addEventListener("click", ()=> {
            sendPassedQuestionnaire(data, questionnaireId);
        });
        defineExpandCollapleLogic(questionnaireId);
    });
}

function sendPassedQuestionnaire(data, questionnaireId) {
        let sourceQuestionnaire = data.filter(questionnaire => questionnaire.id === questionnaireId)[0];
        let targetQuestionnaire = Object.assign(sourceQuestionnaire);
        targetQuestionnaire.questions.forEach(question => {
            let questionId = question.id;
            let sourceAnswers = question.answers.split(properties.answersSeparator);
            let targetAnswers = [];
            let rejectedAnswers = [];
            sourceAnswers.forEach(answer => {
                if(document.getElementById(questionId + answer).checked){
                    targetAnswers.push(answer);
                } else {
                    rejectedAnswers.push(answer);
                }
            });
            question.answers = targetAnswers.join(properties.answersSeparator);
            question.rejectedAnswers = rejectedAnswers.join(properties.answersSeparator);
        });
    currentQuestionnaire = targetQuestionnaire;
    http.post(`${properties.serviceUrl}/send`, targetQuestionnaire)
        .then((data) => stub(data))
        .catch(err => showNotification(err, currentQuestionnaire, false));
}

function showNotification(text, questionnaire, success) {
    if (success) {
        document.getElementsByClassName("notificationContainer_" + questionnaire.heading)[0].innerHTML =
            `<div class="alert alert-success m-auto w-100" role="alert">
            <div class="d-flex align-items-center">
                <strong>${text}</strong>
                <div class="spinner-border ml-auto" role="status" aria-hidden="true"></div>
            </div>
        </div>`;
    } else {
        document.getElementsByClassName("notificationContainer_" + questionnaire.heading)[0].innerHTML =
            `<div class="alert alert-danger" role="alert">
            <div class="d-flex align-items-center">
                <strong>${text}</strong>
                <div class="spinner-border ml-auto" role="status" aria-hidden="true"></div>
            </div>
        </div>`;
    }

    setTimeout(() => {
        window.location.href = `${properties.serviceUrl}/view`;}, 2000)
}

function stub(data) {
    showNotification("Your results are successfully saved!", currentQuestionnaire, true);
}
