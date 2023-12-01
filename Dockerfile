# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jdk-slim

# Set the working directory to /app
WORKDIR /app
# Copy the project files to the container
COPY . .
# Install curl
RUN apt-get update && \
    apt-get install -y curl && \
    apt-get clean
# Install sbt
# Create the sbt launchers directory
RUN mkdir -p /root/.sbt/launchers/1.3.10/

# Manually download sbt launcher
RUN curl -L -o /root/.sbt/launchers/1.3.10/sbt-launch.jar https://repo1.maven.org/maven2/org/scala-sbt/sbt-launch/1.3.10/sbt-launch-1.3.10.jar


RUN curl -L https://git.io/sbt > /usr/local/bin/sbt && \
    chmod +x /usr/local/bin/sbt && \
    sbt sbtVersion && \
    apt-get clean

RUN sbt compile stage

# Expose the Play application's port
EXPOSE 9000

CMD ["target/universal/stage/bin/play-chat-gpt", "-Dplay.http.secret.key=dhU4QIVo8n8t0^ARBv9Wm7G@bhRjXA71fdPu1NYy3V7Z6E^`r=rPAhu<>nxN^NU<"]

