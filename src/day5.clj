(ns day5
  (:require [clojure.string :as str])
  (:import [java.security MessageDigest]
           [java.math BigInteger]))

(def input "ojvtpuvg")

(defn md5 [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))

;; first password
(take 8 (map #(nth % 5)
             (filter #(str/starts-with? % "00000")
                     (map #(md5 (str input %))
                          (range))))) ;;=> (\4 \5 \4 \3 \c \1 \5 \4)

;; second password
(reduce (fn [m [pos value]]
          (if-not (m pos)
            (assoc m pos value)
            (if (= 8 (count m))
              (reduced (map val (sort m)))
              m)))
        {} 
        (keep
         #(let [[position value] (.substring ^String % 5 7)
                position (Integer/parseInt (str position) 16)]
            (when (<= position 7)
              [position value]))
         (filter #(str/starts-with? % "00000")
                 (map #(md5 (str input %))
                      (range))))) ;;=> (\1 \0 \5 \0 \c \b \b \d)
