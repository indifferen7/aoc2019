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
  "Calculates the manhattan distance between two coords."
  [[start-x start-y] [end-x end-y]]
  (+ (Math/abs (long (- start-x end-x)))
     (Math/abs (long (- start-y end-y)))))

(defn part-one []
  (let [[wire-a wire-b] wires
        points-a (set (instructions-path wire-a))
        points-b (set (instructions-path wire-b))
        connections (clojure.set/intersection (set points-a) (set points-b))
        ]
    (->> (map #(manhattan-distance [0 0] %) connections)
         (sort)
         (second))))