package com.zkapp

import org.apache.curator.RetryPolicy
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.framework.imps.CuratorFrameworkState
import org.apache.curator.framework.recipes.cache.NodeCache
import org.apache.curator.framework.recipes.cache.NodeCacheListener
import org.apache.curator.framework.recipes.cache.PathChildrenCache
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener
import org.apache.curator.framework.recipes.cache.TreeCache
import org.apache.curator.framework.recipes.cache.TreeCacheEvent
import org.apache.curator.framework.recipes.cache.TreeCacheListener
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.zookeeper.CreateMode



/**
 * Created by parampreet on 10/24/15.
 */
class TestAPI {
    static CuratorFramework client
    static PathChildrenCache cache
    static {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3)
        client = CuratorFrameworkFactory.newClient("localhost:2181", policy)
        client.start()
    }

    public static void createNode(String path) {
        if (client.checkExists().forPath(path) == null)
            client.create().forPath(path, new byte[0]);
    }

    public static void setData(String path, String data) {
        client.setData().forPath(path,data.getBytes())
    }


    public static boolean nodeExists(String path) {
        return (client.checkExists().forPath(path) != null)
    }

    public static void deleteNode(String path) {
        if (nodeExists(path)) {
            client.delete().forPath(path)
        }
    }

    public static NodeCache setNodeCache(String path, Holder holder) {
        createNode(path)
        NodeCache cache = new NodeCache(client,path)
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            void nodeChanged() throws Exception {
                holder.event++
            }
        });
        cache.start()
        cache
    }

    public static TreeCache setTreeCache(String path, Holder holder) {
        createNode(path)
        TreeCache cache = new TreeCache(client,path)
        cache.getListenable().addListener(new TreeCacheListener() {
            @Override
            void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                holder.event++
            }
        })
        cache.start()
        cache
    }

    public static void setPathChildrenCache(String path,Holder holder) {
        createNode(path)
        cache = new PathChildrenCache(client,path,true)
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                holder.event++
            }
        });
        cache.start()
    }

    public static void shutdown() {
        if(cache != null)
           cache.close()

        if (client != null && client.getState() == CuratorFrameworkState.STARTED) {
            client.close()
        }
    }

    public static void createNode(String path,CreateMode createMode) {
        if (client.checkExists().forPath(path) == null) {
            client.create().withMode(createMode).forPath(path, new byte[0]);
        }
    }


    static Holder newHolder() {
        new Holder()
    }

    static class Holder {
        int event = 0
    }
    public static void main(String[] args) {
        String path = "/zkapp/test"
        createNode(path)
        PathChildrenCache cache = new PathChildrenCache(client,path,true)
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                println event.type
            }
        })
        cache.start()
        Thread.sleep(10000000)
    }
}
