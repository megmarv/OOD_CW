package com.example.demo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private Button goToSignInPage;

    @FXML
    private void redirectToSignInPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignInPage.fxml"));
        Parent MainMenuWindow = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Sign in");
        Scene scene = new Scene(MainMenuWindow, 968, 537);
        stage.setScene(scene);
        stage.show();
    }

}
