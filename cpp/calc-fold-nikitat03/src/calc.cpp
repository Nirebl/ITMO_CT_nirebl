#include "calc.h"

#include <cctype>   // for std::isspace
#include <cmath>    // various math functions
#include <iostream> // for error reporting via std::cerr

namespace {

const std::size_t max_decimal_digits = 10;

enum class Op
{
    ERR,
    SET,
    ADD,
    SUB,
    MUL,
    DIV,
    REM,
    NEG,
    POW,
    SQRT,
    ADD_B,
    SUB_B,
    MUL_B,
    DIV_B,
    REM_B,
    POW_B
};

std::size_t arity(const Op op)
{
    switch (op) {
    // error
    case Op::ERR: return 0;
    // unary
    case Op::NEG: return 1;
    case Op::SQRT: return 1;
    // binary
    case Op::SET: return 2;
    case Op::ADD: return 2;
    case Op::SUB: return 2;
    case Op::MUL: return 2;
    case Op::DIV: return 2;
    case Op::REM: return 2;
    case Op::POW: return 2;
    case Op::ADD_B: return 3;
    case Op::SUB_B: return 3;
    case Op::MUL_B: return 3;
    case Op::DIV_B: return 3;
    case Op::REM_B: return 3;
    case Op::POW_B: return 3;
    }
    return 0;
}

Op parse_op(const std::string & line, std::size_t & i)
{
    const auto rollback = [&i, &line](const std::size_t n) {
        i -= n;
        std::cerr << "Unknown operation " << line << std::endl;
        return Op::ERR;
    };
    switch (line[i++]) {
    case '0':
    case '1':
    case '2':
    case '3':
    case '4':
    case '5':
    case '6':
    case '7':
    case '8':
    case '9':
        --i; // a first digit is a part of op's argument
        return Op::SET;
    case '+':
        return Op::ADD;
    case '-':
        return Op::SUB;
    case '*':
        return Op::MUL;
    case '/':
        return Op::DIV;
    case '%':
        return Op::REM;
    case '_':
        return Op::NEG;
    case '^':
        return Op::POW;
    case 'S':
        switch (line[i++]) {
        case 'Q':
            switch (line[i++]) {
            case 'R':
                switch (line[i++]) {
                case 'T':
                    return Op::SQRT;
                default:
                    return rollback(4);
                }
            default:
                return rollback(3);
            }
        default:
            return rollback(2);
        }

    case '(':
        if (i + 1 >= line.size() || line[i + 1] != ')') {
            return rollback(1);
        }
        switch (line[i]) {
        case '+':
            return Op::ADD_B;
        case '-':
            return Op::SUB_B;
        case '*':
            return Op::MUL_B;
        case '/':
            return Op::DIV_B;
        case '%':
            return Op::REM_B;
        case '^':
            return Op::POW_B;
        default:
            return rollback(1);
        }

    default:
        return rollback(1);
    }
}

std::size_t skip_ws(const std::string & line, std::size_t i)
{
    while (i < line.size() && std::isspace(line[i])) {
        ++i;
    }
    return i;
}

double parse_arg(const std::string & line, std::size_t & i)
{
    double res = 0;
    std::size_t count = 0;
    bool good = true;
    bool integer = true;
    double fraction = 1;
    i = skip_ws(line, i);
    while (good && i < line.size() && count < max_decimal_digits) {
        switch (line[i]) {
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            if (integer) {
                res *= 10;
                res += line[i] - '0';
            }
            else {
                fraction /= 10;
                res += (line[i] - '0') * fraction;
            }
            ++i;
            ++count;
            break;
        case '.':
            integer = false;
            ++i;
            break;

        default:
            good = false;
            break;
        }
    }
    if (!good) {
        std::cerr << "Argument parsing error at " << i << ": '" << line.substr(i) << "'" << std::endl;
    }
    else if (i < line.size()) {
        std::cerr << "Argument isn't fully parsed, suffix left: '" << line.substr(i) << "'" << std::endl;
    }
    return res;
}

double parse_arg_b(const std::string & line, std::size_t & i)
{
    i = skip_ws(line, i);
    std::string temp_ans = "";
    while (i < line.size() && !std::isspace(line[i])) {
        if (('0' <= line[i] && line[i] <= '9') || line[i] == '.') {
            temp_ans += line[i];
        }
        else {
            std::cerr << "Argument parsing error at " << i << ": '" << line.substr(i) << "'" << std::endl;
            return -1000000000000;
        }
        ++i;
    }
    /*if (temp_ans.empty()) {
        std::cerr << "No argument for a binary operation" << std::endl;
        return -1000000000000;
    }*/
    return std::stod(temp_ans);
}

double unary(const double current, const Op op)
{
    switch (op) {
    case Op::NEG:
        return -current;
    case Op::SQRT:
        if (current > 0) {
            return std::sqrt(current);
        }
        else {
            std::cerr << "Bad argument for SQRT: " << current << std::endl;
            [[fallthrough]];
        }
    default:
        return current;
    }
}

double binary(const Op op, const double left, const double right)
{
    switch (op) {
    case Op::SET:
        return right;
    case Op::ADD:
        return left + right;
    case Op::SUB:
        return left - right;
    case Op::MUL:
        return left * right;
    case Op::DIV:
        if (right != 0) {
            return left / right;
        }
        else {
            std::cerr << "Bad right argument for division: " << right << std::endl;
            return left;
        }
    case Op::REM:
        if (right != 0) {
            return std::fmod(left, right);
        }
        else {
            std::cerr << "Bad right argument for remainder: " << right << std::endl;
            return left;
        }
    case Op::POW:
        return std::pow(left, right);
    case Op::ADD_B:
        return left + right;
    case Op::SUB_B:
        return left - right;
    case Op::MUL_B:
        return left * right;
    case Op::DIV_B:
        if (right != 0) {
            return left / right;
        }
        else {
            std::cerr << "Bad right argument for division: " << right << std::endl;
            return left;
        }
    case Op::REM_B:
        if (right != 0) {
            return std::fmod(left, right);
        }
        else {
            std::cerr << "Bad right argument for remainder: " << right << std::endl;
            return left;
        }
    case Op::POW_B:
        return std::pow(left, right);
    default:
        return left;
    }
}

} // anonymous namespace

double process_line(const double current, const std::string & line)
{
    std::size_t i = 0;
    const auto op = parse_op(line, i);
    switch (arity(op)) {
    case 3: {
        double answer = current;
        i += 2;
        bool changed = false;
        i = skip_ws(line, i);
        while (i < line.size()) {
            auto old_i = i;
            auto oper = parse_arg_b(line, i);
            if (oper == -1000000000000) {
                return current;
            }
            if (op == Op::DIV_B && oper == 0) {
                std::cerr << "Bad right argument for division: " << oper << std::endl;
                answer = current;
                break;
            }
            if (op == Op::REM_B && oper == 0) {
                std::cerr << "Bad right argument for remainder: " << oper << std::endl;
                answer = current;
                break;
            }
            answer = binary(op, answer, oper);
            if (old_i == i) {
                changed = false;
                break;
            }
            changed = true;
            i = skip_ws(line, i);
        }
        if (changed) {
            return answer;
        }
        else {
            std::cerr << "No argument for a binary operation" << std::endl;
            return current;
        }
    }
    case 2: {
        i = skip_ws(line, i);
        const auto old_i = i;
        const auto arg = parse_arg(line, i);
        if (i == old_i) {
            std::cerr << "No argument for a binary operation" << std::endl;
            break;
        }
        else if (i < line.size()) {
            break;
        }
        return binary(op, current, arg);
    }
    case 1: {
        if (i < line.size()) {
            std::cerr << "Unexpected suffix for a unary operation: '" << line.substr(i) << "'" << std::endl;
            break;
        }
        return unary(current, op);
    }
    default: break;
    }
    return current;
}
