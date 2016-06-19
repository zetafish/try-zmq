#!/usr/bin/env boot
(set-env!
 :resource-paths #{"src"}
 :repositories #(conj % ["sonatype" {:url "https://oss.sonatype.org/content/repositories/releases"}])
 :dependencies '[[org.clojure/clojure "1.8.0" :scope "provided"]
                 [org.zeromq/jeromq "0.3.5"]
                 [org.zeromq/cljzmq "0.1.4" :exclusions [org.zeromq/jzmq]]

                 [cheshire "5.6.1"]]
)

(deftask build
  []
  (comp
   (aot)
   (pom :project 'zero :version "0.0.1")
   (uber)
   (jar)
   (target)))

;; We can this script as:
;;
;; ./build.boot NS

(defn -main [& args]
  (when-let [[x] args]
    (require (symbol x))
    (apply (resolve (symbol (str x "/-main"))) [])))
