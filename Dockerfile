# 알파인 대신 Slim 버전 사용 (더 안정적)
FROM docker.io/library/openjdk:21-jdk

# 컨테이너 내부 작업 디렉토리 설정
WORKDIR /app

# 현재 프로젝트 폴더(=haso) 내부 파일들을 컨테이너에 복사
COPY . /app

# 실행 권한 추가
RUN chmod +x gradlew

# Gradle 빌드 실행 (테스트 제외, 빌드 캐시 활용)
RUN ./gradlew clean assemble -x test --build-cache --parallel

# 컨테이너에서 실행할 포트 열기
EXPOSE 8080

# 실행할 JAR 파일 지정 (빌드된 JAR 파일 이름 확인!)
CMD ["java", "-jar", "build/libs/Haso-0.0.1-SNAPSHOT.jar"]
