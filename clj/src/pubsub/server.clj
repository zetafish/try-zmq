(ns pubsub.server
  (:gen-class)
  (:require [zeromq.zmq :as zmq]
            [cheshire.core :as j]))

(defn socket []
  (-> (zmq/context)
      (zmq/socket :pub)
      (zmq/bind "tcp://*:5556")))

(defn stop? []
  (.. Thread currentThread isInterrupted))

(defn weather []
  {:zipcode     (rand-int 100000)
   :temperature (+ (rand-int 40) -10)
   :humidity    (+ (rand-int 50) 10)})

(defn -main [& args]
  (println "Server started.")
  (with-open [s (socket)]
   (while (not (stop?))
     (zmq/send-str s (j/encode (weather)))
     (Thread/sleep 1))))
