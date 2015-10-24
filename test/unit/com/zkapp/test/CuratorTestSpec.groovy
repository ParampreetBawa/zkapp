package com.zkapp.test

import com.zkapp.TestAPI
import grails.test.mixin.TestFor
import groovy.util.logging.Log4j
import org.apache.zookeeper.CreateMode
import spock.lang.Ignore
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


    @Ignore
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

    void cleanupSpec() {
//        TestAPI.shutdown()
    }
}
