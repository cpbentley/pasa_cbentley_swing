package pasa.cbentley.swing.glasspanes;

import java.awt.Component;
import java.awt.Point;
import java.awt.Shape;

public class WidgetPeeringHole {
   private Shape     holeShape;

   /**
    * Message describing the hole
    */
   private String    message;

   /**
    * Where to draw the message
    */
   private Point     messagePoint;

   private Component component;

   public Component getComponent() {
      return component;
   }

   public void setComponent(Component component) {
      this.component = component;
   }

   public Shape getHoleShape() {
      return holeShape;
   }

   public void setHoleShape(Shape holeShape) {
      this.holeShape = holeShape;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public Point getMessagePoint() {
      return messagePoint;
   }

   public void setMessagePoint(Point messagePoint) {
      this.messagePoint = messagePoint;
   }
}
