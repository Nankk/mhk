(ns mhk.robot
  (:import java.awt.Robot
           java.awt.event.KeyEvent
           javax.swing.KeyStroke))

(defonce robot (atom nil))

(defn key-press [^KeyEvent key-event]
  (when-not @robot
    (reset! robot ())))
