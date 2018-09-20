(ns bowling.programming-paradigms)

(defprotocol IConsistsOf 
  "A protocol that has to be implemented by all top level data structures."
  (ball [frame player pins] "Sends a single result to the constituent")
  (is-complete? [frame])
  (is-next-frame? [frame])
  (get-score [frame player] "Returns an integer")
  (get-subframes [frame] "Returns a collection of frames")
  (get-copy [frame frame-number] "Returns a custom 'copy' of the frame"))
