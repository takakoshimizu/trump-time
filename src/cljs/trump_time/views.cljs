(ns trump-time.views
  (:require [re-frame.core :as re-frame]
            [trump-time.utils :as tt]
            [inflections.core :as inflect]))


(defn dispatch-value 
  [event]
  (fn [e]
    (re-frame/dispatch [event (aget e "target" "value")])))


(defn day-box 
  [def-val]
  [:form
    [:input {:type "text"
             :class "form-control"
             :on-change (dispatch-value :set-days) 
             :default-value def-val}]])


(defn select-item 
  [option]
  [:option {:key option :value (first option)} 
    ((comp inflect/plural inflect/capitalize name) (second option))])


(defn select-box 
  [options v event]
  [:select {:class "form-control"
            :on-change (dispatch-value event)
            :value v}
    (map select-item options)])


(defn app 
  []
  (let [from      (re-frame/subscribe [:from])
        to        (re-frame/subscribe [:to])
        days      (re-frame/subscribe [:days])
        converted (re-frame/subscribe [:converted])]
    (fn []
      [:div {:class "container-fluid"}
        [:div {:class "row"}
          [:div {:class "col-12"}
            (day-box @days)]]
        [:div {:class "row mt-3 mb-5"}
          [:div {:class "col-md-5 text-center"}
            (select-box (tt/keys-and-names tt/scales) @from :set-from)]
          [:div {:class "col-md-2 align-self-center text-center"} "to"]
          [:div {:class "col-md-5 text-center"} 
            (select-box (tt/keys-and-names tt/scales) @to :set-to)]]
        [:div {:class "row mt-5"}
          [:div {:class "col-md-5"}
            [:div {:class "jumbotron text-center"}
              [:h1 @days]
              [:p {:class "lead"} ((if (= 1 @days) inflect/singular inflect/plural) @from)]]]
          [:div {:class "col-md-2 align-self-center text-center"}
            [:h1 "="]]
          [:div {:class "col-md-5"}
            [:div {:class "jumbotron text-center"}
              [:h1 @converted]
              [:p {:class "lead"} ((if (= 1 @converted) inflect/singular inflect/plural) @to)]]]]])))

