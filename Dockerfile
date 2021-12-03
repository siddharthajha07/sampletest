FROM openjdk:11
COPY . /home/dsdoptimize/sampletest/ChatRoomSocket/src/com
WORKDIR /home/dsdoptimize/sampletest/ChatRoomSocket/src/com
RUN javac Server.java
CMD ["java", "Server"]
