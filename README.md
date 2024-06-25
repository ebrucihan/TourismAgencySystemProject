# Turizm Acentesi Yönetim Sistemi Projesi

Bu proje, turizm acentenizin ihtiyaçlarını karşılayacak kapsamlı bir yönetim sistemi sunmaktadır. Aşağıda, projeye ilişkin genel bilgiler, gereksinimler ve kullanım kılavuzu detaylı olarak açıklanmaktadır.

## Veri Tabanı Bağlantısı
- **Veri tabanı URL**: `jdbc:postgresql://localhost:5432/tourismagencysystem`
- **Kullanıcı adı**: `postgres`
- **Şifre**: `2108`

Bağlantı bilgilerini kendi veri tabanı ayarlarınıza göre güncellemelisiniz.

## Özellikler
- Müşteri kayıt ve yönetimi
- Oda rezervasyonu ve yönetimi
- Otel ve oda bilgileri yönetimi
- Kullanıcı rolleri ve yetkilendirme
- Kullanıcı dostu ve etkileşimli arayüz


## Gereksinimler
- Java 8 veya üzeri sürüm
- Swing kütüphanesi
- JDBC sürücüsü (PostgreSQL için)

## Nasıl Kullanılır?

### Proje İndirme ve Yükleme
1. Depoyu bilgisayarınıza indirin veya klonlayın.
2. Java IDE (IntelliJ IDEA, Eclipse vb.) ile projeyi açın.

### Veri Tabanı Ayarları
1. PostgreSQL üzerinde `tourismagencysystem` adında bir veri tabanı oluşturun.
2. `Db.java` dosyasını açarak veri tabanı bağlantı bilgilerinizi güncelleyin.

### Yazılımın Çalıştırılması
1. Veritabanınızı ve tablolarınızı oluşturduktan sonra yazılımı çalıştırabilirsiniz.
2. Giriş ekranında kullanıcı adı: **"worker"** ve şifre: **"1234"** veya kullanıcı adı: **"admin"** ve şifre: **"1234"** ile giriş yapın.

## Kullanıcı Arayüzleri
Veritabanınızı ve tablolarınızı oluşturduktan sonra yazılımı çalıştırabilirsiniz. Yüklenecek ilk GUI Giriş ekranıdır. Kullanıcı adı: "worker" ve şifre: "1234" ya da kullanıcı adı: "admin" ve şifre: "1234" şeklinde giriş yapılabilir.

<img width="284" alt="login" src="https://github.com/ebrucihan/TourismAgencySystemProject/assets/164398353/a28bd557-bf6e-40b2-8bdd-9164661222a2">

## Kullanıcı Yönetim Sistemi
"admin" kullanıcı adı ve "1234" şifresi ile giriş yaptığınızda karşınıza çıkan "Kullanıcı Yönetim Sistemi" ekranı aşağıdaki gibidir:

<img width="731" alt="adminsystem" src="https://github.com/ebrucihan/TourismAgencySystemProject/assets/164398353/602676d2-c805-40e0-ba89-bb3b611b2be0">

- Başlık: "Kullanıcı Yönetim Sistemi"
- Kullanıcı rolü seçme menüsü: "Kullanıcının Rolü"
- Arama Yap butonu
- Sıfırla butonu
- Kullanıcı listesi tablosu: ID, İsim, Şifre ve Rol sütunları ile kullanıcı bilgilerini gösterir.
- Çıkış Yap butonu

Tabloda, "admin" ve "worker" kullanıcılarının ID, isim, şifre ve rol bilgileri yer almaktadır. Bu ekran, mevcut kullanıcıları yönetmek, arama yapmak ve sıfırlamak için kullanılabilir.


## Acenta Çalışanı (Personel) Yönetim Sistemi
"worker" kullanıcı adı ve "1234" şifresi ile giriş yaptığınızda, karşınıza "Acenta Çalışanı (Personel) Yönetim Sistemi" ekranı çıkacaktır:

 **Üç ana sekme**:
- Otel Yönetimi
- Oda Yönetimi
- Rezervasyon Yönetimi


## Otel Yönetimi
- Otel bilgilerini girmek için alanlar: Otel İsmi, Otel Adresi, Şehir, Mail, Bölge, Tel, Yıldız
- Pansiyon türü seçenekleri: Ultra Her şey Dahil, Her şey Dahil, Yarım Pansiyon, Tam Pansiyon, Oda Kahvaltı, Sadece Yatak, Alkol Hariç Full Credit
- Otel özellikleri seçenekleri: Ücretsiz Otopark, Ücretsiz WI-FI, Yüzme Havuzu, Fitness Center, Hotel Concierge, SPA, 7/24 Oda Servisi
- Butonlar: Güncelle, Ekle, Sil, Sıfırla
- Otel bilgilerini listeleyen tablo: Hotel ID, Hotel Name, Address, City, Region, Email, Phone, Stars, Facilities, Pension Types sütunlarını içerir.
- "Çıkış Yap" butonu

Bu ekran, otel bilgilerini yönetmek için kullanılan çeşitli giriş alanları ve seçenekler sunar. Ayrıca mevcut otel bilgilerini görüntülemek, eklemek, güncellemek, silmek ve sıfırlamak için kullanılabilir.

<img width="959" alt="worker1" src="https://github.com/ebrucihan/TourismAgencySystemProject/assets/164398353/7dcf7d75-98ed-4ad8-b0c7-8973bd2dfb62">

## Oda Yönetimi
- Oda Eklenecek Otel: Otel seçimi yapılacak bir açılır menü.
- Oda Tipi: Oda tipinin seçilebileceği bir açılır menü.
- Dönem Tipi: Konaklama döneminin belirtileceği bir tarih aralığı seçimi.
- Pansiyon Tipi: Pansiyon tipinin seçilebileceği bir açılır menü.
- Yetişkin Fiyatı: Yetişkinler için oda fiyatı girişi.
- Çocuk Fiyatı: Çocuklar için oda fiyatı girişi.
- Oda Stok: Oda stok sayısı girişi.
- Oda Yatak Sayısı: Oda yatak sayısı girişi.
- Oda Metrekare: Oda metrekare girişi.
  
**Oda Özellikleri**:
- TV var mı?: Evet/Hayır seçimi.
- Oyun konsolu var mı?: Evet/Hayır seçimi.
- Projeksiyon var mı?: Evet/Hayır seçimi.
- Minibar var mı?: Evet/Hayır seçimi.
- Kasa var mı?: Evet/Hayır seçimi.
Butonlar:
- Güncelle: Seçili oda bilgilerini günceller.
- Ekle: Yeni oda bilgilerini ekler.
- Sil: Seçili oda bilgisini siler.
- Sıfırla: Giriş alanlarını sıfırlar.
- 
**Oda Bilgileri Tablosu**: Oda ID, Otel ID, Oda Tipi, Yetişkin Fiyatı, Çocuk Fiyatı, Oda Stok, Yatak Sayısı, Metrekare, TV, Oyun Konsolu, Projeksiyon, Minibar, Kasa, Dönem Başlangıcı, Dönem Bitişi, Pansiyon Tipi sütunları ile oda bilgilerini gösterir.

**Oda Arama**:
- Otel Adı: Otel adı seçimi yapılacak bir açılır menü.
- Şehir: Şehir adı seçimi yapılacak bir açılır menü.
- Giriş Tarihi: Giriş tarihi seçimi.
- Çıkış Tarihi: Çıkış tarihi seçimi.
- Arama Yap: Belirtilen kriterlere göre oda araması yapar.
- Temizle: Arama kriterlerini temizler.
  
Bu ekran, otel odalarını yönetmek için kullanılan çeşitli giriş alanları ve seçenekler sunar. Mevcut oda bilgilerini görüntülemek, eklemek, güncellemek, silmek ve sıfırlamak için kullanılabilir.
Ayrıca, belirli kriterlere göre oda araması yapılabilir ve rezervasyon işlemleri gerçekleştirilebilir.

<img width="959" alt="worker2" src="https://github.com/ebrucihan/TourismAgencySystemProject/assets/164398353/85f4338b-a4b6-40fd-afb4-d4dce3d80cae">


**Oda Rezervasyon Ekranı**

Oda Yönetimi sekmesinde, bir oda üzerine sağ tıklayıp "Rezervasyon Yap" butonuna tıklayarak yeni bir rezervasyon ekranı açabilirsiniz. 
Bu ekranda, müşteri iletişim bilgilerini girerek ve rezervasyon detaylarını belirterek işlemi tamamlayabilirsiniz. Rezervasyon işlemi sırasında, toplam fiyat, yetişkin ve çocuk sayısı ile konaklama gün sayısına göre otomatik olarak hesaplanacaktır.

<img width="434" alt="worker3" src="https://github.com/ebrucihan/TourismAgencySystemProject/assets/164398353/125d0c9c-142e-4b59-ac35-0b0c1b204bb6">

## Rezervasyon Yönetimi

Rezervasyon Listesi Tablosu:
- Reservation ID: Rezervasyon kimlik numarası.
- Reservation Room ID: Rezervasyon yapılan oda kimlik numarası.
- Reservation Customer Name: Müşteri adı.
- Reservation Customer Phone: Müşteri telefon numarası.
- Reservation Check-in Date: Giriş tarihi.
- Reservation Check-out Date: Çıkış tarihi.
- Reservation Total Price: Toplam fiyat.
- Reservation Adult Count: Yetişkin sayısı.
- Reservation Child Count: Çocuk sayısı.
- Reservation Customer Email: Müşteri e-posta adresi.
- Reservation Customer TC: Müşteri kimlik numarası.
- Reservation Note: Rezervasyon notu.
- Rezervasyon Güncelle ve Sil Menüsü: Seçili rezervasyon üzerinde güncelleme ve silme işlemleri yapılabilir.
- 
Bu ekran, rezervasyon bilgilerini yönetmek için kullanılır. Mevcut rezervasyonları görüntülemek, güncellemek ve silmek için kullanılabilir. Kullanıcı, rezervasyon detaylarını tablo üzerinden inceleyebilir ve gerekli işlemleri gerçekleştirebilir.

<img width="959" alt="worker4" src="https://github.com/ebrucihan/TourismAgencySystemProject/assets/164398353/c9e6f7dd-1956-4929-9565-b428728edcea">

**Rezervasyon Güncelleme ve Silme İşlemleri**
- Rezervasyon Güncelleme
Rezervasyon güncelleme işlemi, mevcut bir rezervasyonun bilgilerini değiştirmek için kullanılır. İlgili alanlarda gerekli değişiklikleri yaptıktan sonra Kaydet butonuna tıklayarak güncellemeleri kaydedin. Bu işlem, veri tabanında seçili rezervasyonun güncellenmesini sağlar.
- Rezervasyon Silme
Rezervasyon silme işlemi, mevcut bir rezervasyonu sistemden tamamen kaldırmak için kullanılır. Kaydet işleminden sonra seçili rezervasyon veri tabanından silinecektir ve rezervasyon listesi tablosunda artık görünmeyecektir.

<img width="436" alt="worker5" src="https://github.com/ebrucihan/TourismAgencySystemProject/assets/164398353/9a11c0ed-3538-46fc-86ba-327d232049b9">


