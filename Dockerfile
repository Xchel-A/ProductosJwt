FROM ubuntu

# Actualizar el índice de paquetes y instalar MySQL Server
RUN apt-get update && apt-get install -y mysql-server

# Iniciar el servicio de MySQL
RUN /etc/init.d/mysql start

# Instalar OpenJDK 17
RUN apt-get install -y openjdk-17-jdk

VOLUME /tmp
EXPOSE 8080

# Agregar el archivo JAR de la aplicación al contenedor
ARG JAR_FILE=out/artifacts/productosjwt_jar/productosjwt.jar
ADD ${JAR_FILE} app.jar

# Ejecutar la aplicación cuando se inicie el contenedor
ENTRYPOINT ["java", "-jar", "/app.jar"]
