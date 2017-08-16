(ns trump-time.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [trump-time.events]
            [trump-time.subs]
            [trump-time.views :as views]
            [trump-time.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/app]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
