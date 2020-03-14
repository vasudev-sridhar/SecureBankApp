# secureBankApp

Follow these steps to setup the code:
1. Maven clean and Install the project
2. Download the create_sample.sql file from CSE 545 GDrive folder. Create sample database and run the statements in the file
3. Change the mysql username and password in application.properties to the one you use
4. Run SecureBankAppApplication.java
5. Go to localhost:8081/index.html to see the login page

API Requests
URL: http://localhost:8081/api/login
JSON Body: {
	"username":"bhargavi",
	"password":"b12345"
}

http://localhost:8081/api/logout
{
	"id":1
}

http://localhost:8081/api/balance
{
	"accountNo":1,
	"amount":-10.2
}

DB Schema

CREATE TABLE sample.user (
  user_id int(11) NOT NULL AUTO_INCREMENT,
  auth_role_id int(11) NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  dob date NOT NULL,
  contact varchar(12) NOT NULL unique ,
  email_id varchar(255) NOT NULL unique ,
  address varchar(255) NOT NULL,
  created timestamp DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (user_id),
  CONSTRAINT FK_user_role FOREIGN KEY (auth_role_id) REFERENCES auth_role (auth_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sample`.`user` (`user_id`, `auth_role_id`,`username`,`password`,`name`,`dob`, `contact`, `email_id`, `address`, `created`) VALUES
(1, 1, 'bhargavi', 'b12345', 'Bhargavi Hopper', NOW(),'4802863456', 'bhargavineti@gmail.com', 'Agave', NOW());

CREATE TABLE sample.auth_role (
  auth_role_id int(11) NOT NULL AUTO_INCREMENT,
  role_name varchar(255) DEFAULT NULL,
  role_desc varchar(255) DEFAULT NULL,
  PRIMARY KEY (auth_role_id)
);
INSERT INTO sample.auth_role VALUES (1,'ADMIN','Administrator');
INSERT INTO sample.auth_role VALUES (2,'TIER1','Tier 1 Employee');
INSERT INTO sample.auth_role VALUES (3,'TIER2','Tier 2 Employee');
INSERT INTO sample.auth_role VALUES (4,'USER','Bank User');
INSERT INTO sample.auth_role VALUES (5,'MERCHANT','Merchant customers');


CREATE TABLE sample.auth_user (
  auth_user_id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  status varchar(255) NOT NULL,
  otp int(11),
  expiry timestamp NOT NULL,
  PRIMARY KEY (auth_user_id),
  CONSTRAINT FK_auth_user_user FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE sample.account (
  account_no int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  balance decimal(10,2) NOT NULL,
  account_type int(2) NOT NULL,
  interest decimal(5,2),
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  updated timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (account_no),
  CONSTRAINT FK_ACCOUNT_USER FOREIGN KEY (user_id) REFERENCES user(user_id)
);
