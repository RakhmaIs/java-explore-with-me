FROM amazoncorretto:11
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8081
ENV TZ=Europe/Moscow
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]