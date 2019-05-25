package com.mitsuki.algorithm.fractal

class Complex(r: Double, i: Double) {
    var real: Double = r
    var imaginary: Double = i

    fun mod() = Math.sqrt(real * real + imaginary * imaginary)

    operator fun plus(other: Complex) = Complex(real + other.real, imaginary + other.imaginary)

    operator fun minus(other: Complex) = Complex(real - other.real, imaginary - other.imaginary)

    operator fun times(other: Complex) = Complex(
        real * other.real - imaginary * other.imaginary,
        real * other.imaginary + imaginary * other.real
    )
}