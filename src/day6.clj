(ns day6
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (-> "day6.txt"
               io/resource
               slurp
               str/trim
               str/split-lines))

(def columns (apply map vector input))

;; first answer
(map #(->> %
           frequencies
           (sort-by val >)
           ffirst) columns) ;;=> (\u \m \e \j \z \g \d \w)

;; second answer
(map #(->> %
           frequencies
           (sort-by val)
           ffirst) columns) ;;=> (\a \o \v \u \e \a \k \v)
