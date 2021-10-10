DROP TABLE IF EXISTS questionnaires;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS questionnaire_results;
DROP TABLE IF EXISTS answered_questions;

CREATE TABLE IF NOT EXISTS questionnaires (
    id uuid DEFAULT random_uuid() NOT NULL PRIMARY KEY,
    heading VARCHAR(250) NOT NULL
);


CREATE TABLE IF NOT EXISTS questions (
    id uuid DEFAULT random_uuid() NOT NULL PRIMARY KEY,
    questionnaire_id uuid,
    question VARCHAR(250) NOT NULL,
    question_type VARCHAR(250) NOT NULL,
    answers VARCHAR(250) NOT NULL
);

ALTER TABLE questions
    ADD CONSTRAINT "questionnaire_Id_fkey"
    FOREIGN KEY (questionnaire_id)
        REFERENCES questionnaires(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE;


CREATE TABLE IF NOT EXISTS users (
    id int NOT NULL  auto_increment PRIMARY KEY,
    username VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    role VARCHAR(250) NOT NULL
    );

CREATE TABLE IF NOT EXISTS questionnaire_results (
    id uuid DEFAULT random_uuid() NOT NULL PRIMARY KEY,
    heading VARCHAR(250) NOT NULL,
    user_id int NOT NULL,
    source_id uuid NOT NULL
    );

CREATE TABLE IF NOT EXISTS answered_questions (
    id uuid DEFAULT random_uuid() NOT NULL PRIMARY KEY,
    answered_questionnaire_id uuid,
    source_id uuid NOT NULL,
    question VARCHAR(250) NOT NULL,
    answers VARCHAR(250) NOT NULL,
    rejected_answers VARCHAR(250) NOT NULL
    );

INSERT INTO questionnaires VALUES
(random_uuid(), 'Your favourite music'), (random_uuid(), 'Your food preferences'), (random_uuid(), 'Extravert/introvert test');

INSERT INTO questions VALUES
(random_uuid(), SELECT id from questionnaires where heading='Extravert/introvert test', 'Where do you prefer spending friday nights?', 'MULTIPLE', 'At home,,,, With the friends,,,, With parents'),
(random_uuid(), SELECT id from questionnaires where heading='Extravert/introvert test', 'Where do you prefer spending having lunch?', 'SINGLE', 'At home,,,, With the colleagues,,,, Alone in cafe'),
(random_uuid(), SELECT id from questionnaires where heading='Your favourite music', 'If you have a good mood, you will rather play...', 'SINGLE', 'AC/DC - highway to hell,,,, Queen - We will rock you,,,, Eminem - just lose it'),
(random_uuid(), SELECT id from questionnaires where heading='Your favourite music', 'What will you prefer?', 'MULTIPLE', 'Rock concert,,,, Opera concert,,,, Rave'),
(random_uuid(), SELECT id from questionnaires where heading='Your food preferences', 'Fruits or vegetables?', 'SINGLE', 'Fruits,,,, Vegetables'),
(random_uuid(), SELECT id from questionnaires where heading='Your food preferences', 'Your perfect meat is...', 'SINGLE', 'Fried,,,, Boiled,,,, Stewed');

INSERT INTO users(username, password, role) VALUES
                         ('sa', 'password', 'USER'),
                         ('admin', 'admin', 'ADMIN');