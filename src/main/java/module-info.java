module org.example.sharedmobilityfxproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens org.example.sharedmobilityfxproject to javafx.fxml;
    exports org.example.sharedmobilityfxproject;
    exports org.example.sharedmobilityfxproject.model;
    opens org.example.sharedmobilityfxproject.model to javafx.fxml;
    exports org.example.sharedmobilityfxproject.model.tranportMode;
    opens org.example.sharedmobilityfxproject.model.tranportMode to javafx.fxml;
}