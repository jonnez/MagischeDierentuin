MagischeDierentuin
==================

Code Challenge Java Magazine #6-2014

Omdat we een recursieve definitie hebben, waarbij we gebruik maken van een telkens kleinere som
van argumenten (in elke stap 1 kleiner), lijkt dynamisch programmeren hier goed toepasbaar.

De State class wordt slechts gebruikt om een triple van de aantallen van dieren op te slaan.
Terwijl ik een nieuwe map (score) vul, gebruik ik de waarden van de vorige ronde (met x+y+z 1 kleiner) in scorePrevious.

Er zit symmetrie in de mogelijke stappen. Om die reden houden we de hoeveelheid dieren in aflopende volgorde
(S(x, y, z) = S(z, x, y), de volgorde van de argumenten maakt niet uit).

Na zo'n 2,5 uur gaf dit programma de oplossing, zie de uitvoer hieronder.
Later heb ik het programma nog parallel gemaakt met behulp van een Lambda expressie.
De sequentiele methode die ik eerst had heb ik in commentaar laten staan,
omdat het leuk is om te zien hoe minimaal de aanpassing was.
De parallelle versie had bij mij net een rekentijd van 49 minuten.
De gevraagde oplossing is 4023.

- S(x, y, z) = max {
      S(x+1, y-1, z-1),
      S(x-1, y+1, z-1),
      S(x-1, y-1, z+1)}, voor positieve waarden.

- S(x, y, z) = S(z, y, x) met argumenten in willekeurige volgorde. En daarom bekijken we alleen de gevallen met x >= y >= z.

- Basis geval: S(x, y, y) = x + y
- S(x, 1, 0) = S(x-1, 0, 1) = S(x-2, 1, 0) = S(1, 0, 0) = 1

- Het aantal partities van n in 3 groepen (see http://dlmf.nist.gov/26.9):
 p_3(n) = 1 + floor((n^2 + 6n) / 12) = 1, 2, 3, 4, 5, 7, 8, 10, 12, 14, ...

- Om S(x, y, z) met som x+y+z te berekenen, hebben we slechts de waarden van S nodig met som x+y+z-1.

## Uitvoer

```
Thu Dec 18 19:47:24 CET 2014 Round: 1 Map size: 0
Thu Dec 18 19:47:24 CET 2014 Round: 2 Map size: 1
Thu Dec 18 19:47:24 CET 2014 Round: 3 Map size: 2
...
Thu Dec 18 22:18:54 CET 2014 Round: 6075 Map size: 3077494
Thu Dec 18 22:18:58 CET 2014 Round: 6076 Map size: 3078507
Thu Dec 18 22:19:03 CET 2014 Round: 6077 Map size: 3079520
Thu Dec 18 22:19:08 CET 2014 Round: 6078 Map size: 3080533
Score for (2055, 2017, 2006) is 4023
```
