/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.awt.Image;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author mxliteboss
 */
public class ImageUtil {

    public static final Icon blankIcon = getIcon("blank_2.png");
    public static final Icon selectIcon = getIcon("selected.png");
    public static final Icon unSelectIcon = getIcon("un_select.png");
    public static final Icon editWayIcon = getIcon("editWay.png");
    public static final Icon reportIcon = getIcon("report.png");
    public static final Icon queryIcon = getIcon("query_1.png");
    
    public static Image getIconImage(){
        Image iconImage = TransferAccessory.downloadPicture("@Icon");
        if(iconImage!=null){
            return iconImage;
        }else{
            return getImage("frame_icon.png");
        }
    }

    public static URL getImageURL(String iconName) {
        return ImageUtil.class.getResource("/resources/images/" + iconName);
    }

    public static Image getImage(String imageName) {
        return ((ImageIcon) getIcon(imageName)).getImage();
    }

    public static Icon getBlankIcon() {
        return blankIcon;
    }

    public static Icon getSearchIcon() {
        return getIcon("search.png");
    }

    public static Icon getIcon(String icon_name) {
        Icon icon = null;
        if (icon_name.equals("editWay")) {
            icon = editWayIcon;
        } else if (icon_name.equals("select")) {
            icon = selectIcon;
        } else if (icon_name.equals("unSelect")) {
            icon = unSelectIcon;
        } else if (icon_name.equals("blank")) {
            icon = blankIcon;
        } else if (icon_name.equals("blank_2")) {
            icon = blankIcon;
        } else if (icon_name.equals("query")) {
            icon = queryIcon;
        } else if (icon_name.equals("report")) {
            icon = reportIcon;
        } else {
            icon = new ImageIcon(ImageUtil.class.getResource("/resources/images/" + icon_name));
        }
        return icon;
    }
}
