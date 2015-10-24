package com.zkapp.worker

import com.zkapp.util.CuratorUtil
import com.zkapp.util.Util
import groovy.util.logging.Log4j
import org.apache.zookeeper.CreateMode

/**
 * Created by parampreet on 10/24/15.
 */
@Log4j
class WorkerNode {
    public static final String WORKER_NODE_PATH = "/zkapp/workers"

    public WorkerNode() {
        String address = null
        try {
            address = Util.getAddress()
            CuratorUtil.ensureNode(WORKER_NODE_PATH + "/" +address,CreateMode.EPHEMERAL)
        } catch (Exception e) {
            log.error("error occurred while instantiating worker node" + address,e)
        }
    }


}
