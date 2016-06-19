(ns req-rep.server
  (:gen-class)
  (:require [zeromq.zmq :as zmq]))



;;(def context (zmq/zcontext))
;;(def c2 (zmq/zcontext))
;;(def s2 (zmq/socket :rep))y

(defn add-shutdown-hook [fn]
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. fn)))


(defn make-context []
  (let [c (zmq/zcontext)
        t (Thread/currentThread)]
    (add-shutdown-hook
     (fn []
       (println "W: killing")
       (zmq/destroy c)
       #_(try
         (.interrupt t)
         (.join t)
         (catch InterruptedException e))))
    c))

(def context (make-context))
;;(zmq/destroy context)

(defn socket []
  (-> context
      (zmq/socket :rep)
      (zmq/bind "tcp://*:5555")))

;;(socket)

(defn -main [& args]
  (println "Server started")
  ;(add-shutdown-hook #(println "Going down!"))
  ;(add-shutdown-hook cleanup-on-exit)
  (with-open [s (socket)]
    (while (not (.. Thread currentThread isInterrupted))
      (let [req (zmq/receive-str s)]
        (println "Received:" req)
        (Thread/sleep 1)
        (zmq/send-str s (str "Yo! (" req ")"))))))
