(ns day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> "day3.txt"
               io/resource
               slurp
               str/split-lines
               (sequence
                (comp
                 (map str/trim)
                 (map #(str/split % #"\s+"))
                 (map (fn [sides]
                        (map #(Integer. %) sides)))))))

(defn valid-triangle? [[a b c]]
  (and (> (+ a b) c)
       (> (+ a c) b)
       (> (+ b c) a)))

;; answer to first part
(count (filter valid-triangle? input)) ;;=> 982

(defn transpose [m]
  (apply map vector m))

;; answer to second part
(->> input
     (partition-all 3)
     (mapcat transpose)
     (filter valid-triangle?)
     count) ;;=> 1826

;; bonus, with transducers - less friendly on the eyes though
(transduce
 (comp (partition-all 3)
       (mapcat transpose)
       (filter valid-triangle?))
 (fn
   ([acc] acc)
   ([acc n] (inc acc)))
 0
 input) ;;=> 1826
