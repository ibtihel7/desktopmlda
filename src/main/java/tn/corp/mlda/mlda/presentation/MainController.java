package tn.corp.mlda.mlda.presentation;

import java.util.Iterator;
import java.util.List;

import javafx.util.Duration;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import tn.corp.mlda.mlda.model.Album;
import tn.corp.mlda.mlda.service.AlbumService;
import javafx.scene.paint.*;
public class MainController {
	@FXML
	private ChoiceBox<String> anneeFilter;

	

	@FXML
	private ChoiceBox<String> chanteurFilter;

	@FXML
	private Button myButton;

	@FXML
	private GridPane musicBox;

	private AlbumService service = new AlbumService();
    @FXML
    private VBox board;
    
	@FXML
	void clickMe(ActionEvent event) {

	}

	@FXML
	private ListView<String> categoryList;
	
	@FXML
    private BarChart<?, ?> barChart;
    

	@FXML
	public void initialize() {
		
		service.loadData();
		categoryList.getItems().add("<Tous>");
		anneeFilter.getItems().add("<Tous>");
		chanteurFilter.getItems().add("<Tous>");
		
		categoryList.getSelectionModel().getSelectedItems().addListener(this::onChangeListItem);
		anneeFilter.getSelectionModel().selectedItemProperty().addListener(this::onChangeAnnee);
		chanteurFilter.getSelectionModel().selectedItemProperty().addListener(this::onChangeSinger);
		
		categoryList.getItems().addAll(service.findCategoryNames());
		/* 
		categoryList.getItems().add("Pop");
		categoryList.getItems().add("Oriential");
		categoryList.getItems().add("Funk");
		categoryList.getItems().add("Blues");
		categoryList.getItems().add("Classical");
		categoryList.getItems().add("Rock");
		categoryList.getItems().add("Rock&Roll");
		categoryList.getItems().add("Hard Rock");
		
		*/
		

//		
//		anneeFilter.getItems().add("2021");
//		anneeFilter.getItems().add("2020");
//		anneeFilter.getItems().add("2019");
		

		
		
		chanteurFilter.getItems().addAll(service.findSingerNames());

		anneeFilter.getItems().addAll(service.findYearsNames());
		
		
		
		reAppliquerFitre();
		
		prepareBoard();
		prepareBarChart();
	}

/*	public void prepareBoard() {
		board.getChildren().add(
				new Line(0, 0, 20, 20)
		);
		board.getChildren().add(
				new Circle(10, 10, 10)
		);
}
*/	
	
	
	public void prepareBoard() {
		Group  g = new Group();
		Shape M = new Polyline (0,20,0,0,10,10,20,0,20,20);
		M.setStrokeWidth(3);

		g.getChildren().add(M);
		Shape L = new Polyline (30,0,30,20,50,20);
		L.setStrokeWidth(3);

		g.getChildren().add(L);
		

		Shape D= new Arc(60, 10, 20, 10, -90, 180);	
		g.getChildren().add(D);
		D.setFill(Color.GREEN) ;
		D.setStrokeWidth(3);

	
		board.getChildren().add(g);
		Shape A= new Polyline (90,20,90,0,100,0,100,20,100,8,90,8);
		A.setStrokeWidth(3);
		g.getChildren().add(A);
		
		TranslateTransition tr=new TranslateTransition();
		tr.setDuration(Duration.seconds(3));
		tr.setAutoReverse(true);
		tr.setCycleCount(Animation.INDEFINITE);
		tr.setToY(30);
		tr.setNode(board);
		tr.play();
		
	}
	
	
//	public Node createVignetteByFXML(Album a) {
//		try {
//			Node vignette = App.loadFXML("vignette");
//			ImageView imageView = (ImageView) vignette.lookup("image");
//			Label title = (Label) vignette.lookup("title");
//			Label description = (Label) vignette.lookup("description");
//			title.setText(a.getTitle());
//			description.setText(a.getDescription());
//			imageView.setImage(new Image(a.getImage()));
//			return vignette;
//		} catch (Exception ex) {
//			throw new IllegalArgumentException(ex);
//		}
//	}

	public Node createVignetteByCode(Album a) {
		BorderPane bp = new BorderPane();
		ImageView iv = new ImageView();
		bp.setCenter(iv);
		VBox vb = new VBox();
		bp.setBottom(vb);
		Label title = new Label();
		vb.getChildren().add(title);
		Label description = new Label();
		vb.getChildren().add(description);

		iv.setImage(new Image(getClass().getResourceAsStream(a.getImage())));
		iv.setFitHeight(64);
		iv.setFitWidth(64);
		title.setText(a.getTitle());
		description.setText(a.getDescription());
	
		
		RotateTransition rotation = new  RotateTransition (Duration.seconds(0.5),bp);
		rotation.setCycleCount(Animation.INDEFINITE);
		rotation.setByAngle(360);
		bp.setOnMouseEntered(e-> rotation.playFromStart());
		bp.setOnMouseExited( e->
				{
					iv.setRotate(0);
					rotation.pause();
				}
				
				
				);
		return bp;
		
	
	}

	void onChangeAnnee(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reAppliquerFitre();
	}


	public void onChangeListItem(Change<? extends String> ch) {
		reAppliquerFitre();
	}
	public void onChangeSinger(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reAppliquerFitre();
	}

	
	
	void reAppliquerFitre() {
		String category = categoryList.getSelectionModel().getSelectedItem();
		if ("<Tous>".equals(category)) {
			category = null;
		}
		String singer = chanteurFilter.getSelectionModel().getSelectedItem();
		if ("<Tous>".equals(singer)) {
			singer = null;
		}
		String year = anneeFilter.getSelectionModel().getSelectedItem();
		Integer yearInt = (year == null || year.equals("<Tous>")) ? null : Integer.parseInt(year);
		List<Album> theAlbums = service.findAlbums(yearInt, category, singer);
		musicBox.getChildren().clear();
		int columns = musicBox.getColumnCount();
		int rows = musicBox.getRowCount();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				try {
					int p = i * columns + j;
					if(p<theAlbums.size()) {
						Album a = theAlbums.get(p);
//						Node vignette1=createVignetteByFXML(a);
						Node vignette2=createVignetteByCode(a);
						musicBox.add(vignette2, i, j);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@FXML
	void onClickClose(ActionEvent event) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText("Malla Ra7a");
		alert.show();
		// System.exit(0);
	}
	
	public void prepareBarChart() {
		barChart.getXAxis().setLabel("Albums");
		barChart.getYAxis().setLabel("Visits");
		barChart.getData().clear();
		XYChart.Series dataSeries1 = new XYChart.Series();
		dataSeries1.setName("2014");

		String category = categoryList.getSelectionModel().getSelectedItem();
		if ("<Tous>".equals(category)) {
			category = null;
		}
		String singer = chanteurFilter.getSelectionModel().getSelectedItem();
		if ("<Tous>".equals(singer)) {
			singer = null;
		}
		String year = anneeFilter.getSelectionModel().getSelectedItem();
		Integer yearInt = (year == null || year.equals("<Tous>")) ? null : Integer.parseInt(year);
		List<Album> theAlbums = service.findAlbums(yearInt, category, singer);

		for (Album album : theAlbums) {
			dataSeries1.getData().add(new XYChart.Data(album.getTitle(), (int)(Math.random()*200)));
		}
		barChart.getData().add(dataSeries1);
	}
	
	
	
	
	

}
