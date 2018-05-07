(ns tensorflow-test-clj.core
  (:gen-class)
  (:import (org.tensorflow Tensor
                           TensorFlow
                           Graph
                           Session)))

(defn -main
  "I don't do tensorflow stuff"
  [& args]
  (try (let [graph (let [g (Graph.)
                         value (str "Hello from " (TensorFlow/version))
                         t (Tensor/create (.getBytes value))]
                     (-> (.opBuilder g "Const" "MyConst")
                         (.setAttr "dtype" (.dataType t))
                         (.setAttr "value" t)
                         (.build))
                     g)]
         (try (let [output (-> (Session. graph)
                               (.runner)
                               (.fetch "MyConst")
                               (.run)
                               (.get 0))]
                (println (String. (.bytesValue output) "UTF-8")))))))
