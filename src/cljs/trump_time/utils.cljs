(ns trump-time.utils
  (:require [inflections.core :as inflect]))


(def scales
  {:day     [1      "day"]
   :month   [30     "month"]
   :year    [365    "year"]
   :mooch   [10     "mooch"] 
   :spicer  [183    "spicer"] 
   :priebus [182    "priebus"]
   :bannon  [210.5  "bannon"]
   :comey   [109    "comey"]
   :manuf   [26.87  "manufacturing council shutdown" "councildown"]
   :nazi    [2      "nazi condemnation turnaround" "naziround"]
   :nazi2   [1.2    "nazi condemnation turnaround-turnaround" "naziroundaround"]})


(def multipliers
  {:nano    [0.000000001 "nano"]
   :micro   [0.000001    "micro"]
   :milli   [0.001       "milli"]
   :centi   [0.01        "centi"]
   :deci    [0.1         "deci"]
   :one     [1           ""]
   :deca    [10          "deca"]
   :hecto   [100         "hecto"]
   :kilo    [1000        "kilo"]
   :mega    [1000000     "mega"]
   :giga    [1000000000  "giga"]})


(def multiplier-order
  [:nano :micro :milli :centi :deci :one :deca :hecto :kilo :mega :giga])


(declare get-unit)


(defn convert-scale 
  "Converts n units of from-scale to units of to-scale,
  along with metric multipliers specified in from-mult
  and to-mult."
  [n from-scale from-mult to-scale to-mult]
  (let [from-mult (get-unit from-mult multipliers)
        from      (get-unit from-scale scales)
        to-mult   (get-unit to-mult multipliers)
        to        (get-unit to-scale scales)]
    (float (/ (* from-mult from n) to to-mult))))


(def keys-and-names
  (let [sorted-keys (-> scales keys sort)]
    (map vector sorted-keys (map #(-> scales % second) sorted-keys))))


(def multiplier-pairs
  (map vector multiplier-order (map #(-> multipliers % second) multiplier-order))) 


(defn get-unit-def
  "Gets the unit record for the unit key."
  [unit-key unit-col]
  (unit-key unit-col))


(defn get-unit 
  "Returns the unit value for the provided unit key."
  [unit-key unit-col]
  (first (get-unit-def unit-key unit-col)))


(defn get-unit-name 
  "Gets the short unit name for the keyword unit-def
  existing in the scales map."
  [unit-def]
  (let [scale (unit-def scales)]
    (if (-> scale count (= 3))
      (nth scale 2)
      (second scale))))


(defn with-prefix 
  "Applies the appropriate metric prefix to the unit,
  where prefix is a keyword to a multiplier map item."
  [unit prefix]
  (let [pfx (-> multipliers prefix second)]
    (str pfx unit)))


(defn pluralize 
  "Pluralizes the unit if n is not equal to 1."
  [unit n]
  (let [mode (if (= 1 n) inflect/singular inflect/plural)]
    (mode unit)))
