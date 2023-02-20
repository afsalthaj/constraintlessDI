package com.thaj.database

import cats.data.ReaderT
import cats.effect.{IO, Resource}
import com.thaj.Dependencies.HasConnection

trait Sql[A] {
  def run(string: String): ReaderT[IO, A, Unit]
}

object Sql {
  def live[A](implicit connection: HasConnection[A]): Sql[A] =
    (string: String) => ReaderT(env =>
      Resource.make(connection.getFrom(env).getConnection.run(env))(connection => IO(connection.close())).use(
        connection => IO(connection.prepareStatement(string).execute()).map(_ => ())
      ))
}