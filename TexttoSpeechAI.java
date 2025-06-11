build.gradle
  plugins {
    id 'java'
}

group 'org.example'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    // Use this if you have the AssemblyAI SDK jar locally in libs/
    implementation files('libs/assemblyai-sdk.jar')

    // Example: JUnit for testing (optional)
    testImplementation 'org.junit.jupiter:junit-jupiter:5.8.1'
}

test {
    useJUnitPlatform()
}






package org.example;

import com.assemblyai.api.RealtimeTranscriber;
import javax.sound.sampled.*;
import java.io.IOException;

public final class Main {
    public static void main(String... args) throws IOException {
        Thread thread = new Thread(() -> {
            try {
                RealtimeTranscriber realtimeTranscriber = RealtimeTranscriber.builder()
                        .apiKey("API-KEY")
                        .sampleRate(16_000)
                        .onSessionBegin(sessionBegins ->
                                System.out.println("Session open ID: " + sessionBegins.getSessionId()))
                        .onPartialTranscript(transcript -> {
                            if (!transcript.getText().isEmpty()) {
                                System.out.println("Partial: " + transcript.getText());
                            }
                        })
                        .onFinalTranscript(transcript ->
                                System.out.println("Final: " + transcript.getText()))
                        .onError(err ->
                                System.out.println("Error: " + err.getMessage()))
                        .build();

                System.out.println("Connecting to AssemblyAI");
                realtimeTranscriber.connect();

                System.out.println("Start recording");
                AudioFormat format = new AudioFormat(16_000, 16, 1, true, false);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                byte[] data = new byte[line.getBufferSize()];
                line.start();

                while (!Thread.currentThread().isInterrupted()) {
                    int bytesRead = line.read(data, 0, data.length);
                    if (bytesRead > 0) {
                        realtimeTranscriber.sendAudio(data);
                    }
                }

                System.out.println("Stopping recording");
                line.stop();
                line.close();
                System.out.println("Closing real-time transcript connection");
                realtimeTranscriber.close();

            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });

        thread.start();
        System.out.println("Press ENTER key to stop...");
        System.in.read();
        thread.interrupt();
        System.exit(0);
    }
}

