package org.agatom.springatom.data.support.user;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-17</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class NotAuthenticatedUserBean
        extends UserBean {

    public NotAuthenticatedUserBean() {
        super(false);
    }

}
