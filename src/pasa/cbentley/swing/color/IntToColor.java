package pasa.cbentley.swing.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.core.src4.utils.CharUtils;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Maps Integer (1 dimension) to a RGB Color (3 dimensions)
 * @author Charles Bentley
 *
 */
public class IntToColor {

   private SwingCtx sc;

   public IntToColor(SwingCtx sc) {
      this.sc = sc;
   }

   public int getColorDarkFgOp(int iv) {
      if (iv == 0) {
         return ColorUtils.FR_GRIS_BLANC;
      } else {
         if (iv <= 5) {
            return ColorUtils.FR_VIOLET_Pourpre;
         } else if (iv <= 50) {
            return ColorUtils.FR_ROUGE_Coquelicot;
         } else if (iv <= 500) {
            return ColorUtils.FR_ORANGE_Orange;
         } else {
            return ColorUtils.FR_BLEU_Petrole;
         }
      }
   }

   /**
    * 
    * @param iv
    * @return
    */
   public int getColorLightFgOp(int iv) {
      if (iv == 0) {
         return ColorUtils.FR_GRIS_BLANC;
      } else {
         if (iv <= 5) {
            return ColorUtils.FR_JAUNE_Ble;
         } else if (iv <= 50) {
            return ColorUtils.FR_ORANGE_Orange;
         } else if (iv <= 500) {
            return ColorUtils.FR_ROUGE_Coquelicot;
         } else {
            return ColorUtils.FR_VIOLET_Pourpre;
         }
      }
   }

   public Color getColorFromWhite(int account) {
      int white = 0xFFFFFF;
      int color = white - account;
      return new Color(color);
   }

   public Color getColorDarkBgName(String str) {
      return Color.BLACK;
   }

   public Color getColorLightBgName(String str) {
      int red = 255;
      int green = 255;
      int blue = 255;

      str = str.toLowerCase();
      if (str.length() < 3) {
         str = str + "aaaa";
      }
      CharUtils cu = sc.getUCtx().getCU();
      int unit = str.length() / 3;
      int countC = 0;
      int v = 0;
      for (int j = 0; j < unit; j++) {
         char c = str.charAt(countC);
         v += cu.mapZero(c);
         countC++;
      }
      v = v / unit;
      red -= v;
      v = 0;
      for (int j = 0; j < unit; j++) {
         char c = str.charAt(countC);
         v += cu.mapZero(c);
         countC++;
      }
      v = v / unit;
      green -= v;
      v = 0;
      int cc = 0;
      for (int j = countC; j < str.length(); j++) {
         char c = str.charAt(countC);
         v += cu.mapZero(c);
         countC++;
         cc++;
      }
      v = v / cc;
      blue -= v;
      //aaa aaa
      //zzz zzz zzz is gray

      //split word in 3.. take average

      return new Color(red, green, blue);
   }

   private HashMap<String, Color> colorsMiners;

   public HashMap<String, Color> getColorsMiners() {
      if (colorsMiners == null) {
         colorsMiners = new HashMap<String, Color>();
      }
      return colorsMiners;
   }

   /**
    * Get the color for the miner key
    * @param str
    * @return
    */
   public Color getColorLightBgNameMiner(String str) {
      //first look in the cache
      HashMap<String, Color> colors = getColorsMiners();
      Color colorMiner = colors.get(str);
      if (colorMiner != null) {
         return colorMiner;
      }
      char c0 = str.charAt(0);
      int redBias = 0;
      int greenBias = 0;
      int blueBias = 0;
      if (c0 == 'N') {
         redBias = 30;
         greenBias = 30;
      } else if (c0 == 'F') {
         redBias = 30;
         blueBias = 30;
      } else if (c0 == 'O') {
         greenBias = 30;
         blueBias = 30;
      }
      int red = 255 - redBias;
      int green = 255 - greenBias;
      int blue = 255 - blueBias;

      str = str.toLowerCase();
      if (str.length() < 3) {
         str = str + "aaaa";
      }
      CharUtils cu = sc.getUCtx().getCU();
      int unit = str.length() / 3;
      int countC = 0;
      int v = 0;
      for (int j = 0; j < unit; j++) {
         char c = str.charAt(countC);
         v += cu.mapZero(c);
         countC++;
      }
      v = v / unit;
      red -= v;
      v = 0;
      for (int j = 0; j < unit; j++) {
         char c = str.charAt(countC);
         v += cu.mapZero(c);
         countC++;
      }
      v = v / unit;
      green -= v;
      v = 0;
      int cc = 0;
      for (int j = countC; j < str.length(); j++) {
         char c = str.charAt(countC);
         v += cu.mapZero(c);
         countC++;
         cc++;
      }
      v = v / cc;
      blue -= v;
      //aaa aaa
      //zzz zzz zzz is gray

      //split word in 3.. take average

      colorMiner = new Color(red, green, blue);
      colorsMiners.put(str, colorMiner);
      return colorMiner;
   }

   /**
    * 
    * @param v0 0 to 9 modifies the blue factor 
    * @param v1 00 to 99
    * @param v2
    * @return
    */
   public int getWhiteToBlue(int v0, int v1, int v2) {
      int red = 255;
      int green = 255;
      int blue = 255;
      blue -= v0 * 4;
      //limit is 128 ?
      int v12 = v1 / 2;
      red -= v12;
      green -= v12;
      int v22 = v2 / 2;
      red -= v22;
      green -= v22;
      return ColorUtils.getRGBInt(red, green, blue);
   }

   public int getBlueToGreen(int v0, int v1, int v2) {
      int red = 50;
      int green = 50;
      int blue = 255;
      red += v0 * 4;
      //limit is 128 ?
      int v12 = v1 / 2;
      green += v12;
      blue -= v12;
      int v22 = v2 / 2;
      green += v22;
      blue -= v22;
      //go to
      return ColorUtils.getRGBInt(red, green, blue);
   }

   public int getGreenToRed(int v0, int v1, int v2) {
      int red = 50;
      int green = 255;
      int blue = 50;
      green -= v0 * 4;
      //limit is 128 ?
      int v12 = v1 / 2;
      red += v12;
      blue += v12;
      int v22 = v2 / 2;
      red += v22;
      blue += v22;
      //go to
      return ColorUtils.getRGBInt(red, green, blue);
   }

   public int getRedToGrey(int v0, int v1, int v2) {
      int red = 255;
      int green = 50;
      int blue = 50;
      int v12 = v1 / 2;
      int v22 = v2 / 2;
      red -= v12 + v22;

      red += v12;
      blue += v12;
      red += v22;
      blue += v22;
      //go to
      return ColorUtils.getRGBInt(red, green, blue);
   }

   public int getGreyToYellow(int v0, int v1, int v2) {
      int red = 128;
      int green = 128;
      int blue = 128;
      int v12 = v1 / 2;
      int v22 = v2 / 2;
      green += v0 * 4;

      red += v12;
      blue += v12;
      red += v22;
      blue += v22;
      //go to
      return ColorUtils.getRGBInt(red, green, blue);
   }

   private ArrayList<Color> colorsCarouselLight;

   public ArrayList<Color> getColorsCarouselLight() {
      if (colorsCarouselLight == null) {
         colorsCarouselLight = new ArrayList<Color>();
         colorsCarouselLight.add(new Color(ColorUtils.FR_VIOLET_Orchidee_moyen));
         colorsCarouselLight.add(new Color(ColorUtils.FR_VERT_Jaune));
         colorsCarouselLight.add(new Color(ColorUtils.FR_ROUGE_Corail_clair));
         colorsCarouselLight.add(new Color(ColorUtils.FR_BLEU_Azur));
         colorsCarouselLight.add(new Color(ColorUtils.FR_JAUNE_Or));
         colorsCarouselLight.add(new Color(ColorUtils.FR_ROSE_Clair));
         colorsCarouselLight.add(new Color(ColorUtils.FR_VERT_Ocean_moyen));
         colorsCarouselLight.add(new Color(ColorUtils.FR_BRUN_Sable));
         colorsCarouselLight.add(new Color(ColorUtils.FR_BLEU_Acier));
      }

      return colorsCarouselLight;
   }

   private int colorsCarouselLightIndex = 0;

   public void resetLighBgCarrouselIndex() {
      colorsCarouselLightIndex = 0;
   }

   public void incrementLighBgCarrouselIndex() {
      ArrayList<Color> colors = getColorsCarouselLight();
      colorsCarouselLightIndex = (colorsCarouselLightIndex + 1) % colors.size();
   }

   /**
    * 
    * @param account
    * @return
    */
   public Color getColorLightBgCarrousel(int account) {
      ArrayList<Color> colors = getColorsCarouselLight();
      return colors.get(colorsCarouselLightIndex);
   }

   public Color getColorLightBgAccount(int account) {
      return new Color(0xFFFFFF - account);
   }

   public Color getColorLightBgAccount2(int account) {

      //pad with zeros for at least 6 chars
      StringUtils su = sc.getUCtx().getStrU();
      String str = su.prettyInt0Padd(account, 6);
      //
      int len = str.length();
      String sub = str.substring(len - 6, len - 1);
      String colorChars = sub.substring(0, 2);
      String colorSelect = sub.substring(2, 4);
      String colorMod = sub.substring(4, 6);
      char c = colorChars.charAt(0);
      int v0 = Integer.valueOf(colorChars.substring(1, 2));
      int v1 = Integer.valueOf(colorSelect);
      int v2 = Integer.valueOf(colorMod);

      int color = 0xFFFFFF;
      switch (c) {
         case '0':
            //white to bleu
            color = getWhiteToBlue(v0, v1, v2);
            break;
         case '1':
            //blue base to gree
            color = getBlueToGreen(v0, v1, v2);
            break;
         case '2':
            //green to red
            color = getGreenToRed(v0, v1, v2);
            break;
         case '3':
            color = getRedToGrey(v0, v1, v2);
            break;
         case '4':
            color = getGreyToYellow(v0, v1, v2);
            break;
         case '5':
            break;
         case '6':
            break;
         case '7':
            break;
         case '8':
            break;
         case '9':
            break;

         default:
            break;
      }
      int red = ColorUtils.getRed(color);
      int green = ColorUtils.getGreen(color);
      int bleu = ColorUtils.getBlue(color);

      //#debug
      sc.toDLog().pTest("account -> red=" + red + " green=" + green + " blue" + bleu, null, IntToColor.class, "getColorDarkBgAccount", ITechLvl.LVL_05_FINE, true);

      //#debug
      //toDLog().pTest("", null, PascalSwingCtx.class, "getAccountColorLight", IDLog.LVL_05_FINE, true);
      return new Color(red, green, bleu);
   }

   /**
    * Use veins.
    * 1 millions 
    * 
    * 0 = 1 000 000 
    * 
    * 1st 100 000 = red
    * 2nd 200 000 = green
    * 3rd 300 000 = blue
    * 4th 400 000 = yellow
    * 5th 500 000 =  
    * @param account
    * @return
    */
   public Color getColorDarkBgAccount(int account) {
      int red = 0;
      int green = 0;
      int bleu = 0;
      int count = 128;
      while (account != 0 && count != 0) {
         //red alone
         if (account != 0) {
            red += 1;
            account--;
         }
         //red green
         if (account != 0) {
            green += 1;
            account--;
         }
         //green blue
         if (account != 0) {
            red--;
            account--;
         }
         //blue
         if (account != 0) {
            green--;
            account--;
         }
         //green
         if (account != 0) {
            bleu -= 1;
            green += 1;
            account--;
         }
         //red and blue
         if (account != 0) {
            red += 1;
            green -= 1;
            bleu += 1;
            account--;
         }
         //red green blue
         if (account != 0) {
            green += 1;
            account--;
         }
         //do over until
         count -= 1;
      }
      //#debug
      sc.toDLog().pTest("account -> red=" + red + " green=" + green + " blue" + bleu, null, IntToColor.class, "getColorDarkBgAccount", ITechLvl.LVL_05_FINE, true);

      if (red > 128) {
         red = 128;
      }
      if (green > 128) {
         green = 128;
      }
      if (bleu > 128) {
         bleu = 128;
      }
      //#debug
      //toDLog().pTest("", null, PascalSwingCtx.class, "getAccountColorLight", IDLog.LVL_05_FINE, true);
      return new Color(red, green, bleu);
   }

   public Color getColorLightBlockTimeAbove(int diff) {
      //best case
      Color color = Color.WHITE;
      if (diff < 10) {
      } else if (diff < 30) {
         return new Color(ColorUtils.FR_ROSE_Brumeux);
      } else if (diff < 60) {
         return new Color(ColorUtils.FR_ROSE_Clair);
      } else if (diff < 120) {
         return new Color(ColorUtils.FR_ROSE_Fuchsia);
      } else if (diff < 180) {
         return new Color(ColorUtils.FR_ROSE_Profond);
      } else if (diff < 240) {
         return new Color(ColorUtils.FR_ROSE_Rubis);
      } else {
         return new Color(ColorUtils.FR_ROUGE_Bordeaux);
      }
      return color;
   }

   public Color getColorLightBlockTimeBelow(int diff) {
      Color color = Color.WHITE;
      if (diff < 10) {
      } else if (diff < 30) {
         return new Color(ColorUtils.FR_BLEU_Bleuet);
      } else if (diff < 60) {
         return new Color(ColorUtils.FR_BLEU_Ciel);
      } else if (diff < 120) {
         return new Color(ColorUtils.FR_CYAN_Bleu_canard);
      } else if (diff < 180) {
         return new Color(ColorUtils.FR_CYAN_Turquoise_fonce);
      } else if (diff < 240) {
         return new Color(ColorUtils.FR_BLEU_Bleuet);
      } else {
         return new Color(ColorUtils.FR_VIOLET_Byzantin);
      }
      return color;
   }
}
