package org.postgresql.jdbc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;

import org.postgresql.Driver;

public class WNBlob implements Blob {

	private byte[] data;
	
	public WNBlob() {
		
	}
	
	public WNBlob(byte[] data) {
		this.data = data;
	}
	
	@Override
	public long length() throws SQLException {
		if(data == null) {
			return -1;
		}
		return data.length;
	}

	@Override
	public byte[] getBytes(long pos, int length) throws SQLException {
		return data;
	}

	@Override
	public InputStream getBinaryStream() throws SQLException {
		return new ByteArrayInputStream(data);
	}

	@Override
	public long position(byte[] pattern, long start) throws SQLException {
		throw Driver.notImplemented(this.getClass(), "position(byte[] pattern, long start)");
	}

	@Override
	public long position(Blob pattern, long start) throws SQLException {
		throw Driver.notImplemented(this.getClass(), "position(Blob pattern, long start)");
	}

	@Override
	public int setBytes(long pos, byte[] bytes) throws SQLException {
		throw Driver.notImplemented(this.getClass(), "setBytes(long pos, byte[] bytes)");
	}

	@Override
	public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
		throw Driver.notImplemented(this.getClass(), "setBytes(long pos, byte[] bytes, int offset, int len)");
	}

	@Override
	public OutputStream setBinaryStream(long pos) throws SQLException {
		throw Driver.notImplemented(this.getClass(), "setBinaryStream(long pos)");
	}

	@Override
	public void truncate(long len) throws SQLException {
		throw Driver.notImplemented(this.getClass(), "truncate(long len)");
		
	}

	@Override
	public void free() throws SQLException {
		this.data = null;
		
	}

	@Override
	public InputStream getBinaryStream(long pos, long length) throws SQLException {
		byte[] sub = Arrays.copyOfRange(data, (int)pos, (int)(pos + length));
		return new ByteArrayInputStream(sub);
	}

}
