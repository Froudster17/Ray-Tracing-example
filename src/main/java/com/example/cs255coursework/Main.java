package com.example.cs255coursework;/*
CS-255 Getting started code for the assignment
I do not give you permission to post this code online
Do not post your solution online
Do not copy code
Do not use JavaFX functions or other libraries to do the main parts of the assignment (i.e. ray tracing steps 1-7)
All of those functions must be written by yourself
You may use libraries to achieve a better GUI
*/
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.io.*;
import java.lang.Math.*;
import javafx.geometry.HPos;

import static java.lang.Math.sqrt;

public class Main extends Application {
  private int Width = 640;
  private int Height = 640;
  private int xLightPosition = 0;
  private int yLightPosition = 0;

  private ArrayList<Sphere> spheres;

  Camera camera = new Camera(0, 0, -400);

  @Override
  public void start(Stage stage) throws FileNotFoundException {
    stage.setTitle("Ray Tracing");

    GridPane root = new GridPane();
    root.setVgap(12);
    root.setHgap(12);

    spheres = new ArrayList<>();

    //Create an image we can write to
    WritableImage image = new WritableImage(Width, Height);
    //Create a view of that image
    ImageView view = new ImageView(image);

    TabPane tp = new TabPane();
    tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    tp.setPrefWidth(400);
    root.add(tp, 1, 0);

    //Create Sphere Button
    Button createSphereButton = new Button();
    createSphereButton.setPrefWidth(640);
    createSphereButton.setText("Create Sphere");
    root.add(createSphereButton, 0, 1);

    //Create new Sphere Button Event
    EventHandler<ActionEvent> button = e -> {

      Sphere s = new Sphere(0, 0, 0, 255, 255, 255, 50);
      spheres.add(s);

      GridPane sliderPane = new GridPane();
      sliderPane.setVgap(10);
      sliderPane.setHgap(10);

      Tab t = new Tab();
      t.setText("Sphere " + s.getSphereID());
      t.setContent(sliderPane);

      tp.getTabs().add(t);

      //Create labels
      Label xLabel = new Label("X Position");
      Label yLabel = new Label("Y Position");
      Label zLabel = new Label("Z Position");
      Label rLabel = new Label("R Colour");
      Label gLabel = new Label("G Colour");
      Label bLabel = new Label("B Colour");
      Label radiusLabel = new Label("Radius");

      //Create sliders
      Slider x_position_slider = new Slider(-320, 320, s.getxPosition());
      Slider y_position_slider = new Slider(-320, 320, s.getyPosition());
      Slider z_position_slider = new Slider(-320, 320, s.getzPosition());
      Slider r_colour_slider = new Slider(0, 255, s.getR());
      Slider g_colour_slider = new Slider(0, 255, s.getG());
      Slider b_colour_slider = new Slider(0, 255, s.getB());
      Slider radius_slider = new Slider(0, 100, s.getRadius());

      //Setting sliders widths
      x_position_slider.setPrefWidth(1000);
      y_position_slider.setPrefWidth(1000);
      z_position_slider.setPrefWidth(1000);
      r_colour_slider.setPrefWidth(1000);
      g_colour_slider.setPrefWidth(1000);
      b_colour_slider.setPrefWidth(1000);
      radius_slider.setPrefWidth(1000);

      //Showing slider tick marks
      x_position_slider.setShowTickMarks(true);
      y_position_slider.setShowTickMarks(true);
      z_position_slider.setShowTickMarks(true);
      r_colour_slider.setShowTickMarks(true);
      g_colour_slider.setShowTickMarks(true);
      b_colour_slider.setShowTickMarks(true);
      radius_slider.setShowTickMarks(true);

      //Showing slider tick labels
      x_position_slider.setShowTickLabels(true);
      y_position_slider.setShowTickLabels(true);
      z_position_slider.setShowTickLabels(true);
      r_colour_slider.setShowTickLabels(true);
      g_colour_slider.setShowTickLabels(true);
      b_colour_slider.setShowTickLabels(true);
      radius_slider.setShowTickLabels(true);


      //Slider listeners
      x_position_slider.valueProperty().addListener(
              new ChangeListener < Number > () {
                public void changed(ObservableValue < ? extends Number >
                                            observable, Number oldValue, Number newValue) {
                  s.setxPosition(-newValue.intValue());
                  Render(image);
                }
              });

      y_position_slider.valueProperty().addListener(
              new ChangeListener < Number > () {
                public void changed(ObservableValue < ? extends Number >
                                            observable, Number oldValue, Number newValue) {
                  s.setyPosition(-newValue.intValue());
                  Render(image);
                }
              });

      z_position_slider.valueProperty().addListener(
              new ChangeListener < Number > () {
                public void changed(ObservableValue < ? extends Number >
                                            observable, Number oldValue, Number newValue) {
                  s.setzPosition(-newValue.intValue());
                  Render(image);
                }
              });
      r_colour_slider.valueProperty().addListener(
              new ChangeListener < Number > () {
                public void changed(ObservableValue < ? extends Number >
                                            observable, Number oldValue, Number newValue) {
                  s.setR(newValue.intValue());
                  Render(image);
                }
              });

      g_colour_slider.valueProperty().addListener(
              new ChangeListener < Number > () {
                public void changed(ObservableValue < ? extends Number >
                                            observable, Number oldValue, Number newValue) {
                  s.setG(newValue.intValue());
                  Render(image);
                }
              });

      b_colour_slider.valueProperty().addListener(
              new ChangeListener < Number > () {
                public void changed(ObservableValue < ? extends Number >
                                            observable, Number oldValue, Number newValue) {
                  s.setB(newValue.intValue());
                  Render(image);
                }
              });
      radius_slider.valueProperty().addListener(
              new ChangeListener < Number > () {
                public void changed(ObservableValue < ? extends Number >
                                            observable, Number oldValue, Number newValue) {
                  s.setRadius(newValue.intValue());
                  Render(image);
                }
              });

      //Adding labels and sliders
      sliderPane.add(xLabel, 0, 1);
      sliderPane.add(x_position_slider, 0, 2);
      sliderPane.add(yLabel, 0, 3);
      sliderPane.add(y_position_slider, 0, 4);
      sliderPane.add(zLabel, 0, 5);
      sliderPane.add(z_position_slider, 0, 6);
      sliderPane.add(rLabel, 0 ,7);
      sliderPane.add(r_colour_slider, 0, 8);
      sliderPane.add(gLabel, 0 ,9);
      sliderPane.add(g_colour_slider, 0, 10);
      sliderPane.add(bLabel, 0 ,11);
      sliderPane.add(b_colour_slider, 0, 12);
      sliderPane.add(radiusLabel, 0, 13);
      sliderPane.add(radius_slider, 0, 14);

      Render(image);
    };

    createSphereButton.setOnAction(button);

    //The following is in case you want to interact with the image in any way
    //e.g., for user interaction, or you can find out the pixel position for debugging
    view.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
      System.out.println(event.getX() + " " + event.getY());
      event.consume();
    });

    Render(image);

    //3. (referring to the 3 things we need to display an image)
    //we need to add it to the pane

    GridPane sliderPane = new GridPane();
    sliderPane.setVgap(10);
    sliderPane.setHgap(10);

    ////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////CAMERA///////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////


    //Create camera sliders
    Slider camera_x_position_slider = new Slider(-360, 360, camera.getX());
    Slider camera_y_position_slider = new Slider(-360, 360, camera.getY());

    //Create camera labels
    Label xCameraLabel = new Label("X Position");
    Label yCameraLabel = new Label("Y Position");

    //Slider listeners
    camera_x_position_slider.valueProperty().addListener(
            new ChangeListener < Number > () {
              public void changed(ObservableValue < ? extends Number >
                                          observable, Number oldValue, Number newValue) {
                camera.setX(newValue.intValue());
                Render(image);
              }
            });

    camera_y_position_slider.valueProperty().addListener(
            new ChangeListener < Number > () {
              public void changed(ObservableValue < ? extends Number >
                                          observable, Number oldValue, Number newValue) {
                camera.setY(newValue.intValue());
                Render(image);
              }
            });

    //Setting sliders widths
    camera_x_position_slider.setPrefWidth(640);
    camera_y_position_slider.setPrefWidth(640);

    //Showing slider tick marks
    camera_x_position_slider.setShowTickMarks(true);
    camera_y_position_slider.setShowTickMarks(true);

    //Showing slider tick labels
    camera_x_position_slider.setShowTickLabels(true);
    camera_y_position_slider.setShowTickLabels(true);

    //Adding sliders & Labels to root
    root.add(xCameraLabel, 0 ,2);
    root.add(camera_x_position_slider, 0, 3);
    root.add(yCameraLabel, 0 ,4);
    root.add(camera_y_position_slider, 0, 5);

    root.add(view, 0, 0);

    //Display to user
    Scene scene = new Scene(root, 1024, 768);
    stage.setScene(scene);
    stage.show();
  }

  ////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////RENDER////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////

  public void Render(WritableImage image) {

    camera.update();

    //Get image dimensions, and declare loop variables
    int w = (int) image.getWidth(), h = (int) image.getHeight(), i, j;
    PixelWriter image_writer = image.getPixelWriter();

    Vector o = new Vector(0,0,0), d = camera.viewPlaneNormal;
    Vector light = new Vector(0, 0, -200);

    for (j = 0; j < h; j++) {
      for (i = 0; i < w; i++) {
        //ray origin = (i, j, 4)

        double scale = 1;
        double u = (i - ((float) Width) / 2) * scale;
        double v = ((Height - j) - ((float) Height / 2)) * scale;

        o = new Vector(camera.getX(), camera.getY(), camera.getZ()).add(camera.viewRightVector.mul(u)).add(camera.viewUpVector.mul(v));
        double a = d.dot(d);

        Sphere closestShape = null;
        double closestT = Double.POSITIVE_INFINITY;

        image_writer.setColor(i, j, Color.color(0.5, 0.5, 0.5, 1.0));
        for (Sphere s : spheres) {
          if (s.getDisk(d, o) >= 0) {
              if (s.getT(i, j, d, o) < closestT) {
                  closestT = s.getT(i, j, d, o);
                  closestShape = s;
              }
          }
          if (closestShape != null) {
            closestShape.Render(image, o, d, i, j, image_writer, light);
          }
        }
      } // column loop
    } // row loop
  }

  public static void main(String[] args) {
    launch();
  }
}