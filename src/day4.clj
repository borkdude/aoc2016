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
        top-5 (->>
               ;; strip hyphens
               (str/replace name
                            "-"
                            "")
               frequencies
               ;; sort descending
               (sort-by val >)
               ;; group equal adjacent frequencies
               (partition-by val)
               ;; drop frequencies
               (map #(map first %))
               ;; sort groups alphabetically
               (map sort)
               ;; concatenate the groups
               (apply concat)
               ;; take the top 5
               (take 5))]
    (when (= top-5
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
