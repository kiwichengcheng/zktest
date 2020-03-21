package com.example.zktest.basictest;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

@Slf4j
public class ZKConnectSessionWatcher implements Watcher {


    public static final String zkServerPath = "192.168.174.128:2181";//,192.168.174.132:2181,192.168.174.133:2181";
    public static final Integer timeout = 5000;
    public static void main(String[] args) throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper(zkServerPath,timeout, new ZKConnect());

        log.debug("客户端开始连接zookeeper服务器...");
        log.debug("连接状态：{}", zooKeeper.getState());
        Thread.sleep(1000);
        log.debug("连接状态:{}", zooKeeper.getState());
        long sessionId = zooKeeper.getSessionId();
        System.out.println(Long.toHexString(sessionId));
        byte[] sessionPassword = zooKeeper.getSessionPasswd();


        log.debug("开始会话重连...");
        ZooKeeper zkSession = new ZooKeeper(zkServerPath, timeout, new ZKConnectSessionWatcher(), sessionId, sessionPassword);
        log.debug("重新获取zkSession:{}", zkSession.getState());
        Thread.sleep(1000);
        log.debug("重新获取zkSession:{}", zkSession.getState());
    }

    @Override
    public void process(WatchedEvent event) {
        log.debug("接受到watch通知:{}", event);
    }
}
