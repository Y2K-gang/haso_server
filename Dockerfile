# OpenJDK 21 Slim 사용
FROM docker.io/library/openjdk:21-jdk-slim

# 필요한 패키지 설치 (xargs 포함)
RUN apt-get update && apt-get install -y \
    coreutils \
    && rm -rf /var/lib/apt/lists/*

# 필요한 패키지 설치 (Java 17 포함)
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    && rm -rf /var/lib/apt/lists/*



# 컨테이너 내부 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper만 먼저 복사하고 실행 권한 설정
COPY gradlew /app/gradlew
RUN chmod +x gradlew

# 현재 프로젝트 폴더(=haso) 내부 파일들을 컨테이너에 복사
COPY . /app

# Gradle 빌드 실행 (테스트 제외, 빌드 캐시 활용)
RUN ./gradlew clean assemble -x test --build-cache --parallel

# 컨테이너에서 실행할 포트 열기
EXPOSE 8080

# 실행할 JAR 파일 지정 (빌드된 JAR 파일 이름 확인!)
CMD ["java", "-jar", "build/libs/Haso-0.0.1-SNAPSHOT.jar"]
