(ns pipeline.tasksink
  (:gen-class)
  (:require [zeromq.zmq :as zmq]))

(defn socket []
  (-> (zmq/context)
      (zmq/socket :pull)
      (zmq/bind "tcp://*:5558")))


(defn -main [& args]
  (println "Sink ready")
  (with-open [s (socket)]
    ;; Wait for start of batch
    (zmq/receive-str s)

    ;; Process 100 confirmations
    (loop [i 0]
      (zmq/receive-str s)
      (if (zero? (mod i 10))
        (print ":")
        (print "."))
      (recur (inc i)))))
