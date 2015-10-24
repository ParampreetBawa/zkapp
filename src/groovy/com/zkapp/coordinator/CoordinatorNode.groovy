package com.zkapp.coordinator

import com.zkapp.util.CuratorUtil
import com.zkapp.util.Util
import com.zkapp.worker.WorkerNode
import groovy.util.logging.Log4j
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.recipes.cache.PathChildrenCache
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener
import org.apache.zookeeper.CreateMode

/**
 * Created by parampreet on 10/24/15.
 */
@Log4j
class CoordinatorNode {
    public static final String COORDINATOR_PATH = "/zkapp/coordinators"
    PathChildrenCache cache = null
    public CoordinatorNode() {
        try {
            CuratorUtil.ensureNode(COORDINATOR_PATH + "/" + Util.getAddress(),CreateMode.EPHEMERAL)
            cache = new PathChildrenCache(CuratorUtil.getClient(), WorkerNode.WORKER_NODE_PATH,true)
            cache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    println event.data
                }
            });
            cache.start()
        }catch (Exception e) {
            log.error("error while creating coordinator node",e)
        }
    }


}
