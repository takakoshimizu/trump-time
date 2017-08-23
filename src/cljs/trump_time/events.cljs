(ns trump-time.events
  (:require [re-frame.core :as re-frame]
            [trump-time.db :as db]))


(defn assoc-as-key
  [key-name]
  (fn [db [_ e]]
    (assoc db key-name (keyword e))))


(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))


(re-frame/reg-event-db
  :set-days
  (fn [db [_ e]]
    (let [num (.parseFloat js/window e)]
      (if (.isNaN js/window num)
        (assoc db :days 0)
        (assoc db :days num)))))


(re-frame/reg-event-db
  :set-from
  (assoc-as-key :from))


(re-frame/reg-event-db
  :set-from-scale
  (assoc-as-key :from-scale))


(re-frame/reg-event-db
  :set-to
  (assoc-as-key :to))

      
(re-frame/reg-event-db
  :set-to-scale
  (assoc-as-key :to-scale))
