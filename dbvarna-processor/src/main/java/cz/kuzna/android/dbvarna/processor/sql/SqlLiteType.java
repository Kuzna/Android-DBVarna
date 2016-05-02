package cz.kuzna.android.dbvarna.processor.sql;

import com.squareup.javapoet.TypeName;

import java.sql.Blob;
import java.util.HashMap;

/**
 * Description: Represents a type that SQLite understands.
 *
 * https://github.com/Raizlabs/DBFlow/blob/master/dbflow-core/src/main/java/com/raizlabs/android/dbflow/sql/SQLiteType.java
 */
public enum SqlLiteType {

    /**
     * Represents an integer number in the DB.
     */
    INTEGER,

    /**
     * Represents a floating-point, precise number.
     */
    REAL,

    /**
     * Represents text.
     */
    TEXT,

    /**
     * A column defined by {@link byte[]} data. Usually reserved for images or larger pieces of content.
     */
    BLOB;

    private static final HashMap<String, SqlLiteType> sTypeMap = new HashMap<String, SqlLiteType>() {
        {
            put(byte.class.getName(), SqlLiteType.INTEGER);
            put(short.class.getName(), SqlLiteType.INTEGER);
            put(int.class.getName(), SqlLiteType.INTEGER);
            put(long.class.getName(), SqlLiteType.INTEGER);
            put(float.class.getName(), SqlLiteType.REAL);
            put(double.class.getName(), SqlLiteType.REAL);
            put(boolean.class.getName(), SqlLiteType.INTEGER);
            put(char.class.getName(), SqlLiteType.TEXT);
            put(byte[].class.getName(), SqlLiteType.BLOB);
            put(Byte.class.getName(), SqlLiteType.INTEGER);
            put(Short.class.getName(), SqlLiteType.INTEGER);
            put(Integer.class.getName(), SqlLiteType.INTEGER);
            put(Long.class.getName(), SqlLiteType.INTEGER);
            put(Float.class.getName(), SqlLiteType.REAL);
            put(Double.class.getName(), SqlLiteType.REAL);
            put(Boolean.class.getName(), SqlLiteType.INTEGER);
            put(Character.class.getName(), SqlLiteType.TEXT);
            put(String.class.getName(), SqlLiteType.TEXT);
            put(Byte[].class.getName(), SqlLiteType.BLOB);
            put(Blob.class.getName(), SqlLiteType.BLOB);
        }
    };

    /**
     * Returns the {@link SqlLiteType} for this class
     *
     * @param className The fully qualified class name
     * @return The type from the class name
     */
    public static SqlLiteType get(String className) {
        return sTypeMap.get(className);
    }

    public static SqlLiteType get(TypeName type) {
        return sTypeMap.get(type.toString());
    }

    public static boolean containsClass(String className) {
        return sTypeMap.containsKey(className);
    }
}
