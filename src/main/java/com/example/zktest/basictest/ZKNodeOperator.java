package com.example.zktest.basictest;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ZKNodeOperator implements Watcher {

    private ZooKeeper zooKeeper = null;
    public static final String zkServerPath = "192.168.174.128:2181,192.168.174.132:2181,192.168.174.133:2181";
    public static final Integer timeout = 15000;

    public ZKNodeOperator() {
    }

    public ZKNodeOperator(String connectString){
        try{
            zooKeeper = new ZooKeeper(connectString, timeout, new ZKNodeOperator());
            Thread.sleep(20000);
        }catch (IOException e){
            e.printStackTrace();
            if(zooKeeper != null){
                try{
                    zooKeeper.close();
                }catch (InterruptedException e1){
                    e1.printStackTrace();
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void createZKNode(String path, byte[] data, List<ACL> acls) {

        String result = "";
        try {
            /**
             * 同步或者异步创建节点，都不支持子节点的递归创建，异步有一个callback函数
             * 参数：
             * path：创建的路径
             * data：存储的数据的byte[]
             * acl：控制权限策略
             * 			Ids.OPEN_ACL_UNSAFE --> world:anyone:cdrwa
             * 			CREATOR_ALL_ACL --> auth:user:password:cdrwa
             * createMode：节点类型, 是一个枚举
             * 			PERSISTENT：持久节点
             * 			PERSISTENT_SEQUENTIAL：持久顺序节点
             * 			EPHEMERAL：临时节点
             * 			EPHEMERAL_SEQUENTIAL：临时顺序节点
             */
            //result = zooKeeper.create(path, data, acls, CreateMode.PERSISTENT);

			String ctx = "{'create':'success'}";
			zooKeeper.create(path, data, acls, CreateMode.PERSISTENT, new CreateCallBack(), ctx);

            System.out.println("创建节点：\t" + result + "\t成功...");
            new Thread().sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ZKNodeOperator zkNodeOperator = new ZKNodeOperator(zkServerPath);
        zkNodeOperator.createZKNode("/testnode","testnode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    @Override
    public void process(WatchedEvent event) {
        log.debug("接受到watch通知:{}", event);
    }
}
