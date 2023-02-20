package com.thaj

trait Getter[A, B] {
  def getFrom(a: A): B
}

object Getter {
  def apply[A, B](implicit ev: Getter[A, B]): Getter[A, B] = ev

  def instance[A, B](f: A => B): Getter[A, B] = {
    (a: A) => f(a)
  }

  // Allows every function to be independently tested instead of passing the entire environment
  implicit def getterIdentity[A]: Getter[A, A] =
    instance(identity)

  // Allows to pass multiple dependencies to test a method without passing the entire environment
  implicit def getterTuple1[A, B]: Getter[(A, B), A] =
    instance(_._1)

  // Allows to pass multiple dependencies to test a method without passing the entire environment
  implicit def getterTuple2[A, B]: Getter[(A, B), B] =
    instance(_._2)

  // Allows to pass multiple dependencies to test a method without passing the entire environment
  implicit def getterTuple3[A, B, C]: Getter[(A, B, C), A] =
    instance(_._1)

  implicit def getterTuple4[A, B, C]: Getter[(A, B, C), B] =
    instance(_._2)

  implicit def getterTuple5[A, B, C]: Getter[(A, B, C), C] =
    instance(_._3)

  implicit def getterTuple6[A, B, C, D]: Getter[(A, B, C, D), A] =
    instance(_._1)

  implicit def getterTuple7[A, B, C, D]: Getter[(A, B, C, D), B] =
    instance(_._2)

  implicit def getterTuple8[A, B, C, D]: Getter[(A, B, C, D), C] =
    instance(_._3)

  implicit def getterTuple9[A, B, C, D]: Getter[(A, B, C, D), D] =
    instance(_._4)

}