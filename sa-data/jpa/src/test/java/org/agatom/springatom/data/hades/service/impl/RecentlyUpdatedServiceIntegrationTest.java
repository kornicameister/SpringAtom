package org.agatom.springatom.data.hades.service.impl;

import com.google.common.collect.Maps;
import org.agatom.springatom.data.hades.model.appointment.NAppointment;
import org.agatom.springatom.data.hades.model.car.NCar;
import org.agatom.springatom.data.hades.model.reference.NEntityReference;
import org.agatom.springatom.data.hades.model.rupdate.NRecentUpdate;
import org.agatom.springatom.data.hades.repo.repositories.rupdate.NRecentUpdateRepository;
import org.agatom.springatom.data.hades.service.BaseJpaIntegrationTest;
import org.agatom.springatom.data.services.ref.EntityReferenceHelper;
import org.agatom.springatom.data.support.rupdate.RecentUpdateBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Persistable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.HierarchyMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager")
public class RecentlyUpdatedServiceIntegrationTest
        extends BaseJpaIntegrationTest {
    private static Properties              applicationProperties  = null;
    @Spy
    private        RecentlyUpdatedService  recentlyUpdatedService = null;
    @Autowired
    private        EntityReferenceHelper   entityReferenceHelper  = null;
    @Autowired
    private        NRecentUpdateRepository recentUpdateRepository = null;
    @Autowired
    private        MessageSource           messageSource          = null;

    @BeforeClass
    public static void setConstants() {
        applicationProperties = new Properties() {
            private static final long serialVersionUID = -8800323325677516449L;

            @Override
            public String getProperty(final String key) {
                switch (key) {
                    case "org.agatom.springatom.data.services.SRecentlyUpdatedService.supported":
                        return String.format("%s,%s", NCar.class.getName(), NAppointment.class.getName());
                }
                return key;
            }
        };
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(this.recentlyUpdatedService, "applicationProperties", applicationProperties);
        ReflectionTestUtils.setField(this.recentlyUpdatedService, "entityReferenceHelper", this.entityReferenceHelper);
        ReflectionTestUtils.setField(this.recentlyUpdatedService, "repository", this.recentUpdateRepository);
        ReflectionTestUtils.setField(this.recentlyUpdatedService, "messageSource", this.messageSource);

        Mockito.when(this.recentlyUpdatedService.getRecentUpdateListener()).thenReturn(acceptNoneListener());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DirtiesContext(hierarchyMode = HierarchyMode.CURRENT_LEVEL)
    public void testNewRecentUpdate_Create() throws Exception {
        // setting
        final int count = 10;
        // setting

        for (int it = 0; it < count; it++) {
            final Persistable<Long> persistable = this.getSpiedPersistableFromSupported(it);
            Mockito.when(persistable.getId()).thenReturn((long) (it + 1));
            final NRecentUpdate update = this.recentlyUpdatedService.newRecentUpdate(persistable);

            Assert.assertTrue(update.getRef().getRefId().equals((long) it + 1));
            Assert.assertEquals(update.getRef().getRefClass(), ClassUtils.getUserClass(persistable));
            Assert.assertFalse(update.isNew());

        }
    }


    @Test(expected = DuplicateKeyException.class)
    @Transactional
    @Rollback(value = true)
    @DirtiesContext(hierarchyMode = HierarchyMode.CURRENT_LEVEL)
    public void testNewRecentUpdate_CheckNoDuplicatesAllowed() throws Exception {
        final Persistable<Long> persistable1 = this.getSpiedPersistableFromSupported(1);
        final Persistable<Long> persistable2 = this.getSpiedPersistableFromSupported(1);

        Mockito.when(persistable1.getId()).thenReturn((long) 1);
        Mockito.when(persistable2.getId()).thenReturn((long) 1);

        this.recentlyUpdatedService.newRecentUpdate(persistable1);
        this.recentlyUpdatedService.newRecentUpdate(persistable2);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DirtiesContext(hierarchyMode = HierarchyMode.CURRENT_LEVEL)
    public void test_GetAllRecentUpdates() throws Exception {
        final int count = 10;
        // setting

        Map<NEntityReference, Persistable<Long>> persistableMap = Maps.newHashMap();
        for (int it = 0; it < count; it++) {
            final Persistable<Long> persistable = this.getSpiedPersistableFromSupported(it);
            Mockito.when(persistable.getId()).thenReturn((long) (it + 1));
            final NEntityReference ref = (NEntityReference) this.entityReferenceHelper.toReference(persistable);
            this.recentlyUpdatedService.newRecentUpdate(persistable);

            persistableMap.put(ref, persistable);
        }

        final EntityReferenceHelper spy = Mockito.spy(this.entityReferenceHelper);
        for (final NEntityReference ref : persistableMap.keySet()) {
            Mockito.when(spy.fromReference(ref)).thenReturn(persistableMap.get(ref));
        }

        ReflectionTestUtils.setField(this.recentlyUpdatedService, "entityReferenceHelper", spy);
        final Collection<RecentUpdateBean> recentlyUpdated = this.recentlyUpdatedService.getRecentlyUpdated();

        Assert.assertFalse(recentlyUpdated.isEmpty());
        Assert.assertEquals(count, recentlyUpdated.size());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DirtiesContext(hierarchyMode = HierarchyMode.CURRENT_LEVEL)
    public void test_Pageable_GetAllRecentUpdate() throws Exception {
        final int count = 10;

        Map<NEntityReference, Persistable<Long>> persistableMap = Maps.newHashMap();
        for (int it = 0; it < count; it++) {
            final Persistable<Long> persistable = this.getSpiedPersistableFromSupported(it);
            Mockito.when(persistable.getId()).thenReturn((long) (it + 1));
            final NEntityReference ref = (NEntityReference) this.entityReferenceHelper.toReference(persistable);
            this.recentlyUpdatedService.newRecentUpdate(persistable);

            persistableMap.put(ref, persistable);
        }

        final EntityReferenceHelper spy = Mockito.spy(this.entityReferenceHelper);
        for (final NEntityReference ref : persistableMap.keySet()) {
            Mockito.when(spy.fromReference(ref)).thenReturn(persistableMap.get(ref));
        }

        ReflectionTestUtils.setField(this.recentlyUpdatedService, "entityReferenceHelper", spy);

        final Page<RecentUpdateBean> recentlyUpdated = this.recentlyUpdatedService.getRecentlyUpdated(new PageRequest(0, 50));

        Assert.assertNotNull(recentlyUpdated);
        Assert.assertTrue(recentlyUpdated.getNumberOfElements() > 0);
        Assert.assertEquals(count, recentlyUpdated.getNumberOfElements());
    }

    @Test
    @DirtiesContext(hierarchyMode = HierarchyMode.CURRENT_LEVEL)
    public void test_GetAllRecentUpdates_NoUpdates() throws Exception {
        final Collection<RecentUpdateBean> recentlyUpdated = this.recentlyUpdatedService.getRecentlyUpdated();

        Assert.assertNotNull(recentlyUpdated);
        Assert.assertTrue(recentlyUpdated.isEmpty());
    }

    @Test
    @DirtiesContext(hierarchyMode = HierarchyMode.CURRENT_LEVEL)
    public void test_Pageable_GetAllRecentUpdates_NoUpdates() throws Exception {
        final Page<RecentUpdateBean> recentlyUpdated = this.recentlyUpdatedService.getRecentlyUpdated(new PageRequest(0, 10));

        Assert.assertNotNull(recentlyUpdated);
        Assert.assertTrue(recentlyUpdated.getNumberOfElements() == 0);
    }

    private Persistable<Long> getSpiedPersistableFromSupported(final int it) {
        if (it % 2 == 0) {
            return Mockito.spy(new NCar());
        }
        return Mockito.spy(new NAppointment());
    }
}