Run Tests with:
    https://travis-ci.org/

 ClassPath
     -Xbootclasspath/p:config -DcontextName=VS  -Dspring.profiles.active=dev

     create a dir and put the war under deployments.
     oc new-build jboss-webserver30-tomcat8-openshift --name=vs --binary=true
     oc start-build vs --from-dir=. --follow=true --wait=true

     oc new-app vs spring.profiles.active=prod contextName=VS

     create a file with env variables
     oc set env dc/vs -e CATALINA_OPTS="-DcontextName=VS  -Dspring.profiles.active=prod $CATALINA_OPTS"
     export CATALINA_OPTS="-DcontextName=VS  -Dspring.profiles.active=dev $CATALINA_OPTS"

     oc delete bc vs -n vantashala
     oc delete all -l app=vs