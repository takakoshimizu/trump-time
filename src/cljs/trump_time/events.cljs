(ns trump-time.events
  (:require [re-frame.core :as re-frame]
            [trump-time.db :as db]
            [trump-time.utils :as u]))


(defn set-query-string
  [db]
  (let [qs      (u/to-query-string db)
        full-qs (str
                  (-> js/window .-location .-protocol)
                  "//"
                  (-> js/window .-location .-host)
                  (-> js/window .-location .-pathname)
                  qs)]
    (.replaceState (-> js/window .-history) (js-obj) "" full-qs)))


(defn assoc-as-key
  [key-name]
  (fn [db [_ e]]
    (let [new-db (assoc db key-name (keyword e))]
      (do
        (set-query-string new-db)
        new-db))))


(re-frame/reg-event-db
  :initialize-db
  (fn  [_ _]
    (let [query-params (u/get-query-map)]
      (merge db/default-db query-params))))

(re-frame/reg-event-db
  :set-days
  (fn [db [_ e]]
    (let [num (.parseFloat js/window e)]
      (let [n (if (.isNaN js/window num) 0 num)
            new-db (assoc db :days n)]
        (do
          (set-query-string new-db)
          new-db)))))


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
