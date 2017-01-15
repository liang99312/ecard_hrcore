/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.utils;

import javax.swing.UIManager;

/**
 *
 * @author mxliteboss
 */
public class JVM {

    public static final int JDK1_0 = 10;
    public static final int JDK1_1 = 11;
    public static final int JDK1_2 = 12;
    public static final int JDK1_3 = 13;
    public static final int JDK1_4 = 14;
    public static final int JDK1_5 = 15;
    public static final int JDK1_6 = 16;
    public static final int JDK1_6_U10_AND_AFTER = 17;
    public static boolean isJDK1_6_U10 = false;
    public static boolean isJDK1_6_U11 = false;
    public static final int JDK1_7 = 30;
    public static final int JDK1_8 = 31;
    public static final int JDK1_9 = 32;
    private static JVM current = new JVM();
    private int jdkVersion;

    public static JVM current() {
        return current;
    }

    public JVM() {
        this(System.getProperty("java.version"));
    }

    public JVM(String p_JavaVersion) {
        if (p_JavaVersion.startsWith("1.9.")) {
            this.jdkVersion = 32;
        } else if (p_JavaVersion.startsWith("1.8.")) {
            this.jdkVersion = 31;
        } else if (p_JavaVersion.startsWith("1.7.")) {
            this.jdkVersion = 30;
        } else if (p_JavaVersion.startsWith("1.6.")) {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ((!"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel".equals(info.getClassName()))
                        && (!"javax.swing.plaf.nimbus.NimbusLookAndFeel".equals(info.getClassName()))) {
                    continue;
                }
                this.jdkVersion = 17;
                break;
            }

            if (this.jdkVersion == 17) {
                if (p_JavaVersion.startsWith("1.6.0_10")) {
                    isJDK1_6_U10 = true;
                }
                if (p_JavaVersion.startsWith("1.6.0_11")) {
                    isJDK1_6_U11 = true;
                }

            }

            this.jdkVersion = (this.jdkVersion == 0 ? 16 : this.jdkVersion);
        } else if (p_JavaVersion.startsWith("1.5.")) {
            this.jdkVersion = 15;
        } else if (p_JavaVersion.startsWith("1.4.")) {
            this.jdkVersion = 14;
        } else if (p_JavaVersion.startsWith("1.3.")) {
            this.jdkVersion = 13;
        } else if (p_JavaVersion.startsWith("1.2.")) {
            this.jdkVersion = 12;
        } else if (p_JavaVersion.startsWith("1.1.")) {
            this.jdkVersion = 11;
        } else if (p_JavaVersion.startsWith("1.0.")) {
            this.jdkVersion = 10;
        } else {
            this.jdkVersion = 13;
        }
    }

    public boolean isOrLater(int p_Version) {
        return this.jdkVersion >= p_Version;
    }

    public boolean isOneDotOne() {
        return this.jdkVersion == 11;
    }

    public boolean isOneDotTwo() {
        return this.jdkVersion == 12;
    }

    public boolean isOneDotThree() {
        return this.jdkVersion == 13;
    }

    public boolean isOneDotFour() {
        return this.jdkVersion == 14;
    }

    public boolean isOneDotFive() {
        return this.jdkVersion == 15;
    }

    public boolean isOneDotSix() {
        return (this.jdkVersion == 16)
                || (this.jdkVersion == 17);
    }

    public boolean isOneDotSixUpdate12OrAfter() {
        return ((this.jdkVersion == 17)
                && (!isJDK1_6_U10) && (!isJDK1_6_U11))
                || (this.jdkVersion >= 30);
    }

    public boolean isOneDotSeven() {
        return this.jdkVersion == 30;
    }

    public boolean isOneDotEight() {
        return this.jdkVersion == 31;
    }

    public boolean isOneDotNine() {
        return this.jdkVersion == 32;
    }
}
