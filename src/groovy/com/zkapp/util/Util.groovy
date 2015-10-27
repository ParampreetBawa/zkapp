package com.zkapp.util

import com.zkapp.ApplicationContextHolder
import com.zkapp.coordinator.CoordinatorNode
import com.zkapp.worker.WorkerNode

/**
 * Created by parampreet on 10/24/15.
 */
class Util {
    private static String hostName = null
    static {
        Integer port = getPort()
        String name = new Inet4Address().getLocalHost().getHostName()
        if (port != null)
            hostName = name + ":" + port;
        else
            hostName = name

    }

    public static void init() {
        boolean isWorker = ApplicationContextHolder.instance.config.worker.enabled
        if (isWorker) {
            new WorkerNode();
        }
    }

    static String getAddress() {
        return hostName;
    }

    private static Integer getPort() {
        String port = System.getProperty("grails.server.port.http")
        if (port) {
            return port as Integer
        }
        return null
    }
}
