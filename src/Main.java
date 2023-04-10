//Github Linki="https://github.com/AhmetOytun/Vize".
//Gerekli Kütüphaneleri Import Ediyoruz.
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
//Mail Kütüphanesini Dışarıdan Import Ettiğimiz İçin Dosyaları Olmadan Çalışmayacaktır(bkz. activation.jar,javax.mail.jar).
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
public class Main{
    public static void main(String[] args) throws IOException {
        //Nesenelerimizi Tanımlıyoruz
        DosyaIslemleri d=new DosyaIslemleri();
        ElitÜye e=new ElitÜye();
        GenelÜye g=new GenelÜye();
        MailYolla m=new MailYolla();
        d.GeciciDosyaOku();
        d.GeciciBirleştir();
        //Dosya İşlemlerinden Dosya Oluştur Methodunu Çağırıyoruz.
        d.DosyaOluştur();
        //Değişkenlerimizi Tanımlıyoruz
        String İsim,Soyİsim,Mail,Başlik,İçerik;
        Scanner s=new Scanner(System.in);
        int karar=0;
        //Mail Adreslerini Tutacak Listelerimizi Tanımlıyoruz
        List<String> ElitMailleri = new ArrayList<>();
        List<String> GenelMailleri = new ArrayList<>();
        //Menü Sadece İstendiği Durumda Kapansın Diye Ayrıca Bir Değişken Tanımlıyoruz.
        while(karar!=1){
            System.out.println("1-Elit Üye Ekleme");
            System.out.println("2-Genel Üye Ekleme");
            System.out.println("3-Mail Gönderme");
            System.out.println("4-Çikis");
            int seçim1=s.nextInt();
            //İlk Menü Başlangıcı.
            switch(seçim1){
                case 1:
                    System.out.println("Isim:");
                    s.nextLine();//Input Bufferı Boşaltıyoruz Ki Alttaki "s.nextline" İle Çakışmasın.
                    İsim=s.nextLine();
                    System.out.println("SoyIsim:");
                    Soyİsim=s.nextLine();
                    System.out.println("Mail Adresi:");
                    Mail=s.nextLine();
                    ElitMailleri.add(Mail);//Elit Üye Maillerini Listeye Ekliyoruz.
                    e.ElitÜyeEkle(İsim, Soyİsim, Mail);//Elit Üye Ekleme Metodunu Çağırıyoruz.
                    break;
                case 2:
                    System.out.println("Isim:");
                    s.nextLine();
                    İsim=s.nextLine();
                    System.out.println("SoyIsim:");
                    Soyİsim=s.nextLine();
                    System.out.println("Mail Adresi:");
                    Mail=s.nextLine();
                    GenelMailleri.add(Mail);//Genel Üye Maillerini Listeye Ekliyoruz.
                    g.GenelÜyeEkle(İsim, Soyİsim, Mail); //Genel Üye Ekleme Metodunu Çağırıyoruz.
                    break;
                case 3://İç İçe Menüye Geçiyoruz.
                    System.out.println("1-Elit Üyelere Mail");
                    System.out.println("2-Genel Üyelere Mail");
                    System.out.println("3-Tüm Üyelere Mail");
                    System.out.println("4-Onceki Menu");
                    int seçim2=s.nextInt();
                    switch(seçim2){//İç İçe Menü Başlangıcı.
                    case 1://Mail Başlık Ve İçeriklerini Alıyoruz.
                        System.out.println("Mail Basligi:");
                        s.nextLine();
                        Başlik=s.nextLine();
                        System.out.println("Mail Içerigi:");
                        İçerik=s.nextLine();
                        for (int i=0;i<d.ÖncekiElitMailleriBirleştir().size();i++){//for Döngüsü Sayesinde Listedeki Bütün Mail Adreslerini Tarıyoruz.
                            m.MailDetay(Başlik, İçerik, d.ÖncekiElitMailleriBirleştir().get(i));//Taradığımız Mail Adreslerini Mail Yollama Fonksiyonuna Yolluyoruz.
                            System.out.println(i+1+".Mail Basariyla Gonderildi!");
                        }
                        continue;
                    case 2://Mail Başlık Ve İçeriklerini Alıyoruz.
                        System.out.println("Mail Basligi:");
                        s.nextLine();
                        Başlik=s.nextLine();
                        System.out.println("Mail Içerigi:");
                        İçerik=s.nextLine();
                        for (int i=0;i<d.ÖncekiGenelMailleriBirleştir().size();i++){//for Döngüsü Sayesinde Listedeki Bütün Mail Adreslerini Tarıyoruz.
                            m.MailDetay(Başlik, İçerik, d.ÖncekiGenelMailleriBirleştir().get(i));//Taradığımız Mail Adreslerini Mail Yollama Fonksiyonuna Yolluyoruz.
                            System.out.println(i+1+".Mail Basariyla Gonderildi!");
                        }
                    continue;
                    case 3:
                        //Bütün Mailleri Tutacak Olan Bütün Mailler Listesini Oluşturup Diğer İki Liste İle Birleştiriyoruz.
                        List<String> BütünMailler = Stream.concat(d.ÖncekiElitMailleriBirleştir().stream(), d.ÖncekiGenelMailleriBirleştir().stream()).toList();

                        System.out.println("Mail Basligi:");
                        s.nextLine();
                        Başlik=s.nextLine();
                        System.out.println("Mail Içerigi:");
                        İçerik=s.nextLine();
                        for (int i=0;i<BütünMailler.size();i++){//for Döngüsü Sayesinde Listedeki Bütün Mail Adreslerini Tarıyoruz.
                            m.MailDetay(Başlik, İçerik, BütünMailler.get(i));//Taradığımız Mail Adreslerini Mail Yollama Fonksiyonuna Yolluyoruz.
                            System.out.println(i+1+".Mail Basariyla Gonderildi!");
                        }
                        continue;
                    case 4:
                        continue;//Bu Sayede Tekrardan Menüye Dönmeyi Sağlıyoruz
                    default://Hata Sonucunda Göreceğimiz Ekran.
                        System.out.println("Hatali Bir Giris Yaptiniz!");
                        System.out.println("Program Kapaniyor...");
                        karar=1;
                    }
                case 4:
                    karar=1;
                    break;
                default://Hata Sonucunda Göreceğimiz Ekran.
                    System.out.println("Hatali Bir Giris Yaptiniz!");
                    System.out.println("Program Kapaniyor...");
                    karar=1;
            }
        }
        s.close();//Scanner'ı Kapatıyoruz.
        d.GeciciDosyaSil();//Geçici Dosyaları Siliyoruz.
    }
}
class MailYolla{
void MailDetay(String Baslik,String İçerik,String MailAdresi){//Mail Yollayacak Metodumuzu Oluşturuyoruz.
    //Kullanıcıdan Mail Bilgilerini Alıyoruz.
    final String kullaniciadi = "GMAIL ADRESİNİ BURAYA YAZIN";
    final String uygulamaşifresi = "GMAIL UYGULAMA ŞİFRESİNİ BURAYA YAZIN";//ÖNEMLİ!!! ŞİFRE OLARAK HESABIN ŞİFRESİNİ DEĞİL UYGULAMA ŞİFRESİNİ GİRİYORUZ!(Gmail İçin:[https://support.google.com/mail/answer/185833?hl=tr]).

    Properties bilgiler = new Properties();
    bilgiler.put("mail.smtp.auth", "true");
    bilgiler.put("mail.smtp.starttls.enable", "true");
    bilgiler.put("mail.smtp.host", "smtp.gmail.com");//Gmail smtp Adres Bilgilerini Giriyoruz
    bilgiler.put("mail.smtp.port", "587");//Gmail smtp Adres Bilgilerini Giriyoruz

    Session session = Session.getInstance(bilgiler,
      new javax.mail.Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(kullaniciadi, uygulamaşifresi);//smtp Serverına Verilen Bilgiler İle Giriş Yapılıyor.
        }
      });
    session.setDebug(false);//Programı Kullanırken Çıkan Debug Bilgilerininin Ekranımızda Gözükmemesini Sağlıyoruz.
    try {

        Message mesaj = new MimeMessage(session);
        mesaj.setFrom(new InternetAddress(kullaniciadi));//Yollayan Kişinin Kullanıcı Adı Sisteme Sağlanıyor.
        mesaj.setRecipients(Message.RecipientType.TO,
        InternetAddress.parse(MailAdresi));//Mail Alıcısının Mail Adresi Alınıyor.
        mesaj.setSubject(Baslik);//Mail'in Başlığı Alınıyor.
        mesaj.setText(İçerik);//Mail'in İçeriği Alınıyor.
        Transport.send(mesaj);//Mesaj Yollama Fonksiyonu Çağrılarak Gönderiliyor.

    } catch (MessagingException e) {//Hata Oluşursa Ekrana Vermeyi Sağlayacak Fonksiyon.
        throw new RuntimeException(e);
    }
  }
}
class DosyaIslemleri {
    void DosyaOluştur() throws IOException{

        FileWriter d1=new FileWriter("Kullanıcılar.txt");//ÖNEMLİ!! Vize Projesinde İstenilen Kullanıcılar.txt Dosyası Oluşturuluyor. 
        d1.close();//Dosya Kapatılıyor.
    }
    void DosyaBirleştir()throws IOException{//ÖNEMLİ!! Kullanıcılar.txt Dosyasının İçeriği İstenilen Formata Getirilsin Diye Diğer 2 Dosyalar İle Birleştirilmesi Sağlanıyor.

        PrintWriter pw=new PrintWriter("Kullanıcılar.txt");//Kullanıcılar.txt Dosyasının Üzerine Yazacağız.
        BufferedReader br1=new BufferedReader(new FileReader("Elit.txt"));//İlk Önce Elit.txt Dosyasından Okumaya Başlıyoruz.
        String line=br1.readLine();//Satırları "line" İsimli Bir Stringe Adıyoruz.

        while(line!=null){//Dosyadaki Boş Olmayan Bütün Satırları Tarayıp Kullanıcılar.txt Dosyasına Yazıyoruz.
            pw.println(line);
            line=br1.readLine();
        }

        BufferedReader br2=new BufferedReader(new FileReader("Genel.txt"));//Genel.txt Dosyasını Okumaya Başlıyoruz.
        line=br2.readLine();

        while(line!=null){//Dosyadaki Boş Olmayan Bütün Satırları Tarayıp Kullanıcılar.txt Dosyasına Yazıyoruz.
            pw.println(line);
            line=br2.readLine();
        }
        pw.flush();//Streami Boşaltıyoruz.
        br1.close();//BufferedReader'ı Kapatıyoruz.
        br2.close();//BufferedReader'ı Kapatıyoruz.
        pw.close();//PrintWriter'ı Kapatıyoruz.
    }
    void GeciciDosyaOku()throws IOException{//ÖNEMLİ!!! Başında Gecici Olan Metodlar Sadece PROGRAM ÇALIŞMADAN ÖNCE Kullanıcılar.txt Dosyası Doluysa Çalışır! 
        //Bu Metodların Olmasının Sebebi Program Çalışamdan Önce .txt Dosyasında Bulunan Kişilere De Mail Yollanmasının Sağlanması!
        FileWriter d1=new FileWriter("Elit.txt");//Elit Üyeler Dosyasının Üzerine Önceden Eklenen Elit Üyeleri Ekleyeceğiz(Önceden=Program Çalışmadan Önce Veya Bir Önceki Çalışmasında).
        d1.write("#ELİTÜYELER#\n");
        d1.close();//Dosya Kapatılıyor.

        FileWriter d2=new FileWriter("Genel.txt");//Genel Üyeler İçin txt Dosyası Oluşturuluyor.
        d2.write("#GENELÜYELER#\n");
        d2.close();//Dosya Kapatılıyor.

        FileWriter d3=new FileWriter("GeciciElit.txt");//Kullanıcılar.txt Dosyası Önceden Doluysa Onları Kaydedeceğimiz Yeri Oluşturuyoruz.
        FileWriter d4=new FileWriter("GeciciGenel.txt");//Kullanıcılar.txt Dosyası Önceden Doluysa Onları Kaydedeceğimiz Yeri Oluşturuyoruz.
        
        PrintWriter pw1=new PrintWriter("GeciciElit.txt");//Kullanıcılar.txt Dosyasının Üzerine Yazacağız.
        PrintWriter pw2=new PrintWriter("GeciciGenel.txt");//Kullanıcılar.txt Dosyasının Üzerine Yazacağız.

        BufferedReader br1=new BufferedReader(new FileReader("Kullanıcılar.txt"));//Kullanıcılar .txt Dosyasını Okuyoruz Eğer Önceden Bir Giriş Yapılmadıysa İşlem Yapmıyoruz.
        String line=br1.readLine();//Satırları "line" İsimli Bir Stringe Adıyoruz.
        if(line==null){//Kullanıcılar.txt Dosyası Önceden Dolu Değilse Hiçbir İşlem Yapma!
            pw1.flush();//Streami Boşaltıyoruz.
            pw2.flush();//Streami Boşaltıyoruz.
            br1.close();//BufferedReader'ı Kapatıyoruz.
            pw1.close();//PrintWriter'ı Kapatıyoruz.
            pw2.close();//PrintWriter'ı Kapatıyoruz.
            d1.close();//Yazıcıları Kapatalım.
            d2.close();//Yazıcıları Kapatalım.
            d3.close();//Yazıcıları Kapatalım.
            d4.close();//Yazıcıları Kapatalım.
            return;
        }
            if(line.contains("ELİT")){//Başlıkları Ayırt Etmek İçin Kullandığım Bir Fonksiyon.
                line=br1.readLine();
                while(line.contains("GENEL")==false){//Aynı Şekilde Genel Yazısına Gelene Kadar Devam Ediyor.
                    pw1.println(line);
                    line=br1.readLine();
                }
            }
            if(line.contains("GENEL")){//Başlıkları Ayırt Etmek İçin Kullandığım Bir Fonksiyon.
                line=br1.readLine();
                while(line!=null){//Aynı Şekilde null Olana Kadar Kadar Devam Ediyor.
                    pw2.println(line);
                    line=br1.readLine();
                }
            }
        
        pw1.flush();//Streami Boşaltıyoruz.
        pw2.flush();//Streami Boşaltıyoruz.
        br1.close();//BufferedReader'ı Kapatıyoruz.
        pw1.close();//PrintWriter'ı Kapatıyoruz.
        pw2.close();//PrintWriter'ı Kapatıyoruz.
        d1.close();//Yazıcıları Kapatıyoruz.
        d2.close();//Yazıcıları Kapatıyoruz.
        d3.close();//Yazıcıları Kapatıyoruz.
        d4.close();//Yazıcıları Kapatıyoruz.
    }
    void GeciciBirleştir()throws IOException{//Gecici Metodlarının Neden Var Olduklarını Üstteki Yorum Satırında Anlatmıştım, Aynısı Burada da Geçerli.
        FileWriter DosyaYaz1=new FileWriter("Elit.txt",true);//Asıl Elit Dosyamızın Üzerine Eski Kullanıcıları Ekleyeceğiz.
        FileWriter DosyaYaz2=new FileWriter("Genel.txt",true);//Asıl Genel Dosyamızın Üzerine Eski Kullanıcıları Ekleyeceğiz.

        BufferedReader br1=new BufferedReader(new FileReader("GeciciElit.txt"));//İlk Önce GeciciElit.txt Dosyasından Okumaya Başlıyoruz.
        String line=br1.readLine();//Satırları "line" İsimli Bir Stringe Adıyoruz.

        while(line!=null){//Dosyadaki Boş Olmayan Bütün Satırları Tarayıp Elit.txt Dosyasına Yazıyoruz(Neden Diye Sorarsanız Kişi Ekledikten Sonra Hepsini Kullanıcılar.txt Dosyasında Tutacağız).
            DosyaYaz1.write(line+"\n");
            line=br1.readLine();
        }
        BufferedReader br2=new BufferedReader(new FileReader("GeciciGenel.txt"));//GeciciGenel.txt Dosyasını Okumaya Başlıyoruz.
        line=br2.readLine();

        while(line!=null){//Dosyadaki Boş Olmayan Bütün Satırları Tarayıp Genel.txt Dosyasına Yazıyoruz(Neden Diye Sorarsanız Kişi Ekledikten Sonra Hepsini Kullanıcılar.txt Dosyasında Tutacağız).
            DosyaYaz2.write(line+"\n");
            line=br2.readLine();
        }
        br1.close();//BufferedReader'ı Kapatıyoruz.
        br2.close();//BufferedReader'ı Kapatıyoruz.
        DosyaYaz1.close();//PrintWriter'ı Kapatıyoruz.
        DosyaYaz2.close();//PrintWriter'ı Kapatıyoruz.

    }
    void GeciciDosyaSil(){//Geçici Dosyaları Silme Metodu
        File f1= new File("GeciciElit.txt");
        File f2= new File("GeciciGenel.txt");
        File f3= new File("Elit.txt");
        File f4= new File("Genel.txt");
        f1.delete();
        f2.delete();
        f3.delete();
        f4.delete();
    }
    List<String> ÖncekiGenelMailleriBirleştir()throws IOException{
        List<String> GeciciGenelMailleri = new ArrayList<>();//Eski Mailleri Dosya Üzerinden Okuyup Programa Dahil Edeceğiz!
        // E-Mail Patternını Oluşturuyoruz Ki .txt Dosyasından Mail Adreslerini Alabilelim.
        Pattern pat=Pattern.compile( "[a-zA-Z0-9]" + "[a-zA-Z0-9_.]" + "*@[a-zA-Z0-9]" + "+([.][a-zA-Z]+)+"); 
        BufferedReader Okuyucu = new BufferedReader(new FileReader("Genel.txt")); //Genel.txt Dosyasını Okumaya Başlıyoruz.
        
        String line = Okuyucu.readLine(); 
        while (line != null) { 
        Matcher mat = pat.matcher(line); 
        while (mat.find()) { 
        GeciciGenelMailleri.add(mat.group());
        } 
        line = Okuyucu.readLine(); 
        } 
        Okuyucu.close();
        return GeciciGenelMailleri;//Mail Adreslerini Birleştirmek Üzere Return Ediyoruz.
    }
    List<String> ÖncekiElitMailleriBirleştir()throws IOException{
        List<String> GeciciElitMailleri = new ArrayList<>();//Eski Mailleri Dosya Üzerinden Okuyup Programa Dahil Edeceğiz!
        // E-Mail Patternını Oluşturuyoruz Ki .txt Dosyasından Mail Adreslerini Alabilelim.
        Pattern pat=Pattern.compile( "[a-zA-Z0-9]" + "[a-zA-Z0-9_.]" + "*@[a-zA-Z0-9]" + "+([.][a-zA-Z]+)+"); 
        BufferedReader Okuyucu = new BufferedReader(new FileReader("Elit.txt")); //Elit.txt Dosyasını Okumaya Başlıyoruz.

        String line = Okuyucu.readLine(); 
        while (line != null) { 
        Matcher mat = pat.matcher(line); 
        while (mat.find()) { 
        GeciciElitMailleri.add(mat.group());
        } 
        line = Okuyucu.readLine(); 
        } 
        Okuyucu.close();
        return GeciciElitMailleri;//Mail Adreslerini Birleştirmek Üzere Return Ediyoruz.
    }
}
class ElitÜye extends DosyaIslemleri{
    void ElitÜyeEkle(String İsim,String Soyİsim,String Mail) throws IOException{
        //Elit.txt Dosyasının Üzerine Yazacak Şekilde Dosyayı Açıyoruz.
        FileWriter DosyaYaz=new FileWriter("Elit.txt",true);
        DosyaYaz.write(İsim+"\t"+Soyİsim+"\t"+Mail+"\n");//İsim Soyisim Ve Mailleri İstenilen Formatta Dosyamıza Yazıyoruz.
        DosyaYaz.close();//Dosyayı Kapatıyoruz.

        super.DosyaBirleştir();//Kullanıcılar.txt Dosyasına Bu Bilgileri Yazıyoruz.
    }
}
class GenelÜye extends DosyaIslemleri{
    void GenelÜyeEkle(String İsim,String Soyİsim,String Mail) throws IOException{
        //Genel.txt Dosyasının Üzerine Yazacak Şekilde Dosyayı Açıyoruz.
        FileWriter DosyaYaz=new FileWriter("Genel.txt",true);
        DosyaYaz.write(İsim+"\t"+Soyİsim+"\t"+Mail+"\n");//İsim Soyisim Ve Mailleri İstenilen Formatta Dosyamıza Yazıyoruz.
        DosyaYaz.close();//Dosyayı Kapatıyoruz.

        super.DosyaBirleştir();//Kullanıcılar.txt Dosyasına Bu Bilgileri Yazıyoruz.
    }
}
