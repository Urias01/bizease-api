# Etapa 1: Build
FROM maven:3.9.6-amazoncorretto-21-debian AS build

WORKDIR /app

# Copia apenas o pom.xml primeiro para aproveitar cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Agora copia o código-fonte
COPY src ./src

# Compila o projeto e gera o JAR sem rodar testes
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM amazoncorretto:21-alpine

WORKDIR /app

# Copia apenas o artefato necessário
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","app.jar"]
