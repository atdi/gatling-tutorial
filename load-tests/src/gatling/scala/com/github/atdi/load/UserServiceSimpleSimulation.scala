package com.github.atdi.load

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

/**
  * Created by aurelavramescu on 05/04/16.
  */
class UserServiceSimpleSimulation extends Simulation {

  val feeder = csv("users.csv").random

  val httpConf = http.baseURL("http://localhost:50001")
    .header("Content-Type", "application/json")

  val chainScenario = scenario("Chain") // A scenario with a chain of requests and pauses
    .feed(feeder)
    .exec(http("Get user by email")
      .get("/users/search/findByEmail?email=${email}")
      .check(status.is(200))
      .check(bodyString.saveAs("userData"))
      .check(
        jsonPath("$.._links.self.href").find.exists.saveAs("nextUrl"))
    ).exec(http("Update password").put("${nextUrl}")
    .body(StringBody("${userData}"))
    .asJSON.check(status.is(200)))

  setUp(
    chainScenario.inject(constantUsersPerSec(10).during(10 seconds)).protocols(httpConf)
  ).assertions(global.responseTime.max.lessThan(5000))

}




/*StringBody("""{"first_name": "${first_name}",
              "last_name": "${last_name}",
              "birth_date": "${birth_date}",
              "email": "${email}",
              "password": "${password}",
              "phone": "${phone}"}""")*/

