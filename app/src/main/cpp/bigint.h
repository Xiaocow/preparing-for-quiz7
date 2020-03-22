//
// Created by Chek Tien Tan a long time ago
// - adapted from https://sites.google.com/site/indy256/algo_cpp/bigint
//

#ifndef BIGINT_H
#define BIGINT_H

#include <cstdlib>
#include <iostream>
#include <iomanip>
#include <vector>
#include <string>

class BigInt {

private:
    static const int base = 1000000000;
    static const int base_digits = 9;
    std::vector<int> a;
    int sign;

public:

    BigInt(): sign(1) {
    }

    BigInt(long long v) {
        *this = v;
    }

    BigInt(const std::string &s) {
        read(s);
    }

    inline void operator=(const BigInt &v) {
        sign = v.sign;
        a = v.a;
    }

    inline void operator=(long long v) {
        sign = 1;
        if (v < 0)
            sign = -1, v = -v;
        for (; v > 0; v = v / base)
            a.push_back(v % base);
    }

    BigInt operator+(const BigInt &v) const;

    BigInt operator-(const BigInt &v) const;

    void operator*=(int v);

    BigInt operator*(int v) const;

    friend std::pair<BigInt, BigInt> divmod(const BigInt &a1, const BigInt &b1);

    inline BigInt operator/(const BigInt &v) const {
        return divmod(*this, v).first;
    }

    inline BigInt operator%(const BigInt &v) const {
        return divmod(*this, v).second;
    }

    void operator/=(int v);

    BigInt operator/(int v) const;

    int operator%(int v) const;

    inline void operator+=(const BigInt &v) {
        *this = *this + v;
    }
    inline void operator-=(const BigInt &v) {
        *this = *this - v;
    }
    inline void operator*=(const BigInt &v) {
        *this = *this * v;
    }
    inline void operator/=(const BigInt &v) {
        *this = *this / v;
    }

    bool operator<(const BigInt &v) const;

    inline bool operator>(const BigInt &v) const {
        return v < *this;
    }
    inline bool operator<=(const BigInt &v) const {
        return !(v < *this);
    }
    inline bool operator>=(const BigInt &v) const {
        return !(*this < v);
    }
    inline bool operator==(const BigInt &v) const {
        return !(*this < v) && !(v < *this);
    }
    inline bool operator!=(const BigInt &v) const {
        return *this < v || v < *this;
    }

    void trim();

    inline bool isZero() const {
        return a.empty() || (a.size() == 1 && !a[0]);
    }

    inline BigInt operator-() const {
        BigInt res = *this;
        res.sign = -sign;
        return res;
    }

    inline BigInt abs() const {
        BigInt res = *this;
        res.sign *= res.sign;
        return res;
    }

    long long longValue() const;

    friend BigInt gcd(const BigInt &a, const BigInt &b);
    friend BigInt lcm(const BigInt &a, const BigInt &b);

    void read(const std::string &s);

    friend std::istream& operator>>(std::istream &stream, BigInt &v);

    friend std::ostream& operator<<(std::ostream &stream, const BigInt &v);

    static std::vector<int> convert_base(const std::vector<int> &a, int old_digits, int new_digits);

    typedef std::vector<long long> vll;

    static vll karatsubaMultiply(const vll &a, const vll &b);

    BigInt operator*(const BigInt &v) const;
};

#endif //BIGINT_H
