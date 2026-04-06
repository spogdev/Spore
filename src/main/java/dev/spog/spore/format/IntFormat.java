package dev.spog.spore.format;

public abstract class IntFormat {
    public static String secondToDurationString(int i) {
        if (i != 0) {
            int min = Math.floorDiv(i, 60);
            int sec = i % 60;
            String smin = Integer.toString(min);
            String ssec = Integer.toString(sec);
            String fsec = "";
            if (ssec.length() == 1) {
                fsec = "0" + ssec;
            } else {
                fsec = ssec;
            }
            String fin = smin + ":" + fsec;
            return fin;
        } else {
            return "0:00";
        }
    }

    public static String milliToDurationString(int i) {
        if (i != 0) {
            int min = Math.floorDiv(i, 1000);
            int sec = i % 1000;
            String smin = Integer.toString(min);
            String ssec = Integer.toString(sec);
            String fsec = "";
            if (ssec.length() == 1) {
                fsec = "0" + ssec;
            } else {
                fsec = ssec;
            }
            String fin = smin + ":" + fsec;
            return fin;
        } else {
            return "0:00";
        }
    }
}
