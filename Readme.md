##Start rest server##

###On linux/unix###

./gradlew bootRun

###On windows###

gradlew.bat bootRun

##Add data##

###On linux/unix###

./gradlew :load-test:gatling-com.github.atdi.load.UserServiceCreateSimulation

###On windows###

gradlew.bat :load-test:gatling-com.github.atdi.load.UserServiceCreateSimulation

##Run tests##

###On linux/unix###

./gradlew :load-test:gatling-com.github.atdi.load.UserServiceSimpleSimulation

On windows:

gradlew.bat :load-test:gatling-com.github.atdi.load.UserServiceSimpleSimulation
