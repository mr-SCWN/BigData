# Zestaw 4 – IMDB-Persons

## Program MapReduce (2)

Na zbiorze `datasource1` `(1)` została wyznaczona dla każdej osoby liczba filmów, w których zagrała, oraz liczba filmów, które wyreżyserowała.

W wynikowym zbiorze `(3)` znajdują się następujące atrybuty:
- **identyfikator osoby** (`nconst`),
- **liczba filmów, w których dana osoba zagrała**,
- **liczba filmów, które dana osoba wyreżyserowała**.

---

## Program Hive (5)

Na podstawie wyniku zadania MapReduce `(3)` oraz zbioru danych `datasource4` `(4)` wyznaczono:
1. **Trzech aktorów** – osoby, których główną profesją jest aktorstwo (w kolumnie `primaryProfession` znajduje się jedna z wartości {`actress`, `actor`}), którzy zagrali w największej liczbie filmów.
2. **Trzech reżyserów** – osoby, których główną profesją jest reżyseria (w kolumnie `primaryProfession` znajduje się {`director`}), którzy wyreżyserowali największą liczbę filmów.

Ostateczny wynik `(6)` zawiera następujące atrybuty:
- **primaryName** – imię i nazwisko osoby,
- **role** – rola osoby (aktor lub reżyser),
- **movies** – liczba filmów.
