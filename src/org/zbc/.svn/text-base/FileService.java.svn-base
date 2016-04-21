package org.zbc;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import android.content.Context;

public class FileService {
	
	private Context context;

	public FileService(Context context) {
		this.context = context;
	}


	public String read(String filename) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		FileInputStream inStream = new FileInputStream(filename);
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len = inStream.read(buffer)) != -1){
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		byte[] data = outStream.toByteArray();
		return new String(data);
	}

}
