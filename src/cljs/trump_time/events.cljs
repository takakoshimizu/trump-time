(ns trump-time.events
  (:require [re-frame.core :as re-frame]
            [trump-time.db :as db]))

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
  (fn [db [_ e]]
    (assoc db :from (keyword e))))

(re-frame/reg-event-db
  :set-to
  (fn [db [_ e]]
    (assoc db :to (keyword e))))
