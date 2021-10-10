const properties = new props;
class questionEditForm {
    allFieldsFilled = false;

    constructor(questionnaire) {
        this.questionnaire = questionnaire;
    }

    activateAddQuestionButton(button, questionsContainer) {
        if (button) {
            button.addEventListener("click", () => this.renderNewQuestion(questionsContainer, null));
        } else {
            document.getElementById("addQuestion").addEventListener("click", () => this.renderNewQuestion(null, null));
        }
    }

    prepareAnswersList(answers, questionNumber, questionsContainer) {
        let answersList = document.createElement("ul");
        answersList.className = `list-group answers-container question${questionNumber}`;
        if (answers) {
            for (let index in answers) {
                answersList.appendChild(this.prepareAnswerItem(answers[index], questionsContainer));
            }
        } else {
            for (let i = 0; i < 2; i++) {
                answersList.appendChild(this.prepareAnswerItem(null));
            }
        }
        this.labelAnswers(answersList);
        return answersList;
    }

    labelAnswers(answersList) {
        for (let counter = 0; counter < answersList.childElementCount; counter++) {
            answersList.querySelectorAll("input")[counter].setAttribute("placeholder", `answer ${counter + 1}...`);
        }
    }

    activateInputsNotNullCheck(questionnaireContainer) {
        if (questionnaireContainer) {
            questionnaireContainer.querySelectorAll("input").forEach(input => this.activateInputNotNullCheck(input, questionnaireContainer));
        } else {
            document.querySelectorAll("input").forEach(input => this.activateInputNotNullCheck(input));
        }
    }

    activateInputNotNullCheck(input, questionnaireContainer) {
        input.addEventListener("input", () => this.updateSubmitButtonState(questionnaireContainer
            ? questionnaireContainer
            : null));
    }

    prepareAnswerItem(answer, questionsContainer) {
        let newAnswer = document.createElement("li");
        newAnswer.className = "list-group-item answer-item";
        let input = document.createElement("input");
        input.setAttribute("type", "text");
        input.className = "form-control w-85 answer-input";
        if (answer) {
            input.setAttribute("value", answer);
        }
        this.activateInputNotNullCheck(input, questionsContainer);
        let button = document.createElement("button");
        button.setAttribute("type", "button");
        button.className = "btn btn-outline-warning w-15 remove-answer";
        button.innerText = "Remove";
        this.teachToRemoveAnswerItem(button);
        newAnswer.appendChild(input);
        newAnswer.appendChild(button);
        if (document.getElementById("createQuestionnaire")) {
            document.getElementById("createQuestionnaire").setAttribute("disabled", "disabled");
        }
        return newAnswer;
    }

    teachToRemoveAnswerItem(button) {
        button.addEventListener("click", (event) => {
            let answerItem = event.target.parentElement;
            let answersList = answerItem.parentElement;
            if (answersList != null) {
                answersList.removeChild(answerItem);
                this.labelAnswers(answersList);
                if (answersList.childElementCount === 1) {
                    document.getElementById("createQuestionnaire").setAttribute("disabled", "disabled");
                } else {
                    this.updateSubmitButtonState();
                }
            }
        });
    }

    teachToRemoveQuestionItem(button) {
        button.addEventListener("click", (event) => {
            let questionItem = event.target.parentElement.parentElement;
            let questionList = questionItem.parentElement;
            if (questionList != null) {
                questionList.removeChild(questionItem);
                let currentQuestionIndex = 1;
                questionList.querySelectorAll("span.question-heading").forEach(heading => {
                    heading.textContent = "Question " + currentQuestionIndex;
                    currentQuestionIndex++;
                })
            }
            this.updateSubmitButtonState();
        })
    }

    prepareAddAnswerButton() {
        let button = document.createElement("button");
        button.className = "btn btn-warning mt-2 add-answer";
        button.innerText = "Add answer";
        button.addEventListener("click", (event) => {
            let answersList = event.target.previousElementSibling;
            answersList.appendChild(this.prepareAnswerItem(null));
            this.labelAnswers(answersList);
        });
        return button;
    }

    prepareQuestionInput(question, questionsContainer) {
        let questionInput = document.createElement("input");
        questionInput.className = "form-control question-name";
        questionInput.setAttribute("placeholder", "enter question...");
        questionInput.setAttribute("type", "text");
        if (question) {
            questionInput.setAttribute("value", question);
        }
        this.activateInputNotNullCheck(questionInput, questionsContainer);
        return questionInput;
    }

    prepareIdInput(questionData, questionsContainer) {
        let idInput = document.createElement("input");
        idInput.setAttribute("type", "hidden");
        idInput.className = "form-control question-id";
        if (questionData) {
            idInput.setAttribute("value", questionData.id);
        }
        this.activateInputNotNullCheck(idInput, questionsContainer);
        return idInput;
    }

    prepareRemoveQuestionButton() {
        let removeQuestionButton = document.createElement("button");
        removeQuestionButton.className = "btn btn-outline-danger w-10 remove-question";
        removeQuestionButton.setAttribute("type", "text");
        removeQuestionButton.innerText = "Remove";
        this.teachToRemoveQuestionItem(removeQuestionButton);
        return removeQuestionButton;
    }

    renderNewQuestion(questionsContainer, questionData) {
        if (!questionsContainer) {
            questionsContainer = document.querySelector("ul.questions-container");
        }
        let newQuestionNumber = questionsContainer ? questionsContainer.childElementCount : 1;
        if (questionsContainer) {
            let newQuestion = document.createElement("li");
            newQuestion.className = "list-group-item question-container";
            newQuestion.innerHTML +=
                `<div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text question-heading">Question ${newQuestionNumber}</span>
                    </div>
                </div>
                <div>
                    <label class="switch">
                        <input type="checkbox">
                        <span class="slider round"></span>
                    </label>
                    <span>Multiple answers are allowed</span>
                </div>`;
            if (questionData && questionData.questionType === "MULTIPLE") {
                newQuestion.querySelector("input").setAttribute("checked", "checked");
            }
            newQuestion.firstChild.appendChild(this.prepareQuestionInput(questionData ? questionData.question : null, questionData ? questionsContainer : null));
            newQuestion.firstChild.appendChild(this.prepareIdInput(questionData, questionsContainer));
            newQuestion.firstChild.appendChild(this.prepareRemoveQuestionButton());
            newQuestion.appendChild(this.prepareAnswersList(questionData ? questionData.answers.split(properties.answersSeparator) : null, newQuestionNumber, questionsContainer));
            newQuestion.appendChild(this.prepareAddAnswerButton());
            this.teachToSwitchQuestionType(newQuestion);
            questionsContainer.insertBefore(newQuestion, questionsContainer.lastElementChild);
        }
    }

    updateSubmitButtonState(questionnaireContainer) {
        let inputs;
        if (questionnaireContainer) {
            inputs = questionnaireContainer.querySelectorAll('input[type="text"]');
        } else {
            inputs = document.querySelectorAll('input[type="text"]');
        }
        inputs = [...inputs]
        this.allFieldsFilled = inputs.every(input => input.value.length > 0);
        if (questionnaireContainer && questionnaireContainer.querySelector("button.btn-block")) {
            if (this.allFieldsFilled) {
                questionnaireContainer.querySelector("button.btn-block").removeAttribute("disabled");
            } else if (questionnaireContainer.querySelector("button.btn-block")) {
                questionnaireContainer.querySelector("button.btn-block").setAttribute("disabled", "disabled");
            }
        } else if (!questionnaireContainer && document.querySelector("button.btn-block")) {
            if (this.allFieldsFilled) {
                document.querySelector("button.btn-block").removeAttribute("disabled");
            } else {
                document.querySelector("button.btn-block").setAttribute("disabled", "disabled");
            }
        }
    }

    activateCreateQuestionnaireButton() {
        document.querySelector("button.btn-block").addEventListener("click", () => {
            let questionnaireName = document.getElementById("questionnaireHeading").value;
            let questions = [];
            document.querySelectorAll("li.question-container").forEach(questionContainer => {
                let questionName = questionContainer.querySelector("input.question-name").value;
                let answers = [];
                questionContainer.querySelectorAll("input.answer-input")
                    .forEach(answerInput => answers.push(answerInput.value));
                let question = {
                    question: questionName,
                    questionType: questionContainer.querySelector('input[type="checkbox"]').checked ? "MULTIPLE" : "SINGLE",
                    answers: answers.join(properties.answersSeparator)
                }
                questions.push(question);
            })

            const data = {
                heading: questionnaireName,
                questions: questions
            }
            const http = new httpUtils;
            http.post(`${properties.serviceUrl}/new`, data)
                .then(data => this.notifySuccess(null, data))
                .catch(err => this.notifyFailure(null, data));
        })
    }

    activateUpdateQuestionnaireButton(questionnaireEditContainer, questionnaire) {
        questionnaireEditContainer.getElementsByClassName("btn-block")[0].addEventListener("click", () => {
            let questionnaireName = questionnaireEditContainer.getElementsByTagName("input")[0].value;
            let questions = [];
            questionnaireEditContainer.querySelectorAll("li.question-container").forEach(questionContainer => {
                let questionName = questionContainer.querySelector("input.question-name").value;
                let questionId = questionContainer.querySelector("input.question-id").value;
                let answers = [];
                questionContainer.querySelectorAll("input.answer-input")
                    .forEach(answerInput => answers.push(answerInput.value));
                let question = {
                    question: questionName,
                    id: questionId,
                    questionType: questionContainer.querySelector('input[type="checkbox"]').checked ? "MULTIPLE" : "SINGLE",
                    answers: answers.join(properties.answersSeparator)
                }
                questions.push(question);
            })

            const data = {
                heading: questionnaireName,
                id: questionnaire.id,
                questions: questions
            }
            const http = new httpUtils;
            http.put(`${properties.serviceUrl}/update`, data)
                .then(data => this.notifySuccess(questionnaireEditContainer, data))
                .catch(err => this.notifyFailure(questionnaireEditContainer, data));
        })
    }

    teachToSwitchQuestionType(question) {
        question.querySelector("span.slider").addEventListener("click", (event) => {
            event.target.previousElementSibling.click();
            event.preventDefault();
        })
    }

    notifySuccess(questionnaireEditContainer, data) {
        let container = questionnaireEditContainer
            ? questionnaireEditContainer
            : document.querySelector("div.container");
        container.innerHTML =
            `<div class="alert alert-success m-auto w-100" role="alert">
            <div class="d-flex align-items-center">
                <strong>The questionnaire is successfully created. You'll be redirected to home page in a few seconds...</strong>
                <div class="spinner-border ml-auto" role="status" aria-hidden="true"></div>
            </div>
        </div>`;


        setTimeout(() => {
            window.location.href = `${properties.serviceUrl}/view`;
        }, 2000);
    }

    notifyFailure(questionnaireEditContainer, data) {
        let container = questionnaireEditContainer
            ? questionnaireEditContainer
            : document.querySelector("div.container");
        container.innerHTML =
            `<div class="d-flex align-items-center">
                <strong>Oops! Something went wrong. Please try again.</strong>
                <div class="spinner-border ml-auto" role="status" aria-hidden="true"></div>
            </div>`;

        setTimeout(() => {
            window.location.href = `${properties.serviceUrl}/view`;
        }, 2000);
    }
}