(ns aoc2019.day1
  (:require [aoc2019.utils :as utils]))

;;; part one

(defn mass->fuel [mass]
  (-> mass
      (/ 3)
      (int)
      (- 2)))

(def masses
  (->> (utils/lines-from "day1")
       (map utils/string->int)))

(def required-fuel
  (->> (map mass->fuel masses)
       (reduce + 0)))

; print solution for part 1
(println "Solution part one: " required-fuel)

;;; part two

(defn mass->fuel-pt2
  [mass]
  (loop [fuel (mass->fuel mass)
         total 0]
    (if (pos? fuel)
      (recur (mass->fuel fuel) (+ total fuel))
      total)))

(def required-fuel-pt2
  (->> (map mass->fuel-pt2 masses)
       (reduce + 0)))

(println "Solution part two: " required-fuel-pt2)
