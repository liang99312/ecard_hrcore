/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.serviceproxy.http;

import java.io.Serializable;

public class HttpTransport implements Serializable {
    private static final long serialVersionUID = 1L;
    private String serviceName;   // 服务名，增加"Impl"之后为服务器实现该服务的类名
    private String serviceMethod; // 服务方法名
    private boolean success = false;  // 调用是否成功
    private byte[] transportObject = null; // 参数，需要从TransportObject继承下来
    private Object result = null;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    
    private String errorMessage = null;

    public String getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public byte[] getTransportObject() {
        return transportObject;
    }

    public void setTransportObject(byte[] transportObject) {
        this.transportObject = transportObject;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}