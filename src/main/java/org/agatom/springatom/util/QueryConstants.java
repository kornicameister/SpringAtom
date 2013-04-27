package org.agatom.springatom.util;

public class QueryConstants {
    public class QueryBody {
        public static final String FROM_TABLE_BY_TABLE_NAME = "from %s";
    }

    public class QueryTrace {
        public static final String READ_ENTITY_FROM_TABLE = "Read entity %s from table %s";
    }

    public class QueryResult {
        public static final String FROM_TABLE_RESULT_WITH_CLASS_NAME = "Loaded %d objects from %s";
        public static final String FROM_WHERE_RESULT_WITH_CLASS_NAME = "Loaded %d objects from %s by where %s";
    }

    public class Error {
        public static final String INACTIVE_SESSION = "Session is not active";
    }
}
