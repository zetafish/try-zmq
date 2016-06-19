(ns pubsub.client
  (:gen-class)
  (:require [zeromq.zmq :as zmq]))

(defn socket []
  (-> (zmq/context)
      (zmq/socket :sub)
      (zmq/connect "tcp://localhost:5556")
      (zmq/subscribe "")))

(defn -main [& args]
  (println "Client started.")
  (with-open [s (socket)]
    (dotimes [i 1000000]
      (let [m (zmq/receive-str s)]
        (println m)
        (Thread/sleep 1)))))
