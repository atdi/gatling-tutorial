package com.github.atdi.load

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

/**
 * Created by aurelavramescu on 05/04/16.
 */
class UserServiceSimpleSimulation extends Simulation {

  val feeder = csv("data/users.csv").random

  val httpConf = http.baseUrl("http://localhost:50001")
    .header("Content-Type", "application/json")

  val chainScenario = scenario("Chain") // A scenario with a chain of requests and pauses
    .feed(feeder)
    .exec(http("Get user by email")
      .get("/users/search/findByEmail?email=${email}")
      .check(status.is(200))
      .check(jsonPath("$").ofType[Map[String, Any]].find.saveAs("userDataMap"))
      .check(
        jsonPath("$.._links.self.href").find.exists.saveAs("nextUrl"))
    ).exec(http("Update password").put("${nextUrl}")
    .body(StringBody(
      """{"firstName": "${userDataMap.firstName}",
              "lastName": "${userDataMap.lastName}",
              "email": "${userDataMap.email}",
              "password": "newpassword2"}""")).asJson.check(status.is(200)))


  setUp(
    chainScenario.inject(constantUsersPerSec(10).during(600 second)).protocols(httpConf)
  ).assertions(global.responseTime.max.lt(5000))

}


