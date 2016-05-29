Aby umożliwiść dotarcie do jak największej liczby użytkowników nasz projekt nie ma sporych wymagań. Minimalna wersja androida wymagana do uruchomienia aplikacji na systemie mobilnym to API 15. Jednak jeżeli chcemy uruchomić projekt na komputerze osobistym za pomocą wirtualnej maszyny wymagania rosną. Do komfortowego korzystania z projektu wymagane jest 8GB pamięci RAM oraz co najmniej dwurdzeniowy procesor(zalecane minimum intel i3). Projekt jest dość pamięciożerny ponieważ wymaga zainstalowania Android SDK wraz z instalacją co namniej API 15, bazy danych MS SQL oraz Node.js potrzebnego do uruchomienia serwera. 
-Zalecane aplikacje potrzebne do uruchomienia projektu to: 
-Android Studio 2.0
-Node.js 4.2.1
-OpenSSL 1.0.2d
-MySQL Workbench 6.3 CE. 

	Instrukcja uruchomienia naszego projektu na komputerze osobistym:
1. Serwer
    a) Uruchom MySQL Server i zaimportuj wszystkie tabele za pomocą v2.sql z folderu sql (W razie problemów stwórz schema android i zaimportuj wszystko z sql/tables)
    b) Przejdź w konsoli do katalogu server
    c) Uruchom “npm install”
    d) zmień swoje ustawienia dostępu do bazy danych w server/db/properties.js
    e) Uruchom OpenSSL
    f) “genrsa -des3 -out server.key -passout pass:android 1024”
    g) “req -x509 -new -key server.key -passin pass:android -days 365 -out server.crt”
    h) Utworzone pliki server.key i server.crt przenieś do folderu server/cert
    i) Uruchom serwer wykonując operacje run na pliku www (“Node bin/www”)
    j) Uwaga: zamknięcie konsoli spowoduje wyłączenie serwera
2. Android
    a) Otwórz projekt z folderu android w Android Studio
    b) Edytuj swoje IP i ustawienia portu w com.tenantsproject.flatmates.model.rest.Properties
      Zalecane ip koputera z sieci lokalnej
    c) Uruchom swoją aplikacje kliencką Run -> Run “app” 
3. Serwer aplikacji webowej
a) Otworz projekt “flatmates-project” w Netbeans (z zainstalowanym serwerem GlassFish)
b) Kliknij “Run”
c) Uruchom strone Login.jsp, bądź dopisz po uruchomieniu przegladarki do adresu “Login.jsp”

