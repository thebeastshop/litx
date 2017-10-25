package com.thebeastshop.litx.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可补偿标注
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-11 11:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Compensable {
}
