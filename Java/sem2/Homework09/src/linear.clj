(defn is-numbers? [nums]
  ( every? number? nums ) )

(defn is-vector-valid? [vctr]
  ( and (vector? vctr) (is-numbers? vctr) ) )

(defn are-vectors-valid? [vectors]
  (every? is-vector-valid? vectors))

(defn is-same-length? [vectors]
  (apply == (mapv count vectors)))

(defn universal-operation [pre-cond] #(fn [& x]
                       {:pre [(pre-cond x) (is-same-length? x)]}
                       (apply mapv % x)))

(def vector-operation
  (universal-operation #(are-vectors-valid? %)))

(def v+ (vector-operation +))
(def v- (vector-operation -))
(def v* (vector-operation *))
(def vd (vector-operation /))

(defn scalar [& x]
  {:pre [(are-vectors-valid? x) (is-same-length? x)]}
  (reduce + (reduce v* x)))

(defn vect [& xs]
  {:pre [(are-vectors-valid? xs) (every? (partial = 3) (mapv count xs))]}
  (reduce (fn [x y] (vector
                      (- (* (nth x 1) (nth y 2)) (* (nth y 1) (nth x 2)))
                      (- (* (nth y 0) (nth x 2)) (* (nth x 0) (nth y 2)))
                      (- (* (nth x 0) (nth y 1)) (* (nth y 0) (nth x 1))))) xs))

(defn v*s [v & s]
  {:pre [(is-vector-valid? v) (is-numbers? s)]}
  (mapv (partial * (reduce * s)) v))


(defn is-matrix-valid? [x]
  (and (vector? x) (are-vectors-valid? x) (is-same-length? x)))

(def matrix-operation
  (universal-operation #(every? is-matrix-valid? %)))

(def m+ (matrix-operation v+))
(def m- (matrix-operation v-))
(def m* (matrix-operation v*))
(def md (matrix-operation vd))

(defn m*s [m & s]
  {:pre [(is-matrix-valid? m) (is-numbers? s)]}
  (mapv (fn [x] (apply v*s x s)) m))

(defn m*v [m & v]
  {:pre [(is-matrix-valid? m) (are-vectors-valid? v)]}
  (mapv (fn [x] (apply scalar x v)) m))

(defn transpose [x]
  {:pre [(is-matrix-valid? x)]}
  (apply mapv vector x))

(defn m*m [& xs]
  {:pre [(every? is-matrix-valid? xs)]}
  (reduce (fn [x y]
            {:pre [(is-same-length? [(first x) y])]}
            (transpose (mapv (partial m*v x) (transpose y)))) xs))

(defn is-simplex-valid? [x]
  (or
    (number? x)
    (is-vector-valid? x)
    (and
      (= (range (count x) 0 -1) (mapv count x))
      (every? is-simplex-valid? x))
    ))

(defn is-same-size? [& x]
  (and
    (every? vector? x)
    (apply == (mapv (partial count) x))
    (every? (partial mapv is-same-size?) x)
    ))

(defn simplex-app [f] (fn [& x]
                        (if (vector? (first x))
                          (apply (partial mapv (simplex-app f)) x)
                          (apply f x))))

(defn simplex-operation [f]
  ((universal-operation #(and (every? is-simplex-valid? %) (apply is-same-size? %))) (simplex-app f)))

(def x+ (simplex-operation +))
(def x- (simplex-operation -))
(def x* (simplex-operation *))
(def xd (simplex-operation /))