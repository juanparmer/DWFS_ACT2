
package com.relatosdepapel.books.data.utils;

import lombok.Getter;

@Getter
public class SearchCriteria {

    private final String key;
    private final Object value;
    private final SearchOperation operation;

    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", operation=" + operation +
                '}';
    }
}
