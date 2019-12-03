(ns aoc2019.day3
  (:require [aoc2019.utils :as utils]
            [clojure.string :as string]))

;;; part one
(defn parse-instruction [instruction]
  (let [direction (first instruction)
        steps (-> (rest instruction)
                  (string/join)
                  (Integer/parseInt))]
    [direction steps]))

(defn up [[x y] num-steps]
  (for [n (range num-steps)] [x (- y (inc n))]))

(defn down [[x y] num-steps]
  (for [n (range num-steps)] [x (+ y (inc n))]))

(defn left [[x y] num-steps]
  (for [n (range num-steps)] [(- x (inc n)) y]))

(defn right [[x y] num-steps]
  (for [n (range num-steps)] [(+ x (inc n)) y]))

(defn instruction-path
  [point instruction]
  (let [[direction steps] (parse-instruction instruction)]
    (case direction
      \U (up point steps)
      \R (right point steps)
      \D (down point steps)
      \L (left point steps))))

(defn instructions-path [instructions]
  (reduce
    (fn [points instruction]
      (let [point (last points)]
        (into points (instruction-path point instruction))))
    [[0 0]]
    instructions))

(def wires
  (->> (utils/read-resource "day3")
       (string/split-lines)
       (map #(string/split % #","))))

(defn manhattan-distance
  "Calculates the manhattan distance between two points."
  [[start-x start-y] [end-x end-y]]
  (+ (Math/abs (long (- start-x end-x)))
     (Math/abs (long (- start-y end-y)))))

(def connections
  (let [[wire-a wire-b] wires
        points-a (set (instructions-path wire-a))
        points-b (set (instructions-path wire-b))]
    (->> (clojure.set/intersection (set points-a) (set points-b))
         (remove #{[0 0]}))
    ))

; print solution for part 1
(def part-one
  (->> (map #(manhattan-distance [0 0] %) connections)
       (sort)
       (first)
       (println "Solution part one:")))

(def part-two
  (let [[wire-a wire-b] wires
        path-a (instructions-path wire-a)
        path-b (instructions-path wire-b)
        steps (for [point connections]
                (+ (.indexOf path-a point) (.indexOf path-b point)))]
    (println "Solution part two:" (apply min steps))))