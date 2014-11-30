package org.agatom.springatom.cmp.action.model;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class CSSRule {
    private String iconClass = null;
    private String btnClass  = null;
    private String style     = null;

    public String getIconClass() {
        return iconClass;
    }

    public CSSRule setIconClass(final String iconClass) {
        this.iconClass = iconClass;
        return this;
    }

    public String getBtnClass() {
        return btnClass;
    }

    public CSSRule setBtnClass(final String btnClass) {
        this.btnClass = btnClass;
        return this;
    }

    public String getStyle() {
        return style;
    }

    public CSSRule setStyle(final String style) {
        this.style = style;
        return this;
    }
}
