:- use_module(library(clpfd)).
flower(iris).
flower(anemone).
flower(chrysanthemums).
flower(freesia).
flower(dahlia).
flower(narcissus).
flower(camellias).
flower(lily).
flower(begonia).
flower(azaleas).
flower(anemone).
flower(roses).
flower(whitelily).

color(blue).
color(purple).
color(yellow).
color(red).
color(white).
color(pink).
color(orange).
color(violet).
color(pinkishRed).

season(autumn).
season(summer).
season(spring).
season(winter).


rootType(bulb).
rootType(root).

perfume(true).
perfume(false).

lifeType(perennial).
lifeType(annual).

soil(acidic).
soil(loose).
soil(fertile).
soil(rich).
soil(wellDrained).

lifeCycle(oneYear).
lifeCycle(moreThanOneYear).

height(small).
height(medium).
height(tall).


rule5(LifeCycle,LifeType):- 
    lifeCycle(LifeCycle),
    LifeCycle == oneYear,
    lifeType(LifeType),
    LifeType == annual.

rule6(LifeCycle,LifeType):-
    lifeCycle(LifeCycle),
    LifeCycle = moreThanOneYear,
    lifeType(LifeType),
    LifeType = perennial.

isIris(X):-
    flower(X),
    X == iris. 

rule7(Color, LifeType, Root  , Season, Flower):-
    season(Season),
    Season == summer,
    color(Color),
    member(Color , [blue , purple , yellow]),
    lifeType(LifeType),
    LifeType == perennial,
    rootType(Root),
    Root == bulb,
    flower(Flower),
    Flower == iris.
    
rule8(Flower , Color , Season):-
    season(Season),
    Season = autumn,
    color(Color),
    member(Color,[white, pink, pinkishRed]),
    flower(Flower),
    Flower = anemone.


rule9(Flower,Season , Height , Color):-
    season(Season),
    Season = autumn,
    height(Height),
    Height = medium,
    color(Color),
    member(Color , [yellow , white , purple , red]),
    flower(Flower),
    Flower = chrysanthemums.



rule10(Flower, Season , Root , Color , Perfumed):-
    season(Season),
    Season == spring,
    rootType(Root),
    Root == bulb,
    color(Color),
    member(Color , [white , yellow , orange , purple , red , blue]),
    perfume(Perfumed),
    Perfumed == true,
    flower(Flower),
    Flower = freesia.



rule11(LifeType , Height , Root , Season,Flower):-
    lifeType(LifeType),
    LifeType = perennial,
    height(Height),
    Height = tall,
    rootType(Root),
    Root = bulb,
    season(Season),
    Season = summer,
    flower(Flower),
    Flower = dahlia.



rule12(Season , Root , Color , Flower):-
    season(Season),
    Season = spring,
    rootType(Root),
    Root = bulb,
    color(Color),
    member(Color , [yellow , white]),
    flower(Flower),
    Flower = narcissus.


rule13(Soil , Color , LifeType , Root , Flower):-
    soil(Soil),
    Soil = acidic,
    color(Color),
    member(Color , [white ,pink , red]),
    lifeType(LifeType),
    LifeType = perennial,
    rootType(Root),
    Root = root,
    flower(Flower),
    Flower = camellias.



rule14(Season , Root , Perfumed , Height , LifeType , Flower):-
    season(Season),
    Season = spring,
    rootType(Root),
    Root = bulb,
    perfume(Perfumed),
    Perfumed = true,
    height(Height),
    Height = small,
    lifeType(LifeType),
    LifeType = perennial,
    flower(Flower),
    Flower = lily.


rule15(Height , LifeType , Soil , Flower):-
    height(Height),
    Height = small,
    lifeType(LifeType),
    LifeType = annual,
    soil(Soil),
    member(Soil , [rich , loose , fertile]),
    flower(Flower),
    Flower = begonia.


rule16(Season , Color , Flower):-
    season(Season),
    Season = winter,
    color(Color),
    member(Color , [white , pink , red]),
    flower(Flower),
    Flower = azaleas.

rule17(LifeType , Root , Color , Flower):-
    lifeType(LifeType),
    LifeType = perennial,
    rootType(Root),
    Root = root,
    color(Color),
    member(Color , [white , red , blue , yellow]),
    flower(Flower),
    Flower = anemone.



rule18(LifeType , Root , Color , Perfumed , Soil , Flower):-
    lifeType(LifeType),
    LifeType = perennial,
    rootType(Root),
    Root = root,
    color(Color),
    member(Color , [white , pink , red , yellow]),
    perfume(Perfumed),
    Perfumed = true,
    soil(Soil),
    Soil = wellDrained,
    flower(Flower),
    Flower = roses.





rule19(Flower , Perfumed , ProducedFlower):-
    flower(Flower),
    Flower = lily,
    perfume(Perfumed),
    Perfumed = true,
    flower(ProducedFlower),
    ProducedFlower = whitelily.