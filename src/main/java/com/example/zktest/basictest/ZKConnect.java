package com.example.zktest.basictest;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

@Slf4j
public class ZKConnect implements Watcher {

    public static final String zkServerPath = "192.168.174.128:2181";
    public static final Integer timeout = 5000;
    public static void main(String[] args) throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper(zkServerPath,timeout, new ZKConnect());

        log.debug("客户端开始连接zookeeper服务器...");
        log.debug("连接状态：{}", zooKeeper.getState());

        Thread.sleep(20000);
        log.debug("连接状态:{}", zooKeeper.getState());
    }

    @Override
    public void process(WatchedEvent event) {
        log.debug("接受到watch通知:{}", event);
    }
}
