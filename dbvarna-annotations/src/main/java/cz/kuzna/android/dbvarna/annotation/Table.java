package cz.kuzna.android.dbvarna.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Radek Kuznik
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Table {

    String name() default "";
    boolean generateMapper() default true;
    boolean generateDao() default true;
}