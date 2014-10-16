version := "1-SNAPSHOT"

organization := "org.eclipse.jetty.embedded.demo"

val root = project.in( file(".") )

val `example-webapp` = project.in( file("example-webapp") )

val `example-embedded-servlet-override` = project.in( file("example-embedded-servlet-override") )
