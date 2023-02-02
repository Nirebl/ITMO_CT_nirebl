not_primes(1).

fill_sieve(DIV, I, N, STEP) :-
    I < N,
    assert(not_primes(I)),
    NEW_I is I + STEP,
    fill_sieve(DIV, NEW_I, N, STEP).

build_sieve(I, N) :-
    not not_primes(I),
    NEXT_I is I * I,
    fill_sieve(I, NEXT_I, N, I).

build_sieve(I, N) :-
    I * I < N,
    NEXT_I is I + 1,
    build_sieve(NEXT_I, N).

init(MAX_N) :-
    build_sieve(2, MAX_N).

prime(X) :-
    not not_primes(X).
composite(X) :-
    X > 1, not_primes(X).

number_for_divisors_(1, _, []).
number_for_divisors_(Number, Prev, [Head | Tail]) :-
    prime(Head),
    Prev =< Head,
    number_for_divisors_(Number1, Head, Tail),
    Number is Number1 * Head.

divisible_(X, Y) :-
    0 is mod(X, Y), !.

divisors_for_number_(Number, Divisor, [Number]) :-
  Divisor * Divisor > Number, !.
divisors_for_number_(Number, Divisor, [Head | Tail]) :-
    divisible_(Number, Divisor),
    Head is Divisor,
    Number1 is div(Number, Divisor),
    divisors_for_number_(Number1, Divisor, Tail).
divisors_for_number_(Number, Divisor, [Head | Tail]) :-
    not(divisible_(Number, Divisor)),
    Divisor1 is Divisor + 1,
    divisors_for_number_(Number, Divisor1, [Head | Tail]).

prime_divisors(1, []) :- !.
prime_divisors(Number, Divisors) :-
    integer(Number),
    divisors_for_number_(Number, 2, Divisors).
prime_divisors(Number, Divisors) :-
    not(integer(Number)),
    number_for_divisors_(Number, 2, Divisors).

prime_palindrome(N, K) :-
    prime(N),
    to_kth(N, K, R),
    palindrome(R).
to_kth(0, K, []) :- !.
to_kth(N, K, [H | T]) :-
    H is mod(N, K),
    N1 is div(N, K),
    to_kth(N1, K, T).

palindrome(N) :- reverse(N, N).