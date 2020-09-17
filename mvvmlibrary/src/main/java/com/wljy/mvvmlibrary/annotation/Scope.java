package com.wljy.mvvmlibrary.annotation;

import com.wljy.mvvmlibrary.base.AbsViewModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface Scope {
    Class<? extends AbsViewModel> value();
}
