(ns mhk.robot
  (:import java.awt.Robot
           java.awt.event.KeyEvent
           javax.swing.KeyStroke))

(defonce robot (Robot.))

(defn key-press [^KeyEvent key-event]
  (when key-event
    (.keyPress robot key-event)))

(defn key-release [^KeyEvent key-event]
  (when key-event
    (.keyRelease robot key-event)))

(defn key-type
  "Press and release the key."
  [^KeyEvent key-event]
  (when key-event
    (.keyPress robot key-event)
    (.keyRelease robot key-event)))
