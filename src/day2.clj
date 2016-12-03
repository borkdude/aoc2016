(ns day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (-> "day2.txt"
               io/resource
               slurp
               str/trim
               str/split-lines))

;; first part
(defn process-line [begin-position line]
  (reduce (fn [[x y] dir]
            (case dir
              \U [x (max 0 (dec y))]
              \D [x (min 2 (inc y))]
              \L [(max 0 (dec x)) y]
              \R [(min 2 (inc x)) y]))
          begin-position
          line))

(defn pad-number [[x y]]
  (+ x 1 (* y 3)))

(defn answer [start-position]
  (map
   pad-number
   (rest
    (reductions
     process-line start-position input))))

;; answer to first part
(answer [1 1]) ;;=> (5 3 2 5 5)

;; second part
(defn valid-position?
  "Position is valid if within two moves from center"
  [[x y]]
  (<= (+ (Math/abs (- 2 x))
         (Math/abs (- 2 y)))
      2))

(defn process-line [begin-position line]
  (reduce (fn [[x y] dir]
            (let [next (case dir
                         \U [x (max 0 (dec y))]
                         \D [x (min 4 (inc y))]
                         \L [(max 0 (dec x)) y]
                         \R [(min 4 (inc x)) y])]
              (if (valid-position? next)
                next
                [x y])))
          begin-position
          line))

(def pad-number
  (let [valid-positions
        (for [y (range 5)
              x (range 5)
              :let [position [x y]]
              :when (valid-position? position)]
          position)]
    (zipmap valid-positions
            (map #(->> % inc
                       (format "%X"))
                 (range)))))

;; answer to second part
(answer [0 2]) ;;=> ("7" "4" "2" "3" "A")
