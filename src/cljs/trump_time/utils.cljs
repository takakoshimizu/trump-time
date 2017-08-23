(ns trump-time.utils)


(def scales
  {:day     [1      "day"]
   :month   [30     "month"]
   :year    [365    "year"]
   :mooch   [10     "mooch"] 
   :spicer  [183    "spicer"] 
   :priebus [182    "priebus"]
   :bannon  [210.5  "bannon"]
   :comey   [109    "comey"]
   :manuf   [26.87  "manufacturing council shutdown"]
   :nazi    [2      "nazi condemnation turnaround"]
   :nazi2   [1.2    "nazi condemnation turnaround-turnaround"]})


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


(defn convert-scale 
  [n from-scale from-mult to-scale to-mult]
  (let [from-mult (first (from-mult multipliers))
        from      (first (from-scale scales))
        to-mult   (first (to-mult multipliers))
        to        (first (to-scale scales))]
    (float (/ (* from-mult from n) to to-mult))))


(def keys-and-names
  (let [sorted-keys (-> scales keys sort)]
    (map vector sorted-keys (map #(-> scales % second) sorted-keys))))

(def multiplier-pairs
  (map vector multiplier-order (map #(-> multipliers % second) multiplier-order))) 
