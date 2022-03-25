/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentiment_analyzer;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.skins.KpiSkin;
import eu.hansolo.medusa.skins.ModernSkin;
import eu.hansolo.medusa.skins.SimpleDigitalSkin;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.ml.AbstractTrainer;
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import org.apache.commons.lang3.ObjectUtils;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.util.Callback;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author HP - PC
 */
public class Sentiment_Analyzer extends Application {
    
    Label help = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
    Label about = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");       
    ImageView imageview;
    Image image2;
    Label output = new Label();
    Label test = new Label("");
    Label test2 = new Label("");
    VBox vb3 = new VBox();
    HBox hb3 = new HBox();
    VBox vb32 = new VBox();
    Button Analysis = new Button("Analysis");
    GridPane gridpane5 = new GridPane();
    GridPane gridpane6 = new GridPane();
    Button Submit = new Button("Quick Analysis");
    Button Submit3 = new Button("Quick Analysis");
    Button Goer = new Button("GO");
    Button Goer2 = new Button("GO");
    Button csvfile = new Button("External file");
    Button comp = new Button("Compare");
    //Button Submit3 = new Button("Quick Analysis");
    ConfigurationBuilder cb = new ConfigurationBuilder();
    twitter4j.Twitter twitter;
    TextField keyword = new TextField();
    TextField tweetno = new TextField();
    //TextField csvfile = new TextField();
    TextField keyword2 = new TextField();
    TextField tweetno2 = new TextField();
    DatePicker d = new DatePicker();
    DatePicker f = new DatePicker();
    BarChart<String, Number> barChart;
    BarChart<String, Number> barChart2;
    VBox vb12 = new VBox();
    int counter = 0;
            Double Po= 0.0;
            Double Ne= 0.0;
            Double Nu= 0.0;
    Label scores = new Label("");
    Gauge gauge = new Gauge();
    Gauge gauge2 = new Gauge();
    File filer2;
    
    static final String jdbc_driver = "com.mysql.jdbc.Driver";
    static final String db_url = "jdbc:mysql://localhost/saproject";
    static final String db_username = "root";
    static final String db_password = "12345";
    
    //Set connection variables
    static Connection conn = null;
    static ResultSet rs = null;
    static Statement stmt = null;
    PreparedStatement ps;
    
    @Override
    public void start(Stage primaryStage) throws TwitterException {
       Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        //scene 1
        Label appname = new Label("SENTIMENT ANALYSIS APP");
        Button signin = new Button("Sign In");
        Button register = new Button("Register");
        GridPane gridpane = new GridPane();
        gridpane.add(appname, 0,0);
        gridpane.add(signin, 0,1);
        gridpane.add(register, 0,2);
       // register.setAlignment(Pos.CENTER);
       // signin.setAlignment(Pos.CENTER);
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setMargin(appname,new Insets(-70,0,50,0));
        gridpane.setMargin(signin,new Insets(0,0,20,100));
        gridpane.setMargin( register,new Insets(0,0,0,100));
        //signin.setStyle("-fx-background-color: white;-fx-text-fill: black; -fx-font-size: 10pt; -fx-border-color: #8080aa; -fx-border-style: solid"
          //      + "; -fx-border-radius: 10pt");
        signin.setPrefWidth(150);
        signin.getStyleClass().add("custom-menu-button1");
        register.getStyleClass().add("custom-menu-button2");
        //register.setStyle("-fx-background-color: #6060ee; -fx-background-radius: 10pt;-fx-text-fill: white;-fx-font-weight: bold; -fx-font-size: 10pt; -fx-border-color: blue; -fx-border-style: solid"
          //      + "; -fx-border-radius: 10pt");
        register .setPrefWidth(150);
        gridpane.setPrefWidth(600);
        gridpane.setPrefHeight(400);
        gridpane.setStyle("-fx-background-color: white");
        appname.setStyle("-fx-font-size: 20pt; -fx-font-family: arial; -fx-text-fill:blue ");
        
        //scene 2
        Label signin2 = new Label("Sign In");
        TextField username = new TextField("");
        username.setPromptText("Enter your username");
        PasswordField password = new PasswordField();
        //passwordField.setPromptText("Your password");
        //TextField password = new TextField("");
        password.setPromptText("Enter your password");
        Button signin3 = new Button("Sign In");
        Button reg = new Button("Register");
        Button back = new Button("");
        GridPane gridpane2 = new GridPane();
        gridpane2.add(signin2, 1,0);
        gridpane2.add(username, 1,1);   
        gridpane2.add(password, 1,2);
        gridpane2.add(signin3, 1,3);
        gridpane2.add(reg, 1,3);
        //gridpane2.add(back, 0,4);
        gridpane2.setMargin(signin2,new Insets(60,0,30,130));
        gridpane2.setMargin(username,new Insets(0,0,15,130));
        gridpane2.setMargin(password,new Insets(0,0,20,130));
        gridpane2.setMargin(signin3,new Insets(0,0,0,300));
        gridpane2.setMargin(reg,new Insets(0,0,0,130));
        //gridpane2.setMargin(back,new Insets(70,100,0,0));
        //gridpane2.setAlignment(Pos.CENTER);
        //signin3.setAlignment(Pos.BOTTOM_RIGHT);
        //gridpane2.setPrefWidth(500);
        //gridpane2.setPrefHeight(400);
        gridpane2.setStyle("-fx-background-color: white");
        username.setPrefHeight(33);
        password.setPrefHeight(33);
        username.setPrefWidth(200);
        password.setPrefWidth(200);
        signin2.setStyle("-fx-font-size: 20pt; -fx-font-family: arial; -fx-text-fill:black; -fx-font-weight: bold");
        signin3.setPrefWidth(70);
        signin3.setPrefHeight(33);
        username.getStyleClass().add("textfield1");
        password.getStyleClass().add("textfield1");
        signin3.setStyle("-fx-font-size: 10pt;-fx-font-weight: bold;-fx-background-color: #eeeeee; -fx-text-fill: #858585 ;\n" +
"    -fx-background-radius: 5pt;\n" +
"    -fx-border-radius: 5pt;"); 
        reg.getStyleClass().add("button3");
        back.getStyleClass().add("button2");
        HBox hb1 = new HBox();
        //back.setAlignment(Pos.BOTTOM_LEFT);
        hb1.setMargin(back,new Insets(0,0,0,10));
        hb1.getChildren().addAll(back,gridpane2);
        hb1.setPrefWidth(600);
        hb1.setPrefHeight(400);
        hb1.setStyle("-fx-background-color: white");
        
        //scene 3
        Label signin4 = new Label("Register");
        TextField username2 = new TextField("");
        username2.setPromptText("Enter your username");
        TextField email = new TextField("");
        email.setPromptText("Enter your email address");
        PasswordField password2 = new PasswordField();
        //passwordField.setPromptText("Your password");
        //TextField password = new TextField("");
        password2.setPromptText("Enter your password");
        Button signin5 = new Button("Sign In");
        Button reg2 = new Button("Register");
        Button back2 = new Button("");
        Button upload = new Button("Upload Image");
        GridPane gridpane3 = new GridPane();
        gridpane3.add(signin4, 1,0);
        gridpane3.add(username2, 1,1);   
        gridpane3.add(password2, 1,2);
        gridpane3.add(email, 1,3);
        gridpane3.add(upload, 1,4);
        gridpane3.add(output, 1,4);
        gridpane3.add(signin5, 1,5);
        gridpane3.add(reg2, 1,5);
        //gridpane2.add(back, 0,4);
        gridpane3.setMargin(signin4,new Insets(60,0,30,130));
        gridpane3.setMargin(username2,new Insets(0,0,15,130));
        gridpane3.setMargin(password2,new Insets(0,0,15,130));
        gridpane3.setMargin(reg2,new Insets(0,0,0,300));
        gridpane3.setMargin(email,new Insets(0,0,15,130));
        gridpane3.setMargin(upload,new Insets(0,0,20,130));
        gridpane3.setMargin(output,new Insets(0,0,20,250));
        gridpane3.setMargin(signin5,new Insets(0,0,0,130));
        //gridpane2.setMargin(back,new Insets(70,100,0,0));
        //gridpane2.setAlignment(Pos.CENTER);
        //signin3.setAlignment(Pos.BOTTOM_RIGHT);
        //gridpane2.setPrefWidth(500);
        //gridpane2.setPrefHeight(400);
        gridpane3.setStyle("-fx-background-color: white");
        username2.setPrefHeight(33);
        password2.setPrefHeight(33);
        email.setPrefHeight(33);
        username2.setPrefWidth(200);
        password2.setPrefWidth(200);
        signin4.setStyle("-fx-font-size: 20pt; -fx-font-family: arial; -fx-text-fill:black; -fx-font-weight: bold");
        signin5.setPrefWidth(70);
        signin5.setPrefHeight(33);
        username2.getStyleClass().add("textfield1");
        password2.getStyleClass().add("textfield1");
        email.getStyleClass().add("textfield1");
        signin5.setStyle("-fx-font-size: 10pt;-fx-font-weight: bold;-fx-background-color: #eeeeee; -fx-text-fill: #858585 ;\n" +
"    -fx-background-radius: 5pt;\n" +
"    -fx-border-radius: 5pt;"); 
        reg2.getStyleClass().add("button3");
        back2.getStyleClass().add("button2");
        HBox hb2 = new HBox();
        //back.setAlignment(Pos.BOTTOM_LEFT);
        hb2.setMargin(back2,new Insets(0,0,0,10));
        hb2.getChildren().addAll(back2,gridpane3);
        hb2.setPrefWidth(600);
        hb2.setPrefHeight(400);
        hb2.setStyle("-fx-background-color: white");
        
        //scene 4
        GridPane gridpane4 = new GridPane();
        Image image2 = new Image("file:simi.jpg");
        ImageView imageview2 = new ImageView();
        imageview2.setImage(image2);
        imageview2.setFitHeight(70);
        imageview2.setFitWidth(70);
        imageview2.setStyle("-fx-border-radius: 100pt; -fx-background-radius: 100pt;");               
        VBox vb2 = new VBox();
        VBox vb = new VBox();
        HBox hb4 = new HBox();
        vb.getChildren().addAll(vb2, hb4, gridpane4);
        Button Dashboard = new Button("Dashboard");
        
        Button History = new Button("History");
        Button Logout = new Button("Logout");
        Button About = new Button("About");
        Button Help = new Button("Help");
        gridpane4.add(Dashboard, 0,1);
        gridpane4.add(Analysis, 0,2);
        gridpane4.add(History, 0,3);   
        gridpane4.add(Logout, 0,6);
        gridpane4.add(About, 0,5);
        gridpane4.add(Help, 0,4);
        Analysis.setPrefWidth(300);
        Analysis.setPrefHeight(30);
        Analysis.getStyleClass().add("sidebar");
        Dashboard.setPrefWidth(300);
        Dashboard.setPrefHeight(30);
        Dashboard.getStyleClass().add("sidebar");
        History.setPrefWidth(300);
        History.setPrefHeight(30);
        History.getStyleClass().add("sidebar");
        About.setPrefWidth(300);
        About.setPrefHeight(30);
        About.getStyleClass().add("sidebar");
        Help.setPrefWidth(300);
        Help.setPrefHeight(30);
        Help.getStyleClass().add("sidebar");
        Logout.setPrefWidth(300);
        Logout.setPrefHeight(30);
        Logout.getStyleClass().add("sidebar");
        Label app = new Label("SENTIMENT ANALYSIS APP");
        app.setStyle("-fx-text-fill: #ffffff;");
        Label info = new Label("Simi Olowolafe");
        info.setStyle("-fx-text-fill: #ffffff;");
        vb2.getChildren().addAll(app);       
        hb4.getChildren().addAll(imageview2, info);        
        HBox hb3 = new HBox();
        hb3.getChildren().addAll(vb, vb3);
        vb3.getChildren().addAll(test);
        hb3.setPrefWidth(width);
        hb3.setPrefHeight(height);
        hb3.setStyle("-fx-background-color: white; ");        
        vb2.setStyle("-fx-background-color: #000033;");
        hb4.setStyle("-fx-background-color: #000033;");
        //gridpane4.setHgap(15);
        //  gridpane4.setVgap(12);
        //vb3.setStyle("-fx-background-color: #dddddd");
        vb.setPrefWidth(300);
        vb2.setPrefWidth(300);
        vb2.setPrefHeight(50);
        vb2.setAlignment(Pos.CENTER);
        hb4.setAlignment(Pos.CENTER);
        gridpane4.setMargin(Dashboard,new Insets(0,0,0,0));
        gridpane4.setMargin(Analysis,new Insets(0,0,0,0));
        gridpane4.setMargin(History,new Insets(0,0,0,0));
        gridpane4.setMargin(Logout,new Insets(0,0,0,0));
        gridpane4.setMargin(About,new Insets(0,0,0,0));
        gridpane4.setMargin(Help,new Insets(0,0,0,0));
        //Dashboard.setStyle("-fx-background-color: #000033; -fx-font-size: 12pt; -fx-text-fill: #ffffff;");
        //Analysis.setStyle("-fx-background-color: #000033; -fx-font-size: 12pt; -fx-text-fill: #ffffff;");
        //History.setStyle("-fx-background-color: #000033;; -fx-font-size: 12pt; -fx-text-fill: #ffffff;");
        //Logout.setStyle("-fx-background-color: #000033; -fx-font-size: 12pt; -fx-text-fill: #ffffff;");
        //About.setStyle("-fx-background-color: #000033; -fx-font-size: 12pt; -fx-text-fill: #ffffff;");
        //Help.setStyle("-fx-background-color: #000033; -fx-font-size: 12pt; -fx-text-fill: #ffffff;");
        //gridpane4.setStyle("-fx-border-style: solid inside; -fx-border-color: black;");
        hb4.setMargin(imageview2,new Insets(10,10,10,10));
        vb.setPrefHeight(height);
        vb.setStyle("-fx-background-color: #000033;");    
          //Instantiating the Shadow class 
        DropShadow dropShadow = new DropShadow();
        
        

        //setting the type of blur for the shadow 
        //dropShadow.setBlurType(BlurType.ONE_PASS_BOX); 

        //Setting color for the shadow 
        //dropShadow.setColor(Color.ROSYBROWN); 

        //Setting the height of the shadow
        dropShadow.setHeight(0.3); 

        //Setting the width of the shadow 
        dropShadow.setWidth(0.3); 

        //Setting the radius of the shadow 
        dropShadow.setRadius(15); 

        //setting the offset of the shadow 
        dropShadow.setOffsetX(0.3); 
        dropShadow.setOffsetY(0.3); 

        //Setting the spread of the shadow 
        //dropShadow.setSpread(12);  

        //Applying shadow effect to the text 
        //text.setEffect(dropShadow);      

        //Applying shadow effect to the circle 
        vb.setEffect(dropShadow); 
        
        //analysis
        
        Submit.getStyleClass().add("button3");
        Goer.getStyleClass().add("button3");
        Goer2.getStyleClass().add("button3");
        Submit3.getStyleClass().add("button3");
        comp.getStyleClass().add("button3");
        Button Submit2 = new Button("Submit");
        Submit2.getStyleClass().add("button3");
        tweetno.getStyleClass().add("textfield1");
        csvfile.getStyleClass().add("button3");
        keyword.getStyleClass().add("textfield1");
        tweetno2.getStyleClass().add("textfield1");
        keyword2.getStyleClass().add("textfield1");
        gridpane5.add(Goer, 3,1);
        
        gridpane5.add(keyword, 0,0);
        //gridpane5.add(d, 1,0);
        //gridpane5.add(f, 2,0);
        gridpane5.add(tweetno, 3,0);
        gridpane5.add(csvfile, 0,1);
        gridpane5.add(d, 4,1);
        gridpane5.add(f, 5,1);        
        gridpane5.add(comp, 5,0);
        gridpane5.add(Submit, 4,0);
        d.setPrefWidth(100);
        f.setPrefWidth(100);
        tweetno.setPromptText("Enter number of tweets");
        keyword.setPromptText("Enter keyword");
        tweetno2.setPromptText("Enter number of tweets");
        keyword2.setPromptText("Enter keyword");
        d.setPromptText("Enter startdate");
        f.setPromptText("Enter enddate");
        gridpane5.setMargin(keyword,new Insets(50,10,0,50));
        //gridpane5.setMargin(d,new Insets(100,20,0,0));
        //gridpane5.setMargin(f,new Insets(100,20,0,0));
        gridpane5.setMargin(tweetno,new Insets(50,10,0,0));
        gridpane5.setMargin(Submit,new Insets(50,0,0,0));
        gridpane5.setMargin(comp,new Insets(50,0,0,20));
        gridpane5.setMargin(d,new Insets(20,0,0,0));
        gridpane5.setMargin(f,new Insets(20,0,0,0));
        gridpane5.setMargin(csvfile,new Insets(20,0,0,50));
        gridpane5.setMargin(Goer,new Insets(20,0,0,0));
        
        
        
        /*Twitter twitter = TwitterFactory.getSingleton();
        ResponseList<twitter4j.Status> statuses = twitter.getHomeTimeline();
        System.out.println("Showing home timeline.");
        for (twitter4j.Status status : statuses) {
        System.out.println(status.getUser().getName() + ":" +
                           status.getText());
        }*/
        
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("7pYMLN0lDw17VihlgcQMpu1BD");
        cb.setOAuthConsumerSecret("rJVCVApNz3wB7mMmnRTZ55LVtVFZe91lGJFpAQgtzso5wMLEE2");
        cb.setOAuthAccessToken("1169884007237971968-I7N0wploIgWJaDr5sFeaKcoXwTtpBH");
        cb.setOAuthAccessTokenSecret("uHvRimdlOdRHCLgyZ3GqnTVMdsHGlbS6YP7ElMaLKJV5R");
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        
        //List<twitter4j.Status> status = twitter.getHomeTimeline();
        /*for(twitter4j.Status st : status){
            System.out.println(st.getUser().getName()+"========"+st.getText());
        }*/
        
        
        //System.out.println(Count);
        //System.exit(0);
        
        //Defining the axes              
      
      
      
              
      //Setting the data to bar chart       
     
        
      //Creating a Group object 
      //Group root = new Group(barChart);
        
      //Creating a scene object
      //Scene scene = new Scene(root, 600, 400);

      //Setting title to the Stage
      //stage.setTitle("Bar Chart");
        
      //Adding scene to the stage
      //stage.setScene(scene);
        
      //Displaying the contents of the stage
      //stage.show();        
        
        //getFMeasure();        
        
        Scene scene = new Scene(gridpane, 600, 400);
        Scene scene2 = new Scene(hb1, 600, 400);
        Scene scene3 = new Scene(hb2, 600, 400);
        Scene scene4 = new Scene(hb3, width, height);
        
        EventHandler<ActionEvent> Signin = new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
           try{
            Class.forName(jdbc_driver);
            //System.out.println("Connecting to database...");
            //open connection to database
            
            conn = DriverManager.getConnection(db_url, db_username, db_password);
            //System.out.println("Connected to database");
            
            stmt = conn.createStatement();
            //Welcome.setText("WELCOME!! "+username.getText());
           if(username.getText().equals("") || password.getText().equals("") ){
               System.out.println("Information incorrect");
               Alert alert = new Alert(Alert.AlertType.NONE);
        
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Please fill out the form");
            alert.show();
           }else{
           String sql2 = "SELECT * FROM logdetails WHERE username='"+username.getText()+"' AND password='"+password.getText()+"'";
           // String sql2 = "SELECT username FROM data";
           ResultSet rs = conn.createStatement().executeQuery(sql2);
            
            if(rs.next()){
                System.out.println("Information correct");
                 //a = rs.getString("username"); 
                primaryStage.setScene(scene4);  
            }
            
            else{
                System.out.println("Information incorrect");
                Alert alert = new Alert(Alert.AlertType.NONE);        
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Invalid information!! Please check and try again");
            alert.show();
            }
            
            //String sql1 = "INSERT INTO data (username, password, email)" + "VALUES ('" + a + "'"+ ",'" + b
              //      + "'" + ",'" + c + "'"+ ")";
            
            //stmt.executeUpdate(sql1);
            //System.out.println("Successfully inserted into the database");        
                
        }}catch(Exception e){
            
            e.printStackTrace();
            
        }
          
          //Signin(username.getText(),password.getText());
              
      }
    };
      
        EventHandler<ActionEvent> insertIntoDatabase = new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
           try{
            Class.forName(jdbc_driver);
            System.out.println("Connecting to database...");
            //open connection to database
            
            conn = DriverManager.getConnection(db_url, db_username, db_password);
            System.out.println("Connected to database");
            
            stmt = conn.createStatement();
            
            String sql1 = "INSERT INTO logdetails (username, password, email)" + "VALUES ('" + username2.getText() + "'"+ ",'" + password2.getText()
                    + "'" + ",'" + email.getText() + "'"+ ")";
            
            if(username2.getText().equals("") || password2.getText().equals("") || email.getText().equals("")){
                System.out.println("Information incorrect");
               Alert alert = new Alert(Alert.AlertType.NONE);        
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Please fill out the form");
            alert.show();
            }
            else{
            stmt.executeUpdate(sql1);
            primaryStage.setScene(scene4); 
            System.out.println("Successfully inserted into the database");   
            Alert alert = new Alert(Alert.AlertType.NONE);        
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("You have been successfully registered. Please sign in");
            alert.show();
            }
                
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
          
          //Signin(username.getText(),password.getText());
              
      }
    };
        
        signin.setOnAction(e-> primaryStage.setScene(scene2));
        signin3.setOnAction(Signin);
        Dashboard.setOnAction(dashboardEventListener);
        signin5.setOnAction(e-> primaryStage.setScene(scene2));
        register.setOnAction(e-> primaryStage.setScene(scene3));
        reg.setOnAction(e-> primaryStage.setScene(scene3));
        back.setOnAction(e-> primaryStage.setScene(scene));
        back2.setOnAction(e-> primaryStage.setScene(scene));
        upload.setOnAction(uploadEventListener);
        Analysis.setOnAction(analysisEventListener);
        comp.setOnAction(compEventListener);
        Goer.setOnAction(csvEventListener);
        Goer2.setOnAction(csvEventListener2);
        csvfile.setOnAction(csvfile1);
        reg2.setOnAction(insertIntoDatabase);
        
        Submit.setOnAction(Tweetget);
        Submit3.setOnAction(Tweetget2);
        Logout.setOnAction(e-> primaryStage.setScene(scene));
        //Submit2.setOnAction(clickShow);
        //Submit2.setOnAction(Tweetget);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());                
        scene2.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene3.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene4.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        keyword.setOnAction(focusshifta);
        
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        //scene.setStyle("-fx-background-color: white");
        primaryStage.setTitle("Sentiment Analysis");
        primaryStage.getIcons().add(new Image("file:340.png"));
        primaryStage.setScene(scene4);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    EventHandler<ActionEvent> uploadEventListener = new EventHandler<ActionEvent>(){
            
        @Override
        public void handle(ActionEvent t){
            
            FileChooser file = new FileChooser();
            file.setInitialDirectory(new File("C:/Users/HP - PC/Pictures"));
            File filer = file.showOpenDialog(null);
            
            try{
                BufferedImage bufferedImage = ImageIO.read(filer);
                image2 = SwingFXUtils.toFXImage(bufferedImage, null);
                //imageview.setImage(image2);
                output.setText("Image Uploaded");
                
            }catch(IOException ex){
                System.out.println("Error in image upload");
            }
        }
        
        };;
    EventHandler<ActionEvent> csvfile1 = new EventHandler<ActionEvent>(){
            
        @Override
        public void handle(ActionEvent t){
            
            FileChooser file = new FileChooser();
            file.setInitialDirectory(new File("C:/Users/HP - PC"));
            filer2 = file.showOpenDialog(null);
            
           
        }
        
        };;
   
    EventHandler<ActionEvent> csvEventListener = new EventHandler<ActionEvent>(){
            
        @Override
        public void handle(ActionEvent t){
            
            double score=0.0; 
            
            gridpane6.getChildren().remove(barChart);
            gridpane6.getChildren().remove(scores);
            gridpane6.getChildren().remove(gauge);
            
            try {
                    
                    
                   Reader reader = Files.newBufferedReader(Paths.get(filer2.toString()));
                   CSVReader csvReader = new CSVReader(reader);
                   //CSVReader reader = new CSVReader(new FileReader("C:\\Users\\HP - PC\\Documents\\project\\csv\\eilish.csv"));

            String[] record;
             System.out.println("THE FOLLOWING STEP IS FEATURE EXTRACTION (which includes the removal of urls, uppercasing, special characters, user mentions and retweets.) :======>");
            System.out.println("THE RESULT :======>");
             String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\extcssprocessed.csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            //String [] record2 = null;
            //writer.writeNext(record2);
        while ((record = csvReader.readNext()) != null) {
            //System.out.println("Username: " + record[0]);
            //System.out.println("Tweet: " + record[1]);

            //Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
            //String tokens[] = tokenizer.tokenize(record[1]);

            //System.out.println("----------------");
            //for(int i=0;i<tokens.length;i++){
            //    System.out.println(tokens[i]);
            //}
            String h = record[0];
            String a = record[0];
            String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(a);
            int i = 0;
            while (m.find()) {
                a = a.replaceAll(m.group(i)," ").trim();
                i++;
            }
            String f = a.replaceAll("(@[^\\s-]+)"," ").trim();
            String d = f.replaceAll("[^\\p{Alpha} ]"," ").trim();
            String c = d.replaceAll("(?<!\\w)"," ").trim();
            
            //String str = (c.toLowerCase());
            List<String> allWords = new ArrayList<>(Arrays.asList(c.toLowerCase().split(" ")));
            List<String> stopWords = Arrays.asList("a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero");
            allWords.removeAll(stopWords);
            String str = String.join(" ", allWords);
            System.out.println("INPUT: "+h);
            System.out.println("RESULT: "+str);
            writer.writeNext(new String[]{str});
        }

    // close readers
    csvReader.close();
    reader.close();
     writer.close();
    
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        try { 

                   Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\extcssprocessed.csv"));
                   CSVReader csvReader = new CSVReader(reader);
                   String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\extcssprocessedtokens.csv";
                    CSVWriter writer = new CSVWriter(new FileWriter(csv));
                   //CSVReader reader = new CSVReader(new FileReader("C:\\Users\\HP - PC\\Documents\\project\\csv\\eilish.csv"));

            String[] record;
            String tokens[];
            System.out.println();
            System.out.println("THE FINAL STEP IN FEATURE EXTRACTION IS THE TOKENIZATION :======>");
            System.out.println("THE RESULT :======>");
            while ((record = csvReader.readNext()) != null) {
        //System.out.println("Username: " + record[0]);
        //System.out.println("Tweet: " + record[1]);
        
        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
        tokens = tokenizer.tokenize(record[0]);
        
        System.out.println("----------------");
        for(int i=0;i<tokens.length;i++){
            System.out.println(tokens[i]);
            writer.writeNext(new String[]{tokens[i]});
        }
        
    }
   // System.out.println("TOken:" + tokens[23]);
    // close readers
    csvReader.close();
    reader.close();
    writer.close();
    
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {
            // read the training data
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("C:\\Users\\HP - PC\\Documents\\project\\csv\\Custom300.train"));
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);
 
            
            
            // define the training parameters
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 10+"");
            params.put(TrainingParameters.CUTOFF_PARAM, 0+"");
            params.put(AbstractTrainer.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
 
            // create a model from traning data
            DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
            //DoccatModel model = new DoccatModel(new File("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            System.out.println("\nModel is successfully trained.");
 
            // save the model to local
            BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            //BufferedInputStream modelOut = new BufferedInputStream(new FileInputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            model.serialize(modelOut);
            System.out.println("\nTrained Model is saved locally at : "+"model"+File.separator+"en-movie-classifier-naive-bayes.bin");
            
            //Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\Custom.csv"),"utf-8");
            Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\extcssprocessed.csv"),"utf-8"));
            //Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\extcssprocessed.csv"));
            CSVReader csvReader = new CSVReader(reader);
            String[] record;
            
            DocumentCategorizer doccat = new DocumentCategorizerME(model);
            System.out.println();
            //System.out.println("A DATASET IS THEN CREATED WITH REVIEWS LABELLED POSITIVE, NEGATIVE AND NEUTRAL");
            //System.out.println();
            //System.out.println("THE MACHINE LEARNING ALGORITHM NAIVE BAYES IS THEN IMPLEMENTED AND TRAINED USING THE DATASET");
            //System.out.println();
            System.out.println("THE NEXT STEP IS THE DOCUMENT CLASSIFICATION which classifies the text into positive, negative or neutral");
            System.out.println();
            System.out.println("THIS IS DONE USING THE MACHINE LEARNING ALGORITHM NAIVE BAYES WITH A TRAINED MODEL");
            System.out.println();
            System.out.println("THE FINAL RESULT :======>");
            while ((record = csvReader.readNext()) != null) {
                String[] docWords = record[0].replaceAll("[^A-Za-z]", " ").split(" ");
                double[] aProbs = doccat.categorize(docWords);
                //record[1] = "balls";4
                System.out.println();
                System.out.println("THE ORIGINAL TWEET: " +record[0]);
                for(int i=0;i<doccat.getNumberOfCategories();i++){                   
                               
            }   
                System.out.println();
                System.out.println("THE PREDICTION :======>");
                System.out.println(doccat.getBestCategory(aProbs));
                String a = doccat.getBestCategory(aProbs);
                if (a.contains("Positive")){
                    Po++;
                }else if (a.contains("Negative")){
                    Ne++;
                }else{
                    Nu++;
                }
                counter++;
             };
             //System.out.println(Po);
             //System.out.println(Ne);
             //System.out.println(Nu);
             //System.out.println(counter);
             CategoryAxis xAxis = new CategoryAxis();  
                xAxis.setCategories(FXCollections.<String>
                observableArrayList(Arrays.asList("POSITIVE | NEUTRAL | NEGATIVE")));
                xAxis.setLabel("Sentiment Analysis");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("percentage");

                //Creating the Bar chart
                barChart = new BarChart<>(xAxis, yAxis); 
                barChart.setMaxHeight(400);
                barChart.setMaxWidth(500);
                String fileNameWithOutExt = FilenameUtils.removeExtension(filer2.getName());
                barChart.setTitle("Analysis Result of " + fileNameWithOutExt);
                

                //Prepare XYChart.Series objects by setting data  
                XYChart.Series<String, Number> series1 = new XYChart.Series<>();
                series1.setName("Negative");
                //series1.getData().getNode().setStyle("-fx-bar-fill: blue;");
                series1.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Ne/counter)*100));

                XYChart.Series<String, Number> series2 = new XYChart.Series<>();
                series2.setName("Neutral");
                series2.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Nu/counter)*100));

                XYChart.Series<String, Number> series3 = new XYChart.Series<>();
                series3.setName("Positive");
                series3.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Po/counter)*100));
                System.out.println();
                System.out.println("THE AGGREGRATE SCORE OF ALL REVIEWS IS THEN CALCULATED AND REPRESENTED IN A CHART");
                System.out.println();
                System.out.println("THE PERCENTAGE DISTRIBUTION OF ALL REVIEWS :========>");
                System.out.println("POSITIVE: " + (Po/counter)*100 + "% NEGATIVE: " + (Ne/counter)*100 + "% NEUTRAL: " + (Nu/counter)*100 + "%");
            // test the model file by subjecting it tso prediction
                               
                score = Math.round((((Po*5)+(Nu*2.5)+(Ne*0))/counter) * 100.0) / 100.0;
                scores.setText("Average score: "+ (score)+"/5");
                scores.setStyle("-fx-font-size: 20pt; -fx-font-family: arial; -fx-text-fill:blue ");
                //Label.setText();
                 barChart.getData().addAll(series3, series2, series1);
                  barChart.lookupAll(".default-color0.chart-bar")
            .forEach(n -> n.setStyle("-fx-bar-fill: green;"));
                  // barChart.lookupAll(".default-color1.chart-bar")
            //.forEach(n -> n.setStyle("-fx-bar-fill: blue;"));
                    barChart.lookupAll(".default-color2.chart-bar")
            .forEach(n -> n.setStyle("-fx-bar-fill: red;"));
                     barChart.setLegendVisible(false);
                   Po= 0.0;
                Ne= 0.0;
                Nu= 0.0;
                counter = 0;
            
                
            
            
            //String a = "very good, i love, is a brilliant intellectual";
            
            
            for(int i=0; i<5; i++){
                
            }
               //Gauge gauge = new Gauge();
               
               //gauge = GaugeBuilder.maxValue(100);
               gauge.setSkin(new SimpleDigitalSkin(gauge));  //ModernSkin : you guys can change the skin
               gauge.setMaxSize(150.0, 150.0);
         gauge.setTitle("Average Score");  //title
         gauge.setUnit("/5");  //unit
         gauge.setUnitColor(Color.WHITE);
         gauge.setDecimals(2); 
         gauge.setValue(score); //deafult position of needle on gauage
         gauge.setAnimated(true);
         //gauge.setAnimationDuration(1000); 

         gauge.setValueColor(Color.BLUE); 
         gauge.setTitleColor(Color.BLUE); 
         gauge.setSubTitleColor(Color.BLUE); 
         gauge.setBarColor(Color.rgb(0, 214, 215)); 
         gauge.setNeedleColor(Color.RED); 
         gauge.setThresholdColor(Color.RED);  //color will become red if it crosses threshold value
         gauge.setThreshold(85);
         gauge.setThresholdVisible(true);
         gauge.setTickLabelColor(Color.rgb(151, 151, 151)); 
         gauge.setTickMarkColor(Color.BLUE); 
         gauge.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);
         gauge.setMaxValue(5.00);
            // print the probabilities of the categories
            //System.out.println("\n---------------------------------\nCategory : Probability\n---------------------------------");
            
            //System.out.println("---------------------------------");
 
            //System.out.println("\n"+doccat.getBestCategory(aProbs)+" : is the predicted category for the given sentence.");
            //vb3.getChildren().clear();
            //gridpane6.getChildren().remove(barChart);
            //gridpane6.getChildren().addAll(barChart); 
            gridpane6.add(barChart, 0,0);
            //gridpane6.add(scores, 0,1);
            gridpane6.add(gauge, 0,1);
            //vb3.getChildren().addAll(gridpane6); 
            gridpane6.setMargin(barChart,new Insets(10,0,0,0));
            gridpane6.setMargin(gauge,new Insets(20,0,0,200));
            //gridpane6.setMargin(scores,new Insets(50,0,0,150));
}
        catch (IOException e) {
            System.out.println("An exception in reading the training file. Please check.");
            e.printStackTrace();
}           catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        };;
        EventHandler<ActionEvent> csvEventListener2 = new EventHandler<ActionEvent>(){
            
        @Override
        public void handle(ActionEvent t){
            
            double score=0.0; 
            
            gridpane6.getChildren().remove(barChart2);
            gridpane6.getChildren().remove(scores);
            gridpane6.getChildren().remove(gauge2);
            
            try {
                
                   Reader reader = Files.newBufferedReader(Paths.get(filer2.toString()));
                   CSVReader csvReader = new CSVReader(reader);
                   //CSVReader reader = new CSVReader(new FileReader("C:\\Users\\HP - PC\\Documents\\project\\csv\\eilish.csv"));

            String[] record;
             System.out.println("THE FOLLOWING STEP IS FEATURE EXTRACTION (which includes the removal of urls, uppercasing, special characters, user mentions and retweets.) :======>");
            System.out.println("THE RESULT :======>");
             String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\extcssprocessed.csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            //String [] record2 = null;
            //writer.writeNext(record2);
        while ((record = csvReader.readNext()) != null) {
            //System.out.println("Username: " + record[0]);
            //System.out.println("Tweet: " + record[1]);

            //Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
            //String tokens[] = tokenizer.tokenize(record[1]);

            //System.out.println("----------------");
            //for(int i=0;i<tokens.length;i++){
            //    System.out.println(tokens[i]);
            //}
            String h = record[0];
            String a = record[0];
            String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(a);
            int i = 0;
            while (m.find()) {
                a = a.replaceAll(m.group(i)," ").trim();
                i++;
            }
            String f = a.replaceAll("(@[^\\s-]+)"," ").trim();
            String d = f.replaceAll("[^\\p{Alpha} ]"," ").trim();
            String c = d.replaceAll("(?<!\\w)"," ").trim();
            List<String> allWords = new ArrayList<>(Arrays.asList(c.toLowerCase().split(" ")));
            List<String> stopWords = Arrays.asList("a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero");
            allWords.removeAll(stopWords);
            String str = String.join(" ", allWords);
            System.out.println("INPUT: "+h);
            System.out.println("RESULT: "+str);
            writer.writeNext(new String[]{str});
        }

    // close readers
    csvReader.close();
    reader.close();
     writer.close();
    
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        try { 

                   Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\extcssprocessed.csv"));
                   CSVReader csvReader = new CSVReader(reader);
                   String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\extcssprocessedtokens.csv";
                    CSVWriter writer = new CSVWriter(new FileWriter(csv));
                   //CSVReader reader = new CSVReader(new FileReader("C:\\Users\\HP - PC\\Documents\\project\\csv\\eilish.csv"));

            String[] record;
            String tokens[];
            System.out.println();
            System.out.println("THE FINAL STEP IN FEATURE EXTRACTION IS THE TOKENIZATION :======>");
            System.out.println("THE RESULT :======>");
            while ((record = csvReader.readNext()) != null) {
        //System.out.println("Username: " + record[0]);
        //System.out.println("Tweet: " + record[1]);
        
        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
        tokens = tokenizer.tokenize(record[0]);
        
        System.out.println("----------------");
        for(int i=0;i<tokens.length;i++){
            System.out.println(tokens[i]);
            writer.writeNext(new String[]{tokens[i]});
        }
        
    }
   // System.out.println("TOken:" + tokens[23]);
    // close readers
    csvReader.close();
    reader.close();
    writer.close();
    
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {
            // read the training data
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("C:\\Users\\HP - PC\\Documents\\project\\csv\\Custom300.train"));
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);
 
            
            
            // define the training parameters
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 10+"");
            params.put(TrainingParameters.CUTOFF_PARAM, 0+"");
            params.put(AbstractTrainer.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
 
            // create a model from traning data
            DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
            //DoccatModel model = new DoccatModel(new File("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            System.out.println("\nModel is successfully trained.");
 
            // save the model to local
            BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            //BufferedInputStream modelOut = new BufferedInputStream(new FileInputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            model.serialize(modelOut);
            System.out.println("\nTrained Model is saved locally at : "+"model"+File.separator+"en-movie-classifier-naive-bayes.bin");
            
            //Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\Custom.csv"),"utf-8");
            Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\extcssprocessed.csv"),"utf-8"));
            CSVReader csvReader = new CSVReader(reader);
            String[] record;
            
            DocumentCategorizer doccat = new DocumentCategorizerME(model);
            System.out.println();
            //System.out.println("A DATASET IS THEN CREATED WITH REVIEWS LABELLED POSITIVE, NEGATIVE AND NEUTRAL");
            //System.out.println();
            //System.out.println("THE MACHINE LEARNING ALGORITHM NAIVE BAYES IS THEN IMPLEMENTED AND TRAINED USING THE DATASET");
            //System.out.println();
            System.out.println("THE NEXT STEP IS THE DOCUMENT CLASSIFICATION which classifies the text into positive, negative or neutral");
            System.out.println();
            System.out.println("THIS IS DONE USING THE MACHINE LEARNING ALGORITHM NAIVE BAYES WITH A TRAINED MODEL");
            System.out.println();
            System.out.println("THE FINAL RESULT :======>");
            while ((record = csvReader.readNext()) != null) {
                String[] docWords = record[0].replaceAll("[^A-Za-z]", " ").split(" ");
                double[] aProbs = doccat.categorize(docWords);
                //record[1] = "balls";4
                System.out.println();
                System.out.println("THE ORIGINAL TWEET: " +record[0]);
                for(int i=0;i<doccat.getNumberOfCategories();i++){                   
                               
            }   
                System.out.println();
                System.out.println("THE PREDICTION :======>");
                System.out.println(doccat.getBestCategory(aProbs));
                String a = doccat.getBestCategory(aProbs);
                if (a.contains("Positive")){
                    Po++;
                }else if (a.contains("Negative")){
                    Ne++;
                }else{
                    Nu++;
                }
                counter++;
             };
             //System.out.println(Po);
             //System.out.println(Ne);
             //System.out.println(Nu);
             //System.out.println(counter);
             CategoryAxis xAxis = new CategoryAxis();  
                xAxis.setCategories(FXCollections.<String>
                observableArrayList(Arrays.asList("POSITIVE | NEUTRAL | NEGATIVE")));
                xAxis.setLabel("Sentiment Analysis");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("percentage");

                //Creating the Bar chart
                barChart2 = new BarChart<>(xAxis, yAxis); 
                barChart2.setMaxHeight(400);
                barChart2.setMaxWidth(500);
                String fileNameWithOutExt = FilenameUtils.removeExtension(filer2.getName());
                
                barChart2.setTitle("Analysis Result of " + fileNameWithOutExt);

                //Prepare XYChart.Series objects by setting data  
                XYChart.Series<String, Number> series1 = new XYChart.Series<>();
                series1.setName("Negative");
                //series1.getData().getNode().setStyle("-fx-bar-fill: blue;");
                series1.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Ne/counter)*100));

                XYChart.Series<String, Number> series2 = new XYChart.Series<>();
                series2.setName("Neutral");
                series2.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Nu/counter)*100));

                XYChart.Series<String, Number> series3 = new XYChart.Series<>();
                series3.setName("Positive");
                series3.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Po/counter)*100));
                System.out.println();
                System.out.println("THE AGGREGRATE SCORE OF ALL REVIEWS IS THEN CALCULATED AND REPRESENTED IN A CHART");
                System.out.println();
                System.out.println("THE PERCENTAGE DISTRIBUTION OF ALL REVIEWS :========>");
                System.out.println("POSITIVE: " + (Po/counter)*100 + "% NEGATIVE: " + (Ne/counter)*100 + "% NEUTRAL: " + (Nu/counter)*100 + "%");
            // test the model file by subjecting it tso prediction
                               
                score = Math.round((((Po*5)+(Nu*2.5)+(Ne*0))/counter) * 100.0) / 100.0;
                scores.setText("Average score: "+ (score)+"/5");
                scores.setStyle("-fx-font-size: 20pt; -fx-font-family: arial; -fx-text-fill:blue ");
                //Label.setText();
                 barChart2.getData().addAll(series3, series2, series1);
                  barChart2.lookupAll(".default-color0.chart-bar")
            .forEach(n -> n.setStyle("-fx-bar-fill: green;"));
                  // barChart.lookupAll(".default-color1.chart-bar")
            //.forEach(n -> n.setStyle("-fx-bar-fill: blue;"));
                    barChart2.lookupAll(".default-color2.chart-bar")
            .forEach(n -> n.setStyle("-fx-bar-fill: red;"));
                     barChart2.setLegendVisible(false);
                   Po= 0.0;
                Ne= 0.0;
                Nu= 0.0;
                counter = 0;
            
                
            
            
            //String a = "very good, i love, is a brilliant intellectual";
            
            
            for(int i=0; i<5; i++){
                
            }
               //Gauge gauge = new Gauge();
               
               //gauge = GaugeBuilder.maxValue(100);
               gauge2.setSkin(new SimpleDigitalSkin(gauge2));  //ModernSkin : you guys can change the skin
               gauge2.setMaxSize(150.0, 150.0);
         gauge2.setTitle("Average Score");  //title
         gauge2.setUnit("/5");  //unit
         gauge2.setUnitColor(Color.WHITE);
         gauge2.setDecimals(2); 
         gauge2.setValue(score); //deafult position of needle on gauage
         gauge2.setAnimated(true);
         //gauge.setAnimationDuration(1000); 

         gauge2.setValueColor(Color.BLUE); 
         gauge2.setTitleColor(Color.BLUE); 
         gauge2.setSubTitleColor(Color.BLUE); 
         gauge2.setBarColor(Color.rgb(0, 214, 215)); 
         gauge2.setNeedleColor(Color.RED); 
         gauge2.setThresholdColor(Color.RED);  //color will become red if it crosses threshold value
         gauge2.setThreshold(85);
         gauge2.setThresholdVisible(true);
         gauge2.setTickLabelColor(Color.rgb(151, 151, 151)); 
         gauge2.setTickMarkColor(Color.BLUE); 
         gauge2.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);
         gauge2.setMaxValue(5.00);
            // print the probabilities of the categories
            //System.out.println("\n---------------------------------\nCategory : Probability\n---------------------------------");
            
            //System.out.println("---------------------------------");
 
            //System.out.println("\n"+doccat.getBestCategory(aProbs)+" : is the predicted category for the given sentence.");
            //vb3.getChildren().clear();
            //gridpane6.getChildren().remove(barChart);
            //gridpane6.getChildren().addAll(barChart); 
            gridpane6.add(barChart2, 1,0);
            //gridpane6.add(scores, 0,1);
            gridpane6.add(gauge2, 1,1);
            //vb3.getChildren().addAll(gridpane6); 
            gridpane6.setMargin(barChart2,new Insets(10,0,0,0));
            gridpane6.setMargin(gauge2,new Insets(20,0,0,200));
            //gridpane6.setMargin(scores,new Insets(50,0,0,150));
}
        catch (IOException e) {
            System.out.println("An exception in reading the training file. Please check.");
            e.printStackTrace();
}           catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        };;
    /*EventHandler<ActionEvent> logout = new EventHandler<ActionEvent>(){
            
        @Override
    public void handle(ActionEvent event) {
    
    stage.close();
}
    };;*/
   /* EventHandler<ActionEvent> clickShow = new EventHandler<ActionEvent>(){
            
        @Override
        public void handle(ActionEvent t){
            
            String[][]staffArray2 =null;
            try {
                Reader reader;
                reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\ecowas.csv"));
              CSVReader csvReader = new CSVReader(reader);
              String[] record;
              int i=0;
              
              while ((staffArray2[0][0] = csvReader.readNext()) != null) {
                  String a = record[0];
                  staffArray2[0][i] = a;
                  i++;
              }

            } catch (IOException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String[][] staffArray = {{"nice to ", "have", "titles"},
                                 {"a", "b", "c"},
                                 {"d", "e", "f"}};
             
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(staffArray2));
        data.remove(0);//remove titles from data
        TableView<String[]> table = new TableView<>();
        for (int i = 0; i < staffArray[0].length; i++) {
            TableColumn tc = new TableColumn(staffArray[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);
            table.getColumns().add(tc);
        }
        table.setItems(data);
        vb3.getChildren().clear();
        vb3.getChildren().addAll(gridpane5, table);
            
            /*Query query = new Query(keyword.getText() + " +exclude:retweets");
                QueryResult result;
                int Count=1;
                int Count2=1;
                int x = Integer.parseInt(tweetno.getText());
                query.setCount(x);
            //Reader reader;
            try {
                Reader reader;
                reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\ecowas.csv"));
                CSVReader csvReader = new CSVReader(reader);
                TableView table = new TableView();
                TableColumn columnOne = new TableColumn("Tweets");
                table.getColumns().addAll(columnOne);
                columnOne.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
                ObservableList<ObservableList> data;
                data = FXCollections.observableArrayList();
                ObservableList<String> row = FXCollections.observableArrayList();
                //columnOne.setText(STYLESHEET_MODENA);
                //columnOne.
                //String[] record;
                
                csvReader.close();
                reader.close();
                
                while (Count < 2)
        {
            try {
                
         
            result = twitter.search(query);
            List<twitter4j.Status> tweets = result.getTweets();
            for (twitter4j.Status tweet : tweets) {
              System.out.println("Tweet "+ Count2 + " @" + tweet.getUser().getScreenName() + ":" + tweet.getText());
              //writer.writeNext(new String[]{tweet.getText()});
              row.add(tweet.getText()); 
              Count2++;
            }
               
                
            } catch (TwitterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            };
            data.add(row);
            table.setItems(data);
             Count++;
             //writer.close();
        }
                table.getItems().addAll("Column one's data");
                vb3.getChildren().clear();
                vb3.getChildren().addAll(gridpane5, table);
            } catch (IOException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        };;*/
    EventHandler<ActionEvent> analysisEventListener = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
       
           vb3.getChildren().clear();; 
           vb3.getChildren().addAll(gridpane5, gridpane6); 
           //vb3.setMargin(barChart,new Insets(50,0,0,130));
           
        }
        
      
    };;
    EventHandler<ActionEvent> helpEventListener = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
           vb3.getChildren().clear(); 
           vb3.getChildren().addAll(help); 
           //vb3.setMargin(barChart,new Insets(50,0,0,130));
           
        }
        
      
    };;
    EventHandler<ActionEvent> aboutsEventListener = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
           vb3.getChildren().clear(); 
           vb3.getChildren().addAll(about); 
           //vb3.setMargin(barChart,new Insets(50,0,0,130));
           
        }
        
      
    };;
    EventHandler<ActionEvent> aboutEventListener = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
       
           vb3.getChildren().clear();
           vb3.getChildren().addAll(vb12); 
           //vb3.setMargin(barChart,new Insets(50,0,0,130));
           
        }
        
      
    };;
    EventHandler<ActionEvent> compEventListener = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
       
           //vb3.getChildren().clear();; 
           //vb3.getChildren().addAll(gridpane5); 
           //vb3.setMargin(barChart,new Insets(50,0,0,130));
           gridpane5.getChildren().clear();
            gridpane5.add(d, 4,1);
        gridpane5.add(f, 5,1); 
           gridpane5.add(keyword, 0,0);
        //gridpane5.add(d, 1,0);
        //gridpane5.add( f, 2,0);
        gridpane5.add(tweetno, 3,0);
        gridpane5.add(comp, 5,0);
        gridpane5.add(Goer, 3,1);
        gridpane5.add(csvfile, 0,1);
        gridpane5.add(Goer2, 6,1);
        gridpane5.add(Submit, 4,0);
           gridpane5.add(keyword2, 6,0);
           gridpane5.add(tweetno2, 7,0);
            gridpane5.add(Submit3, 8,0);
           gridpane5.setMargin(keyword2,new Insets(50,10,0,10));        
            gridpane5.setMargin(tweetno2,new Insets(50,10,0,0));
            gridpane5.setMargin(Submit3,new Insets(50,0,0,0));            
            gridpane5.setMargin(Goer2,new Insets(20,0,0,10));   
           
        }
        
      
    };;
     EventHandler<ActionEvent> dashboardEventListener = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
       
           vb3.getChildren().clear(); 
           vb3.getChildren().addAll(test); 
           
        }
        
      
    };;
     EventHandler<ActionEvent> focusshifta = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
       
            Analysis.requestFocus(); 
           
        }
        
      
    };;
     EventHandler<ActionEvent> Tweetget = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            
            LocalDate cer = d.getValue();
            LocalDate fer = f.getValue();
        //System.out.println(cer);
            double score=0.0; 
            
            gridpane6.getChildren().remove(barChart);
            gridpane6.getChildren().remove(scores);
            gridpane6.getChildren().remove(gauge);
       Query query = new Query(keyword.getText() + " +exclude:retweets");
        QueryResult result;
        int Count=1;
        int Count2=1;
        int x = Integer.parseInt(tweetno.getText());
        query.setCount(x);
        query.lang("en");
        query.setSince(cer.toString());
        query.setUntil(fer.toString());
        //String STRING_ARRAY_SAMPLE = "C:\\Users\\HP - PC\\Documents\\users.csv  ";
        //query.since("2020-04-13");
        //query.until("2020-04-14");
        
         try {
                
         //Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));

           // CSVWriter csvWriter = new CSVWriter(writer,
             //       CSVWriter.DEFAULT_SEPARATOR,
               //     CSVWriter.NO_QUOTE_CHARACTER,
                 //   CSVWriter.DEFAULT_ESCAPE_CHARACTER,
               //     CSVWriter.DEFAULT_LINE_END);
            //String[] headerRecord = {"Username", "Tweet"};
            //csvWriter.writeNext(headerRecord);

            //csvWriter.writeNext(new String[]{"Sundar Pichai ♥", "sundar.pichai@gmail.com", "+1-1111111111", "India"});
            //csvWriter.writeNext(new String[]{"Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "India"});
            String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\" + keyword.getText() +".csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            //String [] record = null;
            //writer.writeNext(record);
            
        
        while (Count < 2)
        {
            try {
                System.out.println("THE SENTIMENT ANALYSIS PROCESS OF MY PROJECT WILL BE DESCRIBED BELOW :======>");
                System.out.println();
                System.out.println("THE FIRST STEP IS DATA COLLECTION :======>");
                System.out.println("UNFILTERED TWEETS RETRIEVED DIRECTLY FROM TWITTER API USING THE KEYWORD PROVIDED:======>");
            result = twitter.search(query);
            List<twitter4j.Status> tweets = result.getTweets();
            for (twitter4j.Status tweet : tweets) {
              System.out.println("Tweet "+ Count2 + " BY @ " + tweet.getUser().getScreenName() + " WHO SAYS :======>" + tweet.getText());
              writer.writeNext(new String[]{tweet.getText()});
               Count2++;
            }
               
                
            } catch (TwitterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            };
            
             Count++;
             writer.close();
        }
         }  catch (IOException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    
        try {

                   Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\" + keyword.getText() +".csv"));
                   CSVReader csvReader = new CSVReader(reader);
                   //CSVReader reader = new CSVReader(new FileReader("C:\\Users\\HP - PC\\Documents\\project\\csv\\eilish.csv"));

            String[] record;
             System.out.println("THE FOLLOWING STEP IS FEATURE EXTRACTION (which includes the removal of urls, uppercasing, special characters, user mentions and retweets.) :======>");
            System.out.println("THE RESULT :======>");
             String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\"+ keyword.getText() +"processed.csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            //String [] record2 = null;
            //writer.writeNext(record2);
        while ((record = csvReader.readNext()) != null) {
            //System.out.println("Username: " + record[0]);
            //System.out.println("Tweet: " + record[1]);

            //Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
            //String tokens[] = tokenizer.tokenize(record[1]);

            //System.out.println("----------------");
            //for(int i=0;i<tokens.length;i++){
            //    System.out.println(tokens[i]);
            //}
            String h = record[0];
            String a = record[0];
            String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(a);
            int i = 0;
            while (m.find()) {
                a = a.replaceAll(m.group(i)," ").trim();
                i++;
            }
            String f = a.replaceAll("(@[^\\s-]+)"," ").trim();
            String d = f.replaceAll("[^\\p{Alpha} ]"," ").trim();
            String c = d.replaceAll("(?<!\\w)"," ").trim();
            List<String> allWords = new ArrayList<>(Arrays.asList(c.toLowerCase().split(" ")));
            List<String> stopWords = Arrays.asList("a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero");
            allWords.removeAll(stopWords);
            String str = String.join(" ", allWords);
            System.out.println("INPUT: "+h);
            System.out.println("RESULT: "+str);
            writer.writeNext(new String[]{c.toLowerCase()});
        }

    // close readers
    csvReader.close();
    reader.close();
     writer.close();
    
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        try { 

                   Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\"+ keyword.getText() + "processed.csv"));
                   CSVReader csvReader = new CSVReader(reader);
                   String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\" + keyword.getText() + "processedtokens.csv";
                    CSVWriter writer = new CSVWriter(new FileWriter(csv));
                   //CSVReader reader = new CSVReader(new FileReader("C:\\Users\\HP - PC\\Documents\\project\\csv\\eilish.csv"));

            String[] record;
            String tokens[];
            System.out.println();
            System.out.println("THE FINAL STEP IN FEATURE EXTRACTION IS THE TOKENIZATION :======>");
            System.out.println("THE RESULT :======>");
            while ((record = csvReader.readNext()) != null) {
        //System.out.println("Username: " + record[0]);
        //System.out.println("Tweet: " + record[1]);
        
        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
        tokens = tokenizer.tokenize(record[0]);
        
        System.out.println("----------------");
        for(int i=0;i<tokens.length;i++){
            System.out.println(tokens[i]);
            writer.writeNext(new String[]{tokens[i]});
        }
        
    }
   // System.out.println("TOken:" + tokens[23]);
    // close readers
    csvReader.close();
    reader.close();
    writer.close();
    
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {
            // read the training data
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("C:\\Users\\HP - PC\\Documents\\project\\csv\\Custom300.train"));
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);
 
            
            
            // define the training parameters
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 10+"");
            params.put(TrainingParameters.CUTOFF_PARAM, 0+"");
            params.put(AbstractTrainer.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
 
            // create a model from traning data
            DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
            //DoccatModel model = new DoccatModel(new File("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            System.out.println("\nModel is successfully trained.");
 
            // save the model to local
            BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            //BufferedInputStream modelOut = new BufferedInputStream(new FileInputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            model.serialize(modelOut);
            System.out.println("\nTrained Model is saved locally at : "+"model"+File.separator+"en-movie-classifier-naive-bayes.bin");
            
            //Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\Custom.csv"),"utf-8");
            Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\" + keyword.getText() +"processed.csv"),"utf-8"));
            CSVReader csvReader = new CSVReader(reader);
            String[] record;
            
            DocumentCategorizer doccat = new DocumentCategorizerME(model);
            System.out.println();
            //System.out.println("A DATASET IS THEN CREATED WITH REVIEWS LABELLED POSITIVE, NEGATIVE AND NEUTRAL");
            //System.out.println();
            //System.out.println("THE MACHINE LEARNING ALGORITHM NAIVE BAYES IS THEN IMPLEMENTED AND TRAINED USING THE DATASET");
            //System.out.println();
            System.out.println("THE NEXT STEP IS THE DOCUMENT CLASSIFICATION which classifies the text into positive, negative or neutral");
            System.out.println();
            System.out.println("THIS IS DONE USING THE MACHINE LEARNING ALGORITHM NAIVE BAYES WITH A TRAINED MODEL");
            System.out.println();
            System.out.println("THE FINAL RESULT :======>");
            while ((record = csvReader.readNext()) != null) {
                String[] docWords = record[0].replaceAll("[^A-Za-z]", " ").split(" ");
                double[] aProbs = doccat.categorize(docWords);
                //record[1] = "balls";4
                System.out.println();
                System.out.println("THE ORIGINAL TWEET: " +record[0]);
                for(int i=0;i<doccat.getNumberOfCategories();i++){                   
                               
            }   
                System.out.println();
                System.out.println("THE PREDICTION :======>");
                System.out.println(doccat.getBestCategory(aProbs));
                String a = doccat.getBestCategory(aProbs);
                if (a.contains("Positive")){
                    Po++;
                }else if (a.contains("Negative")){
                    Ne++;
                }else{
                    Nu++;
                }
                counter++;
             };
             //System.out.println(Po);
             //System.out.println(Ne);
             //System.out.println(Nu);
             //System.out.println(counter);
             CategoryAxis xAxis = new CategoryAxis();  
                xAxis.setCategories(FXCollections.<String>
                observableArrayList(Arrays.asList("POSITIVE | NEUTRAL | NEGATIVE")));
                xAxis.setLabel("Sentiment Analysis");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("percentage");

                //Creating the Bar chart
                barChart = new BarChart<>(xAxis, yAxis); 
                barChart.setMaxHeight(400);
                barChart.setMaxWidth(500);
//                barChart.setTitle("Analysis Result of " + keyword.getText()+ "\n from " +cer.toString() + " to " +fer.toString());

                //Prepare XYChart.Series objects by setting data  
                XYChart.Series<String, Number> series1 = new XYChart.Series<>();
                series1.setName("Negative");
                //series1.getData().getNode().setStyle("-fx-bar-fill: blue;");
                series1.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Ne/counter)*100));

                XYChart.Series<String, Number> series2 = new XYChart.Series<>();
                series2.setName("Neutral");
                series2.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Nu/counter)*100));

                XYChart.Series<String, Number> series3 = new XYChart.Series<>();
                series3.setName("Positive");
                series3.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Po/counter)*100));
                System.out.println();
                System.out.println("THE AGGREGRATE SCORE OF ALL REVIEWS IS THEN CALCULATED AND REPRESENTED IN A CHART");
                System.out.println();
                System.out.println("THE PERCENTAGE DISTRIBUTION OF ALL REVIEWS :========>");
                System.out.println("POSITIVE: " + (Po/counter)*100 + "% NEGATIVE: " + (Ne/counter)*100 + "% NEUTRAL: " + (Nu/counter)*100 + "%");
            // test the model file by subjecting it tso prediction
                               
               // score = Math.round((((Po*5)+(Nu*2.5)+(Ne*0))/counter) * 100.0) / 100.0;
                //scores.setText("Average score: "+ (score)+"/5");
                scores.setStyle("-fx-font-size: 20pt; -fx-font-family: arial; -fx-text-fill:blue ");
                //Label.setText();
                 barChart.getData().addAll(series3, series2, series1);
                  barChart.lookupAll(".default-color0.chart-bar")
            .forEach(n -> n.setStyle("-fx-bar-fill: green;"));
                  // barChart.lookupAll(".default-color1.chart-bar")
            //.forEach(n -> n.setStyle("-fx-bar-fill: blue;"));
                    barChart.lookupAll(".default-color2.chart-bar")
            .forEach(n -> n.setStyle("-fx-bar-fill: red;"));
                     barChart.setLegendVisible(false);
                   Po= 0.0;
                Ne= 0.0;
                Nu= 0.0;
                counter = 0;
            
                
            
            
            //String a = "very good, i love, is a brilliant intellectual";
            
            
            for(int i=0; i<5; i++){
                
            }
               //Gauge gauge = new Gauge();
               
               //gauge = GaugeBuilder.maxValue(100);
               gauge.setSkin(new SimpleDigitalSkin(gauge));  //ModernSkin : you guys can change the skin
               gauge.setMaxSize(150.0, 150.0);
         gauge.setTitle("Average Score");  //title
         gauge.setUnit("/5");  //unit
         gauge.setUnitColor(Color.WHITE);
         gauge.setDecimals(2); 
//         gauge.setValue(score); //deafult position of needle on gauage
         gauge.setAnimated(true);
         //gauge.setAnimationDuration(1000); 

         gauge.setValueColor(Color.BLUE); 
         gauge.setTitleColor(Color.BLUE); 
         gauge.setSubTitleColor(Color.BLUE); 
         gauge.setBarColor(Color.rgb(0, 214, 215)); 
         gauge.setNeedleColor(Color.RED); 
         gauge.setThresholdColor(Color.RED);  //color will become red if it crosses threshold value
         gauge.setThreshold(85);
         gauge.setThresholdVisible(true);
         gauge.setTickLabelColor(Color.rgb(151, 151, 151)); 
         gauge.setTickMarkColor(Color.BLUE); 
         gauge.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);
         gauge.setMaxValue(5.00);
            // print the probabilities of the categories
            //System.out.println("\n---------------------------------\nCategory : Probability\n---------------------------------");
            
            //System.out.println("---------------------------------");
 
            //System.out.println("\n"+doccat.getBestCategory(aProbs)+" : is the predicted category for the given sentence.");
            //vb3.getChildren().clear();
            //gridpane6.getChildren().remove(barChart);
            //gridpane6.getChildren().addAll(barChart); 
            gridpane6.add(barChart, 0,0);
            //gridpane6.add(scores, 0,1);
            gridpane6.add(gauge, 0,1);
            //vb3.getChildren().addAll(gridpane6); 
            gridpane6.setMargin(barChart,new Insets(10,0,0,0));
            gridpane6.setMargin(gauge,new Insets(20,0,0,200));
            //gridpane6.setMargin(scores,new Insets(50,0,0,150));
}
        catch (IOException e) {
            System.out.println("An exception in reading the training file. Please check.");
            e.printStackTrace();
}           catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
}  
    };;
            EventHandler<ActionEvent> Tweetget2 = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            LocalDate cer = d.getValue();
            LocalDate fer = f.getValue();
             double score=0.0; 
            //Label scores = new Label("");
            //gridpane6.getChildren().remove(barChart);
            //Gauge gauge = new Gauge();
            gridpane6.getChildren().remove(scores);
        gridpane6.getChildren().remove(barChart2);
        gridpane6.getChildren().remove(gauge2);
       Query query = new Query(keyword2.getText() + " +exclude:retweets");
        QueryResult result;
        int Count=1;
        int Count2=1;
        int x = Integer.parseInt(tweetno2.getText());
        query.setCount(x);
        query.lang("en");
        query.setSince(cer.toString());
        query.setUntil(fer.toString());
        //String STRING_ARRAY_SAMPLE = "C:\\Users\\HP - PC\\Documents\\users.csv  ";
        //query.since("2020-04-13");
        //query.until("2020-04-14");
        
         try {
                
         //Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));

           // CSVWriter csvWriter = new CSVWriter(writer,
             //       CSVWriter.DEFAULT_SEPARATOR,
               //     CSVWriter.NO_QUOTE_CHARACTER,
                 //   CSVWriter.DEFAULT_ESCAPE_CHARACTER,
               //     CSVWriter.DEFAULT_LINE_END);
            //String[] headerRecord = {"Username", "Tweet"};
            //csvWriter.writeNext(headerRecord);

            //csvWriter.writeNext(new String[]{"Sundar Pichai ♥", "sundar.pichai@gmail.com", "+1-1111111111", "India"});
            //csvWriter.writeNext(new String[]{"Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "India"});
            String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\" + keyword2.getText() +".csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            //String [] record = null;
            //writer.writeNext(record);
            
        
        while (Count < 2)
        {
            try {
                System.out.println("THE SENTIMENT ANALYSIS PROCESS OF MY PROJECT WILL BE DESCRIBED BELOW :======>");
                System.out.println();
                System.out.println("THE FIRST STEP IS DATA COLLECTION :======>");
                System.out.println("UNFILTERED TWEETS RETRIEVED DIRECTLY FROM TWITTER API USING THE KEYWORD PROVIDED:======>");
            result = twitter.search(query);
            List<twitter4j.Status> tweets = result.getTweets();
            for (twitter4j.Status tweet : tweets) {
              System.out.println("Tweet "+ Count2 + " BY @ " + tweet.getUser().getScreenName() + " WHO SAYS :======>" + tweet.getText());
              writer.writeNext(new String[]{tweet.getText()});
               Count2++;
            }
               
                
            } catch (TwitterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            };
            
             Count++;
             writer.close();
        }
         }  catch (IOException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    
        try {

                   Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\" + keyword2.getText() +".csv"));
                   CSVReader csvReader = new CSVReader(reader);
                   //CSVReader reader = new CSVReader(new FileReader("C:\\Users\\HP - PC\\Documents\\project\\csv\\eilish.csv"));

            String[] record;
             System.out.println("THE FOLLOWING STEP IS FEATURE EXTRACTION (which includes the removal of urls, uppercasing, special characters, user mentions and retweets.) :======>");
            System.out.println("THE RESULT :======>");
             String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\"+ keyword2.getText() +"processed.csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            //String [] record2 = null;
            //writer.writeNext(record2);
        while ((record = csvReader.readNext()) != null) {
            //System.out.println("Username: " + record[0]);
            //System.out.println("Tweet: " + record[1]);

            //Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
            //String tokens[] = tokenizer.tokenize(record[1]);

            //System.out.println("----------------");
            //for(int i=0;i<tokens.length;i++){
            //    System.out.println(tokens[i]);
            //}
            String h = record[0];
            String a = record[0];
            String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(a);
            int i = 0;
            while (m.find()) {
                a = a.replaceAll(m.group(i)," ").trim();
                i++;
            }
            String f = a.replaceAll("(@[^\\s-]+)"," ").trim();
            String d = f.replaceAll("[^\\p{Alpha} ]"," ").trim();
            String c = d.replaceAll("(?<!\\w)"," ").trim();
            List<String> allWords = new ArrayList<>(Arrays.asList(c.toLowerCase().split(" ")));
            List<String> stopWords = Arrays.asList("a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero");
            allWords.removeAll(stopWords);
            String str = String.join(" ", allWords);
            System.out.println("INPUT: "+h);
            System.out.println("RESULT: "+str);
            writer.writeNext(new String[]{str});
        }

    // close readers
    csvReader.close();
    reader.close();
     writer.close();
    
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        try { 

                   Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\"+ keyword2.getText() + "processed.csv"));
                   CSVReader csvReader = new CSVReader(reader);
                   String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\" + keyword2.getText() + "processedtokens.csv";
                    CSVWriter writer = new CSVWriter(new FileWriter(csv));
                   //CSVReader reader = new CSVReader(new FileReader("C:\\Users\\HP - PC\\Documents\\project\\csv\\eilish.csv"));

            String[] record;
            String tokens[];
            System.out.println();
            System.out.println("THE FINAL STEP IN FEATURE EXTRACTION IS THE TOKENIZATION :======>");
            System.out.println("THE RESULT :======>");
            while ((record = csvReader.readNext()) != null) {
        //System.out.println("Username: " + record[0]);
        //System.out.println("Tweet: " + record[1]);
        
        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
        tokens = tokenizer.tokenize(record[0]);
        
        System.out.println("----------------");
        for(int i=0;i<tokens.length;i++){
            System.out.println(tokens[i]);
            writer.writeNext(new String[]{tokens[i]});
        }
        
    }
   // System.out.println("TOken:" + tokens[23]);
    // close readers
    csvReader.close();
    reader.close();
    writer.close();
    
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {
            // read the training data
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("C:\\Users\\HP - PC\\Documents\\project\\csv\\Custom300.train"));
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);
 
            
            
            // define the training parameters
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 10+"");
            params.put(TrainingParameters.CUTOFF_PARAM, 0+"");
            params.put(AbstractTrainer.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
 
            // create a model from traning data
            DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
            //DoccatModel model = new DoccatModel(new File("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            System.out.println("\nModel is successfully trained.");
 
            // save the model to local
            BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            //BufferedInputStream modelOut = new BufferedInputStream(new FileInputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            model.serialize(modelOut);
            System.out.println("\nTrained Model is saved locally at : "+"model"+File.separator+"en-movie-classifier-naive-bayes.bin");
            
            //Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\Custom.csv"),"utf-8");
            Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\" + keyword2.getText() +"processed.csv"),"utf-8"));
            CSVReader csvReader = new CSVReader(reader);
            String[] record;
            
            DocumentCategorizer doccat = new DocumentCategorizerME(model);
            System.out.println();
            //System.out.println("A DATASET IS THEN CREATED WITH REVIEWS LABELLED POSITIVE, NEGATIVE AND NEUTRAL");
            //System.out.println();
            //System.out.println("THE MACHINE LEARNING ALGORITHM NAIVE BAYES IS THEN IMPLEMENTED AND TRAINED USING THE DATASET");
            //System.out.println();
            System.out.println("THE NEXT STEP IS THE DOCUMENT CLASSIFICATION which classifies the text into positive, negative or neutral");
            System.out.println();
            System.out.println("THIS IS DONE USING THE MACHINE LEARNING ALGORITHM NAIVE BAYES WITH A TRAINED MODEL");
            System.out.println();
            System.out.println("THE FINAL RESULT :======>");
            while ((record = csvReader.readNext()) != null) {
                String[] docWords = record[0].replaceAll("[^A-Za-z]", " ").split(" ");
                double[] aProbs = doccat.categorize(docWords);
                //record[1] = "balls";4
                System.out.println();
                System.out.println("THE ORIGINAL TWEET: " +record[0]);
                for(int i=0;i<doccat.getNumberOfCategories();i++){                   
                               
            }   
                System.out.println();
                System.out.println("THE PREDICTION :======>");
                System.out.println(doccat.getBestCategory(aProbs));
                String a = doccat.getBestCategory(aProbs);
                if (a.contains("Positive")){
                    Po++;
                }else if (a.contains("Negative")){
                    Ne++;
                }else{
                    Nu++;
                }
                counter++;
             };
             //System.out.println(Po);
             //System.out.println(Ne);
             //System.out.println(Nu);
             //System.out.println(counter);
             CategoryAxis xAxis = new CategoryAxis();  
                xAxis.setCategories(FXCollections.<String>
                observableArrayList(Arrays.asList("POSITIVE | NEUTRAL | NEGATIVE")));
                xAxis.setLabel("Sentiment Analysis");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("percentage");

                //Creating the Bar chart
                barChart2 = new BarChart<>(xAxis, yAxis); 
                barChart2.setMaxHeight(400);
                barChart2.setMaxWidth(500);
                barChart2.setTitle("Analysis Result of " + keyword2.getText()+ "\n from " +cer.toString() + " to " +fer.toString());

                //Prepare XYChart.Series objects by setting data  
                XYChart.Series<String, Number> series1 = new XYChart.Series<>();
                series1.setName("Negative");
                //series1.getData().getNode().setStyle("-fx-bar-fill: blue;");
                series1.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Ne/counter)*100));

                XYChart.Series<String, Number> series2 = new XYChart.Series<>();
                series2.setName("Neutral");
                series2.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Nu/counter)*100));

                XYChart.Series<String, Number> series3 = new XYChart.Series<>();
                series3.setName("Positive");
                series3.getData().add(new XYChart.Data<>("POSITIVE | NEUTRAL | NEGATIVE", (Po/counter)*100));
                System.out.println();
                System.out.println("THE AGGREGRATE SCORE OF ALL REVIEWS IS THEN CALCULATED AND REPRESENTED IN A CHART");
                System.out.println();
                System.out.println("THE PERCENTAGE DISTRIBUTION OF ALL REVIEWS :========>");
                System.out.println("POSITIVE: " + (Po/counter)*100 + "% NEGATIVE: " + (Ne/counter)*100 + "% NEUTRAL: " + (Nu/counter)*100 + "%");
                
                
         
                score = Math.round((((Po*5)+(Nu*2.5)+(Ne*0))/counter) * 100.0) / 100.0;   
                scores.setText("Average score: "+ (score)+"/5");
                scores.setStyle("-fx-font-size: 20pt; -fx-font-family: arial; -fx-text-fill:blue ");
            // test the model file by subjecting it tso prediction
                 barChart2.getData().addAll(series3, series2, series1);
                  barChart2.lookupAll(".default-color0.chart-bar")
            .forEach(n -> n.setStyle("-fx-bar-fill: green;"));
                  // barChart.lookupAll(".default-color1.chart-bar")
            //.forEach(n -> n.setStyle("-fx-bar-fill: blue;"));
                    barChart2.lookupAll(".default-color2.chart-bar")
            .forEach(n -> n.setStyle("-fx-bar-fill: red;"));
                     barChart2.setLegendVisible(false);
                   Po= 0.0;
                Ne= 0.0;
                Nu= 0.0;
                counter = 0;
            
                
            
            
            //String a = "very good, i love, is a brilliant intellectual";
            
            
            for(int i=0; i<5; i++){
                
            }
 
            gauge2.setSkin(new SimpleDigitalSkin(gauge2));  //ModernSkin : you guys can change the skin
            gauge2.setMaxSize(150.0, 150.0);
         gauge2.setTitle("Average Score");  //title
         gauge2.setUnit("/5");  //unit
         gauge2.setUnitColor(Color.WHITE);
         gauge2.setDecimals(2); 
         gauge2.setValue(score); //deafult position of needle on gauage
         gauge2.setAnimated(true);
         //gauge2.setAnimationDuration(1000); 

         gauge2.setValueColor(Color.BLUE); 
         gauge2.setTitleColor(Color.BLUE); 
         gauge2.setSubTitleColor(Color.BLUE); 
         gauge2.setBarColor(Color.rgb(0, 214, 215)); 
         gauge2.setNeedleColor(Color.RED); 
         gauge2.setThresholdColor(Color.RED);  //color will become red if it crosses threshold value
         gauge2.setThreshold(85);
         gauge2.setThresholdVisible(true);
         gauge2.setTickLabelColor(Color.rgb(151, 151, 151)); 
         gauge2.setTickMarkColor(Color.BLUE); 
         gauge2.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);
         gauge2.setMaxValue(5.00);
            // print the probabilities of the categories
            //System.out.println("\n---------------------------------\nCategory : Probability\n---------------------------------");
            
            //System.out.println("---------------------------------");
 
            //System.out.println("\n"+doccat.getBestCategory(aProbs)+" : is the predicted category for the given sentence.");
            //vb3.getChildren().clear();
            //gridpane6.getChildren().clear();
            //gridpane6.getChildren().remove(barChart2);
            //gridpane6.getChildren().addAll(barChart2); 
            gridpane6.add(barChart2, 1,0);
           // gridpane6.add(scores, 1,1);
            
            //vb3.getChildren().addAll(gridpane6); 
            gridpane6.setMargin(barChart2,new Insets(10,0,0,0));
            gridpane6.setMargin(gauge2,new Insets(20,0,0,200));
            //gridpane6.setMargin(scores,new Insets(50,0,0,150));
            gridpane6.add(gauge2, 1,1);
}
        catch (IOException e) {
            System.out.println("An exception in reading the training file. Please check.");
            e.printStackTrace();
}           catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
}  
    };;

EventHandler<ActionEvent> Tokenize = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
        try { 

                   Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\ecowasprocessed.csv"));
                   CSVReader csvReader = new CSVReader(reader);
                   String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\ecowasprocessed2.csv";
                    CSVWriter writer = new CSVWriter(new FileWriter(csv));
                   //CSVReader reader = new CSVReader(new FileReader("C:\\Users\\HP - PC\\Documents\\project\\csv\\eilish.csv"));

            String[] record;
            String tokens[];
            while ((record = csvReader.readNext()) != null) {
        //System.out.println("Username: " + record[0]);
        //System.out.println("Tweet: " + record[1]);
        
        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
        tokens = tokenizer.tokenize(record[0]);
 
        System.out.println("----------------");
        for(int i=0;i<tokens.length;i++){
            System.out.println(tokens[i]);
            writer.writeNext(new String[]{tokens[i]});
        }
        
    }
   // System.out.println("TOken:" + tokens[23]);
    // close readers
    csvReader.close();
    reader.close();
    writer.close();
    
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
}
};;
EventHandler<ActionEvent> LOwerurl = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
        try {

                   Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\ecowas.csv"));
                   CSVReader csvReader = new CSVReader(reader);
                   //CSVReader reader = new CSVReader(new FileReader("C:\\Users\\HP - PC\\Documents\\project\\csv\\eilish.csv"));

            String[] record;
            
            String csv = "C:\\Users\\HP - PC\\Documents\\project\\csv\\ecowasprocessed.csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            //String [] record2 = null;
            //writer.writeNext(record2);
        while ((record = csvReader.readNext()) != null) {
            //System.out.println("Username: " + record[0]);
            //System.out.println("Tweet: " + record[1]);

            //Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
            //String tokens[] = tokenizer.tokenize(record[1]);

            //System.out.println("----------------");
            //for(int i=0;i<tokens.length;i++){
            //    System.out.println(tokens[i]);
            //}
            String a = record[0];
            String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(a);
            int i = 0;
            while (m.find()) {
                a = a.replaceAll(m.group(i)," ").trim();
                i++;
            }
            String f = a.replaceAll("(@[^\\s-]+)"," ").trim();
            String d = f.replaceAll("[^\\p{Alpha} ]"," ").trim();
            String c = d.replaceAll("(?<!\\w)"," ").trim();
            String str = (c.toLowerCase());
            System.out.println("Input: "+a);
            System.out.println("Result: "+str);
            writer.writeNext(new String[]{str});
        }

    // close readers
    csvReader.close();
    reader.close();
     writer.close();
    
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    };;
EventHandler<ActionEvent> DocClass = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
try {
            // read the training data
            //InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("C:\\Users\\HP - PC\\Documents\\project\\csv\\Custom3000.train"));
            //ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            //ObjectStream sampleStream = new DocumentSampleStream(lineStream);
 
            
            
            // define the training parameters
            //TrainingParameters params = new TrainingParameters();
            //params.put(TrainingParameters.ITERATIONS_PARAM, 10+"");
            //params.put(TrainingParameters.CUTOFF_PARAM, 0+"");
            //params.put(AbstractTrainer.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
 
            // create a model from traning data
            //DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
            DoccatModel model = new DoccatModel(new File("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            System.out.println("\nModel is successfully trained.");
 
            // save the model to local
            //BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            //BufferedInputStream modelOut = new BufferedInputStream(new FileInputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\Dataset.bin"));
            //model.serialize(modelOut);
            //System.out.println("\nTrained Model is saved locally at : "+"model"+File.separator+"en-movie-classifier-naive-bayes.bin");
            
            //Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\HP - PC\\Documents\\project\\csv\\Custom.csv"),"utf-8");
            Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\HP - PC\\Documents\\project\\csv\\" + keyword.getText() +"processed.csv"),"utf-8"));
            CSVReader csvReader = new CSVReader(reader);
            String[] record;
            
            DocumentCategorizer doccat = new DocumentCategorizerME(model);
            
            while ((record = csvReader.readNext()) != null) {
                String[] docWords = record[0].replaceAll("[^A-Za-z]", " ").split(" ");
                double[] aProbs = doccat.categorize(docWords);
                //record[1] = "balls";4
                System.out.println(record[0]);
                for(int i=0;i<doccat.getNumberOfCategories();i++){                   
                               
            }
                System.out.println(doccat.getBestCategory(aProbs));
                String a = doccat.getBestCategory(aProbs);
                if (a.contains("Positive")){
                    Po++;
                }else if (a.contains("Negative")){
                    Ne++;
                }else{
                    Nu++;
                }
                counter++;
             };
             //System.out.println(Po);
             //System.out.println(Ne);
             //System.out.println(Nu);
             //System.out.println(counter);
             CategoryAxis xAxis = new CategoryAxis();  
                xAxis.setCategories(FXCollections.<String>
                observableArrayList(Arrays.asList("User rating")));
                xAxis.setLabel("category");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("score");

                //Creating the Bar chart
                barChart = new BarChart<>(xAxis, yAxis); 
                barChart.setTitle("Analysis Result");

                //Prepare XYChart.Series objects by setting data  
                XYChart.Series<String, Number> series1 = new XYChart.Series<>();
                series1.setName("Negative");
                series1.getData().add(new XYChart.Data<>("User rating", (Ne/counter)*100));
                Node line = series1.getNode().lookup(".chart-series-area-line");
                line.setStyle("-fx-fill: red;");

                XYChart.Series<String, Number> series2 = new XYChart.Series<>();
                series2.setName("Neutral");
                for(Node n:barChart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: purple;");
        }
                series2.getData().add(new XYChart.Data<>("User rating", (Nu/counter)*100));

                XYChart.Series<String, Number> series3 = new XYChart.Series<>();
                series3.setName("Positive");
                series3.getData().add(new XYChart.Data<>("User rating", (Po/counter)*100));
             System.out.println("Positive: " + (Po/counter)*100 + "% Negative: " + (Ne/counter)*100 + "% Neutral: " + (Nu/counter)*100 + "%");
            // test the model file by subjecting it tso prediction
                 barChart.getData().addAll(series3, series2, series1);
                Double Po= 0.0;
                Double Ne= 0.0;
                Double Nu= 0.0;
            
            
            
            //String a = "very good, i love, is a brilliant intellectual";
            
            
            for(int i=0; i<5; i++){
                
            }
 
            // print the probabilities of the categories
            //System.out.println("\n---------------------------------\nCategory : Probability\n---------------------------------");
            
            //System.out.println("---------------------------------");
 
            //System.out.println("\n"+doccat.getBestCategory(aProbs)+" : is the predicted category for the given sentence.");
            vb3.getChildren().clear();
            vb3.getChildren().addAll(gridpane5, barChart); 
            vb3.setMargin(barChart,new Insets(50,0,0,130));
}
        catch (IOException e) {
            System.out.println("An exception in reading the training file. Please check.");
            e.printStackTrace();
}           catch (CsvValidationException ex) {
                Logger.getLogger(Sentiment_Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
};;
    
}