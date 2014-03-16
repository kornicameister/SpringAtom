SET foreign_key_checks = 0;

TRUNCATE TABLE `sauthority`;
TRUNCATE TABLE `suser`;
TRUNCATE TABLE `sperson_history`;
TRUNCATE TABLE `sperson`;
TRUNCATE TABLE `contacts`;
TRUNCATE TABLE `contacts_history`;
TRUNCATE TABLE `suserauthority`;
TRUNCATE TABLE `revinfo`;
TRUNCATE TABLE `appointment`;
TRUNCATE TABLE `appointment_task`;
TRUNCATE TABLE `cars`;
TRUNCATE TABLE `cars_history`;
TRUNCATE TABLE `REVCHANGES`;
TRUNCATE TABLE `car_master`;
TRUNCATE TABLE `notifications`;
TRUNCATE TABLE `acl_class`;
TRUNCATE TABLE `acl_object_identity`;
TRUNCATE TABLE `acl_entry`;
TRUNCATE TABLE `acl_sid`;
TRUNCATE TABLE `issues`;
DROP TABLE IF EXISTS `persistent_logins`;

SET foreign_key_checks = 1;