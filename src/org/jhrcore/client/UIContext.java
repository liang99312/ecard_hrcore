/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client;

import java.util.ArrayList;
import java.util.List;
import org.jhrcore.uimanager.face.BlueFace;
import org.jhrcore.uimanager.face.StandardFace;
import org.jhrcore.uimanager.face.UIFace;

/**
 *
 * @author mxliteboss
 */
public class UIContext {

    private static UIFace currenFace;
    
    public static List getFaces() {
        List list = new ArrayList();
        list.add(new StandardFace());
        list.add(new BlueFace());
        return list;
    }

    public static void setCurrenFace(UIFace currenFace) {
        UIContext.currenFace = currenFace;
    }

    public static UIFace getCurrenFace() {
        return currenFace;
    }
}
