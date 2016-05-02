package cz.kuzna.android.dbvarna.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Radek Kuznik
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * @return The name of the column. The default is the field name.
     */
    String name() default "";

    /**
     * @return An optional column length
     */
    int length() default -1;

    /**
     * Specifies {@code UNIQUE} constraint.
     *
     * @return True if the column value is unique in the table.
     */
    boolean unique() default false;

    /**
     * Specifies {@code NULL} constraint.
     *
     * @return True if the column value is nullable in the table.
     */
    boolean nullable() default true;

    /**
     * The {@code DEFAULT} value for the column.
     *
     * @return An SQLite value. For example. {@code "''"} for an empty string, and {@code "0"} for a literal zero,
     */
    String defaultValue() default "";
}
