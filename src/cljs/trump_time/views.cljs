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
  [no-plurals option]
  [:option {:key (first option) 
            :value (first option)}
    (let [plural-func (if no-plurals identity inflect/plural)]
      (-> option second inflect/capitalize plural-func))])


(defn select-box 
  [{:keys [options value event no-plural classes]}]
  [:select
    {:value value
     :class (str "form-control" (if classes " ") classes)
     :on-change (dispatch-value event)}
    (map #(select-item no-plural %) options)])


(defn type-select 
  [{:keys [sub event options no-plural classes]}]
  (let [val (deref (re-frame/subscribe [sub]))]
    [select-box 
      {:options options
       :value val 
       :classes classes
       :event event
       :no-plural no-plural}]))

      
(defn results-box [{:keys [type sub sub-scale]}]
  (let [type-val  (re-frame/subscribe [type])
        value     (re-frame/subscribe [sub])
        scale     (re-frame/subscribe [sub-scale])
        type-inf  (if (= 1 @value) inflect/singular inflect/plural)
        type-name (-> (@type-val u/scales) second type-inf)
        pfix-name (-> @scale u/multipliers second)
        all-name  (str pfix-name type-name)]
    [:div.col-md-5
      [:div.jumbotron.text-center
        [:h1 @value]
        [:p.lead all-name]]]))


(defn input-rows []
  [:div 
    [:div.row
      [:div.col-md-5.col-sm-12
        [day-box]]]
    [:div.row.mt-3.mb-5
      [:div.col-md-5.text-center.row.ml-0
        [type-select
          {:options   u/multiplier-pairs
           :no-plural true
           :classes   "col-3"
           :sub       :from-scale
           :event     :set-from-scale}]
        [type-select
          {:options u/keys-and-names
           :classes "col-9"
           :sub     :from 
           :event   :set-from}]]
      [:div.col-md-2.align-self-center.text-center.ml-3 "to"]
      [:div.col-md-5.text-center.row.ml-0
        [type-select
          {:options   u/multiplier-pairs
           :no-plural true
           :classes   "col-3"
           :sub       :to-scale
           :event     :set-to-scale}]
        [type-select
          {:options u/keys-and-names
           :classes "col-9"
           :sub     :to
           :event   :set-to}]]]])


(defn output-rows []
  [:div.row.mt-5
    [results-box 
      {:type      :from
       :sub       :days
       :sub-scale :from-scale}]
    [:div.col-md-2.align-self-center.text-center
      [:h1 "="]]
    [results-box
      {:type      :to
       :sub       :converted
       :sub-scale :to-scale}]])


(defn app
  []
  [:div.container-fluid
    [input-rows]
    [output-rows]])
    
