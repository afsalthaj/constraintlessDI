package com.thaj

import cats.effect.IO
import com.thaj.Config.DbDetails
import com.thaj.database.{ConnectionPool, GetConnection, Sql}

import java.net.URL

// The record of functions
final case class Environment(
  config: Config,
  connection: GetConnection[Environment],
  pool: ConnectionPool[Environment],
  repository: Repository[Environment],
  sql: Sql[Environment],
  emailClient: EmailClient,
  logger: String => IO[Unit]
)


object Environment {

  def live =
    Environment(
      Config(DbDetails(new URL(""), 123)), GetConnection.live, ConnectionPool.live, Repository.live, Sql.live, EmailClient.live, string => IO(println(string)))

  implicit def hasConfig[A]: Getter[Environment, Config] =
    Getter.instance(_.config)

  implicit def hasLogger[A]: Getter[Environment, String => IO[Unit]] =
    Getter.instance(_.logger)

  implicit def hasRepository[A]: Getter[Environment, Repository[Environment]] =
    Getter.instance(_.repository)

  implicit def hasConnection[A]: Getter[Environment, GetConnection[Environment]] =
    Getter.instance(_.connection)

  implicit def hasPool[A]: Getter[Environment, ConnectionPool[Environment]] =
    Getter.instance(_.pool)

  implicit def emailClient[A]: Getter[Environment, EmailClient] =
    Getter.instance(_.emailClient)

  implicit def sqlClient[A]: Getter[Environment, Sql[Environment]] =
    Getter.instance(_.sql)
}
