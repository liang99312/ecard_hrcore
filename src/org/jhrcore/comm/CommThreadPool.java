package org.jhrcore.comm;

import javax.swing.SwingUtilities;

/**
 * 
 * @author wangzhenhua 2006-11.25
 * 这个是客户端线程池,用来执行一些耗时任务.
 * 使用方法:
 * Runnable r = new Runnable(){...}
 * ClientThreadPool.getClientThreadPool().handleEvent(r);
 *
 */
public class CommThreadPool extends Wrap {

    private static CommThreadPool clientThreadPool = null;

    public synchronized static CommThreadPool getClientThreadPool() {
        if (clientThreadPool == null) {
            clientThreadPool = new CommThreadPool();
        }
        return clientThreadPool;
    }

    private CommThreadPool() {
        super();
        initWrap(10);
    }

    @Override
    protected boolean processEvent(Object event) {
        return true;
    }

    public void handleUIEvent(final Runnable run) {
        Runnable run2 = new Runnable() {

            public void run() {
                SwingUtilities.invokeLater(run);
            }
        };
        handleEvent(run2);
    }
}
