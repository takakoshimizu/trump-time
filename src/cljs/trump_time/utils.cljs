(ns trump-time.utils)


(def scales
  {:day     [1      "day"] 
   :mooch   [10     "mooch"] 
   :spicer  [183    "spicer"] 
   :priebus [182    "priebus"]
   :comey   [109    "comey"]
   :manuf   [26.87  "manufacturing council shutdown"]
   :nazi    [2      "nazi condemnation turnaround"]
   :nazi2   [1.2    "nazi condemnation turnaround-turnaround"]}) 


(defn convert-scale 
  [n from-scale to-scale]
  (let [from (first (from-scale scales))
        to (first (to-scale scales))]
    (float (/ (* from n) to))))


(defn keys-and-names
  [scales]
  (map vector (keys scales) (map (comp second second) scales)))
