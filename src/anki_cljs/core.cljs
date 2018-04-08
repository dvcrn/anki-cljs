(ns anki-cljs.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :as async :refer [<! >!]]
            [cljs-http.client :as http]
            [cljs.core.match :refer-macros [match]]))

(def API_URL "http://localhost:8765")
(def API_VERSION 5)

(defn send-anki-action [action params]
  (go
    (let [response (<! (http/post API_URL {:keywordize-keys? true
                                           :with-credentials? false
                                           :body (.stringify js/JSON (clj->js {:version API_VERSION
                                                                               :action action
                                                                               :params params}))}))]
      (if (not (response :status))
        [:error (response :error-text)]
        (let [body (->  (.parse js/JSON (response :body)) (js->clj :keywordize-keys true))]
          (if (nil? (body :error))
            [:ok (body :result)]
            [:error (body :error)]))))))

(defn get-deck-names [] (send-anki-action "deckNames" {}))

(defn get-version [] (send-anki-action "version" {}))

(defn add-note [deck-name model-name fields tags]
  (send-anki-action "addNote" {:note {:deckName deck-name
                                      :modelName model-name
                                      :fields fields
                                      :tags tags}}))

