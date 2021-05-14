(ns mhk.listener
  (:import [com.tulskiy.keymaster.common Provider HotKeyListener]
           [javax.swing KeyStroke]))

(defonce provider (atom nil))

(defn- get-arity [f]
  (let [^java.lang.reflect.Method m (-> f class .getDeclaredMethods first)]
    (count (.getParameterTypes m))))

(defn- ^HotKeyListener make-listener [f]
  (reify HotKeyListener
    (onHotKey [this hotKey] (apply f (take (get-arity f) [hotKey])))))

(defn register [^String key-code f]
  (when (nil? @provider)
    (reset! provider (Provider/getCurrentProvider true)))
  (let [^Provider p @provider]
    (.register p (KeyStroke/getKeyStroke key-code) (make-listener f))))

(defn reset []
  (when-let [^Provider p @provider]
    (.reset p)
    (.stop p)
    (reset! provider nil)))
