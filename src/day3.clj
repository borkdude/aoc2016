(ns day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> "day3.txt"
               io/resource
               slurp
               str/trim
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

(defn valid-triangles [input]
  (count (filter valid-triangle? input)))

;; answer to first part
(valid-triangles input) ;;=> 982

(defn transpose [m]
  (apply map vector m))

(def vertical-input (mapcat transpose (partition 3 input)))

;; answer to second part
(valid-triangles vertical-input) ;;=> 1826
