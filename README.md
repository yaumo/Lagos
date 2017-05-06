# Lagos - Optimierung industrieller Prozesse 
#### Nico Himmelein, Philipp Thiele, Lukas Freitag, Thomas Richter

## Vorstellung der Aufgabe
- https://www.dropbox.com/s/3j60eoze10fiawi/SimBasedOptim.pdf?dl=0

## Docker
- Aufsetzen der Infrastruktur: docker-compose up -d in dem Order /Lagos/Miscellaneous
- https://hub.docker.com/r/jreichwald/dhbw_oip/
- [docker-compose.yml](Miscellaneous/docker-compose.yml)
- RabbitMQ Dashboard
  - IP: http://127.0.0.1:15672
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
#### FTYPE 1
- solutionVector:
- isFeasible: 
- resultValue: 

- Iterationen:
- Temperatur:
- Abkühlungsrate:
- Umgebungsvariable: 

#### FTYPE 2
- solutionVector:
- isFeasible: 
- resultValue: 

- Iterationen:
- Temperatur:
- Abkühlungsrate:
- Umgebungsvariable: 

#### FTYPE 3
- solutionVector:
- isFeasible: 
- resultValue: 

- Iterationen:
- Temperatur:
- Abkühlungsrate:
- Umgebungsvariable: 

#### FTYPE 4
- solutionVector:
- isFeasible: 
- resultValue: 

- Iterationen:
- Temperatur:
- Abkühlungsrate:
- Umgebungsvariable: 

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
