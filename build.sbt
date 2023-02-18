name := "constraintlessDI"

version := "0.1"

scalaVersion := "2.13.10"

libraryDependencies += "dev.zio" %% "zio-constraintless" % "0.3.1"

libraryDependencies += "org.typelevel" %% "cats-mtl" % "1.3.0"

libraryDependencies += "us.oyanglul" %% "luci" % "0.7.0"

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)

