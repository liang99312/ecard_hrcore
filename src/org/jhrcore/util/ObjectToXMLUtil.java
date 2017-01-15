package org.jhrcore.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*
 * 序列化对象和xml的相互转换
 */
public class ObjectToXMLUtil {

    public static byte[] objectXmlEncoder(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(bos);
        if (obj instanceof List) {
            for (Object o : (List) obj) {
                encoder.writeObject(o);
            }

        } else {
            encoder.writeObject(obj);
        }
        encoder.flush();
        encoder.close();
        byte[] result = bos.toByteArray();
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object objectXmlDecoder(byte[] source) {
        List<Object> objList = new ArrayList<Object>();
        InputStream is = new ByteArrayInputStream(source);
        XMLDecoder decoder = new XMLDecoder(is);
        Object obj = null;
        try {
            while ((obj = decoder.readObject()) != null) {
                objList.add(obj);
            }
        } catch (Exception e) {
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        decoder.close();
        return objList.size() > 0 ? objList.get(0) : null;
    }

    public static void objectXmlEncoder(Object obj, String fileName)
            throws FileNotFoundException, IOException, Exception {
        File fo = new File(fileName);
        if (!fo.exists()) {
            String path = fileName.substring(0, fileName.lastIndexOf('.'));
            File pFile = new File(path);
            pFile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(fo);
        XMLEncoder encoder = new XMLEncoder(fos);
        if (obj instanceof List) {
            for (Object o : (List) obj) {
                encoder.writeObject(o);
            }
        } else {
            encoder.writeObject(obj);
        }
        encoder.flush();
        encoder.close();
        fos.close();
    }

    public static List objectXmlDecoder(String objSource)
            throws FileNotFoundException, IOException, Exception {
        List<Object> objList = new ArrayList<Object>();
        File fin = new File(objSource);
        FileInputStream fis = new FileInputStream(fin);
        XMLDecoder decoder = new XMLDecoder(fis);
        Object obj = null;
        try {
            while ((obj = decoder.readObject()) != null) {
                objList.add(obj);
            }
        } catch (Exception e) {
        }
        fis.close();
        decoder.close();
        return objList;
    }
}
