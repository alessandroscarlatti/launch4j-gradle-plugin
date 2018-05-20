package com.scarlatti.distribution.extension.launch4jLibraryTaskHelper;

import java.util.function.Supplier;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/5/2018
 */
public class Property<T> implements Supplier<T> {

    private T value;
    private boolean isSet;

    public Property() {
    }

    public Property(T value) {
        setValue(value);
    }

    public void setValue(T value) {
        this.value = value;
        this.isSet = true;
    }

    public boolean isSet() {
        return isSet;
    }

    public T getValue() {
        return value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public String toString() {
        return isSet ?
            "Property{value=" + value +  '}' :
            "Property{notSet}";
    }
}
