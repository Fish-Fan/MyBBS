package com.fanyank.util;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * Created by yanfeng-mac on 2017/3/28.
 */
public class DBHelp {
    public static void update(String sql,Object...param) {
        QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
        try {
            queryRunner.update(sql,param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T> T query(String sql, ResultSetHandler<T> handler, Object...param) {
        QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
        try {
            T t = queryRunner.query(sql,handler,param);
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Long insert(String sql,Object...param) {
        QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
        try {
            Long num = queryRunner.insert(sql,new ScalarHandler<Long>(),param);
            return num;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
