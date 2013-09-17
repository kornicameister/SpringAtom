SET foreign_key_checks = 0;

TRUNCATE TABLE `sauthority`;
TRUNCATE TABLE `smetadata`;
TRUNCATE TABLE `suser`;
TRUNCATE TABLE `suser_history`;
TRUNCATE TABLE `smechanic`;
TRUNCATE TABLE `smechanic_history`;
TRUNCATE TABLE `sperson`;
TRUNCATE TABLE `sperson_history`;
TRUNCATE TABLE `scontact`;
TRUNCATE TABLE `scontact_history`;
TRUNCATE TABLE `sclient`;
TRUNCATE TABLE `suserauthority`;
TRUNCATE TABLE `revinfo`;
TRUNCATE TABLE `sappointment`;
TRUNCATE TABLE `sappointmenttask`;
TRUNCATE TABLE `sappointmentworkerlink`;
TRUNCATE TABLE `sappointmentworkerlink_history`;
TRUNCATE TABLE `scar`;
TRUNCATE TABLE `scar_history`;
TRUNCATE TABLE `sclientproblemreport`;
TRUNCATE TABLE `sclientproblemreport_history`;
TRUNCATE TABLE `sclient_history`;
TRUNCATE TABLE `revchanges`;
TRUNCATE TABLE `scarmaster`;
TRUNCATE TABLE `snotification`;

SET foreign_key_checks = 1;