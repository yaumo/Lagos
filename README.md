# Lagos - Optimierung industrieller Prozesse 
#### Nico Himmelein, Philipp Thiele, Lukas Freitag, Thomas Richter

## Vorstellung der Aufgabe
- https://www.dropbox.com/s/3j60eoze10fiawi/SimBasedOptim.pdf?dl=0

## Docker
- Aufsetzen der Infrastruktur: docker-compose up -d in dem Order /Lagos/Miscellaneous
- https://hub.docker.com/r/jreichwald/dhbw_oip/
- [docker-compose.yml](Miscellaneous/docker-compose.yml)
- RabbitMQ Dashboard
  - IP: http://localhost:15672
  - Username: guest
  - Passwort: guest


## Algorithmus: Simulated Annealing
### Dokumentation
Simualted Annealing ist ein Verfahren zum
Auffinden einer Näherungslösung für ein Optimierungsproblem
(meist Minimierung) ohne
dass alle Lösungen wie bei einem Brute-ForceAnsatz
ausprobiert werden müssen. Es ist eine
Heuristik, d.h. ein Verfahren das oft sehr gut
funktioniert, aber keine Garantie für eine optimale
oder auch nur gute Lösung gibt.

Es wird eine Random Search-Strategie
welche nicht nur Lösungen akzeptiert die
einen niedrigeren Funktionswert ergeben (im
Falle eines Minimierungsproblems), sondern
auch mit einer bestimmten Wahrscheinlichkeit
(Boltzmann-Gibbs-Verteilung) Lösungen die
einen höheren Funktionswert ergeben. Durch
diese Tatsache hat Simualted Annealing den
Vorteil dass lokalen Minima wieder verlassen
werden können.

Dieses Verfahren kann sowohl für kontinuierliche
Probleme der Art f : R
n → R als auch
für diskrete Probleme wie zB das Traveling Salesman
Problem angewandt werden. In diesem
Artikel werden beide Problemtypen einleitend
behandelt.

Simulated Annealing ist die Nachbildung eines
Wärmebehandlungsverfahrens der Metallurgie,
daher auch die englische Bezeichnung
Annealing.

Source: http://www.mycsharp.de/attachments/simulated_annealing.pdf

### Pseudocode
```
Wähle eine Anfangskonfiguration;  
Wähle eine Anfangstemperatur T > 0;  
wiederhole  
 wiederhole  
  Wähle eine neue Konfiguration, die eine kleine Änderung der alten Konfiguration ist;  
  Berechne die Energiefunktion der neuen Konfiguration;  
  DE := E(neu) - E(alt);  
  wenn DE < 0, dann alte_Konfiguration := neue_Konfiguration  
  sonst wenn Zufallszahl < e^-DE/kT,
   dann alte Konfiguration := neue Konfiguration  
 bis lange keine Erniedrigung der Energie;  
 Verringere T;  
bis keine Verringerung der Energiefunktion mehr auftritt;
```
Source: http://fbim.fh-regensburg.de/~saj39122/vhb/NN-Script/script/gen/k02070403.html

## Ergebnisse
Aus unseren Beoachtungen hat sich ergeben, dass die Werte für die Abkühlungsrate und die Umgebungsvariable den größten Einfluss auf das Ergebnis haben.
#### FTYPE 1
- solutionVector: [0.991285972608356,1.0089615942902084,0.9787414594855455,0.9911577781051508,1.0348269049519643,0.9784168649535139,1.012447099689885,1.0255987800938424,1.001368786713254,0.8694626955981883,0.7410469703850415,0.5353980629023698,0.278299525440453,0.05569585976402536,0.018580891843005265,5.695862050725253E-4,0.004217665728023512]
- isFeasible: TRUE
- resultValue: 7.597627721920846

- Iterationen: 500
- Temperatur: 5000
- Abkühlungsrate: 0,01
- Umgebungsvariable: 2

#### FTYPE 2
- solutionVector:
- isFeasible: 
- resultValue: 

- Iterationen:
- Temperatur:
- Abkühlungsrate:
- Umgebungsvariable: 

#### FTYPE 3
- solutionVector: [0.05018381160376206,0.009002388259322647,0.05062449319830575,0.9809244889884137,0.9755489570000773,0.05892642409030957,-0.9596922068449043,-0.03772706507392609,-0.0789895660021247,0.9983449729216947,-0.03781849895227385,0.030773651043841,-0.06688029067312007,0.9182102802127936,-0.05785499672560945,-0.9665045243739296,0.025523744439650997]
- isFeasible: TRUE
- resultValue: 12.950921004506665

- Iterationen: 500
- Temperatur: 5000
- Abkühlungsrate: 0,04
- Umgebungsvariable: 2 

#### FTYPE 4
- solutionVector: [-2.6469060169894876,-3.1956261243315773,-2.976817368061511,-2.9870911810653356,-3.1533244833024328,-2.9138846761187236,-2.877518708141165,-2.858613197492306,-2.785782174400741,-2.866353375208458,-2.9545475799845007,-2.8323305366817357,-2.9508945731400824,-3.110086613502869,-2.747140728069214,-2.806433711484728,-2.888090452736205]
- isFeasible: TRUE
- resultValue: -659.9424572688349

- Iterationen: 500
- Temperatur: 5000
- Abkühlungsrate: 0,05
- Umgebungsvariable: 5 

## Optimale Ergebnisse
#### FTYPE 1
- Rosenbrock Funktion
- Optimum: 0
- Wertebereich: -5 bis 5 

#### FTYPE 2
- Reichwald Funktion
- Optimum: unbekannt
- Wertebereich: -5 bis 5

#### FTYPE 3
- Rastrigin Funktion
- Optimum: 0
- Wertebereich: -5,12 bis 5,12

#### FTYPE 4
- Styblinski-Tang Funktion
- Optimum: -39,16617 * n < ... < -37,16616 * n
- Wertebereich: -5 bis 5 
