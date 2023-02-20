package com.thaj

import com.thaj.Config.DbDetails
import com.thaj.Dependencies.HasConfig

import java.net.URL

final case class Config(dbDetails: DbDetails)

object Config {
  implicit val config: HasConfig[Config] = identity

  final case class DbDetails(connectionUrl: URL, port: Int)
}
