(ns tst.demo.snowflake
  (:use tupelo.core tupelo.test)
  (:require
    [demo.config :as config]
    [next.jdbc :as jdbc]
    ))

(spyx config/snowflake-user)
(spyx config/snowflake-password)

(import 'com.snowflake.client.jdbc.SnowflakeDriver)
(Class/forName "net.snowflake.client.jdbc.SnowflakeDriver")

(def db-credentials nil

; {:user         config/*snowflake-user*
;  :dbname       "MF79778_EQUITY_DATA_SAMPLE_SHARE"
;  :account-name config/*snowflake-account*
;  :password     config/*snowflake-pass*
;  }
  )

;(def snowflake-url (str
;                     "jdbc:snowflake://"
;                     (:account-name db-credentials)
;                     ".snowflakecomputing.com/"))
;(def snowflake-conn
;  (let [props (doto (java.util.Properties.)
;                (.put "account" (db-credentials :account-name))
;                (.put "user" (db-credentials :user))
;                (.put "password" (db-credentials :password))
;                (.put "db" (db-credentials :dbname)))]
;    (java.sql.DriverManager/getConnection snowflake-url props)))

(def snowflake-url "jdbc:snowflake://RBA54017.snowflakecomputing.com" )

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

#_(def snowflake-ds (jdbc/get-datasource snowflake-url))

;************************************************************************************************************
;***** MUST USE JAVA 11 or get exception re:  https://github.com/snowflakedb/snowflake-jdbc/issues/484  *****
;************************************************************************************************************
(dotest
  (spy :tst.demo.snowflake)
  (let [raw (unlazy (jdbc/execute! snowflake-conn
                       ["SELECT count(*) from SNOWFLAKE_SAMPLE_DATA.WEATHER.DAILY_14_TOTAL; "]))
            ; raw => [{:COUNT(*) 36406252}]
        count (-> raw only first val)]
    (spyx count)
    (is (< count 300e6)))
)
