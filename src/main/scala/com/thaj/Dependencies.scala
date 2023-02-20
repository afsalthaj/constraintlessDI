package com.thaj

import cats.effect.IO
import com.thaj.database.{ConnectionPool, GetConnection, Sql}

object Dependencies {
  type HasLogger[A] = Getter[A, String => IO[Unit]]
  type HasEmailClient[A] = Getter[A, EmailClient]
  type HasRepository[A] = Getter[A, Repository[A]]
  type HasSql[A] = Getter[A, Sql[A]]
  type HasConnection[A] = Getter[A, GetConnection[A]]
  type HasPool[A] = Getter[A, ConnectionPool[A]]
  type HasConfig[A] =Getter[A, Config]
}
