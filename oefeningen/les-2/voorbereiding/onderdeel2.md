# Onderdeel oefening met Custom Queries

> [!NOTE]
> De code is te vinden in de map `bestelling`.
> Open deze map in een aparte idea window.

## Derived Query

> [!NOTE]
> In deze opdracht gebruik je ProductNaamDTO. Dit bestand bestaat al, maar is leeg.

- Maak in `dto/ProductNaamDTO.java`een record die alleen een `String naam` bevat. 

- Declareer in de interface `ProductRepository` een methode die `ProductNaamDTO` teruggeeft op basis van het meegegeven `id` van het product. Gebruik hiervoor de 'derived query regels' en niet de `@Query` annotatie.

- Haal regels 35 tot en met 38 van `ProductController` uit commentaar en test de implementatie met het onderste http GET request in het bestand `bestelling/http/testGetProduct.http` 

## @Query 

In Opdracht 1 heb je het onderstaande stuk code verplaatst naar een eigen service. 

```java
bestellingRepository.findAll().forEach(bestelling -> {
    bestelling.veranderStukPrijs(AggregateReference.to(product.getId()), nieuwePrijs);
    bestellingRepository.save(bestelling);
});
```

Het nadeel van bovenstaande code is dat `findAll` alle bestellingen ophaalt, terwijl we alleen de bestellingen nodig hebben die het product bevatten waarvan de prijs is aangepast. Over het algemeen is het sneller om de database te laten filteren dan dit in Java te doen.

- Declareer in de interface `bestelling/src/../repository/BestellingRepository` een methode die alle bestellingen ophaalt die een bepaald product bevatten. Gebruik hiervoor de `@Query`-annotatie en een custom Query.

> [!TIP]
> Als je het leuk vindt en voldoende tijd hebt, kun je zelf de query bedenken die je nodig hebt. Anders kun je de SQL-query die hieronder staat gebruiken:

<details>
    <summary><b>Toon SQL-query</b></summary>
    
```sql
SELECT *
FROM bestelling b
JOIN bestelregel br ON b.id = br.bestelling
WHERE br.product = :productId
```

</details>

### VRAAG

Je zou de query verder kunnen uitbreiden door alleen te zoeken naar bestellingen waarvan de status op `CONCEPT` staat, want de andere bestellingen kunnen we niet meer aanpassen. Dit is efficiënter, maar heeft ook een nadeel. Welk nadeel zou dit kunnen zijn?