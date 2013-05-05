/*
-- Query: SELECT * FROM springatom.sappointmenttasktype
LIMIT 0, 1000

-- Date: 2013-05-02 01:36
*/
INSERT INTO `smetadata` (`type`, `meta`) VALUES ('repair', 'SAT');
INSERT INTO `smetadata` (`type`, `meta`) VALUES ('normal', 'SAT');

INSERT INTO `smetadata` (`type`, `meta`) VALUES ('debts', 'SCPR');
INSERT INTO `smetadata` (`type`, `meta`) VALUES ('fake_id', 'SCPR');
INSERT INTO `smetadata` (`type`, `meta`) VALUES ('missed_app', 'SCPR');
INSERT INTO `smetadata` (`type`, `meta`) VALUES ('no_payment', 'SCPR');

INSERT INTO `smetadata` (`type`, `meta`) VALUES ('callPhone', 'SCT');
INSERT INTO `smetadata` (`type`, `meta`) VALUES ('fax', 'SCT');
INSERT INTO `smetadata` (`type`, `meta`) VALUES ('mail', 'SCT');
INSERT INTO `smetadata` (`type`, `meta`) VALUES ('phone', 'SCT');

INSERT INTO `smetadata` (`type`, `meta`) VALUES ('app_canceled', 'SNT');
INSERT INTO `smetadata` (`type`, `meta`) VALUES ('app_done', 'SNT');
INSERT INTO `smetadata` (`type`, `meta`) VALUES ('app_rejected', 'SNT');
