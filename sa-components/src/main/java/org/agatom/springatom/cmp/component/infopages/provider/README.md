**Structure Notes**

 * Converters set for a single attribute are considered as explicit converters
 * Otherwise, another artifacts will be used to pick up converters for the type associated with the InfoPage and the attribute
 * Each panel contains position attribute describing the order in which they should be displayed
 * Each attribute contains position attribute describing the order in which it should be displayed

**Localization Notes**
Each panel contains title property having appropriate localization entries for each locale

**Icons**
Each panel and the attribute can have an icon. Icon itself can be either:
 * path to the physical file
 * css class 
 * the glyph

**Attributes** are localized using the domain class in conjunction with the path value. 