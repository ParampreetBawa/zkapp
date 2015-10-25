package com.zkapp.test

import com.zkapp.TestAPI
import groovy.util.logging.Log4j
import org.apache.curator.framework.recipes.cache.NodeCache
import org.apache.curator.framework.recipes.cache.TreeCache
import org.apache.zookeeper.CreateMode
import spock.lang.Specification

/**
 * Created by parampreet on 10/24/15.
 */
@Log4j
class CuratorTestSpec extends Specification {
    String path = "/zkapp/test"
    String path2 = "/zkapp/test2"
    def canaryTest() {
        log.info("canary test")
        expect:
        1 == 1
    }

    def "test create node"() {
        when:
        TestAPI.createNode(path)
        then:
        TestAPI.nodeExists(path)
    }


    def "test delete node"() {
        when:
        TestAPI.deleteNode(path)
        then:
        !TestAPI.nodeExists(path)
    }

    def "create ephermeral node"() {
        when:
        TestAPI.createNode(path2,CreateMode.EPHEMERAL)
        then:
        TestAPI.nodeExists(path2)
    }


    def "create a node cache"() {
        when:
        TestAPI.Holder holder = TestAPI.newHolder()
        NodeCache cache = TestAPI.setNodeCache(path,holder)

        TestAPI.createNode(path + "/child1")
        TestAPI.createNode(path + "/child2")
        TestAPI.setData(path, "dataString")
        TestAPI.setData(path + "/child1", "dataString")
        Thread.sleep(4000)
        then:
        holder.event == 2

        cleanup:
        TestAPI.deleteNode(path + "/child1")
        TestAPI.deleteNode(path + "/child2")
        TestAPI.deleteNode(path)
        cache.close()
    }

    def "create a tree cache"() {
        when:
        TestAPI.Holder holder = new TestAPI.Holder();
        TreeCache cache = TestAPI.setTreeCache(path,holder)

        TestAPI.createNode(path + "/child1")
        TestAPI.createNode(path + "/child2")
        TestAPI.setData(path, "dataString")
        TestAPI.setData(path + "/child1", "dataString")
        Thread.sleep(4000)
        then:

        holder.event == 6

        cleanup:
        TestAPI.deleteNode(path + "/child1")
        TestAPI.deleteNode(path + "/child2")
        TestAPI.deleteNode(path)
        cache.close()

    }

    def "create a path childred cache"() {
        when:
        TestAPI.Holder holder = TestAPI.newHolder()
        TestAPI.setPathChildrenCache(path,holder)

        TestAPI.createNode(path + "/child1")
        TestAPI.createNode(path + "/child2")
        TestAPI.setData(path, "dataString")
        TestAPI.setData(path + "/child1", "dataString")
        TestAPI.deleteNode(path + "/child1")
        Thread.sleep(2000)
        then:
        holder.event == 4

        cleanup:
        TestAPI.deleteNode(path + "/child1")
        TestAPI.deleteNode(path + "/child2")
        TestAPI.deleteNode(path)

    }

    void cleanupSpec() {
        //TestAPI.shutdown()
    }
}
