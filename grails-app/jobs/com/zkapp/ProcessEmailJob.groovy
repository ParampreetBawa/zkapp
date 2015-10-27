package com.zkapp

import com.zkapp.util.CuratorUtil
import groovy.util.logging.Log4j

/**
 * Created by parampreet on 10/26/15.
 */
@Log4j
class ProcessEmailJob {
    def concurrent = false
    static triggers = {
        simple startDelay :1000,repeatInterval:5000
    }

    def execute() {
        if (CuratorUtil.isLeader()) {
            log.info("processing job")
            Thread.sleep(2000)
        }
    }
}
