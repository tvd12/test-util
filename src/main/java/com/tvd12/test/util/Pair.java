package com.tvd12.test.util;

import java.util.Map.Entry;

/**
 * Map key and value
 *
 * @param <K> the key type
 * @param <V> the value type
 * @author tvd12
 */
public class Pair<K, V> implements Entry<K, V> {

    protected K key;
    protected V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}
