package com.thaj

import cats.data.ReaderT
import cats.effect.IO
import com.thaj.Dependencies.HasSql
import com.thaj.Repository.User
import com.thaj.Repository.User.Email

trait Repository[A] {
  def get(userId: User.Id): ReaderT[IO, A, User]

}

object Repository {
  type Id = Int

  def live[A](implicit sql: HasSql[A]): Repository[A] =
    (userId: Id) => ReaderT(env => sql.getFrom(env).run(s"select * from users where user = ${userId}").run(env).map(_ => User(userId, "afs", 15, "thaj")))

  final case class User(id: User.Id, name: String, age: Int, email: Email)

  object User {
    type Id = Int
    type Email = String
  }
}
