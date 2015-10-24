package com.zkapp

import org.apache.curator.RetryPolicy
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.framework.imps.CuratorFrameworkState
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.zookeeper.CreateMode

/**
 * Created by parampreet on 10/24/15.
 */
class TestAPI {
    static CuratorFramework client
    static {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3)
        client = CuratorFrameworkFactory.newClient("localhost:2181", policy)
        client.start()
    }

    public static void createNode(String path) {
        if (client.checkExists().forPath(path) == null)
            client.create().forPath(path, new byte[0]);
    }


    public static boolean nodeExists(String path) {
        return (client.checkExists().forPath(path) != null)
    }

    public static void deleteNode(String path) {
        if (nodeExists(path)) {
            client.delete().forPath(path)
        }
    }

    public static void shutdown() {
        if (client != null && client.getState() == CuratorFrameworkState.STARTED) {
            client.close()
        }
    }

    public static void createNode(String path,CreateMode createMode) {
        if (client.checkExists().forPath(path) == null) {
            client.create().withMode(createMode).forPath(path, new byte[0]);
        }
    }

    public static void main(String[] args) {
        createNode()
        println(client.checkExists().forPath(path).class)
    }
}
