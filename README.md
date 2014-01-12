Springatom
==========

For additional information go to:
- [VersionEye](https://www.versioneye.com/user/projects/52d30cebec13754cdb000072)
- [Blimp](https://app.getblimp.com/springatom/springatom/goals/)
- [Codeship](https://www.codeship.io/projects/4264)
- [ReadTheDocs](http://springatom.readthedocs.org/en/latest/)


New version of AgatomProject built entirely with JavaEE and Spring Framework...and ExtJS for client side support
Old version, that was built entirely in Qt is still available [here](https://gitorious.org/agatomproject).

Program features:
- Full support for working car shop
- Database of mechanics, cars, clients and so on
- Ability to make appointments as Assiggne and push them to the particular mechanics
- Internal mail-like capability (notifications for mechanics)
- Embedded guest support, clients are able to log in as guest as review history of their visits
- History of the particular car
- Master server capable to pull from slave and notify mechanics about visit request
- Database backups

Architecture:
- master server installed in the shop
- slave server for guests and users outside the company

Used technologies:
- Spring
- ExtJS
- GWT/GXT support planned
