/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vnpay.db;

/**
 *
 * @author TranManh
 */

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 */
public class DBPoolConnection {
//    private static DataSource ds = getDataSource();

    private static final Logger logger = LogManager.getLogger (DBPoolConnection.class);
    private static long lastChecked = System.currentTimeMillis();
    private static final long PERIOD_CHECK = 3 * 60 * 1000; //1000
    private static BoneCP connectionPool = getDataSource();
    private static String DB_URL = null;
    private static String DB_USERNAME = null;
    private static String DB_PASSWORD = null;

    static {
        Init();
    }

    private static void Init() {
        //Initial Value
    }
    private static DBPoolConnection Instance = new DBPoolConnection();

    public static DBPoolConnection GetInstance() {
        return Instance;
    }

    private static BoneCP getDataSource() {

        try {
            logger.info("current directory: " + System.getProperty("user.dir"));
            //init pool data source
            Properties appConfig = new Properties();

            appConfig.load(new FileInputStream("conf/c3p0.properties"));
//            String TNS_PATH = appConfig.getProperty("tnsPath");
//            System.setProperty("oracle.net.tns_admin", TNS_PATH);
//            logger.info("tns path: " + TNS_PATH);
            try {

//             System.setProperty("oracle.net.tns_admin", "E:\\app\\Nam\\product\\11.2.0\\client_2\\network\\admin");
                // load the database driver (make sure this is in your classpath!)
                Class.forName("oracle.jdbc.OracleDriver");
            } catch (Exception e) {

                logger.info(e.getMessage(), e);
                System.out.println("Error....." + e.getMessage());
                return null;
            }

            DB_URL = appConfig.getProperty("jdbcUrl");
            DB_USERNAME = appConfig.getProperty("user");
            DB_PASSWORD = appConfig.getProperty("password");
            int MIN_POOL_SIZE = Integer.parseInt(appConfig.getProperty("minPoolSize"));
            int MAX_POOL_SIZE = Integer.parseInt(appConfig.getProperty("maxPoolSize"));
            int ACQUIRE_INCREMENT = Integer.parseInt(appConfig.getProperty("acquireIncrement"));
            int MAX_IDLE_TIME = Integer.parseInt(appConfig.getProperty("maxIdleTime"));

            BoneCPConfig config = new BoneCPConfig();
            config.setInitSQL("SELECT 1 FROM DUAL");
            config.setIdleConnectionTestPeriodInMinutes(5); //5 phut test 1 lan
            config.setConnectionTimeoutInMs(5000); //5s
            config.setAcquireRetryAttempts(10);  //bao nhieu lan bi fail
            config.setIdleMaxAgeInMinutes(10); //This sets the time for a connection to remain idle before sending a test query to the DB.
            config.setMaxConnectionAgeInSeconds(36000); //1hour
            config.setAcquireIncrement(ACQUIRE_INCREMENT);
            config.setJdbcUrl(DB_URL); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
            config.setUsername(DB_USERNAME);
            config.setPassword(DB_PASSWORD);
            config.setMinConnectionsPerPartition(MIN_POOL_SIZE);
            config.setMaxConnectionsPerPartition(MAX_POOL_SIZE);
            config.setIdleMaxAgeInSeconds(MAX_IDLE_TIME); //Time to wait before dropping idle connections, in minutes.

//                config.set
            config.setPartitionCount(2);
            connectionPool = new BoneCP(config); // setup the connection pool

            return connectionPool;
        } catch (ExceptionInInitializerError ex) {
            logger.info("getdatasource error: " + ex.getMessage());
//		System.out.println("connection Pool .....Error ...." + ex.getMessage());
            logger.info(ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.info("getdatasource error: " + ex.getMessage());
//		System.out.println("connection Pool .....Error ...." + ex.getMessage());
            logger.info(ex.getMessage(), ex);
        }
        return null;
    }


    public static Connection getConnection() {
        Connection con = null;
        try {
            if (connectionPool == null) {
                getDataSource();
            }

            if (connectionPool != null) {
                con = connectionPool.getConnection();
            } else {
                logger.info("try to get another connection by normal way: " + DB_URL);
                Class.forName("oracle.jdbc.OracleDriver");
                con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            }

        } catch (Exception ex) {
            try {

                logger.info("error pool connection: " + ex.getMessage());

                logger.info(ex.getMessage(), ex);

                Class.forName("oracle.jdbc.OracleDriver");
                con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            } catch (Exception ex1) {
                logger.info("Oracle issue " + ex1.getMessage(), ex1);
            }
        }
        return con;
    }
}
