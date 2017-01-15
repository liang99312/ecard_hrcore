package org.jhrcore.ui.language;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.base.TempFieldInfo;

/**
 * 
 * 
 * @author PanMj
 * 
 */
public class WebHrMessage {

    private static Map<String, String> resourManage = new HashMap();
    private static Map<String, String> baseSrc = new HashMap();

    public static void initInitationRes(String type) {
//        if (baseSrc.isEmpty()) {
//            List list = CommUtil.selectSQL("select ib.res_key,ib.res_text from InternationBase ib ");
//            for (Object obj : list) {
//                Object[] objs = (Object[]) obj;
//                String key = SysUtil.objToStr(objs[0]);
//                String value = SysUtil.objToStr(objs[1]);
//                if (key.trim().equals("")) {
//                    continue;
//                }
//                baseSrc.put(key, value);
//            }
//        }
//        resourManage.clear();
//        for (String key : baseSrc.keySet()) {
//            resourManage.put(key, baseSrc.get(key));
//        }
//        List list = CommUtil.selectSQL("select ib.res_key,ir.res_value from InternationBase ib,InternationRes ir where ir.language ='" + type + "' and ir.res_key=ib.res_key");
//        for (Object obj : list) {
//            Object[] objs = (Object[]) obj;
//            String key = SysUtil.objToStr(objs[0]);
//            String value = SysUtil.objToStr(objs[1]);
//            if (key.equals("") || value.trim().equals("")) {
//                continue;
//            }
//            resourManage.put(key, value);
//        }
    }

    public static void initTfiInitation(TempFieldInfo tfi) {
        String entityCaption = WebHrMessage.getString("Entity." + tfi.getEntity_name());
        if (entityCaption == null || entityCaption.trim().equals("")) {
            entityCaption = tfi.getEntity_caption();
        }
        tfi.setEntity_caption(entityCaption);
        String title = WebHrMessage.getString("Field." + tfi.getEntity_name() + "." + tfi.getField_name());
        title = (title == null || title.trim().equals("")) ? tfi.getCaption_name() : title;
        tfi.setCaption_name(title);
    }

    /**
     * 
     * 
     * @param key
     *            
     * @return
     */
    public static String getString(String key) {

        return resourManage.get(key);
//        try {
//            return (String) (resourManage.get(key) == null ? key : resourManage.get(key));
//        } catch (Exception e) {
//            return '!' + key + '!';
//        }
    }

    public static String getMsgString(Class c, String fieldName, String defaultValue) {
        if (UserContext.languages.size() <= 1 || UserContext.language == null || UserContext.language.equals(UserContext.language_CN) || c == null) {
            return defaultValue;
        }
        String pre = c.getSimpleName();
        pre = pre.substring(0, pre.lastIndexOf("Msg")) + ".msg.";
        String result = resourManage.get(pre + fieldName);
        return result == null ? defaultValue : result;
    }
}
