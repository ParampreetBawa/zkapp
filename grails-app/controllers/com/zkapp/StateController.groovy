package com.zkapp

/**
 * Created by parampreet on 10/24/15.
 */
class StateController {
    def stateService
    def index() {
        List<StateService.InfoTable> tables = stateService.getState()
        [tables:tables]
    }
}
