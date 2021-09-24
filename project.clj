(defproject demo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [; priority-1 libs
                 [org.clojure/algo.generic "0.1.3"]
                 [org.clojure/clojure "1.10.3"]
                 [org.clojure/tools.reader "1.3.6"]
                 [tupelo "21.09.07"]

                 ; priority-2 libs
                 [com.rpl/specter "1.1.3"]
                 [environ "1.2.0"]
                 [http-kit "2.5.3"]
                 [org.flatland/ordered "1.5.9"]

                 ; [org.jfree/jfreechart "1.5.1"]
                 [com.climate/claypoole "1.1.4"]
                 [prismatic/schema "1.1.12"]
                 [seancorfield/next.jdbc "1.2.659"]
                 [net.snowflake/snowflake-jdbc "3.13.8"]

                 ]

  :plugins [[com.jakemccrary/lein-test-refresh "0.24.1"]
            [lein-ancient "0.7.0"]
            [lein-codox "0.10.7"]
            [lein-environ "1.2.0"]
            ]

  :global-vars {*warn-on-reflection* false}
  ; :main ^:skip-aot demo.core

  :source-paths ["src/clj"]
  :java-source-paths ["src/java"]
  :test-paths ["test/clj"]
  :target-path "target/%s"
  :compile-path "%s/class-files"
  :clean-targets [:target-path]

  :profiles {:dev     {:dependencies []}
             :uberjar {:aot :all}}

  :jvm-opts ["-Xms500m" "-Xmx2g"]
  )
