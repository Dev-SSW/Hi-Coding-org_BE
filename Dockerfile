# 베이스 이미지
FROM openjdk:17-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 JAR 파일 복사
COPY ./build/libs/*.jar app.jar

# 실행 명령어
CMD ["java", "-jar", "app.jar"]