FROM openjdk:16-jdk

COPY ./build/libs/LessonInstanceService-0.0.1-all.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java", "-jar", "run.jar"]