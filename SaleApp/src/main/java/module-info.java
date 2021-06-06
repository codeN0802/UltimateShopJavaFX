module com.dht.saleapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 
    requires java.base;
 

    opens com.dht.saleapp to javafx.fxml;
    exports com.dht.saleapp;
    exports com.dht.pojo;
    requires com.jfoenix;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.webEmpty;
   
   
 
}
