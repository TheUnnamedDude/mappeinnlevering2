CREATE TABLE question_db(
  id INT NOT NULL AUTO_INCREMENT,
  question TEXT NOT NULL,
  display_possible_answer VARCHAR(256) NOT NULL,
  correct_regex VARCHAR(256) NOT NULL,
  allowed_regex VARCHAR(2048) NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO question_db(question, display_possible_answer, correct_regex, allowed_regex) VALUES('Does this work?', 'yes/no', 'y(es)?|j', 'y(es)?|j|no?');
INSERT INTO question_db(question, display_possible_answer, correct_regex, allowed_regex) VALUES('Are you sure?', 'yes/no', 'no?', 'y(es)?|j|no?');
SELECT * FROM question_db;