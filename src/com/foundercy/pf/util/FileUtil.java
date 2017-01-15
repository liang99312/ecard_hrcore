package com.foundercy.pf.util;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class FileUtil {

	/**
	 * ��ClassPath�в��������ļ����������ļ��ľ���·��
	 * 
	 * @param fileName
	 *            �����ļ�������
	 * @return
	 * @throws MalformedURLException
	 */
	public static String getFilePath(String fileName) {
		if (fileName == null)
			return null;
		URL url = FileUtil.class.getClassLoader().getResource(fileName);
		if (url == null) {
			try {
				url = new URL("file", "localhost", fileName);
			} catch (MalformedURLException e) {
				return null;
			}
		}
		return url.getFile();
	}

	/**
	 * ���ļ�תΪString
	 * 
	 * @param fileName
	 *            �������·����Ҳ�����Ǿ���·����
	 * @return �ļ������ַ�����
	 * @throws IOException
	 */
	public static String file2String(String fileName) throws IOException {

		if (fileName == null)
			return null;
		byte[] data = getData(fileName);
		if (data == null)
			return null;

		return new String(data,"GBK");
	}

	/**
	 * ���ļ�תΪString ���200,000,000bytes
	 * 
	 * @param fileName
	 *            �������·����Ҳ�����Ǿ���·����
	 * @return �ļ������ַ�����
	 * @throws IOException
	 */
	public static byte[] file2Bytes(String fileName) throws IOException {

		if (fileName == null)
		{
			throw new IOException("δ�趨��Ӧ���ļ�·��!");
		}
		return getData(fileName);
	}
	private static byte[] getData(String fileName) throws IOException {

		//�������ļ����ڴ�ʵ��
		File file = null;
		try{
			file = new File(fileName);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//�Ӿ���·��ȡ���ļ���
		InputStream in = null;
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		if (file.exists() && file != null) {
			in = new java.io.FileInputStream(file);
			if (in.available() > 0) {
				int bit;
				while((bit=in.read()) != -1)
				{
				  if(bit == 10 || bit == 13)continue;
				  data.write(bit);
				}
			}
			in.close();
			//��ClassPath��ȡ���ļ�ʵ����
		} else {
			//�쿴�����Ƿ���ڸ��ļ�
			in = FileUtil.class.getClassLoader().getParent().getResourceAsStream(fileName);
			
			if (in == null) {
				in = FileUtil.class.getClassLoader().getResourceAsStream(
						fileName);
				if (in == null) {
					throw new IOException(
							"Can not read the file in classpath--" + fileName);
				} else {
					if (in.available() > 0) {
						int bit;
						while((bit=in.read()) != -1)
						{
						  if(bit == 10 || bit == 13)continue;
						  data.write(bit);
						}
					}
					in.close();
				}
			} else {
				if (in.available() > 0) {
					int bit;
					while((bit=in.read()) != -1)
					{
					  if(bit == 10 || bit == 13)continue;
					  data.write(bit);
					}
				}
				in.close();
			}
		}
		return data.toByteArray();
	}	
}