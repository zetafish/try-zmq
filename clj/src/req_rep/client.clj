(ns req-rep.client
  (:gen-class)
  (:require [zeromq.zmq :as zmq]))

(defn socket []
  (-> (zmq/context)
      (zmq/socket :req)
      (zmq/connect "tcp://localhost:5555")))

(defn send [s n]
  (zmq/send-str s (str "This is hello " n))
  (println (str "Received: " (zmq/receive-str s))))


(defn -main [& args]
  (println "Client started.")
  (with-open [s (socket)]
    (dotimes [i 10]
      (send s i))))
