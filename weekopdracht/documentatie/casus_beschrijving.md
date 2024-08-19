# casus

## algemeen

Het Restaurant "De Smakelijke Schotel" is een drukbezochte eetgelegenheid in het hartje van de stad. 
Het restaurant heeft een uitgebreid menu met diverse gerechten, variërend van traditionele gerechten tot fusionkeukencreaties. 
De eigenaar, Jan, beheert de voorraad zorgvuldig om ervoor te zorgen dat alle ingrediënten voorhanden zijn voor de chef-kok om de gerechten te bereiden.

De voorraad van het restaurant wordt beheerd door Jan en zijn team. 
Ze houden bij hoeveel van elk ingrediënt beschikbaar is. 
Bijvoorbeeld, er zijn 20 kilogram tomaten, 10 kilogram uien, enzovoort. 
Deze voorraad wordt aangevuld door leveranciers op basis van bestellingen die het restaurant plaatst.

Het menu van De Smakelijke Schotel wordt regelmatig bijgewerkt en bevat een breed scala aan gerechten, waaronder voorgerechten, hoofdgerechten, bijgerechten en desserts. 
Elk gerecht op het menu heeft een naam, categorie en prijs. 
Bijvoorbeeld, het hoofdgerecht "Gegrilde Zalm" behoort tot de categorie "Visgerechten" en kost € 18,95.

Elk gerecht op het menu bestaat uit een combinatie van ingrediënten, waarvan sommige specifieke doseringen vereisen. 
Zo kan het gerecht "Pasta Carbonara" bijvoorbeeld bestaan uit spaghetti, eieren, pancetta, Parmezaanse kaas en zwarte peper, elk met hun eigen doseringen.

Naast het reguliere menu houdt het restaurant ook rekening met voedingsrestricties van klanten, zoals vegetarisch, veganistisch, glutenvrij, enzovoort. 
Dit wordt gedaan door middel van voedingsrestricties die aan bepaalde ingrediënten zijn gekoppeld. 
Bijvoorbeeld, het gerecht "Groentenquiche" is geschikt voor vegetariërs en bevat geen vleesproducten.

Klanten kunnen een tafel reserveren bij De Smakelijke Schotel. 
Elke tafel heeft een uniek tafelnummer en kan één of meerdere bestellingen plaatsen tijdens hun verblijf. 
Een bestelling kan meerdere gerechten bevatten, afhankelijk van de voorkeuren van de klanten aan die tafel. 
Na het diner ontvangen klanten een rekening met de kosten van hun bestellingen.

> opdracht: maak een use case diagram voor deze casus en stel hierbij een domeinmodel op

## restaurant bezoek

Als een klant, Anna, het gezellige restaurant "De Smakelijke Schotel" binnenkomt, wordt ze vriendelijk begroet door een medewerker en naar haar tafel geleid. 
Ze neemt plaats aan tafel nummer 5 en bekijkt het menu dat haar wordt overhandigd. 
Na een paar minuten van grondig bladeren maakt Anna haar keuze: ze besluit de vegetarische lasagne en een glas huisgemaakte ice tea te bestellen.

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

> opdracht:  maak fully-dressed use case(s) van deze scenario beschrijving en stel een system sequence diagram op

## operatie contract - bestelling plaatsen

Operatie: plaatsenBestelling(tafelnr, gerechtNamen)

Korte beschrijving: 

Een nieuwe bestelling wordt ingevoerd in het systeem nadat de voorraad is gecontroleerd door de medewerker

Precondities:

* Er bestaat een tafel T met opgegeven tafelnr.
* De collectie van opgegeven gerechtNamen bevat minstens één gerecht naam.
* Er bestaan gerechten die overeenkomen met de opgegeven gerechtNamen als onderdeel van het actieve (huidige) menu.
* De voorraad van alle ingrediënten die nodig zijn voor de gerechten uit de bestelling is voldoende.

Postcondities:

* Er is een bestelling B aangemaakt
* Voor elk gerecht uit de bestelling is er een associatie gemaakt met B
* De bestelling heeft een associatie met T
* De voorraad is verminderd op basis van de ingredienten die benodigd zijn voor de gerechten uit de bestelling

> opdracht:  maak een sequence diagram van dit operatie contract en zet het domein model om naar een eerste versie van het DCD
>> denk ook eens na over mogelijke alternatieven