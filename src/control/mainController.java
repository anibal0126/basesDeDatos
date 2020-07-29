package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class mainController {
	
	@FXML
	public Pane top;

	@FXML
	private void closeButtonAction(Stage stage) {
		stage.close();
	}

	@FXML
	void abrirStage(Stage actual, String path) {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
			Parent root2 = fxmlLoader.load();
			Stage stage = new Stage();

			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root2));
			stage.show();
			closeButtonAction(actual);
		} catch (Exception ex) {

		}
	}

}
