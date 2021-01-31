package me.elb1to.watermc.hub.utils.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    public String name();

    public String permission() default "";

    public String noPerm() default "§cYou do not have permission to perform that action";

    public String[] aliases() default {};

    public String description() default "";

    public String usage() default "";

    public boolean inGameOnly() default true;
}
