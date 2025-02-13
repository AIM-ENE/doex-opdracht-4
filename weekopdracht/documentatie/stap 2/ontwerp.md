# Ontwerp

Op basis van de scenario's en fine-grained domain stories is het team tot [dit domein ontwerp](domeinmodel.puml) gekomen.

> Wat hierin te zien is dat het team besloten heeft om de voedingsrestricties buiten scope te houden, hiervoor zal een bestaand extern systeem gebruikt worden.

> Aangezien de voorraad van wezenlijk belang is bij het kiezen van de juiste aggregate grenzen is `voorraad` wel meegenomen in het ontwerp, 
maar nog niet in-scope van de eerste iteratie.

Vervolgens heeft het team een high-level sequence diagram opgesteld van een [restaurant bezoek](SSD%20-%20restaurant%20bezoek.puml).

Voor elk van de operaties op het systeem had een operatie contract opgesteld kunnen worden zoals onderstaand voor het `bestelling plaatsen`.
Hier zijn keuzes te maken of dat altijd zinnig is, in dit geval heeft het team de keuze gemaakt om niet meer operatie contracten op te stellen voor de eerste iteratie.

## operatie contract - bestelling plaatsen

Operatie: plaatsBestelling(restaurant, tafelnummer, winkelmand)

Korte beschrijving:

Een nieuwe bestelling wordt ingevoerd in het systeem door een winkelmand om te zetten naar een bestelling

Precondities:

* Er bestaat een `tafel` T met opgegeven `tafelnummer`.
* De `winkelmand` W bevat minstens één `regel`.
* Er bestaan gerechten die overeenkomen met de opgegeven `gerecht`:`id`.

Postcondities:

* Er is een `bestelling` B aangemaakt
* Voor elk uniek gerecht uit de `winkelmand` is een `regel` R binnen de `bestelling` B aangemaakt met het aantal keer dat deze voorkomt in de `winkelmand` W
* voor elke `regel` R is er een associatie met de `bestelling` B
* De `bestelling` heeft een associatie met `tafel` T
* De `winkelmand` W is verwijderd

## etc.
