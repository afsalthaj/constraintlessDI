package com.thaj.database

import cats.data.{Chain, ReaderT}
import cats.effect.IO
import com.thaj.Dependencies.HasConfig
import com.thaj.database.ConnectionPool.Connections

import java.net.URL
import java.sql.{Connection, DriverManager}

trait ConnectionPool[A] {
  def getConnectionPools: ReaderT[IO, A, Connections]
}

object ConnectionPool {
  type Connections = Chain[Connection]

  case class DbDetails(connectionUrl: URL, port: Int)

  def live[A](implicit ev: HasConfig[A]): ConnectionPool[A] = new ConnectionPool[A] {
    override def getConnectionPools: ReaderT[IO, A, Connections] =
      ReaderT(env => {
        IO(DriverManager.getConnection(ev.getFrom(env).dbDetails.connectionUrl.toString, "", "")).map(Chain(_))
      })
  }
}