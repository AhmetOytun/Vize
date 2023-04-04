//Gerekli Kütüphaneleri Import Ediyoruz.
import java.util.Scanner;
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
import java.io.FileReader;
import java.io.PrintWriter;
public class Main{
    public static void main(String[] args) throws IOException {
        //Nesenelerimizi Tanımlıyoruz
        DosyaIslemleri d=new DosyaIslemleri();
        ElitÜye e=new ElitÜye();
        GenelÜye g=new GenelÜye();
        MailYolla m=new MailYolla();
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
                        for (int i=0;i<ElitMailleri.size();i++){//for Döngüsü Sayesinde Listedeki Bütün Mail Adreslerini Tarıyoruz.
                            m.MailDetay(Başlik, İçerik, ElitMailleri.get(i));//Taradığımız Mail Adreslerini Mail Yollama Fonksiyonuna Yolluyoruz.
                            System.out.println(i+1+".Mail Basariyla Gonderildi!");
                        }
                        continue;
                    case 2://Mail Başlık Ve İçeriklerini Alıyoruz.
                        System.out.println("Mail Basligi:");
                        s.nextLine();
                        Başlik=s.nextLine();
                        System.out.println("Mail Içerigi:");
                        İçerik=s.nextLine();
                        for (int i=0;i<GenelMailleri.size();i++){//for Döngüsü Sayesinde Listedeki Bütün Mail Adreslerini Tarıyoruz.
                            m.MailDetay(Başlik, İçerik, GenelMailleri.get(i));//Taradığımız Mail Adreslerini Mail Yollama Fonksiyonuna Yolluyoruz.
                            System.out.println(i+1+".Mail Basariyla Gonderildi!");
                        }
                        continue;
                    case 3:
                        //Bütün Mailleri Tutacak Olan Bütün Mailler Listesini Oluşturup Diğer İki Liste İle Birleştiriyoruz.
                        List<String> BütünMailler = Stream.concat(GenelMailleri.stream(), ElitMailleri.stream()).toList();

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
    }
}
class MailYolla{
void MailDetay(String Baslik,String İçerik,String MailAdresi){//Mail Yollayacak Metodumuzu Oluşturuyoruz.
    //Kullanıcıdan Mail Bilgilerini Alıyoruz.
    final String kullaniciadi = "ahmetoytunkurtuldu@gmail.com";
    final String uygulamaşifresi = "hcmyxgmgamccmjrl";//ÖNEMLİ!!! ŞİFRE OLARAK HESABIN ŞİFRESİNİ DEĞİL UYGULAMA ŞİFRESİNİ GİRİYORUZ!(Gmail İçin:[https://support.google.com/mail/answer/185833?hl=tr]).

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

        FileWriter d1=new FileWriter("Elit.txt");//Elit Üyeler İçin txt Dosyası Oluşturuluyor.
        d1.write("#ELİTÜYELER#\n");
        d1.close();//Dosya Kapatılıyor.

        FileWriter d2=new FileWriter("Genel.txt");//Genel Üyeler İçin txt Dosyası Oluşturuluyor.
        d2.write("#GENELÜYELER#\n");
        d2.close();//Dosya Kapatılıyor.

        FileWriter d3=new FileWriter("Kullanıcılar.txt");//ÖNEMLİ!! Vize Projesinde İstenilen Kullanıcılar.txt Dosyası Oluşturuluyor. 
        d3.close();//Dosya Kapatılıyor.
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
