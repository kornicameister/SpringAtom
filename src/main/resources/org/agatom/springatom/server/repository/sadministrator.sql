INSERT INTO `suser` (idSUser,
                     createdDate,
                     lastModifiedDate,
                     username,
                     password,
                     accountNonExpired,
                     accountNonLocked,
                     credentialsNonExpired,
                     enabled,
                     createdBy_idSUser,
                     lastModifiedBy_idSUser,
                     person,
                     version)
VALUES
  (1,
   null,
   null,
   "SYSTEM",
   "SYSTEM",
   TRUE,
   TRUE,
   TRUE,
   TRUE,
   null,
   null,
   null,
   0);

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
