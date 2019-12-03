(ns aoc2019.day2
  (:require [aoc2019.utils :as utils]
            [clojure.string :as string]))

(defn add [state, idx1, idx2]
  (+ (get state idx1) (get state idx2)))

(defn multiply [state, idx1, idx2]
  (* (get state idx1) (get state idx2)))

(defn algorithm [initial-state]
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

(def parsed-file (string/split (utils/read-resource "day2") #","))

(defn initial-state [one two]
  (let [state (->> (map #(Integer/parseInt %) parsed-file)
                   (map-indexed vector)
                   (into {}))]
    (assoc state 1 one 2 two)))

; print solution for part 1
(println "Solution part one:"
         (get (algorithm (initial-state 12 2)) 0))

(def part-two-solution
  (for [one (range 99)
        two (range 99)
        :let [output
              (get (algorithm (initial-state one two)) 0)]
        :when (= 19690720 output)]
      (+ two (* one 100 ))))

; print solution for part 1
(println "Solution part two:" part-two-solution)