(ns mhk.listener
  (:require [mhk.robot :as robot])
  (:import [com.tulskiy.keymaster.common HotKeyListener Provider]
           java.awt.event.KeyEvent
           javax.swing.KeyStroke))

(defonce provider (atom nil))

(defn- get-arity [f]
  (let [^java.lang.reflect.Method m (-> f class .getDeclaredMethods first)]
    (count (.getParameterTypes m))))

(defn- ^HotKeyListener make-listener [f]
  (reify HotKeyListener
    (onHotKey [this hotKey] (apply f (take (get-arity f) [hotKey])))))

(defmulti register (fn [keycode-or-stroke f] (type keycode-or-stroke)))

(defmethod register java.lang.String
  [keycode f]
  (when (nil? @provider)
    (reset! provider (Provider/getCurrentProvider true)))
  (if-let [keystroke (KeyStroke/getKeyStroke keycode)]
    (let [^Provider p @provider]
      (.register p keystroke (make-listener f)))
    (throw (Exception. (str "Invalid keycode " keycode)))))

(defmethod register javax.swing.KeyStroke
  [keystroke f]
  (when (nil? @provider)
    (reset! provider (Provider/getCurrentProvider true)))
  (if keystroke
    (let [^Provider p @provider]
      (.register p keystroke (make-listener f)))
    (throw (Exception. "Invalid keystroke nil."))))

(defn reset []
  (when-let [^Provider p @provider]
    (.reset p)
    (.stop p)
    (reset! provider nil)))

(comment
  (reset)
  (register (KeyStroke/getKeyStroke KeyEvent/VK_META 0) #(println "無変換 pressed."))
  (register "alt shift I" #(do
                             (robot/key-release KeyEvent/VK_ALT)
                             (robot/key-release KeyEvent/VK_SHIFT)
                             (robot/key-type KeyEvent/VK_UP)
                             (robot/key-press KeyEvent/VK_ALT)
                             (robot/key-press KeyEvent/VK_SHIFT)
                             ))
  (register "shift alt I" #(println "shift alt i pressed."))
  (KeyStroke/getKeyStroke  "SHIFT I")
  (KeyStroke/getKeyStroke  "META")
  (KeyStroke/getKeyStroke KeyEvent/VK_NONCONVERT 0)
  (.register p (KeyStroke/getKeyStroke key-code) (make-listener f)))
