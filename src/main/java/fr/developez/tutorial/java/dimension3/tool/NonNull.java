package fr.developez.tutorial.java.dimension3.tool;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a parameter must be not null
 * A null value will implies a {@link NullPointerException}
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
@Documented
public @interface NonNull
{
}
