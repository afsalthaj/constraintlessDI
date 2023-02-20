package com.thaj

import cats.effect.IO
import com.thaj.Dependencies.{HasConfig, HasEmailClient, HasLogger}

trait EmailClient {
  def send(email: String): IO[Unit]
}

object EmailClient {
  implicit val emailClient: HasEmailClient[EmailClient] = identity

  def live: EmailClient = {
    (email: String) => IO(print(s"send ${email}"))
  }

}