/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.comm;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.jhrcore.client.UserContext;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.base.LogInfo;
import org.jhrcore.iservice.impl.SysImpl;

/**
 *
 * @author ����
 * �������ڰ���־��¼�����ݿ�ȥ��
 * ʹ�÷�����
 */
public class LogRecorder extends WriterAppender {

    @Override
    public void append(LoggingEvent loggingEvent) {
        if ("-1".equals(UserContext.person_key) && !UserContext.isSA) {
            return;
        }

        if (loggingEvent.getRenderedMessage() == null) {
            return;
        }
        if (!loggingEvent.getRenderedMessage().startsWith("$")) {
            return;
        }
        final LogInfo li = (LogInfo) UtilTool.createUIDEntity(LogInfo.class);
        if ("ERROR".equals(loggingEvent.getLevel().toString())) {
            li.setLogLevel("����");
        } else if ("INFO".equals(loggingEvent.getLevel().toString())) {
            li.setLogLevel("��Ϣ");
        } else if ("WARN".equals(loggingEvent.getLevel().toString())) {
            li.setLogLevel("����");
        } else if ("FATAL".equals(loggingEvent.getLevel().toString())) {
            li.setLogLevel("����");
        } else if ("DEBUG".equals(loggingEvent.getLevel().toString())) {
            li.setLogLevel("����");
        } else {
            li.setLogLevel("δ֪");
        }
        li.setLoggerName(loggingEvent.getLoggerName());
        li.setDept_name(UserContext.content);
        String message = loggingEvent.getRenderedMessage().substring(1);
        if (message.length() > 4) {
            String match = message.substring(0, 1);
            if (match.trim().equals("")) {
                if (message.length() > 4000) {
                    message = message.substring(0, 3550);
                }
                message = message.replaceAll("'", "''");
                li.setMessgae(message);
            } else {
                if (match.equals("1")) {
                    li.setLogType("������¼");
                } else if (match.equals("2")) {
                    li.setLogType("�����¼");
                } else if (match.equals("3")) {
                    li.setLogType("ɾ����¼");
                }
                message = message.substring(1);

            }
        }
        li.setLogIp(UserContext.getPerson_ip());
        li.setLogMac(UserContext.getPerson_mac());
        li.setPerson_code(UserContext.person_code);
        li.setPerson_key(UserContext.person_key);
        li.setPerson_name(UserContext.person_name);
        CommThreadPool.getClientThreadPool().handleEvent(new Runnable() {

            @Override
            public void run() {
                try {
                    SysImpl.saveUserLog(li);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
