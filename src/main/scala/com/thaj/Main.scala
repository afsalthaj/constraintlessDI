package com.thaj

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    Service.live[Environment].program(1).run(Environment.live).as(ExitCode.Success)

}

object UniTests {

  val emailLive: EmailClient =
    (email: String) => IO.unit

  type TestEnv = (String => IO[Unit], EmailClient)
  val test: TestEnv = (_ => IO.unit, emailLive)

  // TestEnv works with Getters due to tuple implicits in Getter
  // Sending a blank email should fail
  Service.sendEmail[TestEnv]("")

}
