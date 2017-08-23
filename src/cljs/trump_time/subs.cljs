(ns trump-time.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [trump-time.utils :as tt]
            [cljs.pprint :as pprint]))


(re-frame/reg-sub
  :from
  (fn [db]
    (:from db)))


(re-frame/reg-sub
  :from-scale
  (fn [db]
    (:from-scale db)))


(re-frame/reg-sub
  :to
  (fn [db]
    (:to db)))


(re-frame/reg-sub
  :to-scale
  (fn [db]
    (:to-scale db)))


(re-frame/reg-sub
  :days
  (fn [db]
    (:days db)))


(re-frame/reg-sub
  :converted
  (fn [db]
    (let [days       (:days db)
          from       (:from db)
          from-scale (:from-scale db)
          to         (:to db)
          to-scale   (:to-scale db)] 
      (.toPrecision (tt/convert-scale days from from-scale to to-scale) 2))))
