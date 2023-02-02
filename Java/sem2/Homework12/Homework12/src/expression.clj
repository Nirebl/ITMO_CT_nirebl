(load-file "parser.clj")

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
(def toStringInfix (method :toStringInfix))


(defn ElementProto [evaluate_method diff_method toString toStringInfix]
  {
   :evaluate_method evaluate_method
   :diff_method     diff_method
   :toString        toString
   :toStringInfix   toStringInfix
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
        (fn [this vars] (vars ((comp str first #(clojure.string/lower-case %)) (variable_field this) )))
        (fn [this diffVariable] (if (= (variable_field this) diffVariable) ONE ZERO))
        (fn [this] (variable_field this))
        (fn [this] (variable_field this))
        )
      )
    )
  )

(def sign_field (field :sign))
(def operation_field (field :operation))
(def diff_field (field :diff_f))

(defn ExpressionCtor [this sign_field operation_field diff_field]
  (assoc this
    :sign sign_field
    :operation operation_field
    :diff_f diff_field
    )
  )

(def args_field (field :args))

(def ExpressionProto
  (ElementProto
    (fn [this vars] (apply (operation_field this) (mapv (fn [x] (evaluate x vars)) (args_field this))))
    (fn [this diffMethod] ((diff_field this) (args_field this) (mapv #(diff % diffMethod) (args_field this))))
    (fn [this] (str "(" (sign_field this) " " (clojure.string/join " " (mapv toString (args_field this))) ")"))
    (fn [this] (if (== (count (args_field this)) 1)
                 (str (sign_field this) "(" (toStringInfix (first (args_field this))) ")")
                 (str "(" (clojure.string/join (str " " (sign_field this) " ") (mapv toStringInfix (args_field this))) ")")
                 ))
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

(def IPow (createExpression
              "**"
              #(Math/pow %1 %2)
              (fn [_ _] identity)
              ))

(def ILog (createExpression
            "//"
            #(/ (Math/log (Math/abs %2)) (Math/log (Math/abs %1)))
            (fn [_ _] identity)
            ))


(def EXPRESSIONS
  {
   "+"      Add
   "-"      Subtract
   "*"      Multiply
   "/"      Divide
   "negate" Negate
   "**"     IPow
   "//"     ILog
   }
  )

(def *digits (+char "0123456789"))
(def *chars (mapv char (range 32 128)))
(def *string (fn [s] (apply +seq (mapv #(+char (str %1)) s))))
(def *space (+char (apply str (filter #(Character/isWhitespace %) *chars))))
(def *ws (+ignore (+star *space)))

(def *constant (+map (comp Constant read-string) (+str (+seqf (comp flatten vector) (+opt (+char "+-")) (+plus *digits) (+opt (+char ".")) (+star *digits)))))
(def *variable (+map (comp Variable str read-string) (+str (+plus (+char "xyzXYZ")))))

(defn commonReduce [reduce-type [x y z & rest :as all]]
  (let [[f s] (reduce-type x z)]
    (cond (== (count all) 1) x
          (= rest nil) ((EXPRESSIONS (str y)) f s)
          :else (commonReduce reduce-type (cons ((EXPRESSIONS (str y)) f s) rest)))))

(defn reduceToForward [x]
  (commonReduce #(vector %1 %2) x))

(defn reduceToReverse [x]
  (commonReduce #(vector %2 %1) (reverse x)))

(declare *values)

(defn *unary-expr [x] (+seqn 0 (+ignore (*string x)) *ws (delay *values)))
(defn *negate [& xs] (apply +or (mapv #(+map (EXPRESSIONS %) (*unary-expr %)) xs)))

(declare *expr)

(def *values (+or (*negate "negate")
                  *constant
                  *variable
                  (+seqn 1 (+char "(") *ws (delay *expr) *ws (+char ")"))))

(defn *common-term [fold]
  (fn [lower-term & operations]
    (+seqf (comp fold flatten vector) lower-term (+star (+seq *ws (+str (apply +or (mapv *string operations))) *ws lower-term)))))

(def *left-term
  (*common-term reduceToForward))

(def *right-term
  (*common-term reduceToReverse))

(def *high_priority (*right-term *values "**" "//"))
(def *middle_priority (*left-term *high_priority "*" "/"))
(def *low_priority (*left-term *middle_priority "+" "-"))

(def *expr *low_priority)

(def parseObjectInfix (+parser (+seqn 0 *ws *expr *ws)))

