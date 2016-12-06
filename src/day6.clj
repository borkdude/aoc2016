(ns day6
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def rows
  (with-open [rdr (-> "day6.txt"
                      io/resource
                      io/reader)]
    (into [] (line-seq rdr))))

(def columns
  (apply map vector lines))

;; first answer
(map #(->> %
           frequencies
           (apply max-key val)
           first)
     columns) ;;=> (\u \m \e \j \z \g \d \w)

;; second answer
(map #(->> %
           frequencies
           (apply min-key val)
           first)
     columns) ;;=> (\a \o \v \u \e \a \k \v)
