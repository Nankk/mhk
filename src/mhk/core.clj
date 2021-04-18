(ns mhk.core
  (:gen-class)
  (:require [keymaster.core :as km]))

(defonce )

(defn on-shutdown []
  )

(defn -main []
  (.addShutdownHook
   (Runtime/getRuntime)
   (Thread. on-shutdown))
  ;; (km/register )
  )
