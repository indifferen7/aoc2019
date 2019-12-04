(ns aoc2019.day4)

(def pattern (re-pattern "(.)(\\1)+"))

(defn duplicate-adjacent? [s]
  (some? (re-find pattern s)))

(defn ascending? [s]
  (= s (apply str (sort s))))

(def passwords (range 145852 616942))

(defn part-one-predicate [n]
  (and
    (duplicate-adjacent? (str n))
    (ascending? (str n))))

(println "Solution part one:"
         (count (filter part-one-predicate passwords)))

(defn two-adjacent? [n]
  (->> (str n)
       (frequencies)
       (vals)
       (some #(= 2 %))))

(defn part-two-predicate [n]
  (and
    (part-one-predicate (str n))
    (two-adjacent? (str n))))

(println "Solution part two:"
         (count (filter part-two-predicate passwords)))