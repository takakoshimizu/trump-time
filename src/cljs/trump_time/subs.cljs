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
  :to
  (fn [db]
    (:to db)))

(re-frame/reg-sub
  :days
  (fn [db]
    (:days db)))

(re-frame/reg-sub
  :converted
  (fn [db]
    (let [days (:days db)
          from (:from db)
          to   (:to db)] 
      (pprint/cl-format nil "~,2f" (tt/convert-scale days from to)))))
