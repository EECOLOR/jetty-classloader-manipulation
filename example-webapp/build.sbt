name := "example-webapp"

organization := "org.eclipse.jetty.embedded.demo"

version := "1-SNAPSHOT"

libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"

crossPaths := false

//mappings in (Compile, packageBin) := {
  //(mappings in (Compile, packageBin)).value.map {
    //case (file, mapping) if !mapping.startsWith("WEB-INF") => file -> ("WEB-INF/classes/" + mapping)
    //case m => m
  //}
//}

// create an Artifact for publishing the .war file
//artifact in (Compile, packageBin) := {
  //val previous: Artifact = (artifact in (Compile, packageBin)).value
  //previous.copy(`type` = "war", extension = "war")
//}
