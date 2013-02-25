package proscalafx.ch03

import scalafx.beans.property.{DoubleProperty, FloatProperty, IntegerProperty, LongProperty}

object NumericPropertiesExample extends App {
  val i = new IntegerProperty(null, "i", 1024)
  val l = new LongProperty(null, "l", 0L)
  val f = new FloatProperty(null, "f", 0.0F)
  val d = new DoubleProperty(null, "d", 0.0)
  println("Constructed numerical properties i, l, f, d.")

  println("i = " + i())
  println("l = " + l())
  println("f = " + f())
  println("d = " + d())

  l <== i
  f <== l
  d <== f
  println("Bound l to i, f to l, d to f.")

  println("i = " + i())
  println("l = " + l())
  println("f = " + f())
  println("d = " + d())

  println("Calling i.set(2048).")
  i() = 2048

  println("i = " + i())
  println("l = " + l())
  println("f = " + f())
  println("d = " + d())

  d.unbind()
  f.unbind()
  l.unbind()
  println("Unbound l to i, f to l, d to f.")

  f <== d
  l <== f
  i <== l
  println("Bound f to d, l to f, i to l.")

  println("Calling d.set(10000000000L).")
  d() = 10000000000L

  println("d = " + d())
  println("f = " + f())
  println("l = " + l())
  println("i = " + i())
}