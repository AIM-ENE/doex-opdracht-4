# DoEx Opdracht 4

## Weekopdracht

De weekopdracht draait om een Restaurant casus beschreven in [Restaurant casus](documentatie/restaurant-casus.md), lees de beschrijving en scenario's goed door voor les 1.

De opdracht is om de endpoints aan te vullen voor een nog later te bouwen front-end (front-end is buiten scope van deze weekopdracht).

De repository bevat al de implementatie van de domeinlaag en gedeeltelijk ook services, custom queries en controller.

Het is mogelijk om de applicatie op te starten middels `Application` om vervolgens te testen met de **HTTP Client van Intellij**, gebruik bijvoorbeeld [deze requests](request.http).

Echter zijn er nog een aantal gaten door jullie in te vullen, te vinden als je zoekt in de weekopdracht map op `PLACEHOLDER`:

- In de `RestaurantController` moet je nog de systeemoperatie [plaatsBestelling(restaurant, tafelnummer, winkelmand)](documentatie/stap%202/ontwerp.md) uitwerken
  - geef hiervoor invulling aan deze methode in de controller: 
  `plaatsBestelling(@PathVariable("restaurantId") int restaurantId,
    @RequestBody TafelBestelling tafelBestelling)`
- In de `RestaurantController` moet je de functie om de juiste tafel te vinden op basis van restaurantId en tafelnummer implementeren
  - de tafel heeft als primary key een Id, maar er mag ook maar 1 combinatie van restaurant en tafelnummer bestaan 
  - zie ook constraint op de `Tafel` tabel in het [schema](src/main/resources/schema.sql)
- In de `RestaurantController` moet je nog de filtering doen van de menukaart rekeninghoudend met `VoedingRestrictie` 

Hierbij is het de bedoeling dat jullie de relatie kunnen leggen tussen de domeinlaag en API gebruikmakend van `hexagonal architecture` en op basis daarvan een keuze maken dit op te lossen in de `Controller`, in een `Service` of in een `Repository` met een `Custom Query`.

Hiervoor moet je (na les 3) in de architectuur kunnen aangeven welk object in welke laag thuishoort.
Dit kan in drawio in [jouw uitwerking](jouw_uitwerking.drawio), maar wellicht is het sneller/efficiënter om dit gewoon op papier te doen.

Neem jouw architectuur plaatje en jouw invulling van de placeholders in de `RestaurantController` mee naar les 4.

## Les voorbereidingen

Hernoem deze opdrachtbeschrijving in de README naar `opdracht.md` en maak daarna een eigen/nieuw README.md bestand met je uitwerking.

TODO: [les voorbereiding](oefeningen/README.md) (oefeningen met user story map voor koffieapparaat casus)

## Weekopdracht (parallel aan de lessen)

### User Storymap (na les 1)

- Zet de door jou in les 1 gemaakte User Story Map voor de Restaurant casus als plaatje in de README.md en zet een deel van casus beschrijving er als toelichting onder.
- Maak een issue Bord aan in Github, en maak voor elke user stories een issue bepaald in deze les.

###  (na les 2)

- 

### Implementeren van de benodigde glue code (na les 3)

- Vervolgens kopieer je deze [RunCucumberTest.java](oefeningen/les3/voorbereiding/bowling/src/test/java/RunCucumberTest.java) en deze [StepDefinitions.java](oefeningen/les3/voorbereiding/bowling/src/test/java/StepDefinitions.java) over in de map *src/test/java*
- Schrijf de benodigde code in *Stepdefinitions.java* om de features automatisch te kunnen testen (eventueel reeds bestaande glue code van het huiswerk verwijderen)

