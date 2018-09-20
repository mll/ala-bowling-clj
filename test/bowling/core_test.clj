(ns bowling.core-test
  (:require [clojure.test :refer :all]
            [bowling.core :refer [bowling]]
            [bowling.programming-paradigms :refer :all])) 

(deftest simple-tests
  (testing "Testing 1-1-1-1-1..."
    (let [result (->
                  (bowling)
                  (ball 0 1) ; 1
                  (ball 0 1) ; 2 
                  (ball 0 1) ; 3
                  (ball 0 1) ; 4
                  (ball 0 1) ; 5
                  (ball 0 1) ; 6
                  (ball 0 1) ; 7
                  (ball 0 1) ; 8
                  (ball 0 1) ; 9
                  (ball 0 1) ; 10
                  (ball 0 1) ; 11
                  (ball 0 1) ; 12
                  (ball 0 1) ; 13
                  (ball 0 1) ; 14
                  (ball 0 1) ; 15
                  (ball 0 1) ; 16
                  (ball 0 1) ; 17
                  (ball 0 1) ; 18
                  (ball 0 1) ; 19
                  (ball 0 1))] ; 20
      (is (is-complete? result))
      (is (= 20 (get-score result 0)))))
  (testing "Testing 10-1-1-1-1..."
    (let [result (->
                  (bowling)
                  (ball 0 10) ; 1
                  (ball 0 1) ; 2 
                  (ball 0 1) ; 3
                  (ball 0 1) ; 4
                  (ball 0 1) ; 5
                  (ball 0 1) ; 6
                  (ball 0 1) ; 7
                  (ball 0 1) ; 8
                  (ball 0 1) ; 9
                  (ball 0 1) ; 10
                  (ball 0 1) ; 11
                  (ball 0 1) ; 12
                  (ball 0 1) ; 13
                  (ball 0 1) ; 14
                  (ball 0 1) ; 15
                  (ball 0 1) ; 16
                  (ball 0 1) ; 17
                  (ball 0 1) ; 18
                  (ball 0 1))] ; 19
      (is (is-complete? result))
      (is (= 30 (get-score result 0)))))

  (testing "Testing 5-5-1-1-1..."
    (let [result (->
                  (bowling)
                  (ball 0 5) ; 1
                  (ball 0 5) ; 2 
                  (ball 0 1) ; 3
                  (ball 0 1) ; 4
                  (ball 0 1) ; 5
                  (ball 0 1) ; 6
                  (ball 0 1) ; 7
                  (ball 0 1) ; 8
                  (ball 0 1) ; 9
                  (ball 0 1) ; 10
                  (ball 0 1) ; 11
                  (ball 0 1) ; 12
                  (ball 0 1) ; 13
                  (ball 0 1) ; 14
                  (ball 0 1) ; 15
                  (ball 0 1) ; 16
                  (ball 0 1) ; 17
                  (ball 0 1) ; 18
                  (ball 0 1) ; 19
                  (ball 0 1))] ; 20
      (is (is-complete? result))
      (is (= 29 (get-score result 0)))))
  (testing "Testing 10-10-10-..."
    (let [result (->
                  (bowling)
                  (ball 0 10) ; 1
                  (ball 0 10) ; 2 
                  (ball 0 10) ; 3
                  (ball 0 10) ; 4
                  (ball 0 10) ; 5
                  (ball 0 10) ; 6
                  (ball 0 10) ; 7
                  (ball 0 10) ; 8
                  (ball 0 10) ; 9
                  (ball 0 10) ; 10
                  (ball 0 10) ; 11
                  (ball 0 10))] ; 12 
      (is (is-complete? result))
      (is (= 300 (get-score result 0))))))
