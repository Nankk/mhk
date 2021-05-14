(ns mhk.core
  (:gen-class)
  (:require [environ.core :refer [env]]
            [mhk.listener :as listener])
  (:import [com.tulskiy.keymaster.common HotKeyListener Provider]
           java.awt.Robot
           java.awt.event.KeyEvent
           javax.swing.KeyStroke))

(defn on-shutdown []
  ;; Release bindings and provider
  (listener/reset))

(defn config-map [config-text]
  )

(defn register-keys [config-map]
  )

(defn -main [& [path]]
  (.addShutdownHook (Runtime/getRuntime) (Thread. on-shutdown))
  (-> (or path (str (env :home) "/.mhkrc"))
      config-map
      register-keys))

