package org.jhrcore.serviceproxy;

public interface ILocator {

    public Object lookupServiceOf(String serviceName);

    public Object lookupControlCenterServiceOf(String serviceName);

    public Object lookupServiceOf(String serverName, String serviceName);

    public void setServicePara(String ip, String port);

    public Object invokeCurService(String serviceName, String methodName, Object[] params);
}
