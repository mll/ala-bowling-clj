(ns bowling.core
  (:require [bowling.domain-abstractions :refer [game-frame contains]]
            [bowling.programming-paradigms :refer :all])
  (:import  [bowling.domain-abstractions.GameFrame]))

(defn describe-game [game]
  (println "=============================================")
  (println "UUID: " (:uuid game))
  (println "Type: " (cond
                      (and (:is-next-frame game) (:is-frame-complete game)) "frame"
                      (:is-frame-complete game) "game"
                      :else "ball"))
  (println "The frame completion: " (is-complete? game))
  (println "Score: " (get-score game 0))
  (println "Frame number " (:frame-number game))
  (println "Nplays " (:n-plays game))
  (println "Is next frame: " (is-next-frame? game))
  (when (>  (count (get-subframes game)) 0)
    (do
      (println "Subframes: [[[[[[")
      
      (doseq [subframe  (get-subframes game)]
        (describe-game subframe)
        (println "------")))
    (println "]]]]]]]")))


(defn bowling []
  (->
   (game-frame nil (fn [frames score] 
                       (= frames 10)))
   (contains 
    (-> (game-frame (fn [frame balls pins] 
                        (and (or (= balls 2) 
                                 (= 10 (first pins)))
                             (not (= frame 9))))
                      (fn [balls pins] (or 
                                        (and (= 2 balls)
                                             (< (first pins) 10))
                                        (= 3 balls))))
        (contains (game-frame nil nil))))))

(defn game-step [game]
  (if (is-complete? game)
    game
  (do
    (describe-game game)
    (println "***************** Throw a ball (enter number of pins 0..10)")
    (let [score (Integer/parseInt (read-line))]
      (when (> score 10) 
        (do
          (println "The score must be smaller than 11")
          (System/exit 1)))
      (recur (ball game 0 score))))))

(defn -main [& args]
  (let [game (bowling)]
    (println "FINAL SCORE: " (get-score (game-step game) 0))))







