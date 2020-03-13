package io.hexaforce.audio.client;

import static java.lang.System.out;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

public class Microphone implements Runnable {

	// SampleRate:16000Hz, SampleSizeInBits: 16, Number of channels: 1, Signed:true, bigEndian: false
	public static final AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);

	// Set the system information to read from the microphone audio stream
	public static final DataLine.Info targetInfo = new Info(TargetDataLine.class, audioFormat);

	private final ListenableFuture<WebSocketSession> future;

	public Microphone(ListenableFuture<WebSocketSession> future) {
		this.future = future;
	}
	
	public boolean recording = false;
	
	@Override
	public void run() {

		if (!AudioSystem.isLineSupported(targetInfo)) {
			out.println("Microphone not supported");
			return;
		}

		byte[] data = new byte[1024];
		TargetDataLine dataLine;

		try {

			dataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			dataLine.open(audioFormat);
			dataLine.start();

			while (dataLine.isOpen()) {
				int numBytesRead = dataLine.read(data, 0, data.length);
				if (numBytesRead <= 0 && dataLine.isOpen())
					continue;
				if (future.get().isOpen()) {
					if (recording) {
						future.get().sendMessage(new BinaryMessage(data));
					}
				} else {
					dataLine.stop();
					System.exit(0);
				}
			}

		} catch (LineUnavailableException | InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}

	}

}
