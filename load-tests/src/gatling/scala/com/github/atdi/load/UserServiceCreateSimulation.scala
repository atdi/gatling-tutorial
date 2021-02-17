package com.github.atdi.load

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class UserServiceCreateSimulation extends Simulation {

  val feeder = csv("data/users.csv")

  val httpConf = http.baseUrl("http://localhost:50001")
    .header("Content-Type", "application/json")

  val createUserScenario = scenario("Create user")
    .feed(feeder)
    .exec(http("Create User")
      .post("/users").body(StringBody(
      """{"firstName": "${first_name}",
              "lastName": "${last_name}",
              "email": "${email}",
              "password": "${password}"}""")).asJson.check(status.is(201)))

  setUp(
    createUserScenario.inject(constantUsersPerSec(25).during(1 second)).protocols(httpConf)
  )
}