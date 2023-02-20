package com.thaj.database

import cats.data.ReaderT
import cats.effect.IO
import com.thaj.Dependencies.HasPool
import com.thaj.Getter

import java.sql.Connection

trait GetConnection[A] {
  def getConnection: ReaderT[IO, A, Connection]
}

object GetConnection {
  def live[A](implicit ev: HasPool[A]): GetConnection[A] =
    new GetConnection[A] {
      override def getConnection: ReaderT[IO, A, Connection] =
        ReaderT(a => ev.getFrom(a).getConnectionPools.run(a).flatMap(connections => connections.headOption match {
          case Some(value) => IO.pure(value)
          case None => IO.raiseError(new RuntimeException("Failed to retrieve connection"))
        }))
    }
}