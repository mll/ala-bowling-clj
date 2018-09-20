(ns bowling.domain-abstractions
  (:require [bowling.programming-paradigms :refer :all]))

; Type

(defrecord GameFrame [n-players 
                      winner-of-subframe-scores-one-point 
                      is-next-frame 
                      is-frame-complete 
                      subframes
                      frame-number
                      frame-score
                      n-plays
                      start-next-super-frame
                      our-frame-complete
                      uuid])

; Constructor

(defn game-frame [is-next-frame is-frame-complete]
  (map->GameFrame {:n-players 2
                   :winner-of-subframe-scores-one-point false
                   :is-next-frame is-next-frame
                   :is-frame-complete is-frame-complete
                   :subframes []
                   :frame-number 0
                   :frame-score [0 0]
                   :n-plays 0
                   :start-next-super-frame false
                   :our-frame-complete false
                   :uuid (java.util.UUID/randomUUID)}))

; Logic

(defn transpose [collections]
  "Transposes a matrix"
  (apply map vector collections))

(defn should-process-subframes? [frame]
  "Checks if a frame's subframes should be computed"
  (and (not (empty? (:subframes frame)))
       (not (:start-next-super-frame frame))))

(defn score-for-frame [frame]
  "Computes the score for a frame in a form of a vector"
  (let [complete-subframes (filter is-complete? (:subframes frame))
        players (take (:n-players frame) (range))
        scores-fn (fn [player] (into [0] (map #(get-score % player) complete-subframes)))
        tuples (transpose (map scores-fn players))
        scores (if (:winner-of-subframe-scores-one-point frame)
                 (map (fn [results]
                        (let [m (apply max results)]
                          (map #(if (= m %) 1 0) results))) tuples)
                 tuples)]
    (into [] (map #(reduce + 0 %) (transpose scores)))))

; Helpers

(defn update-score [frame player score]
  (if (should-process-subframes? frame)
    (let [subframes (into [] (map #(if (not (is-complete? %)) (ball % player score) %) (:subframes frame)))]
      (-> frame
       (assoc-in [:n-plays] (count subframes))
       (assoc-in [:subframes] subframes)
       (#(assoc-in % [:frame-score] (score-for-frame %)))))
    (-> frame
        (update-in [:n-plays] inc)
        (update-in [:frame-score player] #(+ % score)))))

(defn check-is-next-frame [frame]
  (let [is-next-frame (:is-next-frame frame)
        is-next (and is-next-frame 
                     (is-next-frame 
                      (:frame-number frame) 
                      (:n-plays frame)
                      (:frame-score frame)))]
    (if is-next
      (assoc-in frame [:start-next-super-frame] true)
      frame)))

(defn check-is-complete [frame]
  (let [is-frame-complete (:is-frame-complete frame)
        is-complete (or (not is-frame-complete) 
                        (is-frame-complete 
                         (:n-plays frame)
                         (:frame-score frame)))]
    (if (and is-complete 
             (empty? (filter (comp not is-complete?) (get-subframes frame))))
      (-> frame
          (assoc-in [:start-next-super-frame] true)
          (assoc-in [:our-frame-complete] true))
      frame)))

(defn create-subframe [frame]
  (let [last-frame (last (:subframes frame))]
    (if (and last-frame 
             (is-next-frame? last-frame) 
             (not (:start-next-super-frame frame)))
      (update-in frame [:subframes] 
                 #(conj % (get-copy last-frame (:n-plays frame))))
      frame)))
    
; Interface

(extend-protocol IConsistsOf 
  GameFrame

  (is-complete? [frame] 
    (:our-frame-complete frame))

  (is-next-frame? [frame] 
    (:start-next-super-frame frame))

  (get-score [frame player] 
    (-> frame 
        :frame-score
        (nth player)))

  (get-subframes [frame]
    (:subframes frame))

  (ball [frame player score]
    (assert (not (is-complete? frame)))
    (-> frame
      (update-score player score)
      (check-is-next-frame)
      (check-is-complete)
      (create-subframe)))

  (get-copy [frame f-num]
    (let [first-subframe (first (:subframes frame))]
      (->
       (game-frame (:is-next-frame frame) (:is-frame-complete frame))
       (assoc-in [:frame-number] f-num)
       (assoc-in [:winner-of-subframe-scores-one-point] (:winner-fo-subframe-scores-one-point frame))
       (assoc-in [:subframes] (if first-subframe 
                                [(get-copy first-subframe 0)] 
                                []))))))

; Additional methods

(defn contains [frame other-frame]
  (update-in frame [:subframes] #(conj % other-frame)))
