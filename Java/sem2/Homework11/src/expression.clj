(defn proto-get [obj key]
  (cond
    (contains? obj key) (get obj key)
    (contains? obj :proto) (recur (get obj :proto) key)
    :else nil))

(defn proto-call [this key & args]
  (apply (proto-get this key) this args))

(defn field [key]
  (fn [this] (proto-get this key)))

(defn method [key]
  (fn [this & args] (apply proto-call this key args)))

(defn constructor [ctor proto]
  (fn [& args] (apply ctor {:proto proto} args)))

(def evaluate (method :evaluate_method))
(def diff (method :diff_method))
(def toString (method :toString))

(defn ElementProto [evaluate_method diff_method toString]
  {
   :evaluate_method evaluate_method
   :diff_method     diff_method
   :toString        toString
   }
  )

(declare ZERO)

(def Constant
  (let [value_field (field :value)]
    (constructor
      (fn [this value]
        (assoc this :value value))
      (ElementProto
        (fn [this _] (value_field this))
        (fn [_ _] ZERO)
        (fn [this] (format "%.1f" (double (value_field this))))
        )
      )
    )
  )

(def ZERO (Constant 0.0))
(def ONE (Constant 1.0))

(def Variable
  (let [variable_field (field :variableName)]
    (constructor
      (fn [this varName]
        (assoc this :variableName varName))
      (ElementProto
        (fn [this vars] (get vars (variable_field this)))
        (fn [this diffVariable] (if (= (variable_field this) diffVariable) ONE ZERO))
        (fn [this] (variable_field this))
        )
      )
    )
  )

(def operation_field (field :operation))
(def args_field (field :args))
(def diff_field (field :diff_f))
(def sign_field (field :sign))

(defn ExpressionCtor [this sign_field operation_field diff_field]
  (assoc this
    :sign sign_field
    :operation operation_field
    :diff_f diff_field
    )
  )

(def ExpressionProto
  (ElementProto
    (fn [this vars] (apply (operation_field this) (mapv (fn [x] (evaluate x vars)) (args_field this))))
    (fn [this diffMethod] ((diff_field this) (args_field this) (mapv #(diff % diffMethod) (args_field this))))
    (fn [this] (str "(" (sign_field this) " " (clojure.string/join " " (mapv toString (args_field this))) ")"))
    )
  )

(def Expression (constructor ExpressionCtor ExpressionProto))

(defn createExpression [sign operation diff_f]
  (constructor
    (fn [this & args] (assoc this :args args))
    (Expression sign operation diff_f)))

(defn simpleDiff [operation] (fn [_ args'] (apply operation args')))

(def Add (createExpression
           "+"
           +
           (fn [_ diff] (apply Add diff))
           ))

(def Subtract (createExpression
                "-"
                -
                (fn [_ diff] (apply Subtract diff))
                ))

(def Negate (createExpression
              "negate"
              -
              (fn [_ diff] (apply Negate diff))
              ))

(declare Multiply)
(defn Multiply-Diff [fs diff]
  (second (reduce (fn [[f df] [g dg]]
                    [(Multiply f g)
                     (Add (Multiply f dg)
                          (Multiply df g))])
                  [ONE ZERO]
                  (mapv vector fs diff))))
(def Multiply (createExpression
                "*"
                *
                Multiply-Diff
                ))


(def Divide (createExpression
              "/"
              (fn ([x] (/ 1.0 x))
                ([first & args] (reduce #(/ (double %1) (double %2)) first args)))

              (fn [[f & fs] [df & dfs]]
                (if (== (count fs) 0)
                  (Divide (Negate df)
                          (Multiply f f))
                  (let [g (apply Multiply fs)
                        dg (Multiply-Diff fs dfs)]
                    (Divide
                      (Subtract (Multiply df g)
                                (Multiply f dg))
                      (Multiply g g)))))

              ))

(declare Mean)
(defn mean-f [& args] (/ (reduce + 0 args) (count args)))
(defn mean-f-diff [_ diff] (apply Mean diff))
(def Mean (createExpression
            "mean"
            mean-f
            mean-f-diff
            ))

(defn square [x] (* x x))
(defn varn-f [& args]
  (/ (reduce (fn [x y]
               (+ x (square (- y (apply mean-f args))))
               )
             0 args)
     (count args)))

(defn varn-f-diff [fs diff]
  (Subtract
    (Divide
      (apply Add (mapv (fn [[x dx]] (Multiply x dx)) (map vector fs diff)))
      (Constant (/ (count fs) 2.0)))
    (Divide
      (Multiply (apply Add fs) (apply Add diff))
      (Constant (/ (square (count fs) ) 2.0)))
    )
  )

(def Varn (createExpression
            "varn"
            varn-f
            varn-f-diff
            ))

(def EXPRESSIONS
  {
   '+      Add,
   '-      Subtract,
   '*      Multiply,
   '/      Divide,
   'negate Negate,
   'mean   Mean
   'varn   Varn
   }
  )

(defn parseExpression [EXPRESSIONS constant variable expression]
  (cond
    (seq? expression) (apply (EXPRESSIONS (first expression))
                             (mapv (partial parseExpression EXPRESSIONS constant variable) (rest expression)))
    (number? expression) (constant expression)
    (symbol? expression) (variable (str expression))))

(defn commonParser
  [operations constant variable]
  (fn [expression]
    (parseExpression operations constant variable (read-string expression))))

(def parseObject (commonParser EXPRESSIONS Constant Variable))
