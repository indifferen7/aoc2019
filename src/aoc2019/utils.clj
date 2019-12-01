(ns aoc2019.utils
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn lines-from [filename]
  (-> (io/resource filename)
      (slurp)
      (string/split-lines)))

(defn string->int [s] (Integer/parseInt s))
