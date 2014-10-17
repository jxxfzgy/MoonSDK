package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.Dao;

/**
 * Interface returned by the {@link com.j256.ormlite.stmt.QueryBuilder#prepare()} which supports custom SELECT queries. This should be in turn
 * passed to the {@link com.j256.ormlite.dao.Dao#query(com.j256.ormlite.stmt.PreparedQuery)} or {@link com.j256.ormlite.dao.Dao#iterator(com.j256.ormlite.stmt.PreparedQuery)} methods.
 * 
 * @param <T>
 *            The class that the code will be operating on.
 * @author graywatson
 */
public interface PreparedQuery<T> extends PreparedStmt<T> {
}
