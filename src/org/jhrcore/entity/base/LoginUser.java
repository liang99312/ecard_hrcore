/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity.base;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.EnumHint;

/**
 *
 * @author hflj
 */
@Entity
public class LoginUser extends Model implements Serializable,KeyInterface {
    private String loginUser_key;//标识
    private String user_code;//登录标识
    private String user_name;//登录用户名
    private Date login_time;//登录时间
    private String log_ip;//登录IP
    private String log_mac;//登录MAC
    private long server_time;//服务器启动时间
    private long last_visit_time;//最近一次操作时间
    @EnumHint(enumList = "在线;离线;超时;强制下线")
    private String user_state="离线";//当前用户状态
    private String msg;//用于记录强制用户下线的提示消息
    private int msg_no = 0;//新消息数量
    public transient int new_flag = 0;
    @Id
    public String getLoginUser_key() {
        return loginUser_key;
    }

    public void setLoginUser_key(String loginUser_key) {
        String old = this.loginUser_key;
        this.loginUser_key = loginUser_key;
        this.firePropertyChange("loginUser_key", old, loginUser_key);
    }
    public String getLog_ip() {
        return log_ip;
    }

    public void setLog_ip(String log_ip) {
        String old = this.log_ip;
        this.log_ip = log_ip;
        this.firePropertyChange("log_ip", old, log_ip);
    }

    public String getLog_mac() {
        return log_mac;
    }

    public void setLog_mac(String log_mac) {
        String old = this.log_mac;
        this.log_mac = log_mac;
        this.firePropertyChange("log_mac", old, log_mac);
    }

    public Date getLogin_time() {
        return login_time;
    }

    public void setLogin_time(Date login_time) {
        Date old = this.login_time;
        this.login_time = login_time;
        this.firePropertyChange("login_time", old, login_time);
    }

    public long getServer_time() {
        return server_time;
    }

    public void setServer_time(long server_time) {
        long old = this.server_time;
        this.server_time = server_time;
        this.firePropertyChange("server_time", old, server_time);
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        String old = this.user_code;
        this.user_code = user_code;
        this.firePropertyChange("user_code", old, user_code);
    }

    public long getLast_visit_time() {
        return last_visit_time;
    }

    public void setLast_visit_time(long last_visit_time) {
        long old = this.last_visit_time;
        this.last_visit_time = last_visit_time;
        this.firePropertyChange("last_visit_time", old, last_visit_time);
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        String old = this.user_state;
        this.user_state = user_state;
        this.firePropertyChange("user_state", old, user_state);
    }

    @Override
    public String toString() {
        return user_name+"("+user_state+")";
    }

    @Override
    public void assignEntityKey(String key) {
        this.loginUser_key = key;
        this.new_flag = 1;
    }
    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        int old = this.new_flag;
        this.new_flag = new_flag;
        this.firePropertyChange("new_flag", old, new_flag);
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        String old = this.msg;
        this.msg = msg;
        this.firePropertyChange("msg", old, msg);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LoginUser other = (LoginUser) obj;
        if ((this.loginUser_key == null) ? (other.loginUser_key != null) : !this.loginUser_key.equals(other.loginUser_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.loginUser_key != null ? this.loginUser_key.hashCode() : 0);
        return hash;
    }

    public int getMsg_no() {
        return msg_no;
    }

    public void setMsg_no(int msg_no) {
        int old = this.msg_no;
        this.msg_no = msg_no;
        this.firePropertyChange("msg_no", old, msg_no);
    }
    
}
