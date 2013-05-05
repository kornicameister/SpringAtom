package org.agatom.springatom.model.client;

import com.google.common.base.Objects;
import org.agatom.springatom.model.SPerson;
import org.hibernate.annotations.BatchSize;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author kornicamaister
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity(name = "SClient")
@DiscriminatorValue(value = "SC")
public class SClient extends SPerson {

    @BatchSize(size = 10)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<SClientProblemReport> problemReportSet;

    public SClient() {
        super();
    }

    public Set<SClientProblemReport> getProblemReportSet() {
        return problemReportSet;
    }

    public void setProblemReportSet(final Set<SClientProblemReport> problemReportSet) {
        this.problemReportSet = problemReportSet;
    }

    public boolean addProblemReport(final SClientProblemReport sClientProblemReport) {
        return problemReportSet.add(sClientProblemReport);
    }

    public boolean removeProblemReport(final Object sClientProblemReport) {
        return problemReportSet.remove(sClientProblemReport);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("problems", problemReportSet.size())
                .toString();
    }
}
