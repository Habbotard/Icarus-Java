package org.alexdev.icarus.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.alexdev.icarus.log.Log;

public class GZIPCompression {

	public static void write(String name, String data) {
		try {
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name), "utf-8"))) {
				writer.write(data);
				writer.close();
			}
		} catch (Exception e) {
			Log.exception(e);
		}
	}

	public static byte[] compress(final String str) throws IOException {
		if ((str == null) || (str.length() == 0)) {
			return null;
		}
		ByteArrayOutputStream obj = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(obj);

		gzip.write(str.getBytes("UTF-8"));
		gzip.close();

		return obj.toByteArray();
	}

	public static String decompress(final byte[] compressed) throws IOException {

		String outStr = "";

		if ((compressed == null) || (compressed.length == 0)) {
			return "";
		}

		if (isCompressed(compressed)) {
			GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed, 2, compressed.length - 2));
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, "UTF-8"));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				outStr += line;
			}
		} else {
			outStr = new String(compressed);
		}
		return outStr;
	}

	public static boolean isCompressed(final byte[] compressed) {
		return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
	}
}