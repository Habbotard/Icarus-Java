package org.alexdev.icarus.dao.util;

public interface IProcessStorage<T, I> {

	public T fill(T instance, I data) throws Exception;
}
