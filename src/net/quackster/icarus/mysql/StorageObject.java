package net.quackster.icarus.mysql;

import java.sql.ResultSet;

public interface StorageObject {
	
	public void fill(ResultSet set) throws Exception;
}
