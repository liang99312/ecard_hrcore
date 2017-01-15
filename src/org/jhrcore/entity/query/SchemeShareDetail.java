/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "统计方案共享明细表", moduleName = "系统维护")
public class SchemeShareDetail extends Model implements Serializable, KeyInterface, IKey {

    public String schemeShareDetail_key;
    public String scheme_key;
    public String shareType;//共享模式：按角色；按员工
    public String userKey;//根据共享模式写入rolekey或a01key
    public transient int new_flag = 0;

    @Id
    public String getSchemeShareDetail_key() {
        return schemeShareDetail_key;
    }

    public void setSchemeShareDetail_key(String schemeShareDetail_key) {
        this.schemeShareDetail_key = schemeShareDetail_key;
    }

    public String getScheme_key() {
        return scheme_key;
    }

    public void setScheme_key(String scheme_key) {
        this.scheme_key = scheme_key;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    @Override
    public void assignEntityKey(String key) {
        this.schemeShareDetail_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }
}
