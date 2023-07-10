package kr.ac.doowon.healthmanageapp.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YYC
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table{
    String name();

    boolean primaryKey() default false;

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TypeInteger{
        String name();

        boolean primaryKey() default false;

        boolean autoIncrement() default false;

        boolean notNull() default false;

        String defaultValue() default "";

    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TypeText {
        String name();

        boolean primaryKey() default false;

        boolean notNull() default false;

        String defaultValue() default "";
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TypeReal {
        String name();

        boolean primaryKey() default false;

        boolean notNull() default false;

        String defaultValue() default "";
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TypeBlob {
        String name();
    }
}

