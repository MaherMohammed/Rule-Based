:- use_module(library(clpfd)).

rule1(Size , Height):-
    Size > 10,
    Size < 50,
    Height = small.

rule2(Size , Height):-
    Size > 50,
    Size < 150,
    Height = medium.

rule4(Size , Height):-
    Size > 150,
    Height = tall.

rule5(LifeCycle,LifeType):- 
    LifeCycle = oneYear,
    LifeType = annual. 
    

rule6(LifeCycle,LifeType):-
    LifeCycle = moreThanOneYear,
    LifeType = perennial.



rule7(Season,Color, LifeType, Root , Flower):-
    Season = summer, 
    member(Color , [blue , purple , yellow]),
    LifeType = perennial,
    Root = bulb,
    Flower = iris.


rule8(Season , Color ,Flower ):-
    Season = autumn,
    member(Color,[white, pink, pinkishRed]),
    Flower = anemone.



rule9(Season , Height , Color , Flower):-
    Season = autumn,
    Height = medium,
    member(Color , [yellow , white , purple , red]),
    Flower = chrysanthemums.




rule10(Season , Root , Color , Perfumed , Flower):-
    Season = spring,
    Root = bulb,
    member(Color , [white , yellow , orange , purple , red , blue]),
    Perfumed = true,
    Flower = freesia.


rule11(LifeType , Height , Root , Season,Flower):-
    LifeType = perennial,
    Height = tall,
    Root = bulb,
    Season = summer,
    Flower = dahlia.




rule12(Season , Root , Color , Flower):-
    Season = spring,
    Root = bulb,
    member(Color , [yellow , white]),
    Flower = narcissus.


rule13(Soil , Color , LifeType , Root , Flower):-
    Soil = acidic,
    member(Color , [white ,pink , red]),
    LifeType = perennial,
    Root = root,
    Flower = camellias.


rule14(Season , Root , Perfumed , Height , LifeType , Flower):-
    Season = spring,
    Root = bulb,
    Perfumed = true,
    Height = small,
    LifeType = perennial,
    Flower = lily.


rule15(Height , LifeType , Soil , Flower):-
    Height = small,
    LifeType = annual,
    member(Soil , [rich , loose , fertile]),
    Flower = begonia.



rule16(Season , Color , Flower):-
    Season = winter,
    member(Color , [white , pink , red]),
    Flower = azaleas.



rule17(LifeType , Root , Color , Flower):-
    LifeType = perennial,
    Root = root,
    member(Color , [white , red , blue , yellow]),
    Flower = anemone.


rule18(LifeType , Root , Color , Perfumed , Soil , Flower):-
    LifeType = perennial,
    Root = root,
    member(Color , [white , pink , red , yellow]),
    Perfumed = true,
    Soil = wellDrained,
    Flower = roses.


rule19(Flower , Perfumed , ProducedFlower):-
    Flower = lily,
    Perfumed = true,
    ProducedFlower = whitelily.

