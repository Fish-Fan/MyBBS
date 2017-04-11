package com.fanyank.dao;

import com.fanyank.entity.Node;
import com.fanyank.util.DBHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by yanfeng-mac on 2017/3/29.
 */
public class NodeDao {
    public void save(Node node) {
        String sql = "insert into t_node(nodename) values(?)";
        DBHelp.update(sql,node.getNodename());
    }

    public List<Node> getAllNode() {
        String sql = "select * from t_node";
        return DBHelp.query(sql,new BeanListHandler<Node>(Node.class));
    }

    public Node findById(Integer node_id) {
        String sql = "select * from t_node where id = ?";
        return DBHelp.query(sql,new BeanHandler<Node>(Node.class),node_id);
    }
}
