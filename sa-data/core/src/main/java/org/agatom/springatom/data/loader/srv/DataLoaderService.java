package org.agatom.springatom.data.loader.srv;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-10-10</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
public interface DataLoaderService {
    /**
     * Method executes actual installation.
     * It should return an InstallationMarker
     *
     * @return InstallationMarker
     */
    InstallationMarker loadData();

    final class InstallationMarker {
        private long      hash;
        private String    path;
        private Throwable error;

        public long getHash() {
            return hash;
        }

        public InstallationMarker setHash(final long hash) {
            this.hash = hash;
            return this;
        }

        public String getPath() {
            return path;
        }

        public InstallationMarker setPath(final String path) {
            this.path = path;
            return this;
        }

        public Throwable getError() {
            return error;
        }

        public InstallationMarker setError(final Throwable error) {
            this.error = error;
            return this;
        }
    }
}
