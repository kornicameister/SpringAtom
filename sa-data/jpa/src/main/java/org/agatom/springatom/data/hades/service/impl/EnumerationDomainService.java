package org.agatom.springatom.data.hades.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.hades.model.enumeration.NEnumeration;
import org.agatom.springatom.data.hades.model.enumeration.NEnumerationEntry;
import org.agatom.springatom.data.hades.repo.repositories.enumeration.NEnumerationEntryRepository;
import org.agatom.springatom.data.hades.repo.repositories.enumeration.NEnumerationRepository;
import org.agatom.springatom.data.hades.service.NEnumerationService;
import org.agatom.springatom.data.services.enumeration.EnumerationServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Collection;
import java.util.Set;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-29</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
class EnumerationDomainService
        extends AbstractDomainService<NEnumeration>
        implements NEnumerationService {
    private static final String                      ENUMERATIONS          = "enumerations";
    @Autowired
    private              ObjectMapper                objectMapper          = null;
    @Autowired
    private              NEnumerationEntryRepository entryRepository       = null;
    private              NEnumerationRepository      enumerationRepository = null;

    @PostConstruct
    private void initRepo() {
        this.enumerationRepository = (NEnumerationRepository) this.repository;
    }

    @Override
    public NEnumeration getEnumeration(final String name) throws EnumerationServiceException {
        NEnumeration value;
        try {
            value = this.enumerationRepository.findByName(name);
            Assert.notNull(value);
        } catch (IllegalArgumentException exp) {
            throw EnumerationServiceException.enumerationNotFound(name);
        } catch (Exception exp) {
            throw new EnumerationServiceException(exp);
        }
        return value;
    }

    @Override
    public NEnumeration newEnumeration(final String name, final File file) throws EnumerationServiceException {
        final BufferedInputStream stream;
        try {
            stream = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException exp) {
            throw new EnumerationServiceException(exp);
        }
        return this.newEnumeration(name, stream);
    }

    @Override
    public NEnumeration newEnumeration(final String name, final InputStream stream) throws EnumerationServiceException {
        try {
            final Object object = this.objectMapper.readTree(new BufferedInputStream(stream));
            Assert.isInstanceOf(Collection.class, object);
        } catch (Exception exp) {
            throw new EnumerationServiceException(exp);
        }
        return null;
    }

    @Override
    public NEnumeration newEnumeration(final String name, final Collection<NEnumerationEntry> entries) {
        final NEnumeration enumeration = new NEnumeration()
                .setName(name)
                .setEntries(Lists.newArrayList(entries));
        return this.repository.save(enumeration);
    }

    @Override
    public Iterable<NEnumeration> newEnumerations(final File file) throws EnumerationServiceException {
        final BufferedInputStream stream;
        try {
            stream = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException exp) {
            throw new EnumerationServiceException(exp);
        }
        return this.newEnumerations(stream);
    }

    @Override
    public Iterable<NEnumeration> newEnumerations(final InputStream stream) throws EnumerationServiceException {
        final Set<NEnumeration> nEnumerations = Sets.newHashSet();
        try {
            final JsonNode node = this.objectMapper.readTree(new BufferedInputStream(stream));
            final ArrayNode enumerations = (ArrayNode) node.get(ENUMERATIONS);
            for (final JsonNode next : enumerations) {
                final String name = next.get("name").asText();
                final Collection<NEnumerationEntry> entries = this.readEntries((ArrayNode) next.get("entries"));
                nEnumerations.add(this.newEnumeration(name, entries));
            }
        } catch (Exception exp) {
            throw new EnumerationServiceException(exp);
        }
        return nEnumerations;
    }

    private Collection<NEnumerationEntry> readEntries(final ArrayNode entries) {
        final Set<NEnumerationEntry> set = Sets.newHashSetWithExpectedSize(entries.size());
        for (final JsonNode entry : entries) {
            final NEnumerationEntry enumerationEntry = new NEnumerationEntry();
            enumerationEntry.setKey(entry.get("key").asText())
                    .setComment(entry.get("comment").asText())
                    .setValue(entry.get("value").asText());
            set.add(enumerationEntry);
        }
        return set;
    }

    @Override
    public NEnumerationEntry getEnumerationEntry(final String name, final String entryKey) throws EnumerationServiceException {
        final NEnumeration enumeration = this.getEnumeration(name);
        final Collection<NEnumerationEntry> entries = enumeration.getEntries();
        if (CollectionUtils.isEmpty(entries)) {
            return null;
        }
        final Optional<NEnumerationEntry> match = FluentIterable.from(entries).firstMatch(new Predicate<NEnumerationEntry>() {
            @Override
            public boolean apply(final NEnumerationEntry input) {
                return input != null && input.getKey().equalsIgnoreCase(entryKey);
            }
        });
        if (match.isPresent()) {
            return match.get();
        }
        throw EnumerationServiceException.enumerationEntryNotFound(name, entryKey);
    }

    @Override
    public String getEnumeratedValue(final String name, final String entryKey) throws EnumerationServiceException {
        final NEnumerationEntry entry = this.getEnumerationEntry(name, entryKey);
        return entry != null ? entry.getKey() : null;
    }
}
