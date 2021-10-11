(ns demo.config
  (:use tupelo.core)
  (:require
    [environ.core :as environ]
    ))

(def snowflake-account (environ/env :snowflake-account))
(def snowflake-user (environ/env :snowflake-user))
(def snowflake-password (environ/env :snowflake-password))




