package pasa.cbentley.swing.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.utils.CharUtils;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.core.src4.utils.interfaces.IColors;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Maps Integer (1 dimension) to a RGB Color (3 dimensions)
 * @author Charles Bentley
 *
 */
public class IntToColor implements IColors {

   private SwingCtx sc;

   public IntToColor(SwingCtx sc) {
      this.sc = sc;
   }

   public int getColorDarkFgOp(int iv) {
      if (iv == 0) {
         return FR_GRIS_BLANC;
      } else {
         if (iv <= 5) {
            return FR_VIOLET_Pourpre;
         } else if (iv <= 50) {
            return FR_ROUGE_Coquelicot;
         } else if (iv <= 500) {
            return FR_ORANGE_Orange;
         } else {
            return FR_BLEU_Petrole;
         }
      }
   }

   /**
    * 
    * @param iv
    * @return
    */
   public Color getColorLightFgOp(int iv) {
      if (iv == 0) {
         return getColorsOpAccount().get(0);
      } else {
         if (iv <= 5) {
            return getColorsOpAccount().get(1);
         } else if (iv <= 50) {
            return getColorsOpAccount().get(2);
         } else if (iv <= 500) {
            return getColorsOpAccount().get(3);
         } else if (iv <= 5000) {
            return getColorsOpAccount().get(4);
         } else {
            return getColorsOpAccount().get(5);
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
         colorsCarouselLight.add(new Color(FR_VIOLET_Orchidee_moyen));
         colorsCarouselLight.add(new Color(FR_VERT_Jaune));
         colorsCarouselLight.add(new Color(FR_ROUGE_Corail_clair));
         colorsCarouselLight.add(new Color(FR_BLEU_Azur));
         colorsCarouselLight.add(new Color(FR_JAUNE_Or));
         colorsCarouselLight.add(new Color(FR_ROSE_Clair));
         colorsCarouselLight.add(new Color(FR_VERT_Ocean_moyen));
         colorsCarouselLight.add(new Color(FR_BLEU_Dragee));
         colorsCarouselLight.add(new Color(FR_BRUN_Sable));
         colorsCarouselLight.add(new Color(FR_VERT_Asperge));
         colorsCarouselLight.add(new Color(FR_BLEU_Acier));
         colorsCarouselLight.add(new Color(FR_JAUNE_Imperial));
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

   //   public Color getColorLightBgAccount(int account) {
   //      int rest = account;
   //      return new Color(0xFFFFFF - rest);
   //   }

   public Color getColorLightBgAccount(int account) {
      int r = 255;
      int b = 255;
      int g = 255;
      float ratio = account / 1000000;
      float ratioInverse = 1 - ratio;
      float saturation = ratioInverse;
      float brightness = ratioInverse;
      float accountF = ((float) account / 1670000f) * 360f;

      return Color.getHSBColor(accountF, saturation, brightness);
      //int rest = account >> 2;
      //return new Color(0xFFFFFF - account);
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

   private ArrayList<Color> colorsBlockTimeAbove;

   public ArrayList<Color> getColorsBlockTimeAbove() {
      if (colorsBlockTimeAbove == null) {
         colorsBlockTimeAbove = new ArrayList<Color>(8);
         colorsBlockTimeAbove.add(new Color(FR_VERT_Miellat));
         colorsBlockTimeAbove.add(new Color(FR_VERT_Deau));
         colorsBlockTimeAbove.add(new Color(FR_VERT_Anis));
         colorsBlockTimeAbove.add(new Color(FR_VERT_Absinthe));
         colorsBlockTimeAbove.add(new Color(FR_VERT_Amande));
         colorsBlockTimeAbove.add(new Color(FR_VERT_Ocean));
         colorsBlockTimeAbove.add(new Color(FR_VERT_Citron_vert));
         colorsBlockTimeAbove.add(new Color(FR_JAUNE_Bouton_dor));
      }
      return colorsBlockTimeAbove;
   }

   public Color getColorLightBlockTimeAbove(int diff) {
      //best case
      Color color = Color.WHITE;
      if (diff < 10) {
      } else if (diff < 30) {
         return getColorsBlockTimeAbove().get(0);
      } else if (diff < 60) {
         return getColorsBlockTimeAbove().get(1);
      } else if (diff < 120) {
         return getColorsBlockTimeAbove().get(2);
      } else if (diff < 180) {
         return getColorsBlockTimeAbove().get(3);
      } else if (diff < 240) {
         return getColorsBlockTimeAbove().get(4);
      } else if (diff < 600) {
         return getColorsBlockTimeAbove().get(5);
      } else if (diff < 1200) {
         return getColorsBlockTimeAbove().get(6);
      } else {
         return getColorsBlockTimeAbove().get(7);
      }
      return color;
   }

   private ArrayList<Color> colorsBlockTimeBelow;

   public ArrayList<Color> getColorsBlockTimeBelow() {
      if (colorsBlockTimeBelow == null) {
         colorsBlockTimeBelow = new ArrayList<Color>(6);
         colorsBlockTimeBelow.add(new Color(FR_GRIS_BLANC));
         colorsBlockTimeBelow.add(new Color(FR_BLEU_Fumee));
         colorsBlockTimeBelow.add(new Color(FR_BLEU_Acier_clair));
         colorsBlockTimeBelow.add(new Color(FR_BLEU_Ciel));
         colorsBlockTimeBelow.add(new Color(FR_BLEU_Acier));
         colorsBlockTimeBelow.add(new Color(FR_BLEU_Bleuet_fonce));
      }
      return colorsBlockTimeBelow;
   }

   private ArrayList<Color> colorsOpAccount;

   public ArrayList<Color> getColorsOpAccount() {
      if (colorsOpAccount == null) {
         colorsOpAccount = new ArrayList<Color>(6);
         colorsOpAccount.add(new Color(FR_GRIS_BLANC));
         colorsOpAccount.add(new Color(FR_JAUNE_Ble));
         colorsOpAccount.add(new Color(FR_ORANGE_Orange));
         colorsOpAccount.add(new Color(FR_ROUGE_Coquelicot));
         colorsOpAccount.add(new Color(FR_VIOLET_Pourpre));
         colorsOpAccount.add(new Color(FR_GRIS_Noir));
      }
      return colorsOpAccount;
   }

   public Color getColorLightBlockTimeBelow(int diff) {
      Color color = Color.WHITE;
      if (diff < 10) {
      } else if (diff < 30) {
         return getColorsBlockTimeBelow().get(0);
      } else if (diff < 60) {
         return getColorsBlockTimeBelow().get(1);
      } else if (diff < 120) {
         return getColorsBlockTimeBelow().get(2);
      } else if (diff < 180) {
         return getColorsBlockTimeBelow().get(3);
      } else if (diff < 240) {
         return getColorsBlockTimeBelow().get(4);
      } else {
         return getColorsBlockTimeBelow().get(5);
      }
      return color;
   }
}
