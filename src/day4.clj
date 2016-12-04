(ns day4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> "day4.txt"
                io/resource
                slurp
                str/split-lines))

(defn sector-id
  "Returns pair of encrypted name and sector-id if valid, nil if
  invalid"
  [encrypted-name]
  (let [[_ name sector-id freq-order]
        (re-matches #"(.*?)-(\d+)\[(.*)\]"
                    encrypted-name)
        top-n (apply concat
                     (map
                      sort
                      (partition-by
                       val
                       (sort-by
                        (fn [[k v]]
                          (- v))
                        (frequencies
                         (apply str
                                (str/replace name
                                             "-"
                                             "")))))))
        top-5-letters (map
                       first
                       (take 5
                             top-n))]
    (when (= top-5-letters
             (seq freq-order))
      [name (Integer. sector-id)])))

(def valid-lines (keep sector-id input))

;; answer to first part
(reduce + (map second valid-lines)) ;;=> 185371

;; second part
(defn rotate [n c]
  (if (= \- c) \space
      (char (+ 97
               (mod (+ n
                       (- (int c) 97))
                    26)))))

;; answer to second part
(keep (fn [[name sector-id]]
        (let [rotated
              (apply str
                     (map #(rotate sector-id %)
                          name))]
          (when (str/includes?
                 rotated
                 "pole")
            [rotated sector-id])))
      valid-lines) ;;=> (["northpole object storage" 984])
