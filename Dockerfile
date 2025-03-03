# 빌드 스테이지
FROM amazoncorretto:21-alpine AS build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# Gradle 래퍼에 실행 권한 부여
RUN chmod +x ./gradlew

# Gradle을 사용하여 애플리케이션 빌드
RUN ./gradlew bootJar

# 실행 스테이지
FROM amazoncorretto:21-alpine

WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# Java 21 최적화 옵션
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]
