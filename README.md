# doex-opdracht-4

De weekopdracht draait rondom een Restaurant casus hieronder beschreven in [Restaurant casus](#restaurant-casus).

De opdracht is om endpoints te maken voor een nog later te bouwen front-end (buiten scope van deze opdracht).
Deze repository bevat naast de opdracht ook starter code.

In Semester 1 CNP - Construction and Production - heb je al een full stack applicatie leren maken met hierbij het zelf opstellen van een OpenAPI specificatie. Deze legt vast hoe de front-end en back-end met elkaar communiceren en welke informatie ze van elkaar verwachten. En daarmee voor een deel ook hoe ze werken.

Voor de eenvoud is deze OpenAPI specificatie al gegeven. En hoef je in deze opdracht alleen de back-end te realiseren. Ook zijn de modellen in de domain laag al gegeven. Er zijn ook enkele unit tests van de domeinlaag. Je moet deze week de API laag implementeren op basis van gegeven domein code. In de volgende week ga je tests schrijven voor API laag.

## Restaurant casus

Het Restaurant "De Smakelijke Schotel" is een drukbezochte eetgelegenheid in het hartje van de stad. 
Het restaurant heeft een uitgebreid menu met diverse gerechten, variërend van traditionele gerechten tot fusionkeukencreaties. 
De eigenaar, Jan, beheert de voorraad zorgvuldig om ervoor te zorgen dat alle ingrediënten voorhanden zijn voor de chef-kok om de gerechten te bereiden.

De voorraad van het restaurant wordt beheerd door Jan en zijn team. Ze houden bij hoeveel van elk ingrediënt beschikbaar is. Bijvoorbeeld, er zijn 20 kilogram tomaten, 10 kilogram uien, enzovoort. Deze voorraad wordt aangevuld door leveranciers op basis van bestellingen die het restaurant plaatst.

Het menu van De Smakelijke Schotel wordt regelmatig bijgewerkt en bevat een breed scala aan gerechten, waaronder voorgerechten, hoofdgerechten, bijgerechten en desserts. 
Elk gerecht op het menu heeft een naam, categorie en prijs. Bijvoorbeeld, het hoofdgerecht "Gegrilde Zalm" behoort tot de categorie "Visgerechten" en kost € 18,95.

Elk gerecht op het menu bestaat uit een combinatie van ingrediënten, waarvan sommige specifieke doseringen vereisen. 
Zo kan het gerecht "Pasta Carbonara" bijvoorbeeld bestaan uit spaghetti, eieren, pancetta, Parmezaanse kaas en zwarte peper, elk met hun eigen doseringen.

Naast het reguliere menu houdt het restaurant ook rekening met voedingsrestricties van klanten, zoals vegetarisch, veganistisch, glutenvrij, enzovoort. 
Dit wordt gedaan door middel van voedingsrestricties die aan bepaalde ingrediënten zijn gekoppeld. 
Bijvoorbeeld, het gerecht "Groentenquiche" is geschikt voor vegetariërs en bevat geen vleesproducten.

Klanten kunnen een tafel reserveren bij De Smakelijke Schotel. 
Elke tafel heeft een uniek tafelnummer en kan één of meerdere bestellingen plaatsen tijdens hun verblijf. 
Een bestelling kan meerdere gerechten bevatten, afhankelijk van de voorkeuren van de klanten aan die tafel. 
Na het diner ontvangen klanten een rekening met de kosten van hun bestellingen.

## Voorbeeld Restaurant bezoek

Als een klant, Anna, het gezellige restaurant "De Smakelijke Schotel" binnenkomt, wordt ze vriendelijk begroet door een medewerker en naar haar tafel geleid. 
Ze neemt plaats aan tafel nummer 5 en bekijkt het menu dat haar wordt overhandigd. Na een paar minuten van grondig bladeren maakt Anna haar keuze: ze besluit de vegetarische lasagne en een glas huisgemaakte ice tea te bestellen.

De medewerker, Peter genaamd, komt naar haar tafel en vraagt vriendelijk naar haar bestelling. 
Anna glimlacht en geeft haar keuze door. 
Peter noteert de bestelling op zijn blok en controleert vervolgens de beschikbaarheid van de gerechten in het systeem. 
Gelukkig zijn alle ingrediënten voor de vegetarische lasagne op voorraad.

Nadat Peter de bestelling heeft gecontroleerd, voert hij deze in het systeem in en stuurt deze door naar de keuken. 
De chef-kok, Sarah, ontvangt de bestelling en begint onmiddellijk met de voorbereiding van de vegetarische lasagne. 
Ondertussen geniet Anna van de gezellige sfeer in het restaurant en kijkt ze tevreden naar de andere gasten die genieten van hun maaltijden.

Na een korte wachttijd brengt een serveerster, Lisa genaamd, de dampende vegetarische lasagne naar Anna's tafel. 
Anna bedankt haar vriendelijk en begint met smaak te eten van haar heerlijke maaltijd. 
De lasagne is perfect gebakken en de smaken zijn verrukkelijk.

Terwijl Anna van haar eten geniet, houdt Peter de bestelling in de gaten in het systeem en zorgt ervoor dat alles vlot verloopt. 
Zodra Anna haar maaltijd heeft voltooid, vraagt Peter vriendelijk of ze nog iets wilt bestellen of dat ze klaar is voor de rekening. 
Anna besluit om nog een dessert te bestellen, een stukje huisgemaakte appeltaart met een bolletje vanille-ijs.

Peter noteert haar dessertbestelling en regelt dat deze naar de keuken wordt gestuurd. 
Binnen enkele minuten wordt het dessert geserveerd, en Anna geniet van de heerlijke combinatie van warme appeltaart en romig ijs.

Nadat Anna haar maaltijd heeft genoten, vraagt ze om de rekening. 
Peter brengt de rekening snel naar haar tafel, en Anna betaalt gemakkelijk met haar bankpas. 
Ze bedankt het personeel voor de uitstekende service en verlaat het restaurant met een voldaan gevoel, klaar om haar avond voort te zetten.

- Twee subdomeinen: bestellen en voorraadbeheer (inloggen/tafelzetting buiten scope)
- Domain stories zowel coarse grained als fine grained, uit de laatste komen de user stories
- Sequence diagrammen met operaties invoegen en toelichting.
- En domein model met toelichting.

- Docent heeft een voorbeeld user story map te gebruiken voor beoordelen student uitwerkingen.


# Doex opdrachten week 5

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

