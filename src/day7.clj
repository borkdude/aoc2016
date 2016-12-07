(ns day7
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.set :as set]))

(defn run
  "Reduces f over the lines from input file"
  [f init]
  (with-open [rdr (-> "day7.txt"
                      io/resource
                      io/reader)]
    (reduce f init (line-seq rdr))))

(defn count-when
  "Counts lines for which predicate holds"
  [p]
  (run (fn [count l]
         (if (p l) (inc count) count))
    0))

(defn partition-by-index
  "Returns vector of elements at even indices followed by vector of
  elements at odd indices"
  [coll]
  (reduce (fn [[even odd] e]
            (if (> (count even) (count odd))
              [even (conj odd e)]
              [(conj even e) odd]))
          [[] []]
          coll))

(defn ip-parts
  "Returns vector of sequences outside brackets followed by vector of
  sequences inside brackets"
  [ip]
  (->> (str/split ip #"\W")
       partition-by-index))

;; first part
(defn abba? [[a b c d]]
  (and (not= a b)
       (= a d)
       (= b c)))

(defn has-abba? [s]
  (some abba? (partition 4 1 s)))

(defn tls? [ip]
  (let [[outside inside] (ip-parts ip)]
    (and (some has-abba? outside)
         (not (some has-abba? inside)))))

;; answer to first part
(count-when tls?) ;;=> 115

;; second part
(defn aba [[a b c]]
  (when (and (= a c) (not= a b))
    [a b]))

(defn abas [s]
  (keep aba (partition 3 1 s)))

(defn ssl? [ip]
  (let [[outside inside] (split-ip ip)
        reduce-into-set #(reduce into #{} %)
        aba-s (reduce-into-set (map abas outside))
        bab-s (reduce-into-set (map (comp
                                     #(map reverse %)
                                     abas) inside))]
    (boolean (seq (set/intersection aba-s bab-s)))))

;; answer to second part
(count-when ssl?) ;; 231
