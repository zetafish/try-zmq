(ns pipeline.taskwork
  (:gen-class)
  (:require [zeromq.zmq :as zmq]))

(def context (zmq/context))

(defn receiver []
  (-> context
      (zmq/socket :pull)
      (zmq/connect "tcp://localhost:5557")))

(defn sender []
  (-> context
      (zmq/socket :push)
      (zmq/connect "tcp://localhost:5558")))

(defn -main [& args]
  (println "Worker ready.")
  (with-open [s (sender)
              r (receiver)]
    (while true
      (let [m (zmq/receive-str r)]
        (when (nil? m)
          (throw (Exception.)))
        (print ".")
        (Thread/sleep 1)
        (zmq/send-str s "")))))
