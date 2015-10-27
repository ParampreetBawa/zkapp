package com.zkapp

import groovy.util.logging.Log4j

/**
 * Created by parampreet on 10/24/15.
 */
@Log4j
class StateController {
    def stateService
    def index() {
        List<StateService.InfoTable> tables = stateService.getState()
        [tables:tables]
    }
}
