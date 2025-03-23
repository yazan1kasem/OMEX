package com.example.omex_quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class OMEXController  {
    @FXML
    private BorderPane mainBorder;
    @FXML
    private BorderPane FrameBorder;

    private METABereich_Controller metaBereichController =new METABereich_Controller();
    private FragenController fragenController=new FragenController();

    public OMEXController() {

    }


    public void EinstellungenClicked(ActionEvent actionEvent) {
    }

    public void HomeClicked(ActionEvent actionEvent) {

    }
    public void FragenClicked(ActionEvent actionEvent) {
        FrameBorder.setCenter(fragenController);
        // fragenController.refresh();

    }
    public void META_Clicked(ActionEvent actionEvent) {
        FrameBorder.setCenter(metaBereichController);
        metaBereichController.refresh();
    }
}
