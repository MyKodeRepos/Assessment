DROP TABLE IF EXISTS users;

CREATE TABLE users (
  employeeId VARCHAR(255) PRIMARY KEY NOT NULL,
  login VARCHAR(255) NOT NULL,
  name VARCHAR(250) NOT NULL,
  salary Decimal(13,2) DEFAULT NULL
);

INSERT INTO users (employeeId, login, name,salary) VALUES
  ('e0001','hpotter','Harry Potter',1234.00),
  ('e0002','rwesley','Ron Weasley',19234.50),
  ('e0004','rhagrid','Rubeus Hagrid',3999.99),
  ('e0003','ssnape','Severus Snape',4000.00);