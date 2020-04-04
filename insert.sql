INSERT INTO `sample`.`user` (`user_id`, `auth_role_id`,`username`,`password`,`name`,`dob`, `contact`, `email_id`, `address`, `created`) VALUES 
(30000, 1, 'bhargavi', '8989e805956d8fdeeeaf0007ac273217', 'Bhargavi Neti', NOW(),'4802863456', 'bhargavineti@gmail.com', 'Agave', NOW());
#pw = b12345
INSERT INTO `sample`.`user` (`user_id`, `auth_role_id`,`username`,`password`,`name`,`dob`, `contact`, `email_id`, `address`, `created`) VALUES 
(30001, 1, 'sushmita', 'a0c6d3983ab6c7cd0f87d59a8825fc02', 'Sushmita Muthe', NOW(),'4805672349', 'sush.muthe594@gmail.com', '1255', NOW());
#pw = sushmitha
INSERT INTO `sample`.`user` (`user_id`, `auth_role_id`,`username`,`password`,`name`,`dob`, `contact`, `email_id`, `address`, `created`) VALUES 
(30002, 2, 'madhu', 'aa6e01d3db639d8eae802cc20924033a', 'Madhu Madhavan', NOW(),'4806523083', 'msridh11@asu.edu', 'UPark', NOW());
#pw = madhavan2
INSERT INTO `sample`.`user` (`user_id`, `auth_role_id`,`username`,`password`,`name`,`dob`, `contact`, `email_id`, `address`, `created`) VALUES 
(30003, 2, 'hongquy', '610493f1777cd6c17dc33cd4e17d7d92', 'Hongquy Nguyen', NOW(),'4807654390', 'hlnguye2@asu.edu', 'Desert Palms', NOW());
#pw = hongquyn
INSERT INTO `sample`.`user` (`user_id`, `auth_role_id`,`username`,`password`,`name`,`dob`, `contact`, `email_id`, `address`, `created`) VALUES 
(30004, 3, 'shankar', 'df9c9b5c36d7a227972527a83e96ac2b', 'Shankar Krishnamurthy', NOW(),'4803957366', 'skris106@asu.edu', 'Omnia', NOW());
#pw = shankar1 
INSERT INTO `sample`.`user` (`user_id`, `auth_role_id`,`username`,`password`,`name`,`dob`, `contact`, `email_id`, `address`, `created`) VALUES 
(30005, 3, 'michael', 'a13031433561312257b14fceb90be924', 'Michael Kintscher', NOW(),'4803957365', 'mkintsch@asu.edu', 'Hyve', NOW());
#pw = michaelk
INSERT INTO `sample`.`user` (`user_id`, `auth_role_id`,`username`,`password`,`name`,`dob`, `contact`, `email_id`, `address`, `created`) VALUES 
(30006, 4, 'vasu', '08a9d4aba1bcc8bacb776a667dbd6a08', 'Vasudev Sridhar', NOW(),'4802743516', 'vsridh20@asu.edu', 'Thrive', NOW());
#pw = vasudevs
INSERT INTO `sample`.`user` (`user_id`, `auth_role_id`,`username`,`password`,`name`,`dob`, `contact`, `email_id`, `address`, `created`) VALUES 
(30007, 4, 'avinash', 'dcecae9ce26a42a362ecd06eebe2395f', 'Avinash Khatwani', NOW(),'4803743516', 'akhatwa1@asu.edu', 'Fleetwood', NOW());
#pw = avinashk
INSERT INTO `sample`.`ACCOUNT` (`account_no`, `user_id`, `balance`, `account_type`, `interest`, `created`, `updated`) VALUES (1001, 30000, 998.10, 0, 0, NOW(), NOW());
INSERT INTO `sample`.`ACCOUNT` (`account_no`, `user_id`, `balance`, `account_type`, `interest`, `created`, `updated`) VALUES (1002, 30000, 15, 0, 0, NOW(), NOW());
INSERT INTO `sample`.`ACCOUNT` (`account_no`, `user_id`, `balance`, `account_type`, `interest`, `created`, `updated`) VALUES (3001, 30002, 100, 0, 0, NOW(), NOW());
INSERT INTO `sample`.`ACCOUNT` (`account_no`, `user_id`, `balance`, `account_type`, `interest`, `created`, `updated`) VALUES (4001, 30006, 1500, 0, 0, NOW(), NOW());
INSERT INTO `sample`.`ACCOUNT` (`account_no`, `user_id`, `balance`, `account_type`, `interest`, `created`, `updated`) VALUES (5001, 30007, 350, 0, 0, NOW(), NOW());
