package cz.kuzna.android.dbvarna.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Radek Kuznik
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.CLASS)
public @interface Setter {

    String value() default "";
}