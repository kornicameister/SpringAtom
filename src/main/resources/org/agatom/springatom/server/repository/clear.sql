SET foreign_key_checks = 0;

TRUNCATE TABLE `SAuthority`;
TRUNCATE TABLE `SUser`;
TRUNCATE TABLE `SPerson_history`;
TRUNCATE TABLE `SPerson`;
TRUNCATE TABLE `contacts`;
TRUNCATE TABLE `contacts_history`;
TRUNCATE TABLE `SUserAuthority`;
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