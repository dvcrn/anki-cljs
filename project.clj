(defproject anki-cljs "0.1.0"
  :description "CLJS interface for interacting with Anki"
  :url "https://github.com/dvcrn/anki-cljs"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [cljs-http "0.1.45"]
                 [lein-figwheel "0.5.15"]
                 [org.clojure/core.match "0.3.0-alpha5"]]
  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.2"]
                                  [org.clojure/tools.nrepl "0.2.10"]
                                  [figwheel-sidecar "0.5.8"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-npm "0.6.2"]
            [lein-cljsbuild "1.1.7"]
            [refactor-nrepl "2.3.1"]
            [cider/cider-nrepl "0.14.0"]]
  :npm {:dependencies [[source-map-support "0.4.0"]]}
  :source-paths ["src" "target/classes"]
  :clean-targets [:target-path "out" "release"]
  :target-path "target"
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {:main "anki-cljs.core"
                                   :asset-path "js/out"
                                   :output-to "resources/public/js/build.js"
                                   :output-dir "resources/public/js/out"}}]}
  :figwheel {:http-server-root "public"
             :server-port 5309
             :server-ip   "localhost"
             :nrepl-port 7888

             :nrepl-middleware ["cider.nrepl/cider-middleware"
                                "refactor-nrepl.middleware/wrap-refactor"
                                "cemerick.piggieback/wrap-cljs-repl"]})

