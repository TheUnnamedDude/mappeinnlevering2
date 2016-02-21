CREATE DATABASE pg4100innlevering2;
USE pg4100innlevering2;

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
INSERT INTO question_db(question, display_possible_answer, correct_regex, allowed_regex) VALUES('Hvem har skrevet GUNS, GERMS AND STEEL?', '', 'diamond, jared', '.+');
SELECT * FROM question_db;

CREATE TABLE `pg4100innlevering2` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `forfatter` CHAR(255) NOT NULL,
  `tittel` CHAR(255) NOT NULL,
  `ISBN` CHAR(255) NOT NULL,
  `sider` INT NOT NULL,
  `utgitt` INT NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO `pg4100innlevering2` (`forfatter`, `tittel`, `ISBN`, `sider`, `utgitt`) VALUES
  ('NYGÅRDSHAUG, GERT', 'MENGELE ZOO', '978-82-02-28849-5', 455, 2008),
  ('DIAMOND, JARED', 'GUNS, GERMS AND STEEL', '0-099-30278-0', 480, 2005),
  ('KEHLMANN, DANIEL', 'OPPMÅLINGEN AV VERDEN', '978-82-05-38839-0', 250, 2008),
  ('ESPEDAL, TOMAS', 'IMOT KUNSTEN', '978-82-05-39616-6', 164, 2009),
  ('TOLKIEN, J. R. R.', 'THE HOBBIT', '0048230707', 279, 1966),
  ('ECO, UMBERTO', 'ROSENS NAVN', '82-10-02718-2', 551, 1985),
  ('ATWOOD, MARGARET', 'THE YEAR OF THE FLOOD', '978-1-84408-564-4', 518, 2010),
  ('NESBØ, JO', 'SØNNEN', '978-8-20335-593-6', 422, 2014
);