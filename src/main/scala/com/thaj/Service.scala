package com.thaj

import cats.data.ReaderT
import cats.effect.IO
import com.thaj.Dependencies.{HasEmailClient, HasLogger, HasRepository}
import com.thaj.Repository.User
import com.thaj.Repository.User.Id

trait Service[A] {

  def getUser(user: User.Id): ReaderT[IO, A, User]

  def isEligibleForLoan(user: User): ReaderT[IO, A, Boolean]

  def sendEmail(user: User): ReaderT[IO, A, Unit]

  def program(id: User.Id)(implicit logger: HasLogger[A]): ReaderT[IO, A, Unit] =
    for {
      user <- getUser(id)
      _ <- ReaderT[IO, A, Unit](logger.getFrom(_)("Successfully fetched user"))
      bool <- isEligibleForLoan(user)
      _ <- if (bool) {
        for {
          _ <- ReaderT[IO, A, Unit](logger.getFrom(_)("Succesfully validated the suer"))
          _ <- sendEmail(user)
        } yield ()
      } else ReaderT[IO, A, Unit](logger.getFrom(_)("Failed to validate the user"))
    } yield ()

}


object Service {
  def live[A](implicit repo: HasRepository[A], email: HasEmailClient[A]): Service[A] = new Service[A] {
    override def getUser(user: Id): ReaderT[IO, A, User] = ReaderT(env => repo.getFrom(env).get(user).run(env))

    override def isEligibleForLoan(user: User): ReaderT[IO, A, Boolean] =
      if (user.age > 10) ReaderT.liftF(IO(true)) else ReaderT.liftF(IO(false))

    override def sendEmail(user: User): ReaderT[IO, A, Unit] =
      ReaderT(email.getFrom(_).send(s"Congratulations ${user.name}. We have processed your loan"))
  }

  def sendEmail[A](emailString: String)(implicit email: HasEmailClient[A], logger: HasLogger[A]): ReaderT[IO, A, Unit] = {
    if (emailString.isEmpty) ReaderT(logger.getFrom(_).apply("Failed to send email")) else ReaderT(email.getFrom(_).send(emailString))
  }

}