package org.agatom.springatom.data.event;

import com.google.common.base.MoreObjects;
import org.agatom.springatom.data.event.after.*;
import org.agatom.springatom.data.event.before.*;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import static org.springframework.core.GenericTypeResolver.resolveTypeArgument;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-15</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class PersistenceEventListenerAdapter<T extends Persistable<Long>>
        implements ApplicationListener<PersistenceEvent> {
    private final Class<?> INTERESTED_TYPE = resolveTypeArgument(getClass(), PersistenceEventListenerAdapter.class);

    /*
     * (non-Javadoc)
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    @SuppressWarnings({"unchecked"})
    public final void onApplicationEvent(final PersistenceEvent event) {
        final Class<?> srcType = event.getObjectType();

        if (null != INTERESTED_TYPE && !INTERESTED_TYPE.isAssignableFrom(srcType)) {
            return;
        }

        if (event instanceof BeforeSaveEvent) {
            onBeforeSave((T) event.getSource());
        } else if (event instanceof BeforeCreateEvent) {
            onBeforeCreate((T) event.getSource());
        } else if (event instanceof AfterCreateEvent) {
            onAfterCreate((T) event.getSource());
        } else if (event instanceof AfterSaveEvent) {
            onAfterSave((T) event.getSource());
        } else if (event instanceof BeforeLinkSaveEvent) {
            onBeforeLinkSave((T) event.getSource(), ((BeforeLinkSaveEvent) event).getRoleB());
        } else if (event instanceof AfterLinkSaveEvent) {
            onAfterLinkSave((T) ((AfterLinkSaveEvent) event).getRoleA(), ((AfterLinkSaveEvent) event).getRoleB());
        } else if (event instanceof BeforeLinkDeleteEvent) {
            onBeforeLinkDelete((T) ((BeforeLinkDeleteEvent) event).getRoleA(), ((BeforeLinkDeleteEvent) event).getRoleB());
        } else if (event instanceof AfterLinkDeleteEvent) {
            onAfterLinkDelete((T) ((AfterLinkDeleteEvent) event).getRoleA(), ((AfterLinkDeleteEvent) event).getRoleB());
        } else if (event instanceof BeforeDeleteEvent) {
            onBeforeDelete((T) event.getSource());
        } else if (event instanceof AfterDeleteEvent) {
            onAfterDelete((T) event.getSource());
        }

    }

    /**
     * Override this method if you are interested in {@literal beforeSave} events.
     *
     * @param entity The entity being saved.
     */
    protected void onBeforeSave(T entity) {
    }

    /**
     * Override this method if you are interested in {@literal beforeCreate} events.
     *
     * @param entity The entity being created.
     */
    protected void onBeforeCreate(T entity) {
    }

    /**
     * Override this method if you are interested in {@literal afterCreate} events.
     *
     * @param entity The entity that was created.
     */
    protected void onAfterCreate(T entity) {
    }

    /**
     * Override this method if you are interested in {@literal afterSave} events.
     *
     * @param entity The entity that was just saved.
     */
    protected void onAfterSave(T entity) {
    }

    /**
     * Override this method if you are interested in {@literal beforeLinkSave} events.
     *
     * @param parent The parent entity to which the child object is linked.
     * @param linked The linked, child entity.
     */
    protected void onBeforeLinkSave(T parent, Object linked) {
    }

    /**
     * Override this method if you are interested in {@literal afterLinkSave} events.
     *
     * @param parent The parent entity to which the child object is linked.
     * @param linked The linked, child entity.
     */
    protected void onAfterLinkSave(T parent, Object linked) {
    }

    /**
     * Override this method if you are interested in {@literal beforeLinkDelete} events.
     *
     * @param parent The parent entity to which the child object is linked.
     * @param linked The linked, child entity.
     */
    protected void onBeforeLinkDelete(T parent, Object linked) {
    }

    /**
     * Override this method if you are interested in {@literal afterLinkDelete} events.
     *
     * @param parent The parent entity to which the child object is linked.
     * @param linked The linked, child entity.
     */
    protected void onAfterLinkDelete(T parent, Object linked) {
    }

    /**
     * Override this method if you are interested in {@literal beforeDelete} events.
     *
     * @param entity The entity that is being deleted.
     */
    protected void onBeforeDelete(T entity) {
    }

    /**
     * Override this method if you are interested in {@literal afterDelete} events.
     *
     * @param entity The entity that was just deleted.
     */
    protected void onAfterDelete(T entity) {
    }

    public boolean canAccept(final Object object) {
        if (object == null) {
            return false;
        }
        final Class<?> theClass = ClassUtils.getUserClass(object);
        return ClassUtils.isAssignable(INTERESTED_TYPE, theClass);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("INTERESTED_TYPE", INTERESTED_TYPE)
                .toString();
    }
}
