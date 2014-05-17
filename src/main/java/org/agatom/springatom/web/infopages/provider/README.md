**Structure Notes**

 * Converters set for a single attribute are considered as explicit converters
 * Otherwise, another artifacts will be used to pick up converters for the type associated with the InfoPage and the attribute

**Localization Notes**

When localizing the page and the panels following approach is used:
 * key is computed using **springatom.infopage** prefix
 * info page domain class name (lowercase) is appended to it
 * at last id of the the panel (lowercase) is used to create a full key 

**Attributes** are localized using the domain class in conjunction with the path value. 