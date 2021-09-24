(ns tst.demo.snowflake
  (:use tupelo.core tupelo.test)
  (:require
    ; [demo.config :as config]
    [next.jdbc :as jdbc]
    ))

(when false ; #todo get this working

  (import 'com.snowflake.client.jdbc.SnowflakeDriver)
  (Class/forName "com.snowflake.client.jdbc.SnowflakeDriver")

  (def db-credentials nil

  ; {:user         config/*snowflake-user*
  ;  :dbname       "MF79778_EQUITY_DATA_SAMPLE_SHARE"
  ;  :account-name config/*snowflake-account*
  ;  :password     config/*snowflake-pass*
  ;  }
    )

  (def snowflake-url (str
                       "jdbc:snowflake://"
                       (:account-name db-credentials)
                       ".snowflakecomputing.com/"))

  (def snowflake-conn
    (let [props (doto (java.util.Properties.)
                  (.put "account" (db-credentials :account-name))
                  (.put "user" (db-credentials :user))
                  (.put "password" (db-credentials :password))
                  (.put "db" (db-credentials :dbname)))]
      (java.sql.DriverManager/getConnection snowflake-url props)))

  (def snowflake-ds (jdbc/get-datasource snowflake-url))

  (dotest
    (spyx-pretty
      (jdbc/execute!
        snowflake-conn
        ["SELECT *
          FROM PUBLIC.SAMPLE_EQUITY_VIEW
         LIMIT 9"])))

  )

(dotest 
  (spy :tst.demo.snowflake)
  (is= 2 3)

)
