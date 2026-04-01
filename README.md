# Employee Mood System

##  Opis projektu

Employee Mood System to aplikacja backendowa stworzona w Javie (Spring Boot), której celem jest monitorowanie samopoczucia pracowników oraz wspieranie działu HR w identyfikowaniu potencjalnych problemów w zespole.

System umożliwia pracownikom codzienne raportowanie nastroju, a na podstawie zgromadzonych danych analizuje trendy oraz generuje alerty w przypadku wykrycia niepokojących zmian. Dział HR ma dostęp do panelu umożliwiającego przegląd danych oraz podejmowanie działań na podstawie raportów.

---

##  Funkcjonalności

* dodawanie i edycja codziennych wpisów nastroju
* przegląd historii nastrojów pracownika
* analiza trendów nastroju w czasie
* automatyczne generowanie alertów
* panel HR do analizy pracowników
* tworzenie i zarządzanie raportami HR
* filtrowanie danych

---

## Model danych

Aplikacja opiera się na następujących encjach:

* Employee – użytkownik systemu
* DailyEntry – zapis codziennego nastroju
* Alert – alert generowany automatycznie przez system
* HRReport – raport tworzony przez dział HR

### Relacje

Model danych wykorzystuje relacje typu jeden-do-wielu:

* `Employee` → `DailyEntry` (1:N)
  jeden pracownik może posiadać wiele wpisów nastroju
  każdy wpis nastroju należy do jednego pracownika

* `Employee` → `Alert` (1:N)
  jeden pracownik może mieć wiele alertów generowanych przez system
  każdy alert dotyczy jednego pracownika

* `Employee` → `HRReport` (1:N)
  jeden pracownik może mieć wiele raportów HR
  każdy raport dotyczy jednego pracownika

* `Alert` → `HRReport` (1:N)
  jeden alert może być powiązany z wieloma raportami HR
  raport HR może odnosić się do konkretnego alertu

---

### Warstwa serwisów

* DailyEntryService

  * zapis i edycja nastroju
  * pobieranie historii użytkownika

* AlertService

  * analiza danych
  * wykrywanie niepokojących trendów
  * generowanie alertów

* HRReportService

  * zarządzanie raportami HR

* HrDashboardService
  * agreguje i przetwarza dane dotyczące nastrojów pracowników
  * przygotowuje dane do analizy w panelu HR

* EmployeeService

    * wyszukiwanie pracowników

---

## Kontrolery

Aplikacja udostępnia endpointy oraz widoki umożliwiające:

* zarządzanie wpisami nastroju
* przegląd raportów HR
* obsługę panelu HR

---

## Bezpieczeństwo

Zaimplementowano mechanizm uwierzytelniania i autoryzacji przy użyciu Spring Security:

* własna implementacja `UserDetails` oraz `UserDetailsService`
* uwierzytelnianie użytkowników na podstawie danych z bazy
* role użytkowników:

  * `EMPLOYEE`
  * `HR`
* ograniczenie dostępu do endpointów na podstawie ról
* bezpieczne przechowywanie haseł (BCrypt)

---

## DTO

Zastosowano wzorzec DTO (Data Transfer Object) w celu:

* walidacji danych wejściowych
* oddzielenia warstwy API od modelu bazy danych
* zwiększenia bezpieczeństwa aplikacji

---

## Enumy

W projekcie wykorzystano enumy do modelowania logiki biznesowej:

* `Role` – role użytkowników
* `Severity` – poziom zagrożenia
* `Status` – status raportu
* `ActionType` – akcje HR
* `AlertType` – typ alertów

---

## Technologie

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA (Hibernate)
* Maven
* H2 / PostgreSQL
* JUnit + Mockito

---

## Testy

Projekt zawiera testy jednostkowe dla wybranych serwisów, w tym testy logiki biznesowej.

---

## Inicjalizacja danych

* `DataLoader` – inicjalizuje przykładowych użytkowników przy starcie aplikacji

---

## Czego się nauczyłem

* projektowania REST API
* pracy z relacyjną bazą danych
* implementacji zabezpieczeń (Spring Security)
* stosowania wzorca DTO
* organizacji kodu w architekturze warstwowej
* pisania testów jednostkowych

---
