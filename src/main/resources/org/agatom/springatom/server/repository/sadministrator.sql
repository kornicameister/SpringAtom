INSERT INTO `sperson` (idSPerson, createdDate, lastModifiedDate, version, primaryMail, fName, lName, createdBy_idSUser, lastModifiedBy_idSUser)
VALUES (1, '2013-11-13 00:00:00', '2013-11-13 00:00:00', '1', 'kornicameister@gmail.com', 'Tomasz', 'Trębski', NULL, NULL);

INSERT INTO `suser` (idSUser, createdDate, lastModifiedDate, username, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, createdBy_idSUser, lastModifiedBy_idSUser, person, version)
VALUES
  (1, '2013-11-13 00:00:00', '2013-11-13 00:00:00', "SYSTEM", "$2a$10$b3Xjq0/iV31I911hIpuVKuBR19iN2YSDtj6crlZhOkScb7hFmoDJy", TRUE, TRUE, TRUE, TRUE,
   null, null, null, 0);

INSERT INTO `suserauthority` (user, authority) VALUES (1, (SELECT
                                                             `idSAuthority`
                                                           FROM `sauthority`
                                                           WHERE `authority` = "ROLE_ADMIN"));

INSERT INTO `suserauthority` (user, authority) VALUES (1, (SELECT
                                                             `idSAuthority`
                                                           FROM `sauthority`
                                                           WHERE `authority` = "ROLE_CLIENT"));

INSERT INTO `suserauthority` (user, authority) VALUES (1, (SELECT
                                                             `idSAuthority`
                                                           FROM `sauthority`
                                                           WHERE `authority` = "ROLE_MECHANIC"));
