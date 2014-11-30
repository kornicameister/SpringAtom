package org.agatom.springatom.data.support.rupdate;

import org.agatom.springatom.core.data.Labeled;
import org.joda.time.DateTime;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-11-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public class RecentUpdateBean
        implements Labeled {
    private DateTime ts    = null;
    private String   oid   = null;
    private String   label = null;
    private Long     id    = null;

    @Override
    public String getLabel() {
        return this.label;
    }

    public RecentUpdateBean setLabel(final String label) {
        this.label = label;
        return this;
    }

    public DateTime getTs() {
        return ts;
    }

    public RecentUpdateBean setTs(final DateTime ts) {
        this.ts = ts;
        return this;
    }

    public String getOid() {
        return oid;
    }

    public RecentUpdateBean setOid(final String oid) {
        this.oid = oid;
        return this;
    }

    public Long getId() {
        return id;
    }

    public RecentUpdateBean setId(final Long id) {
        this.id = id;
        return this;
    }


}
