(ns tst.demo.snowflake
  (:use tupelo.core tupelo.test)
  (:require
    [demo.config :as config]
    [next.jdbc :as jdbc]
    ))
(Class/forName "net.snowflake.client.jdbc.SnowflakeDriver")
  ; (import 'com.snowflake.client.jdbc.SnowflakeDriver)

(def snowflake-url "jdbc:snowflake://RBA54017.snowflakecomputing.com" )

#_(def snowflake-ds (jdbc/get-datasource snowflake-url))
(def snowflake-conn
  (let [props (doto (java.util.Properties.)
                (.put "user" config/snowflake-user)
                (.put "password" config/snowflake-password)
                (.put "warehouse" "COMPUTE_WH")
                (.put "db" "SNOWFLAKE_SAMPLE_DATA")
                (.put "schema" "WEATHER")
                (.put "role" "ACCOUNTADMIN") ; or SYSADMIN
                )]
    (java.sql.DriverManager/getConnection snowflake-url props)))

;**********************************************************************************************************************************
;***** MUST USE JAVA 15 or earlier, else get exception re:  https://github.com/snowflakedb/snowflake-jdbc/issues/484
;**********************************************************************************************************************************
(dotest
  (spy :tst.demo.snowflake)
  (let [raw (unlazy (jdbc/execute! snowflake-conn
                       ["SELECT count(*) from SNOWFLAKE_SAMPLE_DATA.WEATHER.DAILY_14_TOTAL; "]))
            ; raw => [{:COUNT(*) 36406252}]
        count (-> raw only first val)]
    (spyx count)
    (is (< 30e6 count)))
)

