package com.j256.ormlite.dao;

import java.sql.SQLException;

/**
 * Class which is used to help folks use for loops but still close at the end. This is a wrapper to allow multiple
 * threads to iterate across the same dao or the same lazy collection at the same time. See
 * {@link com.j256.ormlite.dao.Dao#getWrappedIterable()} or {@link com.j256.ormlite.dao.ForeignCollection#getWrappedIterable()}.
 * 
 * @author graywatson
 */
public class CloseableWrappedIterableImpl<T> implements CloseableWrappedIterable<T> {

	private final CloseableIterable<T> iterable;
	private CloseableIterator<T> iterator;

	public CloseableWrappedIterableImpl(CloseableIterable<T> iterable) {
		this.iterable = iterable;
	}

	public CloseableIterator<T> iterator() {
		return closeableIterator();
	}

	public CloseableIterator<T> closeableIterator() {
		try {
			// close an existing iterator, if any
			close();
		} catch (SQLException e) {
			// ignored
		}
		iterator = iterable.closeableIterator();
		return iterator;
	}

	public void close() throws SQLException {
		if (iterator != null) {
			iterator.close();
			iterator = null;
		}
	}
}
