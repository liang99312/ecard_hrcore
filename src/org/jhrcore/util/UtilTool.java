package org.jhrcore.util;

import org.apache.log4j.Logger;
import org.jhrcore.entity.KeyInterface;

public class UtilTool {

    private static Logger log = Logger.getLogger("UtilTool");

    public static String getUID() {
        return java.util.UUID.randomUUID().toString();
    }

    public static Object createUIDEntity(Class cl) {
        try {
            Object object = cl.newInstance();
            if (object instanceof KeyInterface) {
                KeyInterface ki = (KeyInterface) object;
                ki.assignEntityKey(getUID());
                //System.out.println("UID:"+uid.toString());
            }
            return object;
        } catch (InstantiationException e) {
            log.error(e);
        } catch (IllegalAccessException e) {
            log.error(e);
        }
        return null;
    }

    public static Object createUIDEntity(String className) {
        try {
            Class cl = Class.forName(className);
            Object object = cl.newInstance();
            if (object instanceof KeyInterface) {
                KeyInterface ki = (KeyInterface) object;
                ki.assignEntityKey(getUID());
            }
            return object;
        } catch (ClassNotFoundException e) {
            log.error(e);
        } catch (InstantiationException e) {
            log.error(e);
        } catch (IllegalAccessException e) {
            log.error(e);
        }
        return null;
    }
}
