package org.agatom.springatom.boot.ds;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.hades.model.person.NPerson;
import org.agatom.springatom.data.hades.model.person.NPersonContact;
import org.agatom.springatom.data.hades.model.user.NUser;
import org.agatom.springatom.data.hades.service.NEnumerationService;
import org.agatom.springatom.data.hades.service.NUserService;
import org.agatom.springatom.data.loader.annotation.DataLoader;
import org.agatom.springatom.data.loader.srv.AbstractDataLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-11</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Order(2)
@DataLoader
class UsersDataLoader
        extends AbstractDataLoaderService {
    private static final Logger              LOGGER             = LoggerFactory.getLogger(UsersDataLoader.class);
    private static final String              PATH               = "classpath:org/agatom/springatom/boot/ds/users.json";
    @Autowired
    private              NUserService        userService        = null;
    @Autowired
    private              ObjectMapper        objectMapper       = null;
    @Autowired
    private              NEnumerationService enumerationService = null;

    @Override
    public InstallationMarker loadData() {
        final InstallationMarker marker = new InstallationMarker();

        try {
            final InputStream stream = this.getStream(PATH);
            final JsonNode node = this.objectMapper.readTree(stream);
            final ArrayNode users = (ArrayNode) node.get("users");
            final int hashCode = this.readAndRegister(users.iterator());

            marker.setHash(hashCode);
            marker.setPath(PATH);

        } catch (Exception exp) {
            LOGGER.error(String.format("Error when loading users from %s", PATH), exp);
            marker.setError(exp);
        }

        return marker;
    }

    private int readAndRegister(final Iterator<JsonNode> iterator) throws Exception {
        final Collection<NUser> users = Lists.newArrayList();
        while (iterator.hasNext()) {
            final JsonNode node = iterator.next();

            NUser user = this.getUser(node);
            NPerson person = this.getPerson(node);
            Collection<InLoadingGrantedAuthority> authorities = this.getRoles(node);
            final boolean authoritiesEmpty = CollectionUtils.isEmpty(authorities);

            if (person != null && !authoritiesEmpty) {
                user = this.userService.registerNewUser(user, person, authorities);
            } else if (person != null) {
                user = this.userService.registerNewUser(user, person);
            } else if (!authoritiesEmpty) {
                user = this.userService.registerNewUser(user, null, authorities);
            } else {
                user = this.userService.registerNewUser(user);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Loaded user %s", user.getUsername()));
            }

            users.add(user);
        }

        return users.hashCode();
    }

    private NUser getUser(final JsonNode node) {
        return (NUser) new NUser().setUsername(node.get("login").asText())
                .setEnabled(true)
                .setPassword(node.get("password").asText())
                .setEmail(node.get("email").asText());
    }

    private NPerson getPerson(final JsonNode node) throws Exception {
        if (!node.has("person")) {
            return null;
        }
        final JsonNode personNode = node.get("person");
        final NPerson person = new NPerson();
        return person.setFirstName(personNode.get("firstName").asText())
                .setLastName(personNode.get("lastName").asText())
                .setContacts(this.getContacts(personNode));
    }

    private Collection<InLoadingGrantedAuthority> getRoles(final JsonNode node) {
        final ArrayNode roles = (ArrayNode) node.get("roles");
        final Collection<InLoadingGrantedAuthority> collection = Sets.newHashSetWithExpectedSize(roles.size());
        for (final JsonNode roleNode : roles) {
            final String role = roleNode.asText();
            collection.add(new InLoadingGrantedAuthority(role));
        }
        return collection;
    }

    private List<NPersonContact> getContacts(final JsonNode personNode) throws Exception {
        if (!personNode.has("contacts")) {
            return null;
        }
        final JsonNode contactsNode = personNode.get("contacts");
        final List<NPersonContact> contacts = Lists.newArrayListWithExpectedSize(contactsNode.size());
        for (final JsonNode cn : contactsNode) {
            contacts.add(this.getContact(cn));
        }
        return contacts;
    }

    private NPersonContact getContact(final JsonNode cn) throws Exception {
        final NPersonContact pc = new NPersonContact();

        pc.setContact(cn.get("value").asText());
        pc.setType(this.enumerationService.getEnumeratedValue("CONTACT_TYPES", cn.get("type").asText()));

        return pc;
    }

    private static class InLoadingGrantedAuthority
            implements GrantedAuthority {
        private static final long   serialVersionUID = 8781835677341014517L;
        private              String authority        = null;

        public InLoadingGrantedAuthority(final String role) {
            this.authority = role;
        }

        @Override
        public String getAuthority() {
            return this.authority;
        }
    }
}
