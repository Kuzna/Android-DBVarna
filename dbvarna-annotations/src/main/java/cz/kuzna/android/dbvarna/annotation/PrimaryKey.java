package cz.kuzna.android.dbvarna.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Radek Kuznik
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface PrimaryKey {

    /**
     * Specifies if the column is autoincrementing or not
     */
    boolean autoincrement() default false;

}