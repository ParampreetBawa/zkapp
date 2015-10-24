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

    protected void printTable(String tableId, PrintWriter pout, List<Map> items, String caption) {
        if (items.size() == 0) {
            return;
        }

        if(tableId != null){
            pout.println("<TABLE id='" + tableId + "' border='1'>");
        }else{
            pout.println("<TABLE border='1'>");
        }

        if(caption != null) {
            pout.println("<CAPTION>"+caption+"<CAPTION>");
        }
        pout.println("<TBODY>");

        pout.println("<TR>");
        Set<String> keys = items.get(0).keySet();
        for (Object key : keys) {
            pout.println("<TH style='horizontal-align:left'>" + key + "</TH>");
        }
        pout.println("</TR>");


        for (Map item : items) {
            pout.println("<TR>");
            for (Object key : keys) {
                pout.println("<TD style='vertical-align:top; white-space:nowrap;' class='" + key + "'>" + item.get(key) + "</TD>");
            }
            pout.println("</TR>");
        }

        pout.println("</TBODY>");

        pout.println("</TABLE>");
    }
}
