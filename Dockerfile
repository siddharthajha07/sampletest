FROM openjdk:11
COPY . /home/dsdoptimize/sampletest/ChatRoomSocket/src/com/chatRoom
WORKDIR /home/dsdoptimize/sampletest/ChatRoomSocket/src/com/chatRoom/
RUN javac Server.java
CMD ["java", "Server"]
