# doex-opdracht-4

## Weekopdracht

De weekopdracht draait rondom een Restaurant casus beschreven in [Restaurant casus](documentatie/restaurant-casus.md).

De opdracht is om de endpoints aan te vullen voor een nog later te bouwen front-end (buiten scope van deze weekopdracht).

De repository bevat al de implementatie van de domeinlaag en gedeeltelijk ook services, custom queries en controller implementatie.

Echter zijn er nog een aantal gaten door jullie in te vullen, te vinden als je zoekt in de weekopdracht map op `PLACEHOLDER`:

- In de `RestaurantController` moet nog de systeemoperatie [plaatsBestelling(restaurant, tafelnummer, winkelmand)](documentatie/stap%202/ontwerp.md) uitgewerkt worden
  - geef hiervoor aan invulling deze methode in de controller: 
  `plaatsBestelling(@PathVariable("restaurantId") int restaurantId,
    @RequestBody TafelBestelling tafelBestelling)`
- In de `RestaurantController` moet nog de functie om de juiste tafel te vinden op basis van restaurantId en tafelnummer
  - de tafel is uniek vanwege een Id, maar er mag ook maar 1 combinatie van restaurant en tafelnummer bestaan 
  - zie ook constraint op de `Tafel` tabel in het [schema](src/main/resources/schema.sql)
- In de `RestaurantController` moet nog de filtering gedaan worden van de menukaart rekeninghoudend met `VoedingRestrictie` 

Hierbij is het de bedoeling dat jullie de relatie kunnen leggen tussen de domeinlaag en API gebruikmakend van `hexagonal architecture` 
en op basis daarvan een keuze maken of dit opgelost dient te worden in de `Controller` een `Service` of in de `Repository` met een `Custom Query`.

Hiervoor moeten jullie (na les 3) in de architectuur kunnen aangeven welk object in welke laag thuishoort in [jouw uitwerking](jouw_uitwerking.drawio).
Neem dit ook mee naar les 4, evenals jouw invulling van de placeholders in de `RestaurantController`.

## Les voorbereidingen

Hernoem deze opdrachtbeschrijving in de README naar `opdracht.md` en maak daarna een eigen/nieuw README.md bestand met je uitwerking.

TODO: [les voorbereiding](oefeningen/README.md) (oefeningen met user story map voor koffieapparaat casus)

## Weekopdracht (parallel aan de lessen)

### User Storymap (na les 1)

- Zet jou in les 1 gemaakt User Story Map voor de Restaurant casus als plaatje in de README.md en zet een deel van casus beschrijving er als toelichting onder.
- Maak een issue Bord aan in Github, en maak voor elke user stories een issue bepaald in deze les

###  (na les 2)

- 

### Implementeren van de benodigde glue code (na les 3)

- Vervolgens kopieer je deze [RunCucumberTest.java](oefeningen/les3/voorbereiding/bowling/src/test/java/RunCucumberTest.java) en deze [StepDefinitions.java](oefeningen/les3/voorbereiding/bowling/src/test/java/StepDefinitions.java) over in de map *src/test/java*
- Schrijf de benodigde code in *Stepdefinitions.java* om de features automatisch te kunnen testen (eventueel reeds bestaande glue code van het huiswerk verwijderen)

