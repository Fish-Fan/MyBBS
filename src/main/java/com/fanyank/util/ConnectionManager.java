package com.fanyank.util;

import com.fanyank.exception.DataAccessException;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by yanfeng-mac on 2017/3/28.
 */
public class ConnectionManager {
    private static BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setDriverClassName(ConfigProp.get("jdbc.Driver"));
        dataSource.setUrl(ConfigProp.get("jdbc.url"));
        dataSource.setUsername("jdbc.username");
        dataSource.setPassword("jdbc.password");

        dataSource.setInitialSize(5);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(5);
        dataSource.setMaxWaitMillis(5000);

    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e,"获取数据库连接异常");
        }
    }
}
