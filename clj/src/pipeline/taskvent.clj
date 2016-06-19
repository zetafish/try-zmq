;; Task ventilator
;; Binds PUSH socket to tcp://*:5557
;; Sends batch of tasks to workers via that socket
(ns pipeline.taskvent
  (:gen-class)
  (:require [zeromq.zmq :as zmq]))

(def context (zmq/context))

(defn socket []
  (-> (zmq/context)
      (zmq/socket :push)
      (zmq/bind "tcp://*:5557")))

(defn -main [& args]
  (println "Presse enter when workers are ready...")
  (read-line)
  (with-open [s (socket)]
    (zmq/send-str s "0")   ; first message is "0" to signal start of batch

    ;; Send 100 tasks
    (while true
      (let [workload (rand-int 100)]
        (zmq/send-str s (str workload))))

    ;; Give 0MQ time to deliver
    (Thread/sleep 1)))
