(defn constant [number] (fn [_] number))
(defn variable [name] (fn [vars] (vars name)))

(defn createExpression [function]
  (fn [& args]
    (fn [params] (apply function (mapv (fn [f] (f params)) args)))))

(def add (createExpression +))
(def subtract (createExpression -))
(def multiply (createExpression *))
(def divide (createExpression (fn ([x] (/ 1.0 x))
                                ([first & args] (reduce #(/ (double %1) (double %2)) first args)))
                              ))
(def negate subtract)

(defn mean-f [& args] (/ (reduce + 0 args) (count args)))
(def mean (createExpression mean-f))

(defn square [x] (* x x))
(defn varn-f [& args]
  (/ (reduce (fn [x y]
               ( + x (square (- y (apply mean-f args))  )  )
               )
             0 args)
             (count args)))
(def varn (createExpression varn-f))

(def EXPRESSIONS
  {'+      add,
   '-      subtract,
   '*      multiply,
   '/      divide,
   'negate negate,
   'mean   mean,
   'varn   varn})

(defn parseFunction [expression]
  (letfn [(parse [token]
            (cond
              (number? token) (constant token)
              (symbol? token) (variable (str token))
              :else (apply (EXPRESSIONS (first token)) (mapv parse (rest token)))
              ))]
    (parse (read-string expression))))