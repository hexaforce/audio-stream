package io.hexaforce.audio.client;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import javafx.embed.swt.FXCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class GUI {

	final Display display = new Display();
	final Shell shell = new Shell(display);
	final RowLayout layout = new RowLayout();

	public void launch(Microphone recording) {

		shell.setLayout(layout);

		/* Create the SWT button */
		final Button swtButton = new Button(shell, SWT.PUSH);
		swtButton.setText("SWT Button");

		/* Create an FXCanvas */
		final FXCanvas fxCanvas = new FXCanvas(shell, SWT.NONE) {
			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				getScene().getWindow().sizeToScene();
				int width = (int) getScene().getWidth();
				int height = (int) getScene().getHeight();
				return new Point(width, height);
			}
		};

		/* Create a JavaFX button */
		final javafx.scene.control.Button jfxButton = new javafx.scene.control.Button("JFX Button");

		/* Assign the CSS ID ipad-dark-grey */
		jfxButton.setId("ipad-dark-grey");

		/* Create a JavaFX Group node */
		Group group = new Group();

		/* Add the button as a child of the Group node */
		group.getChildren().add(jfxButton);

		/* Create the Scene instance and set the group node as root */
		Scene scene = new Scene(group, convert(shell.getBackground()));

		/* Attach an external stylesheet */
		scene.getStylesheets().add("Buttons.css");

		fxCanvas.setScene(scene);

		/* Add Listeners */
		swtButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				jfxButton.setText("Recording start");
				recording.recording = true;
				shell.layout();
			}
		});

		jfxButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				swtButton.setText("Recording stop");
				recording.recording = false;
				shell.layout();
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();

	}

	private javafx.scene.paint.Color convert(org.eclipse.swt.graphics.Color c) {
		return Color.rgb(c.getRed(), c.getGreen(), c.getBlue());
	}

}
