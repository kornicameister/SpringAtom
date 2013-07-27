INSERT INTO `suser` (idSUser, createdDate, lastModifiedDate, login, secPass, createdBy_idSUser, lastModifiedBy_idSUser, person, version)
  VALUES
  (1, null, null, "SYSTEM", "SYSTEM", null, null, null, 0);

INSERT INTO `susertorole` (user, role) VALUES (1, (SELECT
                                                     `idSRole`
                                                   FROM `srole`
                                                   WHERE `role` = "ROLE_ADMIN"));
