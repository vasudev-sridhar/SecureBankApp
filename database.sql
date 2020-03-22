SELECT * FROM sample.user;

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

DROP TABLE IF EXISTS sample.account;
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

CREATE TABLE sample.auth_permission (
	auth_permission_id int(11) NOT NULL AUTO_INCREMENT,
    perm_name varchar(255) NOT NULL,
	perm_desc varchar(255) DEFAULT NULL,
	PRIMARY KEY (auth_permission_id)
);

CREATE TABLE sample.auth_role_permission (
	auth_role_permission_id int(11) NOT NULL AUTO_INCREMENT,
    auth_role_id int(11) NOT NULL,
    auth_permission_id int(11) NOT NULL,
	PRIMARY KEY (auth_role_permission_id),
    CONSTRAINT FK_auth_role_permission__auth_role FOREIGN KEY (auth_role_id) REFERENCES auth_role(auth_role_id),
    CONSTRAINT FK_auth_role_permission__auth_permission FOREIGN KEY (auth_permission_id) REFERENCES auth_permission(auth_permission_id)
);

SELECT * FROM ACCOUNT;
ALTER TABLE bank.account AUTO_INCREMENT=1000000;

INSERT INTO `sample`.`ACCOUNT` (`account_no`, `user_id`, `balance`, `account_type`, `interest`, `created`, `updated`) VALUES (1, 1, 998.10, 0, 0, NOW(), NOW());
INSERT INTO `sample`.`ACCOUNT` (`account_no`, `user_id`, `balance`, `account_type`, `interest`, `created`, `updated`) VALUES (2, 1, 15, 0, 0, NOW(), NOW());

INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('VIEW_CUSTOMER_ACCOUNT');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('CREATE_CUSTOMER_ACCOUNT');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('MODIFY_CUSTOMER_ACCOUNT');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('CLOSE_CUSTOMER_ACCOUNT');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('AUTHORIZE_NON_CRITICAL_TRANSACTIONS');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('AUTHORIZE_CRITICAL_TRANSACTIONS');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('VIEW_EMPLOYEE_ACCOUNT');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('CREATE_EMPLOYEE_ACCOUNT');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('MODIFY_EMPLOYEE_ACCOUNT');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('CLOSE_EMPLOYEE_ACCOUNT');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('AUTHORIZE_EMPLOYEE_REQUEST');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('VIEW_SYSTEM_LOG');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('AUTHORIZE_CUSTOMER_TRANSFER_REQUEST');
INSERT INTO `sample`.`auth_permission` (`perm_name`) VALUES ('CREATE_ACCOUNT_REQUEST');

#Admin
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 2);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 2);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 3);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 4);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 5);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 6);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 7);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 8);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 9);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 10);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 11);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 12);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 13);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 14);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (1, 15);

INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (2, 2);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (2, 6);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (2, 14);

INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (3, 2);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (3, 3);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (3, 4);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (3, 5);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (3, 6);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (3, 7);

INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (4, 14);
INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (4, 15);

INSERT INTO `sample`.`auth_role_permission` (`auth_role_id`, `auth_permission_id`) VALUES (5, 14);


#CONSTRAINT FK_auth_user_role FOREIGN KEY (auth_role_id) REFERENCES auth_role (auth_role_id)
