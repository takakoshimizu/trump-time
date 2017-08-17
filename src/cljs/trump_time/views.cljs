(ns trump-time.views
  (:require [re-frame.core :as re-frame]
            [trump-time.utils :as u]
            [inflections.core :as inflect]))


(defn dispatch-value 
  [event]
  (fn [e]
    (re-frame/dispatch [event (aget e "target" "value")])))


(defn day-box 
  []
  (let [value (deref (re-frame/subscribe [:days]))]
    [:input.form-control 
      {:type "text"
       :default-value value
       :on-change (dispatch-value :set-days)}])) 


(defn select-item 
  [option]
  [:option {:key (first option) 
            :value (first option)} 
    (-> option second inflect/capitalize inflect/plural)])


(defn select-box 
  [{:keys [options value event]}]
  [:select.form-control 
    {:value value
     :on-change (dispatch-value event)}
    (map select-item options)])


(defn type-select 
  [{:keys [sub event]}]
  (let [val (deref (re-frame/subscribe [sub]))]
    [select-box 
      {:options u/keys-and-names 
       :value val 
       :event event}]))

      
(defn results-box [{:keys [type sub]}]
  (let [type-val  (re-frame/subscribe [type])
        value     (re-frame/subscribe [sub])
        type-inf  (if (= 1 @value) inflect/singular inflect/plural)
        type-name (-> (@type-val u/scales) second type-inf)]
    [:div.col-md-5
      [:div.jumbotron.text-center
        [:h1 @value]
        [:p.lead type-name]]]))


(defn input-rows []
  [:div 
    [:div.row
      [:div.col-12
        [day-box]]]
    [:div.row.mt-3.mb-5
      [:div.col-md-5.text-center
        [type-select
          {:sub   :from} 
          :event :set-from]]
      [:div.col-md-2.align-self-center.text-center "to"]
      [:div.col-md-5.text-center
        [type-select
          {:sub   :to
           :event :set-to}]]]])


(defn output-rows []
  [:div.row.mt-5
    [results-box 
      {:type :from
        :sub  :days}]
    [:div.col-md-2.align-self-center.text-center
      [:h1 "="]]
    [results-box
      {:type :to
        :sub  :converted}]])


(defn app
  []
  [:div.container-fluid
    [input-rows]
    [output-rows]])
    
