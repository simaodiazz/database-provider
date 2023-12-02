package com.github.simaodiazz.database.provider.adapter;

import java.sql.ResultSet;
import java.util.Set;

public interface DatabaseAdapter<T> {

    T adapt(ResultSet resultSet);
    Set<T> adaptAll(ResultSet resultSet);

}