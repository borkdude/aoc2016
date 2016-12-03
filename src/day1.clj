(ns day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (-> "day1.txt"
               io/resource
               slurp
               str/trim
               (str/split #", ")))

;; first part
;; 0 = N, 1 = O, 2 = S, 3 = W
(apply +
       (map #(Math/abs %)
            (first
             (reduce (fn [[[x y] pole] [direction & steps]]
                       (let [steps (Integer. (apply str steps))
                             new-pole (mod (case direction
                                             \L (dec pole)
                                             \R (inc pole))
                                           4)]
                         (case new-pole
                           0 [[x (+ y steps)] new-pole]
                           1 [[(+ x steps) y] new-pole]
                           2 [[x (- y steps)] new-pole]
                           3 [[(- x steps) y] new-pole])))
                     [[0 0] 0]
                     input)))) ;; => 287
;; second part
;; see: https://codepen.io/borkdude/pen/woydpG
