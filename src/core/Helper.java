package core;

import javax.swing.*;
import java.awt.*;

public class Helper {

    // Tema ayarlarını tanımlar
    public static void setTheme(){
        optionPaneTR();
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;

            }
        }
    }
    // Mesajları ekranda gösterir
    public static void showMsg(String str){
        String msg;
        String title;

        switch (str){
            case "fill" -> {
                msg = "Lütfen tüm alanları doldurunuz!";
                title = "Hata !";
            }
            case "done" -> {
                msg = "İşlem Başarılı !";
                title = "Sonuç";
            }
            case "notFound" -> {
                msg = "Kayıt bulunamadı!";
                title = "Bulunamadı";
            }
            case "error" -> {
                msg = "Hatalı işlem yaptınız!";
                title = "Hata!";
            }
            default -> {
                msg = str;
                title = "Mesaj";
            }
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    // Kullanıcıdan onay alır.
    public static boolean confirm(String str){
        String msg;
        if (str.equals("sure")){
            msg = "Bu işlemi yapmak istediğine emin misin ?";
        }else {
            msg =str;
        }

        return JOptionPane.showConfirmDialog(null,msg,"Emin misin ?",JOptionPane.YES_NO_OPTION) == 0;
    }

    // Bir JTextField'ın boş olup olmadığını kontrol eder
    public static boolean isFieldEmpty(JTextField field, JTextField fld_pass){
        return field.getText().trim().isEmpty();
    }

    // Bir JTextField listesindeki alanların boş olup olmadığını kontrol eder
    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (field.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    // Pencerenin konumunu ayarlar
    public static  int getLocationPoint(String type, Dimension size){
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };

    }
    // Dialog mesajlarını Türkçe'ye çevirir
    public static void optionPaneTR(){
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");
    }
}

