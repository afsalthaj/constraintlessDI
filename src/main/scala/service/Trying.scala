package service

import cats.data.{Chain, Kleisli, ReaderT, StateT, WriterT}
import cats.effect.IO
import cats.free.Free
import doobie.ConnectionIO
import org.http4s.Uri
import us.oyanglul.luci.Eff7
import us.oyanglul.luci.effects.{GetStatus, Http4sClient}

object Trying {
  final case class Config()
  type Program[A] = Eff7[
    Http4sClient[IO, *],
    WriterT[IO, Chain[String], *],
    ReaderT[IO, Config, *],
    IO,
    ConnectionIO,
    StateT[IO, Int, *],
    Either[Throwable, *],
    A
  ]
  type ProgramF[A] = Free[Program, A]

  

  val program = for {
    config <- free.mk[Program](Kleisli.ask[IO, Config])
    _ <- free[Program](
      GetStatus[IO](GET(Uri.uri("https://blog.oyanglul.us"))))
    _ <- free[Program](StateT.modify[IO, Int](1 + _))
    _ <- free[Program](StateT.modify[IO, Int](1 + _))
    state <- free[Program](StateT.get[IO, Int])
    _ <- free[Program](
      WriterT.tell[IO, Chain[String]](
        Chain.one("config: " + config.token)))
    resOrError <- free[Program](sql"""select true""".query[Boolean].unique)
    _ <- free[Program](
      resOrError.handleError(e => println(s"handle db error $e")))
    _ <- free[Program](IO(println(s"im IO...state: $state")))
  } yield ()

}
