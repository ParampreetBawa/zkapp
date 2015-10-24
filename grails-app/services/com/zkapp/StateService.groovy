package com.zkapp

import com.zkapp.coordinator.CoordinatorNode
import com.zkapp.util.CuratorUtil
import com.zkapp.worker.WorkerNode

/**
 * Created by parampreet on 10/24/15.
 */
class StateService {
    List<InfoTable> getState() {
        List<String> workers = CuratorUtil.client.getChildren().forPath(WorkerNode.WORKER_NODE_PATH)
        List<String> coordinators = CuratorUtil.client.getChildren().forPath(CoordinatorNode.COORDINATOR_PATH)
        Set<String> coordinatorSet = new HashSet<>(coordinators)
        String leader = coordinatorSet.size() > 0 ? coordinatorSet.first() : null


        Set<String> servers = []
        servers.addAll(workers)
        servers.addAll(coordinators)
        InfoTable table = new InfoTable()

        servers.each {server->
            table.newRow().addValue("Server",server)
            .addValue("Is Coordinator",coordinators.contains(server))
            .addValue("Is Worker",workers.contains(server))
            .addValue("Is Leader", leader ? server.equals(leader) :false)
        }
        return [table]

    }

    class InfoTable {
        List<Row> rows = new ArrayList<>();

        Row newRow() {
            Row row = new Row();
            rows.add(row);
            return row;
        }

        List<Map> getRows() {
            List list =  new ArrayList();
            for(Row row: rows) {
                list.add(row.colValue);
            }
            return list;
        }

        class Row {
            Map colValue = new LinkedHashMap();

            Row addValue(Object key, Object value) {
                this.colValue.put(key, value);
                return this;
            }
        }
    }
}
