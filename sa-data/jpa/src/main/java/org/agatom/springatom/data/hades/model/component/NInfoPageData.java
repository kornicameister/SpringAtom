package org.agatom.springatom.data.hades.model.component;

import org.agatom.springatom.data.hades.model.NAbstractVersionedPersistable;
import org.agatom.springatom.data.hades.model.blob.NBlob;
import org.agatom.springatom.data.types.component.InfoPageData;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-14</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "page_id", columnNames = "pageId"),
                @UniqueConstraint(name = "page_domain", columnNames = "pageDomain")
        },
        indexes = {
                @Index(name = "page_version", columnList = "pageVersion")
        }
)
public class NInfoPageData
        extends NAbstractVersionedPersistable
        implements InfoPageData {
    private static final long     serialVersionUID = 1415122784975055889L;
    @NotNull
    @Column(length = 20)
    private              String   pageName         = null;
    @NotNull
    @Column(length = 20, unique = true)
    private              String   pageId           = null;
    @Audited
    @Column(length = 10)
    private              String   pageVersion      = null;
    @NotNull
    @OneToOne(optional = false, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private              NBlob    pageObject       = null;
    @Column(length = 400)
    @NotNull
    private              Class<?> pageDomain       = null;

    @Override
    public String getPageId() {
        return this.pageId;
    }

    @Override
    public String getPageName() {
        return this.pageName;
    }

    @Override
    public String getPageVersion() {
        return this.pageVersion;
    }

    @Override
    public Serializable getPageObject() {
        return this.pageObject != null ? this.pageObject.getData() : null;
    }

    @Override
    public Class<?> getPageDomain() {
        return pageDomain;
    }

    public void setPageDomain(final Class<?> pageDomain) {
        this.pageDomain = pageDomain;
    }

    public NInfoPageData setPageObject(final Serializable pageObject) {
        this.pageObject = new NBlob().setData(pageObject);
        return this;
    }

    public NInfoPageData setPageVersion(final String pageVersion) {
        this.pageVersion = pageVersion;
        return this;
    }

    public NInfoPageData setPageName(final String pageName) {
        this.pageName = pageName;
        return this;
    }

    public NInfoPageData setPageId(final String pageId) {
        this.pageId = pageId;
        return this;
    }

    @Override
    public String getName() {
        return this.getPageName();
    }


}
