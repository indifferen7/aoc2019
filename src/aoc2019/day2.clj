(ns aoc2019.day2
  (:require [aoc2019.utils :as utils]
            [clojure.string :as string]))

(defn add [state, idx1, idx2]
  (+ (get state idx1) (get state idx2)))

(defn multiply [state, idx1, idx2]
  (* (get state idx1) (get state idx2)))

(defn part-one-algorithm [initial-state]
  (loop [positions-left (range (count initial-state))
         state initial-state]
    (let [[opcode-pos a-pos b-pos target-pos] (take 4 positions-left)
          opcode (get state opcode-pos)]
      (if (= opcode 99)
        state
        (let [a-idx (get state a-pos)
              b-idx (get state b-pos)
              target-idx (get state target-pos)
              op (if (= opcode 1) add multiply)
              target-val (op state a-idx b-idx)]
          (recur
            (drop 4 positions-left)
            (assoc-in state [target-idx] target-val)))))))

(def initial-state-part-one
  (-> (utils/read-resource "day2")
      (string/split  #",")
      (as-> parts
            (->>
              (map #(Integer/parseInt %) parts)
              (map-indexed vector)
              (into {})))
      (assoc 1 12 2 2)))

; print solution for part 1
(println "Solution part one:"
         (get (part-one-algorithm initial-state-part-one) 0))

