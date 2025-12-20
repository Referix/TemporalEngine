package org.referix.temporalEngine.persistence.sqlite;


import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

public final class OrmLiteBootstrap {

    private OrmLiteBootstrap() {}

    public static ConnectionSource init(File dataFolder) throws SQLException {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File dbFile = new File(dataFolder, "temporal.db");
        String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();

        ConnectionSource source = new JdbcConnectionSource(url);
        TableUtils.createTableIfNotExists(source, TemporalStateEntity.class);

        return source;
    }
}
