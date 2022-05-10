
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
	@FXML
    private ComboBox<Country> cmbNations;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	this.txtResult.clear();
    	int anno;
    	try {
    		anno = Integer.parseInt(this.txtAnno.getText());
    	}catch(NumberFormatException nfe) {
    		this.txtResult.setText("Devi inserire un valore numerico!");
    		return;
    	}
    	//controllo sull'input
    	if(anno < 1816 || anno >2006) {
    		this.txtResult.setText("Devi inserire un anno fra il 1816 e il 2006!");
    		return;
    	}
    	//se l'input è stato inserito correttamente possiamo proseguire con il calcolo dei confini
    	this.model.creaGrafo(anno);
    	this.cmbNations.getItems().addAll(this.model.getAllCountries());
    	
    	//stampa elenco stati e rispettivo grado dei vertici
    	for(Country cc : this.model.getVertexGrade().keySet()) {
    		this.txtResult.appendText(cc.getStateName()+" ha "+this.model.getVertexGrade().get(cc)+" stati confinanti.\n");
    	}
    	//Stampa numero delle componenti connesse
    	this.txtResult.appendText("Il grafo contiene "+this.model.getNumberConnectedComponents()+" componenti connesse.\n");
    }
    
    @FXML
    void findStatiRaggiungibili(ActionEvent event) {
    	this.txtResult.clear();
    	if(this.cmbNations.getItems().isEmpty()) {
    		this.txtResult.setText("Prima devi selezionare un anno e creare il grafo!");
    		return;
    	}
    	Country state = this.cmbNations.getValue();
    	if(state == null) {
    		this.txtResult.setText("Non hai selezionato una nazione");
    		return;
    	}
    	//controllo se lo stato è presente nel grafo
    	if(!this.model.getVertexGrade().keySet().contains(state)) {
    		this.txtResult.setText("Lo stato non è presente nel grafo!");
    		return;
    	}
    	//se l'input è stato inserito correttamente, possiamo proseguire con la visita del grafo
    	List<Country> reachable = this.model.getStatiRaggiungibili(state);
    	this.txtResult.appendText("Lista degli stati raggiungibili a partire da "+state.getStateName()+"\n");;
    	for(Country cc : reachable) {
    		this.txtResult.appendText(cc.getStateName()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbNations.getItems().clear();

    }
}
