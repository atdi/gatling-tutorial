package com.github.atdi.load

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class UserServiceCreateSimulation extends Simulation {

  val feeder = csv("users.csv")

  val httpConf = http.baseURL("http://localhost:50001")
    .header("Content-Type", "application/json")

  val createUserScenario = scenario("Create user")
    .feed(feeder)
    .exec(http("Create User")
      .post("/users")
      .body(
        StringBody("""{"firstName": "${first_name}",
              "lastName": "${last_name}",
              "email": "${email}",
              "password": "${password}"}""")).asJSON
      .check(status.is(201))
    )

  setUp(
    createUserScenario.inject(constantUsersPerSec(25).during(1 seconds)).protocols(httpConf)
  )
}