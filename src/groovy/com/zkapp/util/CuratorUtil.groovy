package com.zkapp.util

import com.zkapp.ApplicationContextHolder
import org.apache.curator.RetryPolicy
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.zookeeper.CreateMode

/**
 * Created by parampreet on 10/24/15.
 */
class CuratorUtil {
    private static CuratorFramework client = null
    static {
        String zookeeperStr = ApplicationContextHolder.instance.config.zookeeper.address
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3)
        client = CuratorFrameworkFactory.newClient(zookeeperStr, retryPolicy)
        client.start()

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            void run() {
                if(client != null)
                    client.close()
            }
        }))
    }
    public static CuratorFramework getClient(){
        return client
    }

    public static boolean checkNodeExist(String path) {
        CuratorFramework client = getClient();
        try {
            return client.checkExists().forPath(path) != null ||
                    client.getChildren().forPath(path).size() > 0;
        }catch (Exception e) {
            //ok
        }
        return false;
    }

    public static void ensureNode(String path) {
        ensureNode(path,CreateMode.PERSISTENT)
    }

    public static void ensureNode(String path, CreateMode createMode) {
        if(checkNodeExist(path))
            return

        CuratorFramework client = getClient();
        client.create().creatingParentsIfNeeded().withMode(createMode).forPath(path,new byte[0])
    }

    public static void writeData(String path, String data) {
        CuratorFramework client = getClient();
        ensureNode(path)

        client.setData().forPath(path,data.getBytes())
    }
}
